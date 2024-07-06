package com.google.mlkit.vision.demo.kotlin.textdetector

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.demo.GraphicOverlay
import com.google.mlkit.vision.demo.kotlin.StrainDatabase
import com.google.mlkit.vision.demo.kotlin.VisionProcessorBase
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class StrainRecognitionProcessor(context: Context) : VisionProcessorBase<Text>(context) {

    private val textRecognizer: TextRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    override fun stop() {
        super.stop()
        textRecognizer.close()
    }

    override fun detectInImage(image: InputImage): Task<Text> {
        return textRecognizer.process(image)
    }

    override fun onSuccess(results: Text, graphicOverlay: GraphicOverlay) {
        Log.d(TAG, "On-device Strain recognition successful")

        for (textBlock in results.textBlocks) {
            for (line in textBlock.lines) {
                val strainInfo = StrainDatabase.findStrain(line.text)
                if (strainInfo != null) {
                    graphicOverlay.add(StrainInfoGraphic(graphicOverlay, strainInfo, line.boundingBox))
                }
            }
        }

        graphicOverlay.add(
            TextGraphic(
                graphicOverlay,
                results,
                shouldGroupTextInBlocks = false,
                showLanguageTag = false,
                showConfidence = false
            )
        )
    }

    override fun onFailure(e: Exception) {
        Log.w(TAG, "Strain Recognition failed: $e")
    }

    companion object {
        private const val TAG = "StrainRecProcessor"
    }
}