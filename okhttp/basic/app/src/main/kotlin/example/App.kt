package example

import okhttp3.OkHttpClient
import okhttp3.Request

fun main()
{
    val body = requestGet("http://example.com")
    System.out.println("body: $body")
}

fun requestGet(url: String): String? {
    val client = OkHttpClient()
    
    val request = Request.Builder()
        .url(url)
        .build()

    val response = try {
        client.newCall(request).execute()
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
    return response?.body?.string()
}
