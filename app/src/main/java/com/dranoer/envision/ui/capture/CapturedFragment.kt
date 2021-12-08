package com.dranoer.envision.ui.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dranoer.envision.databinding.FragmentCapturedBinding
import com.dranoer.envision.ui.listener.NavigationListener
import com.dranoer.envision.ui.listener.TabListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CapturedFragment : Fragment() {
    private var _binding: FragmentCapturedBinding? = null
    private val binding get() = _binding!!

    var tabsListener: TabListener? = null
    var navigationListener: NavigationListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCapturedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(
            tabListener: TabListener,
            navigationListener: NavigationListener,
        ) =
            CapturedFragment().apply {
                this.tabsListener = tabListener
                this.navigationListener = navigationListener
            }
    }
}