package com.github.pedroluis02.fixedpdfgen.template

import kotlinx.serialization.Serializable

@Serializable
data class FixedPdfTemplateModel(
    val elements: List<TemplateElement>,
    val fontSize: Float? = null,
)