package com.hudhudit.catchapp.core.base

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

interface OnItemTouchListenerAdapter : RecyclerView.OnItemTouchListener {

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }
}