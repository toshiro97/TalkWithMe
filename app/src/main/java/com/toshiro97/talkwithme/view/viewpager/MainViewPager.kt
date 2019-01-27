package com.toshiro97.talkwithme.view.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.toshiro97.talkwithme.view.fragment.ChatFragment
import com.toshiro97.talkwithme.view.fragment.HomeFragment
import com.toshiro97.talkwithme.view.fragment.SettingFragment

class MainViewPager(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }
    override fun getItem(position:Int): Fragment? {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = HomeFragment()
            1 -> fragment = ChatFragment()
            2 -> fragment = SettingFragment()
        }
        return fragment
    }
}