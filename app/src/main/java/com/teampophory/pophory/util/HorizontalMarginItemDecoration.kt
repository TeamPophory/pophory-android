package com.teampophory.pophory.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalMarginItemDecoration(horizontalMarginInDp: Int) : RecyclerView.ItemDecoration() {
    private val horizontalMarginInDP = horizontalMarginInDp

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.right = horizontalMarginInDP
        outRect.left = horizontalMarginInDP
    }
}