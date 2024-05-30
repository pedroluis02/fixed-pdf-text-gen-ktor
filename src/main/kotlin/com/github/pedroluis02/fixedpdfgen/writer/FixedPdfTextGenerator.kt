package com.github.pedroluis02.fixedpdfgen.writer

import com.lowagie.text.Document
import com.lowagie.text.Rectangle
import com.lowagie.text.pdf.BaseFont
import com.lowagie.text.pdf.PdfWriter
import java.io.*


abstract class FixedPdfTextGenerator(
    private val pageWidth: Float,
    private val pageHeight: Float,
    private val unitMeasure: UnitMeasure = UnitMeasure.POINTS
) {

    private lateinit var writer: PdfWriter

    private var baseFont: BaseFont = BaseFont.createFont()
    private var baseFontSize: Float = 8.0f

    fun generateFile(output: String): File {
        try {
            generate(FileOutputStream(output))
            return File(output)
        } catch (e: IOException) {
            throw PdfGenerationException(e.message)
        }
    }

    fun generateStream(): ByteArrayOutputStream {
        val stream = ByteArrayOutputStream()
        generate(stream)

        return stream
    }

    private fun generate(stream: OutputStream) {
        try {
            val size = Rectangle(unitMeasure.compute(pageWidth), unitMeasure.compute(pageHeight))
            Document(size).use { document ->
                writer = PdfWriter.getInstance(document, stream)
                document.open()

                baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED)

                onAddElements()
            }
        } catch (e: Exception) {
            throw PdfGenerationException(e.message)
        }
    }

    protected fun addFixedText(text: String, x: Float, y: Float, fontSize: Float = baseFontSize) {
        val cb = writer.directContent;

        val topY = pageHeight - y
        val pointX = unitMeasure.compute(x)
        val pointY = unitMeasure.compute(topY)

        cb.saveState();
        cb.beginText();
        cb.moveText(pointX, pointY);
        cb.setFontAndSize(baseFont, fontSize)
        cb.showText(text)
        cb.endText()
        cb.restoreState()
    }

    protected abstract fun onAddElements()
}