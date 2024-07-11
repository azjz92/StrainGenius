package com.google.mlkit.vision.demo.kotlin.textdetector
/**
 * StrainInfoGraphic.kt
 *
 * This file contains the StrainInfoGraphic class, which is responsible for rendering
 * visual information about recognized cannabis strains on top of the camera preview.
 *
 * Key functionalities:
 * 1. Draws a bounding box around the recognized strain name in the camera view.
 * 2. Displays the strain name, THC content, and rating near the recognized text.
 * 3. Uses the GraphicOverlay to ensure proper positioning of graphics relative to the camera preview.
 *
 * The class extends GraphicOverlay.Graphic and overrides the draw() method to perform custom drawing.
 *
 * Usage:
 * This graphic is typically added to a GraphicOverlay when a strain is recognized in the
 * StrainRecognitionProcessor. The overlay then calls the draw() method to render the graphic.
 *
 *  overlay The GraphicOverlay this graphic is added to
 *  strainInfo The StrainInfo object containing details about the recognized strain
 *  boundingBox The Rect object representing the area where the strain name was recognized
 */
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
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
