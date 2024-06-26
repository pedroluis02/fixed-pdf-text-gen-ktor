package com.github.pedroluis02.fixedpdfgen.writer

fun interface MeasureFunction {
    fun compute(value: Float): Float
}