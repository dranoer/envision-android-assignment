package com.dranoer.envision.ui.capture

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dranoer.envision.Constants
import com.dranoer.envision.databinding.FragmentCapturedBinding
import com.dranoer.envision.ui.MainActivity
import com.dranoer.envision.ui.SharedViewModel
import com.dranoer.envision.ui.listener.NavigationListener
import com.dranoer.envision.ui.listener.TabListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CapturedFragment : Fragment() {
    private var _binding: FragmentCapturedBinding? = null
    private val binding get() = _binding!!

    var tabsListener: TabListener? = null
    var navigationListener: NavigationListener? = null

    val viewModel: SharedViewModel by viewModels()

    private var params: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { params = it.getString(Constants.ARG_PARAGRAPH) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCapturedBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.paragraph.text = params

        binding.saveButton.setOnClickListener {
            params?.let { it1 -> viewModel.saveParagraph(it1) }

            with(binding.saveButton) {
                isEnabled = true
                setBackgroundColor(Color.GRAY)
            }
            (requireActivity() as MainActivity).openSnack()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(
            tabListener: TabListener,
            navigationListener: NavigationListener,
            paragraphs: String,
        ) =
            CapturedFragment().apply {
                arguments = Bundle().apply { putString(Constants.ARG_PARAGRAPH, paragraphs) }
                this.tabsListener = tabListener
                this.navigationListener = navigationListener
            }
    }
}