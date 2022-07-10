package com.jisoo.identityvalarmapp.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jisoo.identityvalarmapp.setting.SettingFragment
import com.jisoo.identityvalarmapp.main.charac_fragment.SurvivorFragment

class FragmentPagerAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    private val fragments: List<Fragment> =
        listOf(
            SurvivorFragment(),
            SettingFragment()
        )

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]

}