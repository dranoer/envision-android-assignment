package com.dranoer.envision.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dranoer.envision.Constants.NUM_TABS
import com.dranoer.envision.R
import com.dranoer.envision.ui.capture.CaptureFragment
import com.dranoer.envision.ui.capture.CapturedFragment
import com.dranoer.envision.ui.library.LibraryFragment
import com.dranoer.envision.ui.listener.NavigationListener
import com.dranoer.envision.ui.listener.TabListener

private val tabArray = arrayOf(
    R.string.capture,
    R.string.library,
)

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val context: Context,
    private val tabListener: TabListener
) :
    FragmentStatePagerAdapter(fragmentManager),
    NavigationListener {

    private var fragment: Fragment? = null

    override fun getCount(): Int {
        return NUM_TABS
    }

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            if (fragment == null) {
                fragment = CaptureFragment.newInstance(this)
            }
            return fragment!!
        } else return LibraryFragment.newInstance()
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(tabArray[position])
    }

    override fun getItemPosition(`object`: Any): Int {
        return if (`object` is CaptureFragment && fragment is CapturedFragment)
            POSITION_NONE
        else if (`object` is CapturedFragment && fragment is CaptureFragment)
            POSITION_NONE
        else POSITION_UNCHANGED
    }

    override fun openCaptured(paragraphs: String) {
        fragment = CapturedFragment.newInstance(
            tabListener = tabListener, navigationListener = this, paragraphs = paragraphs
        )
        notifyDataSetChanged()
    }

    override fun backToCapture() {
        fragment = CaptureFragment.newInstance(this)
        notifyDataSetChanged()
    }
}