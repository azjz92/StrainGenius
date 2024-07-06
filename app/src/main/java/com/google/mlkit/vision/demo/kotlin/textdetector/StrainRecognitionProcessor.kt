package com.google.mlkit.vision.demo.kotlin.textdetector //btw, spinning some bach well tempered clavier while im coding this, 10/10 would recommend ;)

import android.content.Context
import android.util.Log
import com.google.mlkit.vision.demo.GraphicOverlay
import com.google.mlkit.vision.demo.kotlin.StrainDatabase
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognizerOptions

class StrainRecognitionProcessor(context: Context, options: TextRecognizerOptions) :
    TextRecognitionProcessor(context, options) {

    override fun onSuccess(text: Text, graphicOverlay: GraphicOverlay) {
        Log.d(TAG, "On-device Strain recognition successful")

        for (textBlock in text.textBlocks) {
            for (line in textBlock.lines) {
                val strainInfo = StrainDatabase.findStrain(line.text)
                if (strainInfo != null) {
                    graphicOverlay.add(StrainInfoGraphic(graphicOverlay, strainInfo, line.boundingBox))
                }
            }
        }

        // Keep the existing code for drawing text blocks
        graphicOverlay.add(
            TextGraphic(
                graphicOverlay,
                text,
                shouldGroupRecognizedTextInBlocks,
                showLanguageTag,
                showConfidence
            )
        )
    }

    companion object {
        private const val TAG = "StrainRecProcessor"
    }
}
