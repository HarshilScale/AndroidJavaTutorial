package com.demo.androidjavatutorial.ChipTagView.TagView;

import android.text.TextPaint
import android.text.style.MetricAffectingSpan

internal class DummySpan private constructor() : MetricAffectingSpan() {
    override fun updateMeasureState(textPaint: TextPaint) {}
    override fun updateDrawState(tp: TextPaint) {}

    companion object {
        val INSTANCE = DummySpan()
    }
}