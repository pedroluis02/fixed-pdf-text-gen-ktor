package com.github.pedroluis02.fixedpdfgen.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureGeneralRouting() {
    routing {
        get("/api/v1/info") {
            call.respond(
                HttpStatusCode.OK,
                mapOf(
                    "name" to "fixed-pdf-text-gen-ktor",
                    "version" to "0.0.1"
                )
            )
        }
    }
}
