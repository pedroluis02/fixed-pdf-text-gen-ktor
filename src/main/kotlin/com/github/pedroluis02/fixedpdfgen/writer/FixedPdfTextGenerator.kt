package com.github.pedroluis02.fixedpdfgen.writer

import com.lowagie.text.Document
import com.lowagie.text.PageSize
import com.lowagie.text.Rectangle
import com.lowagie.text.Utilities
import com.lowagie.text.pdf.BaseFont
import com.lowagie.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


abstract class FixedPdfTextGenerator(
    private val pageWidth: Float,
    private val pageHeight: Float
) {

    private companion object {
        const val MILLIMETERS_FACTOR_CM: Float = 10.0f
    }

    private lateinit var writer: PdfWriter

    private var baseFont: BaseFont = BaseFont.createFont()
    private var baseFontSize: Float = 8.0f

    fun generateFile(output: String) {
        try {
            generate(FileOutputStream(output))
        } catch (e: IOException) {
            throw FixedPdfGenerationException(e.message)
        }
    }

    fun generateStream(): ByteArrayOutputStream {
        val stream = ByteArrayOutputStream()
        generate(stream)

        return stream
    }

    private fun generate(stream: OutputStream) {
        try {
            PageSize.A4
            val size = Rectangle(centimetersToPoints(pageWidth), centimetersToPoints(pageHeight))
            Document(size).use { document ->
                writer = PdfWriter.getInstance(document, stream)
                document.open()

                baseFont = BaseFont.createFont(
                    BaseFont.HELVETICA,
                    BaseFont.CP1252,
                    BaseFont.EMBEDDED
                )

                onAddElements()
            }
        } catch (e: Exception) {
            throw FixedPdfGenerationException(e.message)
        }
    }

    protected fun addFixedText(text: String, x: Float, y: Float, fontSize: Float = baseFontSize) {
        val cb = writer.directContent;

        val topY = pageHeight - y
        val pointX = centimetersToPoints(x)
        val pointY = centimetersToPoints(topY)

        cb.saveState();
        cb.beginText();
        cb.moveText(pointX, pointY);
        cb.setFontAndSize(baseFont, fontSize)
        cb.showText(text)
        cb.endText()
        cb.restoreState()
    }

    private fun centimetersToPoints(value: Float): Float {
        return Utilities.millimetersToPoints(centimetersToMillimeters(value))
    }

    private fun centimetersToMillimeters(value: Float): Float {
        return MILLIMETERS_FACTOR_CM * value
    }

    protected abstract fun onAddElements()
}