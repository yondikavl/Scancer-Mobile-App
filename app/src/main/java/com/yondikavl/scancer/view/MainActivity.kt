package com.yondikavl.scancer.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.yondikavl.scancer.R
import com.yondikavl.scancer.databinding.ActivityMainBinding
import com.yondikavl.scancer.helper.ImageClassifierHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentImageUri: Uri? = null
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        imageClassifierHelper = ImageClassifierHelper(this)

        binding.galleryButton.setOnClickListener {
            startGallery()
        }

        binding.analyzeButton.setOnClickListener {
            currentImageUri?.let { uri ->
                analyzeImage(uri)
            } ?: showToast("Please select an image first.")
        }
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                currentImageUri = uri
                binding.previewImageView.setImageURI(uri)
            }
        }
    }

    private fun analyzeImage(uri: Uri) {
        val result = imageClassifierHelper.classifyStaticImage(uri)
        val (label, confidence) = result
        val message = "Label: $label\nConfidence: ${(confidence * 100).toInt()}%"
        showToast(message)
        moveToResult(label, confidence)
    }

    private fun moveToResult(label: String, confidence: Float) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("imageUri", currentImageUri.toString())
        intent.putExtra("label", label)
        intent.putExtra("confidence", confidence)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        imageClassifierHelper.close()
    }
}
