package com.dranoer.envision.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.dranoer.envision.R
import com.dranoer.envision.databinding.ActivityMainBinding
import com.dranoer.envision.ui.capture.CapturedFragment
import com.dranoer.envision.ui.listener.TabListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Setup tabs
        pagerAdapter =
            ViewPagerAdapter(
                fragmentManager = supportFragmentManager,
                context = this,
                tabListener = object : TabListener {
                    override fun onChangeTab() {
                        tabLayout.getTabAt(1)!!.select()
                    }
                })

        viewPager = binding.viewPager
        viewPager.adapter = pagerAdapter
        tabLayout = binding.tabLayout
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(1)!!.select()
    }

    override fun onBackPressed() {
        if (pagerAdapter.getItem(0) is CapturedFragment && tabLayout.selectedTabPosition == 0)
            pagerAdapter.backToCapture()
        else finish()
    }

    fun openSnack() {
        val snackbar =
            Snackbar.make(binding.root, getString(R.string.text_saved), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.go_to_library)) { viewPager.currentItem = 1 }

        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.secondary))
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.WHITE)
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.grey))
        textView.textSize = 14f

        snackbar.show()
    }
}