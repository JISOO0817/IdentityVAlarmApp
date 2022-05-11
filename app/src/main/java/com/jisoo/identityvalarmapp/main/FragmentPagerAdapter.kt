package com.jisoo.identityvalarmapp.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jisoo.identityvalarmapp.main.fragment.HunterFragment
import com.jisoo.identityvalarmapp.main.fragment.SurvivorFragment

class FragmentPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    val fragments: List<Fragment>
    init {
        fragments = listOf(SurvivorFragment(),HunterFragment())
    }

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]

}