package com.teampophory.pophory.bottomnavigation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.Snackbar
import com.teampophory.pophory.bottomnavigation.util.Utils

internal class SpaceNavigationViewBehavior<V : View> : CoordinatorLayout.Behavior<V> {

    constructor(context: Context, attrs: AttributeSet) : super()

    constructor() : super()

    override fun layoutDependsOn(parent: CoordinatorLayout, child: V, dependency: View): Boolean {
        return dependency is Snackbar.SnackbarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: V,
        dependency: View,
    ): Boolean {
        val translationY = 0f.coerceAtMost(dependency.translationY - dependency.height)
        child.translationY = translationY
        return true
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int,
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type,
        )
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray,
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed,
        )
        if (dyConsumed > 0) {
            Utils.makeTranslationYAnimation(child, child.height.toFloat())
        } else if (dyConsumed < 0) {
            Utils.makeTranslationYAnimation(child, 0f)
        }
    }
}
