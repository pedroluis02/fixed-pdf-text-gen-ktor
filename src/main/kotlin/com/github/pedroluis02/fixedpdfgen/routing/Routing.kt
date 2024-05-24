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
            try {
                val generator = PdfGeneratorSampleService()
                generator.generateFile(file.path)

                call.response.header(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.Inline.withParameter(
                        ContentDisposition.Parameters.FileName, file.name
                    ).toString()
                )

                call.respondFile(file)
            } catch (ex: Exception) {
                call.respondText(ex.message ?: "", status = HttpStatusCode.InternalServerError)
            } finally {
                if (file.exists()) {
                    file.delete()
                }
            }
        }
    }
}
