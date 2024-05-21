package com.yondikavl.scancer.view

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yondikavl.scancer.R
import com.yondikavl.scancer.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val imageUriString = intent.getStringExtra("imageUri")
        val label = intent.getStringExtra("label") ?: "Unknown"
        val confidence = intent.getFloatExtra("confidence", 0f)

        val imageUri = Uri.parse(imageUriString)
        binding.resultImage.setImageURI(imageUri)

        val resultText = "Label: $label\nConfidence: ${(confidence * 100).toInt()}%"
        binding.resultText.text = resultText
    }
}