package com.github.pedroluis02.fixedpdfgen.template

import kotlinx.serialization.Serializable

@Serializable
data class PdfTemplateModel(
    val elements: List<TemplateElement>,
    val fontSize: Float? = null,
)