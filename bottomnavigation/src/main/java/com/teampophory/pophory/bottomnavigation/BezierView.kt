package com.teampophory.pophory.bottomnavigation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat

internal class BezierView : RelativeLayout {

    private var backgroundColor: Int? = null

    constructor(context: Context, backgroundColor: Int) : this(context) {
        this.backgroundColor = backgroundColor
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 0f
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val path: Path = Path()

    private var bezierWidth: Int = 0
    private var bezierHeight: Int = 0

    private var isLinear = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setBackgroundColor(ContextCompat.getColor(context, R.color.space_transparent))
    }

    override fun onDraw(canvas: Canvas) {

        /**
         * Set paint color to fill view
         */
        paint.color = backgroundColor ?: Color.WHITE

        /**
         * Reset path before drawing
         */
        path.reset()

        /**
         * Start point for drawing
         */
        path.moveTo(0f, bezierHeight.toFloat())

        if (!isLinear) {
            /**
             * Seth half path of bezier view
             */
            path.cubicTo(
                (bezierWidth / 4).toFloat(),
                bezierHeight.toFloat(),
                (bezierWidth / 4).toFloat(),
                0f,
                (bezierWidth / 2).toFloat(),
                0f
            )
            /**
             * Seth second part of bezier view
             */
            path.cubicTo(
                (bezierWidth / 4 * 3).toFloat(),
                0f,
                (bezierWidth / 4 * 3).toFloat(),
                bezierHeight.toFloat(),
                bezierWidth.toFloat(),
                bezierHeight.toFloat()
            )
        }

        /**
         * Draw our bezier view
         */
        canvas.drawPath(path, paint)
    }

    /**
     * Build bezier view with given width and height
     *
     * @param bezierWidth  Given width
     * @param bezierHeight Given height
     * @param isLinear True, if curves are not needed
     */
    fun build(bezierWidth: Int, bezierHeight: Int, isLinear: Boolean) {
        this.bezierWidth = bezierWidth
        this.bezierHeight = bezierHeight
        this.isLinear = isLinear
    }

    /**
     * Change bezier view background color
     *
     * @param backgroundColor Target color
     */
    fun changeBackgroundColor(backgroundColor: Int) {
        this.backgroundColor = backgroundColor
        invalidate()
    }
}

