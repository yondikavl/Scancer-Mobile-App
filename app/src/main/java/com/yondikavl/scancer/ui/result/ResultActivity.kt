package com.yondikavl.scancer.ui.result

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yondikavl.scancer.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUriString = arguments?.getString("imageUri")
        val label = arguments?.getString("label") ?: "Unknown"
        val confidence = arguments?.getFloat("confidence") ?: 0f

        val imageUri = Uri.parse(imageUriString)
        binding.resultImage.setImageURI(imageUri)

        val resultText = "Label: $label\nConfidence: ${(confidence * 100).toInt()}%"
        binding.resultText.text = resultText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(imageUri: String, label: String, confidence: Float): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putString("imageUri", imageUri)
            args.putString("label", label)
            args.putFloat("confidence", confidence)
            fragment.arguments = args
            return fragment
        }
    }
}
