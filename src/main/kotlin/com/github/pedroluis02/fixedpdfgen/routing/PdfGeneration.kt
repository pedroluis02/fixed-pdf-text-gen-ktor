package com.github.pedroluis02.fixedpdfgen.routing

import com.github.pedroluis02.fixedpdfgen.service.PdfGeneratorSampleService
import com.github.pedroluis02.fixedpdfgen.service.PdfTemplateGeneratorService
import com.github.pedroluis02.fixedpdfgen.template.PdfTemplateModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configurePdfGenerationRouting() {
    routing {
        route("/api/v1/pdf-generation") {
            post("/sample") {
                val file = File("sample-pdf-${System.currentTimeMillis()}.pdf")
                respondInlineFile(call) { PdfGeneratorSampleService().generateFile(file.path) }
            }

            post("/template") {
                val model = call.receive<PdfTemplateModel>()
                val file = File("fixed-pdf-${System.currentTimeMillis()}.pdf")
                respondInlineFile(call) { PdfTemplateGeneratorService(model).generateFile(file.path) }
            }
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
