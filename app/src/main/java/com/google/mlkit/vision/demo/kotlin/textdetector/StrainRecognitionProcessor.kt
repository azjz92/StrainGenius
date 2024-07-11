

package com.google.mlkit.vision.demo.kotlin.textdetector
// /**
// * StrainRecognitionProcessor.kt
// *
// * This file contains the StrainRecognitionProcessor class, which processes images to recognize
// * cannabis strain names and match them with a database of known strains.
// *
// * Key functionalities:
// * 1. Utilizes ML Kit's text recognition to detect text in camera frames.
// * 2. Matches recognized text against a database of cannabis strains.
// * 3. Creates and adds StrainInfoGraphic objects to the GraphicOverlay for visual feedback.
// *
// * The class extends VisionProcessorBase<Text> and overrides methods to handle the text recognition
// * process and results.
// *
// * Usage:
// * This processor is typically set as the image processor in the CameraXLivePreviewActivity
// * when the user selects the strain recognition option.
// *
// * @property context The application context
// * @property textRecognizer The ML Kit TextRecognizer instance used for text detection
// */
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
        Log.d(TAG, "Recognized text: ${results.text}")
        /**
         * Strain Recognition and Visualization Process:
         * 1. Iterate through recognized text blocks and lines.
         * 2. For each line, attempt to match with known strains.
         * 3. If matched, create a StrainInfoGraphic object:
         *    - Acts as a node in a dynamic visual linked list.
         *    - Encapsulates strain data and drawing logic.
         * 4. Add StrainInfoGraphic to GraphicOverlay:
         *    - GraphicOverlay behaves like a spatial hash table.
         *    - Efficiently manages and renders multiple graphics.
         * 5. GraphicOverlay handles drawing cycle, calling each graphic's draw method.
         */
        for (textBlock in results.textBlocks) {
            for (line in textBlock.lines) {
                Log.d(TAG, "Checking line: ${line.text}")
                val strainInfo = StrainDatabase.findStrain(line.text)
                if (strainInfo != null) {
                    Log.d(TAG, "Matched strain: ${strainInfo.name}")
                    graphicOverlay.add(StrainInfoGraphic(graphicOverlay, strainInfo, line.boundingBox)) //
                } else {
                    Log.d(TAG, "No matching strain found for: ${line.text}")
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