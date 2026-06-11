package com.example.com

import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.*
import io.ktor.http.contentType
import io.ktor.client.statement.bodyAsText

class ServerTest {

    @Test
    fun `test root endpoint`() = testApplication {
        // loads default configuration
        configure()

        val response = client.get("/test1")
        
        // verify server root returns 200
        assertEquals(HttpStatusCode.OK, client.get("/").status)
        assertEquals("html", response.contentType()?.contentSubtype)
        assertContains(response.bodyAsText(), "Hello From Ktor")
    }

}
