package com.hudhudit.catchapp.views.introduction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.apputils.models.introduction.Intro

class IntroAdapter(contents: ArrayList<Intro>, introductionActivity: IntroductionActivity) :
    PagerAdapter() {
    private val contents: ArrayList<Intro>
    private val introductionActivity: IntroductionActivity
    override fun getCount(): Int {
        return contents.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater =
            introductionActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.intro_slider_layout, container, false)
        container.addView(view)
        val title = view.findViewById<TextView>(R.id.slider_title)
        val icon = view.findViewById<ImageView>(R.id.slider_image)
        val description = view.findViewById<TextView>(R.id.slider_description)

        if (contents[position].image.isNotEmpty()){
            Glide.with(view).load(contents[position].image).into(icon)
        }
        title.text = contents[position].title
        description.text = contents[position].description
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    init {
        this.contents = contents
        this.introductionActivity = introductionActivity
    }

}
