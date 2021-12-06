package com.dranoer.envision.ui.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dranoer.envision.databinding.FragmentCaptureBinding

class CaptureFragment : Fragment() {
    private var _binding: FragmentCaptureBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCaptureBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}