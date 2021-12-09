package com.dranoer.envision.ui.capture

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dranoer.envision.Constants
import com.dranoer.envision.R
import com.dranoer.envision.databinding.FragmentCapturedBinding
import com.dranoer.envision.ui.listener.NavigationListener
import com.dranoer.envision.ui.listener.TabListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CapturedFragment : Fragment() {
    private var _binding: FragmentCapturedBinding? = null
    private val binding get() = _binding!!

    var tabsListener: TabListener? = null
    var navigationListener: NavigationListener? = null

    val viewModel: CaptureViewModel by viewModels()

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
            openSnack(view)
        }

        return view
    }

    fun openSnack(view: View) {
        val snackbar =
            Snackbar.make(view, getString(R.string.text_saved), Snackbar.LENGTH_INDEFINITE)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.WHITE)
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.DKGRAY)
        textView.textSize = 14f

        snackbar.setActionTextColor(Color.MAGENTA)
        snackbar.setAction(resources.getString(R.string.go_to_library)) {
            navigationListener!!.openLibrary()
        }

        snackbar.show()
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