package com.github.pedroluis02.fixedpdfgen.service

import com.github.pedroluis02.fixedpdfgen.template.PdfTemplateModel
import com.github.pedroluis02.fixedpdfgen.template.TemplateElement
import com.github.pedroluis02.fixedpdfgen.writer.FixedPdfTextGenerator
import com.github.pedroluis02.fixedpdfgen.writer.UnitMeasure

class PdfTemplateGeneratorService(private val model: PdfTemplateModel) :
    FixedPdfTextGenerator(model.width, model.height, UnitMeasure.CENTIMETERS) {

    override fun onAddElements() {
        if (model.fontSize != null) {
            baseFontSize = model.fontSize
        }
        model.elements.forEach(this::addElement)
    }

    private fun addElement(element: TemplateElement) {
        addFixedText(element.text, element.x, element.y, element.fontSize)
    }
}
