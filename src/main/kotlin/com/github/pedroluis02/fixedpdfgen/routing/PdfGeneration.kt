package com.github.pedroluis02.fixedpdfgen.routing

import com.github.pedroluis02.fixedpdfgen.service.PdfGeneratorSampleService
import com.github.pedroluis02.fixedpdfgen.service.PdfTemplateGeneratorService
import com.github.pedroluis02.fixedpdfgen.template.PdfTemplateModel
import io.ktor.server.application.*
import io.ktor.server.request.*
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
