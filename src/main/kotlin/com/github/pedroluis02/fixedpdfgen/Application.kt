package com.github.pedroluis02.fixedpdfgen

import com.github.pedroluis02.fixedpdfgen.plugins.configureSerialization
import com.github.pedroluis02.fixedpdfgen.routing.configurePdfGenerationRouting
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configurePdfGenerationRouting()
}
