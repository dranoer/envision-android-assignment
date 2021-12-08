package com.dranoer.envision.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.dranoer.envision.databinding.ActivityMainBinding
import com.dranoer.envision.ui.capture.CapturedFragment
import com.dranoer.envision.ui.listener.TabListener
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var tabLayout: TabLayout

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

        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = pagerAdapter
        tabLayout = binding.tabLayout
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(1)!!.select()
    }

    override fun onBackPressed() {
        if (pagerAdapter.getItem(0) is CapturedFragment && tabLayout.selectedTabPosition == 0)
            pagerAdapter.openLibrary()
        else finish()
    }
}