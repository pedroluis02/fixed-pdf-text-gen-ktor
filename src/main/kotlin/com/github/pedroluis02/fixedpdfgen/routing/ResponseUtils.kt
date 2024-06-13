package com.github.pedroluis02.fixedpdfgen.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.io.File

suspend fun respondInlineFile(call: ApplicationCall, generationFun: () -> File) {
    var file: File? = null
    try {
        file = generationFun()

        call.response.header(
            HttpHeaders.ContentDisposition,
            ContentDisposition.Inline.withParameter(
                ContentDisposition.Parameters.FileName, file.name
            ).toString()
        )

        call.respondFile(file)
    } catch (ex: Exception) {
        call.respond(HttpStatusCode.InternalServerError, ex.message ?: "")
    } finally {
        if (file != null && file.exists()) {
            file.delete()
        }
    }
}