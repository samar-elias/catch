package com.hudhudit.catchapp.apputils.helpers

import android.os.Handler
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class Helpers {
    companion object{
        fun setSliderTimer(delay: Int, viewPager: ViewPager, pagerAdapter: PagerAdapter) {
            val handler = Handler()
            val runnable = arrayOfNulls<Runnable>(1)
            handler.postDelayed(object : Runnable {
                override fun run() {
                    var pagerIndex = viewPager.currentItem
                    pagerIndex++
                    if (pagerIndex >= pagerAdapter.count) {
                        pagerIndex = 0
                    }
                    pagerAdapter.notifyDataSetChanged()
                    viewPager.currentItem = pagerIndex
                    pagerAdapter.notifyDataSetChanged()
                    runnable[0] = this
                    handler.postDelayed(runnable[0]!!, delay.toLong())
                }
            }, delay.toLong())
        }
    }
}