package com.yondikavl.scancer.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yondikavl.scancer.MainActivity
import com.yondikavl.scancer.R
import com.yondikavl.scancer.databinding.FragmentHomeBinding
import com.yondikavl.scancer.helper.ImageClassifierHelper
import com.yondikavl.scancer.ui.result.ResultFragment

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageClassifierHelper = ImageClassifierHelper(requireContext())

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
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
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
        val imageUriString = currentImageUri.toString()
        (requireActivity() as MainActivity).showResultFragment(imageUriString, label, confidence)
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        imageClassifierHelper.close()
    }
}
