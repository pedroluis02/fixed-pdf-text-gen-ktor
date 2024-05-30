package com.github.pedroluis02.fixedpdfgen.routing

import com.github.pedroluis02.fixedpdfgen.service.PdfGeneratorSampleService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/pdf-sample") {
            val file = File("sample-pdf-${System.currentTimeMillis()}.pdf")
            respondInlineFile(call) { PdfGeneratorSampleService().generateFile(file.path) }
        }
    }
}

private suspend fun respondInlineFile(call: ApplicationCall, generationFun: () -> File) {
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
