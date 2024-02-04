package com.teampophory.pophory.bottomnavigation.util

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import android.widget.ImageView

internal object Utils {

    /**
     * Change given image view tint
     *
     * @param imageView target image view
     * @param color     tint color
     */
    fun changeImageViewTint(imageView: ImageView, color: Int) {
        imageView.setColorFilter(color)
    }

    /**
     * Change view visibility
     *
     * @param view target view
     */
    fun changeViewVisibilityGone(view: View?) {
        if (view != null && view.visibility == View.VISIBLE) {
            view.visibility = View.GONE
        }
    }

    /**
     * Change view visibility
     *
     * @param view target view
     */
    fun changeViewVisibilityVisible(view: View?) {
        if (view != null && view.visibility == View.GONE) {
            view.visibility = View.VISIBLE
        }
    }

    /**
     * Change given image view tint with animation
     *
     * @param image     target image view
     * @param fromColor start animation from color
     * @param toColor   final color
     */
    fun changeImageViewTintWithAnimation(image: ImageView, fromColor: Int, toColor: Int) {
        val imageTintChangeAnimation = ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor)
        imageTintChangeAnimation.addUpdateListener { animator -> image.setColorFilter(animator.animatedValue as Int) }
        imageTintChangeAnimation.duration = 150
        imageTintChangeAnimation.start()
    }

    fun makeTranslationYAnimation(view: View, value: Float) {
        view.animate()
            .translationY(value)
            .setDuration(150)
            .start()
    }

    fun getPressedColorRippleDrawable(normalColor: Int, pressedColor: Int): RippleDrawable {
        return RippleDrawable(
            getPressedColorSelector(normalColor, pressedColor),
            ColorDrawable(normalColor),
            null,
        )
    }

    private fun getPressedColorSelector(normalColor: Int, pressedColor: Int): ColorStateList {
        return ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_focused),
                intArrayOf(android.R.attr.state_activated),
                intArrayOf(),
            ),
            intArrayOf(pressedColor, pressedColor, pressedColor, normalColor),
        )
    }
}
