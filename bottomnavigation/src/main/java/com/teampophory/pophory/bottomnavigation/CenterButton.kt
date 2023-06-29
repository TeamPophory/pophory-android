package com.teampophory.pophory.bottomnavigation

import android.content.Context
import android.view.MotionEvent
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CenterButton(context: Context) : FloatingActionButton(context) {
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val result = super.onTouchEvent(ev)
        if (!result) {
            if (ev.action == MotionEvent.ACTION_UP) {
                cancelLongPress()
            }
            isPressed = false
        }
        return result
    }
}
