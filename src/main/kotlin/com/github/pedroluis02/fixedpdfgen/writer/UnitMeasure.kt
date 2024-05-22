package com.github.pedroluis02.fixedpdfgen.writer

import com.lowagie.text.Utilities

enum class UnitMeasure {
    POINTS {
        override fun compute(value: Float) = value
    },
    INCHES {
        override fun compute(value: Float) = Utilities.inchesToPoints(value)
    },
    CENTIMETERS {
        override fun compute(value: Float) = MILLIMETERS.compute(10f * value)
    },
    MILLIMETERS {
        override fun compute(value: Float) = Utilities.millimetersToPoints(value)
    };

    abstract fun compute(value: Float): Float
}