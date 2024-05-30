package com.github.pedroluis02.fixedpdfgen.template

import kotlinx.serialization.Serializable

@Serializable
data class TemplateElement(
    val text: String,
    val x: Float,
    val y: Float,
    val fontSize: Float? = null
)