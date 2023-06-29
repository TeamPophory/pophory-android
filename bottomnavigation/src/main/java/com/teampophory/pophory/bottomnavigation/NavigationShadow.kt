package com.teampophory.pophory.bottomnavigation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat


class NavigationShadow(
    private val context: Context
) : RelativeLayout(context) {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 2f
        isAntiAlias = true
        style = Paint.Style.STROKE
    }
    private var bezierWidth = 0f
    private var bezierHeight = 0f
    private var navigationWidth = 0f
    private var isLinear = false
    private val alphaArray = intArrayOf(25, 23, 18, 15, 13, 8, 5, 3)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setBackgroundColor(ContextCompat.getColor(context, R.color.space_transparent))
    }

    override fun onDraw(canvas: Canvas) {
        // translate canvas by shaow width (8 width of shadow). Otherwise canvas clips top part bezier curve
        canvas.translate(0f, 8f)
        for (i in 0..7) {
            /**
             * Draw shadow
             */
            canvas.drawPath(drawPathWithOffset(alphaArray[i], i), paint)
        }
    }

    private fun drawPathWithOffset(alpha: Int, offset: Int): Path {
        val offsettBeizerHeight = bezierHeight - offset
        val path = Path()
        /**
         * Reset path before drawing
         */
        path.reset()
        /**
         * Set paint color to draw the path with
         */
        paint.color = Color.BLACK
        /**
         * Set alpha to change opacity of path
         */
        paint.alpha = alpha
        /**
         * Start point for drawing
         */
        path.moveTo(0f, offsettBeizerHeight)
        /**
         * Draw line over left main-content
         */
        path.lineTo((navigationWidth - bezierWidth) / 2, offsettBeizerHeight)
        if (!isLinear) {
            /**
             * Seth half path of bezier view
             */
            path.cubicTo(
                bezierWidth / 4 + (navigationWidth - bezierWidth) / 2,
                offsettBeizerHeight,
                bezierWidth / 4 + (navigationWidth - bezierWidth) / 2,
                -offset.toFloat(),
                bezierWidth / 2 + (navigationWidth - bezierWidth) / 2,
                -offset.toFloat(),
            )
            /**
             * Seth second part of bezier view
             */
            path.cubicTo(
                bezierWidth / 4 * 3 + (navigationWidth - bezierWidth) / 2,
                -offset.toFloat(),
                bezierWidth / 4 * 3 + (navigationWidth - bezierWidth) / 2,
                offsettBeizerHeight,
                bezierWidth + (navigationWidth - bezierWidth) / 2,
                offsettBeizerHeight
            )
        }
        /**
         * Draw line over right main-content
         */
        path.lineTo(navigationWidth, offsettBeizerHeight)
        return path
    }

    /**
     * Build bezier view with given width and height
     *
     * @param bezierWidth  Given width
     * @param bezierHeight Given height
     * @param isLinear True, if curves are not needed
     */
    fun build(navigationWidth: Int, bezierWidth: Int, bezierHeight: Int, isLinear: Boolean) {
        this.navigationWidth = navigationWidth.toFloat()
        this.bezierWidth = bezierWidth.toFloat()
        this.bezierHeight = bezierHeight.toFloat()
        this.isLinear = isLinear
    }
}