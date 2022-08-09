package com.example.musicapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.musicapp.ui.MediaListFragment
import com.example.musicapp.ui.PlayerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private val viewPagerAdapter = ViewPagerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById<ViewPager2>(R.id.view_pager)
        viewPager.adapter = viewPagerAdapter
    }

    fun navigate(fragmentId: Int) {
        viewPager.currentItem = if(fragmentId==R.layout.fragment_player)  0 else 1
    }

    fun changeMediaItem(id: Int){
        (viewPagerAdapter.getFragment(0) as PlayerFragment).playItemAt(id)
    }
}

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    val playerFragment = PlayerFragment()
    val mediaListFragment = MediaListFragment()

    override fun getItemCount(): Int {
        return 2
    }

    fun getFragment(position: Int): Fragment{
        return if(position==0) playerFragment else mediaListFragment
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) playerFragment
        else mediaListFragment
    }
}