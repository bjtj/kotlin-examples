package com.example

import io.ktor.network.tls.certificates.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.*
import java.io.File
import java.io.FileInputStream
import java.security.KeyStore
import java.security.MessageDigest
import java.security.cert.X509Certificate
import java.util.Base64

fun main(args: Array<String>) {
    System.out.println("tls server example")
    embeddedServer(
        factory = Netty,
        environment = applicationEnvironment {
            log = LoggerFactory.getLogger("ktor.application")
        },
        configure = {
            envConfig()
        },
        module = Application::configureRouting
    ).start(wait = true)
    System.out.println("DONE.")
}

private fun ApplicationEngine.Configuration.envConfig() {
    val keyStoreFile = File("build/keystore.jks")
    val keyStore = if (!keyStoreFile.exists()) {
        buildKeyStore {
            certificate("sampleAlias") {
                password = "foobar"
                domains = listOf("127.0.0.1", "0.0.0.0", "localhost")
            }
        }.also {
            it.saveToFile(keyStoreFile, "123456")
        }
    } else {
        KeyStore.getInstance("JKS").also { store ->
            FileInputStream(keyStoreFile).use { stream ->
                store.load(stream, "123456".toCharArray())
            }
        }
    }

    val alias = keyStore.aliases().toList().first()
    val cert = keyStore.getCertificate(alias) as X509Certificate
    val pin = Base64.getEncoder().encodeToString(
        MessageDigest.getInstance("SHA-256")
            .digest(cert.publicKey.encoded)
    )
    System.out.println("pinned pubkey:")
    System.out.println("")
    System.out.println("sha256//$pin")
    System.out.println("")

    connector {
        port = 8080
    }
    sslConnector(
        keyStore = keyStore,
        keyAlias = "sampleAlias",
        keyStorePassword = { "123456".toCharArray() },
        privateKeyPassword = { "foobar".toCharArray() }) {
        port = 8443
        keyStorePath = keyStoreFile
    }
}
