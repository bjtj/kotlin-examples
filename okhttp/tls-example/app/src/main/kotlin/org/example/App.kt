package org.example

import okhttp3.OkHttpClient
import okhttp3.CertificatePinner
import okhttp3.Request
import okhttp3.tls.HandshakeCertificates
import java.net.URI
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.Base64
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import javax.net.ssl.X509TrustManager
import javax.net.ssl.TrustManager

fun String?.notEmpty(): String? {
    if (!isNullOrEmpty()) {
        return this
    } else {
        return null
    }
}

fun main() {

    requestGet("https://example.com")?.let {
        println("body: $it")
    }

    // certificate pinning w/ self-signed certs
    System.getProperty("pinnedpubkey").notEmpty()?.let {
        requestGet(
            url = "https://localhost:8443",
            pinnedPubkey = it
        )?.let {
            println("body: $it")
        }
    }
}

fun requestGet(url: String, pinnedPubkey: String? = null): String? {
    val uri = URI(url)
    val hostname = uri.host
    val client = OkHttpClient.Builder()
        .hostnameVerifier { _, _ -> true }
        .apply {
            if (pinnedPubkey != null) {
                applyCertPin(this, setOf(pinnedPubkey))
            }
        }
        .build()

    val request = Request.Builder()
        .url(url)
        .build()

    val response = try {
        client.newCall(request).execute().also {
            println("protocol: ${it.protocol}")
            println("handshake: ${it.handshake}")
            println("peer certificates size: ${it.handshake?.peerCertificates?.size}")
        }
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
    return response?.body?.string()
}

fun X509Certificate.sha256Pin(): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val pubKeyHash = digest.digest(publicKey.encoded)
    return "sha256/${Base64.getEncoder().encodeToString(pubKeyHash)}"
}

fun applyCertPin(builder: OkHttpClient.Builder, allowedPins: Set<String>) {
    val trustAllCerts = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            chain.forEach { cert ->
                println("Subject: ${cert.subjectX500Principal}")
                println("Pin: ${cert.sha256Pin()}")
            }

            val matched = chain.any { cert -> cert.sha256Pin() in allowedPins }
            if (!matched) {
                throw java.security.cert.CertificateException(
                    "Certificate pinning failure!\n" +
                        "Expected: $allowedPins\n" +
                        "Got: ${chain.map { it.sha256Pin() }}"
                )
            }
        }
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }

    val sslContext = SSLContext.getInstance("TLS").apply {
        init(null, arrayOf<TrustManager>(trustAllCerts), SecureRandom())
    }
    
    val certs = HandshakeCertificates.Builder()
        .addInsecureHost("*")
        .build()
    builder.sslSocketFactory(sslContext.socketFactory, trustAllCerts)
}
