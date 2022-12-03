package com.hudhud.eva.core

import android.content.Context
import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.hudhudit.catchapp.core.base.OnItemTouchListenerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.ChipGroup


const val CLICK_DELAY = 1000L

val Context.statusBarHeight: Int
    get() {
        var result = 0
        val resourceId = resources.getIdentifier(
            "status_bar_height",
            "dimen",
            "android"
        )
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

inline fun MenuItem.onClick(crossinline callback: (MenuItem) -> Unit) {
    setOnMenuItemClickListener { menu ->
        callback(menu)
        return@setOnMenuItemClickListener true
    }
}

fun View.onClick(callback: (View) -> Unit) {
    setOnClickListener(callback)
}

fun ChipGroup.onChecked(callback: (checkedId: Int) -> Unit) {
    setOnCheckedChangeListener { _, checkedId ->
        callback(checkedId)
    }
}

fun RadioGroup.onChecked(callback: (checkedId: Int) -> Unit) {
    setOnCheckedChangeListener { _, checkedId ->
        callback(checkedId)
    }
}

fun View?.isVisible(): Boolean {
    return this?.visibility == View.VISIBLE
}

fun View.show() {
    post {
        visibility = View.VISIBLE
    }
}

fun View.gone() {
    post {
        visibility = View.GONE
    }
}

fun View.invisible() {
    post {
        visibility = View.INVISIBLE
    }
}

fun View.trueOf() {
    post {
        isEnabled = false
    }
}
fun View.trueOn() {
    post {
        isEnabled = true
    }
}



fun RecyclerView.handleNestedRecyclerTouch() {
    addOnItemTouchListener(object : OnItemTouchListenerAdapter {

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            if (e.action == MotionEvent.ACTION_DOWN && rv.scrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                rv.stopScroll()
            }
            return false
        }
    })
}

fun <T : View> BottomSheetBehavior<T>.isCollapsed(): Boolean {
    return state == BottomSheetBehavior.STATE_COLLAPSED
}

fun <T : View> BottomSheetBehavior<T>.isHidden(): Boolean {
    return state == BottomSheetBehavior.STATE_HIDDEN
}

fun <T : View> BottomSheetBehavior<T>.isExpanded(): Boolean {
    return state == BottomSheetBehavior.STATE_EXPANDED
}

inline fun EditText.onTextChanged(crossinline callback: (text: CharSequence?) -> Unit) {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            callback(s)
        }
    }
    addTextChangedListener(textWatcher)
}

inline fun EditText.onDoneClicked(crossinline callback: () -> Boolean) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            return@setOnEditorActionListener callback()
        }
        false
    }
}

fun EditText.addCompoundDrawables(
    @DrawableRes leftDrawableId: Int? = null,
    @DrawableRes topDrawableId: Int? = null,
    @DrawableRes rightDrawableId: Int? = null,
    @DrawableRes bottomDrawableId: Int? = null
) {
    this.setCompoundDrawablesWithIntrinsicBounds(
        if (leftDrawableId == null) null else this.context.getDrawable(leftDrawableId),
        if (topDrawableId == null) null else this.context.getDrawable(topDrawableId),
        if (rightDrawableId == null) null else this.context.getDrawable(rightDrawableId),
        if (bottomDrawableId == null) null else this.context.getDrawable(bottomDrawableId)
    )
}

fun EditText.removeCompoundDrawables() {
    this.addCompoundDrawables()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    this.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

inline fun AutoCompleteTextView.onItemSelected(crossinline callback: (position: Int) -> Unit) {
    setOnItemClickListener { _, _, position, _ -> callback(position) }
}

inline fun ViewPager.onPageChanged(crossinline callback: (position: Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            callback(position)
        }
    })
}

inline fun ViewPager.onPageScrolled(crossinline callback: (position: Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            callback(position)
        }

        override fun onPageSelected(position: Int) {
        }
    })
}

fun ViewPager.goToNextPage() {
    if (currentItem < (adapter?.count ?: 0)) {
        currentItem = currentItem.plus(1)
    }
}

fun ViewPager.goToPreviousPage() {
    if (currentItem > 0) {
        currentItem = currentItem.minus(1)
    }
}




fun resetEditText(view: EditText) {
    view.text = null
}



inline fun TextView.afterTextChanged(crossinline afterTextChanged: (String) -> Unit) {

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }
    })
}

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()






fun EditText.setOnDrawbleRightClick(onClick : () -> Unit){
    this.setOnTouchListener(View.OnTouchListener { v, event ->
        val DRAWABLE_RIGHT = 2

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (getRight() - compoundDrawablesRelative[DRAWABLE_RIGHT].getBounds()
                    .width())
            ) {
                // your action here
                onClick()

                return@OnTouchListener true
            }
        }
        false;
    })
}


fun EditText.setMinCharacterLength (minCharacterLength:Int) {
    onTextChanged {
        if (text.toString().trim().length < minCharacterLength) {
            error = "more ${minCharacterLength.minus(text?.length!!)} character";
        } else {
            error = null;
        }
    }


}

fun Toolbar.setupWithNavController(
    navController: NavController,
    configuration: AppBarConfiguration = AppBarConfiguration(navController.graph)
) {
    NavigationUI.setupWithNavController(this, navController, configuration)
}