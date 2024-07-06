package com.google.mlkit.vision.demo.kotlin.textdetector

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import com.google.mlkit.vision.demo.GraphicOverlay
import com.google.mlkit.vision.demo.kotlin.StrainInfo

class StrainInfoGraphic(
    overlay: GraphicOverlay,
    private val strainInfo: StrainInfo,
    private val boundingBox: Rect?
) : GraphicOverlay.Graphic(overlay) {

    private val rectPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = STROKE_WIDTH
    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = TEXT_SIZE
    }

    override fun draw(canvas: Canvas) {
        boundingBox?.let { rect ->
            canvas.drawRect(rect, rectPaint)
            canvas.drawText(strainInfo.name, rect.left.toFloat(), rect.bottom + TEXT_SIZE, textPaint)
            canvas.drawText("THC: ${strainInfo.thc}", rect.left.toFloat(), rect.bottom + TEXT_SIZE * 2, textPaint)
            canvas.drawText("Rating: ${strainInfo.rating}", rect.left.toFloat(), rect.bottom + TEXT_SIZE * 3, textPaint)
        }
    }

    companion object {
        private const val TEXT_SIZE = 54.0f
        private const val STROKE_WIDTH = 4.0f
    }
}
