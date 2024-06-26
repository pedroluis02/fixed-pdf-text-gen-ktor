package com.github.pedroluis02.fixedpdfgen.template

import com.github.pedroluis02.fixedpdfgen.writer.UnitMeasure

enum class TemplateUnitMeasure {
    px, inch, cm, mm;

    fun toUnitMeasure(): UnitMeasure {
        return when (this) {
            inch -> UnitMeasure.INCHES
            cm -> UnitMeasure.CENTIMETERS
            mm -> UnitMeasure.MILLIMETERS
            px -> UnitMeasure.POINTS
        }
    }
}