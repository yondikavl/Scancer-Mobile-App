package com.yondikavl.scancer.helper

import android.content.Context
import android.net.Uri
import com.yondikavl.scancer.Utils
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.classifier.ImageClassifier

class ImageClassifierHelper(private val context: Context) {
    private lateinit var imageClassifier: ImageClassifier

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        imageClassifier = ImageClassifier.createFromFile(context, "cancer_classification.tflite")
    }

    fun classifyStaticImage(imageUri: Uri): Pair<String, Float> {
        val bitmap = Utils.getBitmapFromUri(context, imageUri)
        val image = TensorImage.fromBitmap(bitmap)
        val classifications = imageClassifier.classify(image)

        // Get the highest classification result
        val result = classifications[0]

        val label = result.categories[0].label
        val score = result.categories[0].score

        return Pair(label, score)
    }

    fun close() {
        imageClassifier.close()
    }
}