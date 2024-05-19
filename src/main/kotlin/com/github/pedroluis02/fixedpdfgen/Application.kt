package com.github.pedroluis02.fixedpdfgen

import com.github.pedroluis02.fixedpdfgen.plugins.configureRouting
import com.github.pedroluis02.fixedpdfgen.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
