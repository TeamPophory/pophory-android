package com.teampophory.pophory.bottomnavigation

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView

import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListenerAdapter
import com.teampophory.pophory.bottomnavigation.util.Utils

internal object BadgeHelper {

    /**
     * Show badge
     *
     * @param view      target badge
     * @param badgeItem BadgeItem object
     */
    fun showBadge(
        view: RelativeLayout,
        badgeItem: BadgeItem,
        shouldShowBadgeWithNinePlus: Boolean
    ) {

        Utils.changeViewVisibilityVisible(view)
        val badgeTextView = view.findViewById<View>(R.id.badge_text_view) as TextView
        if (shouldShowBadgeWithNinePlus)
            badgeTextView.text = badgeItem.badgeText
        else
            badgeTextView.text = badgeItem.fullBadgeText

        view.scaleX = 0f
        view.scaleY = 0f

        ViewCompat.animate(view)
            .setDuration(200)
            .scaleX(1f)
            .scaleY(1f)
            .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                override fun onAnimationEnd(view: View) {
                    Utils.changeViewVisibilityVisible(view)
                }
            })
            .start()
    }

    /**
     * Show badge
     *
     * @param view target badge
     */
    fun hideBadge(view: View) {
        ViewCompat.animate(view)
            .setDuration(200)
            .scaleX(0f)
            .scaleY(0f)
            .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                override fun onAnimationEnd(view: View) {
                    Utils.changeViewVisibilityGone(view)
                }
            })
            .start()
    }

    /**
     * Force show badge without animation
     *
     * @param view      target budge
     * @param badgeItem BadgeItem object
     */
    fun forceShowBadge(
        view: RelativeLayout,
        badgeItem: BadgeItem,
        shouldShowBadgeWithNinePlus: Boolean
    ) {
        Utils.changeViewVisibilityVisible(view)
        view.background = makeShapeDrawable(badgeItem.badgeColor)
        val badgeTextView = view.findViewById<View>(R.id.badge_text_view) as TextView
        if (shouldShowBadgeWithNinePlus)
            badgeTextView.text = badgeItem.badgeText
        else
            badgeTextView.text = badgeItem.fullBadgeText
    }

    /**
     * Make circle drawable for badge background
     *
     * @param color background color
     * @return return colored circle drawable
     */
    fun makeShapeDrawable(color: Int): ShapeDrawable {
        val badgeBackground = ShapeDrawable(OvalShape())
        badgeBackground.intrinsicWidth = 10
        badgeBackground.intrinsicHeight = 10
        badgeBackground.paint.color = color
        return badgeBackground
    }
}
