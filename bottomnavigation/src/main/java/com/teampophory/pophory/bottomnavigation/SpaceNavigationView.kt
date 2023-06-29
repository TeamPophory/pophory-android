package com.teampophory.pophory.bottomnavigation

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.teampophory.pophory.bottomnavigation.util.Utils
import com.teampophory.pophory.bottomnavigation.util.setColorFilter

class SpaceNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val spaceNavigationHeight =
        resources.getDimension(R.dimen.space_navigation_height).toInt()
    private val mainContentHeight = resources.getDimension(R.dimen.main_content_height).toInt()
    private val centreContentWight = resources.getDimension(R.dimen.centre_content_width).toInt()
    private val itemContentWight = resources.getDimension(R.dimen.item_content_width).toInt()
    private val centreButtonSize =
        resources.getDimension(R.dimen.space_centre_button_default_size).toInt()
    private var spaceNavigationWidth = 0
    private val spaceItems = ArrayList<SpaceItem>()
    private val spaceItemList = ArrayList<View>()
    private val badgeList = ArrayList<RelativeLayout>()
    private var badgeSaveInstanceHashMap: HashMap<Int, Any>? = HashMap()
    private var changedItemAndIconHashMap: HashMap<Int, SpaceItem>? = HashMap()
    private var spaceOnClickListener: SpaceOnClickListener? = null
    private var spaceOnLongClickListener: SpaceOnLongClickListener? = null
    private var savedInstanceState: Bundle? = null
    private var centerButton: CenterButton? = null
    private var centreBackgroundView: RelativeLayout? = null
    private var leftContent: LinearLayout? = null
    private var rightContent: LinearLayout? = null
    private var centreContent: BezierView? = null
    private var navigationViewBorderLine: NavigationShadow? = null
    private var customFont: Typeface? = null
    private var spaceItemIconSize = NOT_DEFINED

    private var spaceItemIconOnlySize = NOT_DEFINED

    private var spaceItemTextSize = NOT_DEFINED

    private var spaceBackgroundColor = NOT_DEFINED

    private var centreButtonId = NOT_DEFINED

    private var centreButtonColor = NOT_DEFINED

    private var activeCentreButtonIconColor = NOT_DEFINED

    private var inActiveCentreButtonIconColor = NOT_DEFINED

    private var activeCentreButtonBackgroundColor = NOT_DEFINED

    private var centreButtonIcon = NOT_DEFINED

    private var activeSpaceItemColor = NOT_DEFINED

    private var inActiveSpaceItemColor = NOT_DEFINED

    private var centreButtonRippleColor = NOT_DEFINED

    private var currentSelectedItem = 0

    private var contentWidth: Int = 0

    private var isCentreButtonSelectable = false

    private var isCentrePartLinear = false

    private var isTextOnlyMode = false

    private var isIconOnlyMode = false

    private var isCustomFont = false

    private var isCentreButtonIconColorFilterEnabled = true

    private var shouldShowBadgeWithNinePlus = true

    init {
        init(attrs)
    }

    /**
     * Init custom attributes
     *
     * @param attrs attributes
     */
    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val resources = resources

            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpaceNavigationView)
            spaceItemIconSize = typedArray.getDimensionPixelSize(
                R.styleable.SpaceNavigationView_space_item_icon_size,
                resources.getDimensionPixelSize(R.dimen.space_item_icon_default_size)
            )
            spaceItemIconOnlySize = typedArray.getDimensionPixelSize(
                R.styleable.SpaceNavigationView_space_item_icon_only_size,
                resources.getDimensionPixelSize(R.dimen.space_item_icon_only_size)
            )
            spaceItemTextSize = typedArray.getDimensionPixelSize(
                R.styleable.SpaceNavigationView_space_item_text_size,
                resources.getDimensionPixelSize(R.dimen.space_item_text_default_size)
            )
            spaceItemIconOnlySize = typedArray.getDimensionPixelSize(
                R.styleable.SpaceNavigationView_space_item_icon_only_size,
                resources.getDimensionPixelSize(R.dimen.space_item_icon_only_size)
            )
            spaceBackgroundColor = typedArray.getColor(
                R.styleable.SpaceNavigationView_space_background_color,
                ContextCompat.getColor(context, R.color.space_default_color)
            )
            centreButtonColor = typedArray.getColor(
                R.styleable.SpaceNavigationView_centre_button_color,
                ContextCompat.getColor(context, R.color.centre_button_color)
            )
            activeSpaceItemColor = typedArray.getColor(
                R.styleable.SpaceNavigationView_active_item_color,
                ContextCompat.getColor(context, R.color.space_white)
            )
            inActiveSpaceItemColor = typedArray.getColor(
                R.styleable.SpaceNavigationView_inactive_item_color,
                ContextCompat.getColor(context, R.color.default_inactive_item_color)
            )
            centreButtonIcon = typedArray.getResourceId(
                R.styleable.SpaceNavigationView_centre_button_icon,
                R.drawable.near_me
            )
            isCentrePartLinear =
                typedArray.getBoolean(R.styleable.SpaceNavigationView_centre_part_linear, false)
            activeCentreButtonIconColor = typedArray.getColor(
                R.styleable.SpaceNavigationView_active_centre_button_icon_color,
                ContextCompat.getColor(context, R.color.space_white)
            )
            inActiveCentreButtonIconColor = typedArray.getColor(
                R.styleable.SpaceNavigationView_inactive_centre_button_icon_color,
                ContextCompat.getColor(context, R.color.default_inactive_item_color)
            )
            activeCentreButtonBackgroundColor = typedArray.getColor(
                R.styleable.SpaceNavigationView_active_centre_button_background_color,
                ContextCompat.getColor(context, R.color.centre_button_color)
            )

            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        /**
         * Set default colors and sizes
         */
        if (spaceBackgroundColor == NOT_DEFINED)
            spaceBackgroundColor = ContextCompat.getColor(context, R.color.space_default_color)

        if (centreButtonColor == NOT_DEFINED)
            centreButtonColor = ContextCompat.getColor(context, R.color.centre_button_color)

        if (centreButtonIcon == NOT_DEFINED)
            centreButtonIcon = R.drawable.near_me

        if (activeSpaceItemColor == NOT_DEFINED)
            activeSpaceItemColor = ContextCompat.getColor(context, R.color.space_white)

        if (inActiveSpaceItemColor == NOT_DEFINED)
            inActiveSpaceItemColor =
                ContextCompat.getColor(context, R.color.default_inactive_item_color)

        if (spaceItemTextSize == NOT_DEFINED)
            spaceItemTextSize = resources.getDimension(R.dimen.space_item_text_default_size).toInt()

        if (spaceItemIconSize == NOT_DEFINED)
            spaceItemIconSize = resources.getDimension(R.dimen.space_item_icon_default_size).toInt()

        if (spaceItemIconOnlySize == NOT_DEFINED)
            spaceItemIconOnlySize =
                resources.getDimension(R.dimen.space_item_icon_only_size).toInt()

        if (centreButtonRippleColor == NOT_DEFINED)
            centreButtonRippleColor =
                ContextCompat.getColor(context, R.color.colorBackgroundHighlightWhite)

        if (activeCentreButtonIconColor == NOT_DEFINED)
            activeCentreButtonIconColor = ContextCompat.getColor(context, R.color.space_white)

        if (inActiveCentreButtonIconColor == NOT_DEFINED)
            inActiveCentreButtonIconColor =
                ContextCompat.getColor(context, R.color.default_inactive_item_color)

        /**
         * Set main layout size and color
         */
        val params = layoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = spaceNavigationHeight + SHADOW_HEIGHT
        setBackgroundColor(ContextCompat.getColor(context, R.color.space_transparent))
        layoutParams = params
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        spaceNavigationWidth = width

        /**
         * Restore current item index from savedInstance
         */
        restoreCurrentItem()

        /**
         * Trow exceptions if items size is greater than 4 or lesser than 2
         */
        if (spaceItems.size < MIN_SPACE_ITEM_SIZE && !isInEditMode) {
            throw NullPointerException(
                "Your space item count must be greater than 1 ," +
                        " your current items count isa : " + spaceItems.size
            )
        }

        if (spaceItems.size > MAX_SPACE_ITEM_SIZE && !isInEditMode) {
            throw IndexOutOfBoundsException(
                "Your items count maximum can be 4," +
                        " your current items count is : " + spaceItems.size
            )
        }

        /**
         * Get left or right content width
         */
        contentWidth = (width - spaceNavigationHeight) / 2

        /**
         * Removing all view for not being duplicated
         */
        removeAllViews()

        /**
         * Views initializations and customizing
         */
        initAndAddViewsToMainView()

        /**
         * Redraw main view to make subviews visible
         */
        postRequestLayout()

        /**
         * Retore Translation height
         */

        restoreTranslation()
    }

    //private methods

    /**
     * Views initializations and customizing
     */
    private fun initAndAddViewsToMainView() {

        val mainContent = RelativeLayout(context)
        centreBackgroundView = RelativeLayout(context)

        leftContent = LinearLayout(context)
        rightContent = LinearLayout(context)

        centreContent = buildBezierView()
        navigationViewBorderLine = buildBezierBorderline()

        centerButton = CenterButton(context)

        if (centreButtonId != NOT_DEFINED) {
            centerButton!!.id = centreButtonId
        }

        centerButton!!.size = FloatingActionButton.SIZE_NORMAL
        centerButton!!.useCompatPadding = false
        centerButton!!.rippleColor = centreButtonRippleColor
        centerButton!!.backgroundTintList = ColorStateList.valueOf(centreButtonColor)
        centerButton!!.setImageResource(centreButtonIcon)

        if (isCentreButtonIconColorFilterEnabled || isCentreButtonSelectable)
            centerButton!!.drawable.setColorFilter(
                inActiveCentreButtonIconColor
            )

        centerButton!!.setOnClickListener {
            if (spaceOnClickListener != null)
                spaceOnClickListener!!.onCentreButtonClick()
            if (isCentreButtonSelectable)
                updateSpaceItems(-1)
        }
        centerButton!!.setOnLongClickListener {
            if (spaceOnLongClickListener != null)
                spaceOnLongClickListener!!.onCentreButtonLongClick()

            true
        }

        /**
         * Set fab layout params
         */
        val fabParams = LayoutParams(centreButtonSize, centreButtonSize)
        fabParams.addRule(CENTER_IN_PARENT)

        /**
         * Main content size
         */
        val mainContentParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mainContentHeight)
        mainContentParams.addRule(ALIGN_PARENT_BOTTOM)

        /**
         * Borderline params
         */
        /**
         * Borderline params
         */
        val borderLineParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, spaceNavigationHeight)
        borderLineParams.addRule(CENTER_HORIZONTAL)
        borderLineParams.addRule(ALIGN_PARENT_TOP)

        /**
         * Centre content size
         */
        val centreContentParams =
            LayoutParams(centreContentWight, spaceNavigationHeight)
        centreContentParams.addRule(CENTER_HORIZONTAL)
        centreContentParams.addRule(ALIGN_PARENT_BOTTOM)

        /**
         * Centre Background View content size and position
         */
        val centreBackgroundViewParams =
            LayoutParams(itemContentWight, mainContentHeight)
        centreBackgroundViewParams.addRule(CENTER_HORIZONTAL)
        centreBackgroundViewParams.addRule(ALIGN_PARENT_BOTTOM)

        /**
         * Left content size
         */
        val leftContentParams = LayoutParams(contentWidth, mainContentHeight)
        leftContentParams.addRule(ALIGN_PARENT_LEFT)
        leftContentParams.addRule(LinearLayout.HORIZONTAL)
        leftContentParams.addRule(ALIGN_PARENT_BOTTOM)

        /**
         * Right content size
         */
        val rightContentParams = LayoutParams(contentWidth, mainContentHeight)
        rightContentParams.addRule(ALIGN_PARENT_RIGHT)
        rightContentParams.addRule(LinearLayout.HORIZONTAL)
        rightContentParams.addRule(ALIGN_PARENT_BOTTOM)

        /**
         * Adding views background colors
         */
        setBackgroundColors()

        /**
         * Adding shadow first, so it appears under all views
         */
        addView(navigationViewBorderLine, borderLineParams)

        /**
         * Adding view to centreContent
         */
        centreContent!!.addView(centerButton, fabParams)

        /**
         * Adding views to mainContent
         */
        addView(leftContent, leftContentParams)
        addView(rightContent, rightContentParams)


        /**
         * Adding views to mainView
         */
        addView(centreBackgroundView, centreBackgroundViewParams)
        addView(centreContent, centreContentParams)
        addView(mainContent, mainContentParams)

        /**
         * Restore changed icons and texts from savedInstance
         */
        restoreChangedIconsAndTexts()

        /**
         * Adding current space items to left and right content
         */
        addSpaceItems(leftContent!!, rightContent!!)
    }

    /**
     * Adding given space items to content
     *
     * @param leftContent  to left content
     * @param rightContent and right content
     */
    private fun addSpaceItems(leftContent: LinearLayout, rightContent: LinearLayout) {

        /**
         * Removing all views for not being duplicated
         */
        if (leftContent.childCount > 0 || rightContent.childCount > 0) {
            leftContent.removeAllViews()
            rightContent.removeAllViews()
        }

        /**
         * Clear spaceItemList and badgeList for not being duplicated
         */
        spaceItemList.clear()
        badgeList.clear()

        /**
         * Getting LayoutInflater to inflate space item view from XML
         */
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        for (i in spaceItems.indices) {
            val targetWidth: Int = if (spaceItems.size > MIN_SPACE_ITEM_SIZE) {
                contentWidth / 2
            } else {
                contentWidth
            }

            val textAndIconContainerParams = LayoutParams(
                targetWidth, mainContentHeight
            )
            val textAndIconContainer =
                inflater.inflate(R.layout.space_item_view, this, false) as RelativeLayout
            textAndIconContainer.layoutParams = textAndIconContainerParams

            val spaceItemIcon =
                textAndIconContainer.findViewById<View>(R.id.space_icon) as ImageView
            val spaceItemText = textAndIconContainer.findViewById<View>(R.id.space_text) as TextView
            val badgeContainer =
                textAndIconContainer.findViewById<View>(R.id.badge_container) as RelativeLayout
            spaceItemIcon.setImageResource(spaceItems[i].itemIcon)
            spaceItemText.text = spaceItems[i].itemName
            spaceItemText.setTextSize(TypedValue.COMPLEX_UNIT_PX, spaceItemTextSize.toFloat())

            /**
             * Set a custom id to the item
             */
            if (spaceItems[i].id != -1)
                textAndIconContainer.id = spaceItems[i].id

            /**
             * Set custom font to space item textView
             */
            if (isCustomFont)
                spaceItemText.typeface = customFont

            /**
             * Hide item icon and show only text
             */
            if (isTextOnlyMode)
                Utils.changeViewVisibilityGone(spaceItemIcon)

            /**
             * Hide item text and change icon size
             */
            val iconParams = spaceItemIcon.layoutParams
            if (isIconOnlyMode) {
                iconParams.height = spaceItemIconOnlySize
                iconParams.width = spaceItemIconOnlySize
                spaceItemIcon.layoutParams = iconParams
                Utils.changeViewVisibilityGone(spaceItemText)
            } else {
                iconParams.height = spaceItemIconSize
                iconParams.width = spaceItemIconSize
                spaceItemIcon.layoutParams = iconParams
            }

            /**
             * Adding space items to item list for future
             */
            spaceItemList.add(textAndIconContainer)

            /**
             * Adding badge items to badge list for future
             */
            badgeList.add(badgeContainer)

            /**
             * Adding sub views to left and right sides
             */
            if (spaceItems.size == MIN_SPACE_ITEM_SIZE && leftContent.childCount == 1) {
                rightContent.addView(textAndIconContainer, textAndIconContainerParams)
            } else if (spaceItems.size > MIN_SPACE_ITEM_SIZE && leftContent.childCount == 2) {
                rightContent.addView(textAndIconContainer, textAndIconContainerParams)
            } else {
                leftContent.addView(textAndIconContainer, textAndIconContainerParams)
            }

            /**
             * Changing current selected item tint
             */
            if (i == currentSelectedItem) {
                spaceItemText.setTextColor(activeSpaceItemColor)
                Utils.changeImageViewTint(spaceItemIcon, activeSpaceItemColor)
            } else {
                spaceItemText.setTextColor(inActiveSpaceItemColor)
                Utils.changeImageViewTint(spaceItemIcon, inActiveSpaceItemColor)
            }

            textAndIconContainer.setOnClickListener { updateSpaceItems(i) }

            textAndIconContainer.setOnLongClickListener {
                if (spaceOnLongClickListener != null)
                    spaceOnLongClickListener!!.onItemLongClick(i, spaceItems[i].itemName!!)
                true
            }
        }

        /**
         * Restore available badges from saveInstance
         */
        restoreBadges()
    }

    /**
     * Update selected item and change it's and non selected item tint
     *
     * @param selectedIndex item in index
     */
    private fun updateSpaceItems(selectedIndex: Int) {

        /**
         * return if item already selected
         */
        if (currentSelectedItem == selectedIndex) {
            if (spaceOnClickListener != null && selectedIndex >= 0)
                spaceItems[selectedIndex].itemName?.let {
                    spaceOnClickListener!!.onItemReselected(
                        selectedIndex,
                        it
                    )
                }

            return
        }

        if (isCentreButtonSelectable) {
            /**
             * Selects the centre button as current
             */
            if (selectedIndex == -1) {
                if (centerButton != null) {
                    centerButton!!.drawable.setColorFilter(
                        activeCentreButtonIconColor
                    )

                    if (activeCentreButtonBackgroundColor != NOT_DEFINED) {
                        centerButton!!.backgroundTintList =
                            ColorStateList.valueOf(activeCentreButtonBackgroundColor)
                    }
                }
            }

            /**
             * Removes selection from centre button
             */
            if (currentSelectedItem == -1) {
                if (centerButton != null) {
                    centerButton!!.drawable.setColorFilter(
                        inActiveCentreButtonIconColor
                    )

                    if (activeCentreButtonBackgroundColor != NOT_DEFINED) {
                        centerButton!!.backgroundTintList =
                            ColorStateList.valueOf(centreButtonColor)
                    }
                }
            }
        }

        /**
         * Change active and inactive icon and text color
         */
        for (i in spaceItemList.indices) {
            if (i == selectedIndex) {
                val textAndIconContainer = spaceItemList[selectedIndex] as RelativeLayout
                val spaceItemIcon =
                    textAndIconContainer.findViewById<View>(R.id.space_icon) as ImageView
                val spaceItemText =
                    textAndIconContainer.findViewById<View>(R.id.space_text) as TextView
                spaceItemText.setTextColor(activeSpaceItemColor)
                Utils.changeImageViewTint(spaceItemIcon, activeSpaceItemColor)
            } else if (i == currentSelectedItem) {
                val textAndIconContainer = spaceItemList[i] as RelativeLayout
                val spaceItemIcon =
                    textAndIconContainer.findViewById<View>(R.id.space_icon) as ImageView
                val spaceItemText =
                    textAndIconContainer.findViewById<View>(R.id.space_text) as TextView
                spaceItemText.setTextColor(inActiveSpaceItemColor)
                Utils.changeImageViewTint(spaceItemIcon, inActiveSpaceItemColor)
            }
        }

        /**
         * Set a listener that gets fired when the selected item changes
         *
         * @param listener a listener for monitoring changes in item selection
         */
        if (spaceOnClickListener != null && selectedIndex >= 0)
            spaceOnClickListener!!.onItemClick(selectedIndex, spaceItems[selectedIndex].itemName!!)

        /**
         * Change current selected item index
         */
        currentSelectedItem = selectedIndex
    }

    /**
     * Set views background colors
     */
    private fun setBackgroundColors() {
        rightContent!!.setBackgroundColor(spaceBackgroundColor)
        centreBackgroundView!!.setBackgroundColor(spaceBackgroundColor)
        leftContent!!.setBackgroundColor(spaceBackgroundColor)
    }

    /**
     * Indicate event queue that we have changed the View hierarchy during a layout pass
     */
    private fun postRequestLayout() {
        this@SpaceNavigationView.handler.post { this@SpaceNavigationView.requestLayout() }
    }

    /**
     * Restore current item index from savedInstance
     */
    private fun restoreCurrentItem() {
        val restoredBundle = savedInstanceState
        if (restoredBundle != null) {
            if (restoredBundle.containsKey(CURRENT_SELECTED_ITEM_BUNDLE_KEY))
                currentSelectedItem = restoredBundle.getInt(CURRENT_SELECTED_ITEM_BUNDLE_KEY, 0)
        }
    }

    /**
     * Restore available badges from saveInstance
     */
    private fun restoreBadges() {
        val restoredBundle = savedInstanceState

        if (restoredBundle != null) {
            if (restoredBundle.containsKey(BADGE_FULL_TEXT_KEY)) {
                shouldShowBadgeWithNinePlus = restoredBundle.getBoolean(BADGE_FULL_TEXT_KEY)
            }

            if (restoredBundle.containsKey(BADGES_ITEM_BUNDLE_KEY)) {
                badgeSaveInstanceHashMap =
                    savedInstanceState!!.getSerializable(BADGES_ITEM_BUNDLE_KEY) as? HashMap<Int, Any>?
                if (badgeSaveInstanceHashMap != null) {
                    for (integer in badgeSaveInstanceHashMap!!.keys) {
                        BadgeHelper.forceShowBadge(
                            badgeList[integer],
                            (badgeSaveInstanceHashMap!![integer] as BadgeItem?)!!,
                            shouldShowBadgeWithNinePlus
                        )
                    }
                }
            }
        }
    }

    /**
     * Restore changed icons,colors and texts from saveInstance
     */
    private fun restoreChangedIconsAndTexts() {
        val restoredBundle = savedInstanceState
        if (restoredBundle != null) {
            if (restoredBundle.containsKey(CHANGED_ICON_AND_TEXT_BUNDLE_KEY)) {
                changedItemAndIconHashMap =
                    restoredBundle.getSerializable(CHANGED_ICON_AND_TEXT_BUNDLE_KEY) as HashMap<Int, SpaceItem>?
                if (changedItemAndIconHashMap != null) {
                    var spaceItem: SpaceItem?
                    for (i in 0 until changedItemAndIconHashMap!!.size) {
                        spaceItem = changedItemAndIconHashMap!![i]
                        spaceItems[i].itemIcon = spaceItem!!.itemIcon
                        spaceItems[i].itemName = spaceItem.itemName
                    }
                }
            }

            if (restoredBundle.containsKey(CENTRE_BUTTON_ICON_KEY)) {
                centreButtonIcon = restoredBundle.getInt(CENTRE_BUTTON_ICON_KEY)
                centerButton!!.setImageResource(centreButtonIcon)
            }

            if (restoredBundle.containsKey(SPACE_BACKGROUND_COLOR_KEY)) {
                val backgroundColor = restoredBundle.getInt(SPACE_BACKGROUND_COLOR_KEY)
                changeSpaceBackgroundColor(backgroundColor)
            }
        }
    }

    /**
     * Creating bezier view with params
     *
     * @return created bezier view
     */
    private fun buildBezierView(): BezierView {
        val bezierView = BezierView(context, spaceBackgroundColor)
        bezierView.build(
            centreContentWight,
            spaceNavigationHeight - mainContentHeight,
            isCentrePartLinear
        )
        return bezierView
    }

    /**
     * Throw Array Index Out Of Bounds Exception
     *
     * @param itemIndex item index to show on logs
     */
    private fun throwArrayIndexOutOfBoundsException(itemIndex: Int) {
        throw ArrayIndexOutOfBoundsException(
            "Your item index can't be 0 or greater than space item size," +
                    " your items size is " + spaceItems.size + ", your current index is :" + itemIndex
        )
    }

    /**
     * Creating borderline with params
     *
     * @return created borderline view
     */
    private fun buildBezierBorderline(): NavigationShadow {
        val borderline = NavigationShadow(context)
        borderline.build(
            spaceNavigationWidth,
            centreContentWight,
            spaceNavigationHeight - mainContentHeight,
            isCentrePartLinear
        )
        return borderline
    }

    //public methods

    /**
     * Initialization with savedInstanceState to save current selected
     * position and current badges
     *
     * @param savedInstanceState bundle to saveInstance
     */
    fun initWithSaveInstanceState(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
    }

    /**
     * Save badges and current position
     *
     * @param outState bundle to saveInstance
     */
    fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CURRENT_SELECTED_ITEM_BUNDLE_KEY, currentSelectedItem)
        outState.putInt(CENTRE_BUTTON_ICON_KEY, centreButtonIcon)
        outState.putInt(SPACE_BACKGROUND_COLOR_KEY, spaceBackgroundColor)
        outState.putBoolean(BADGE_FULL_TEXT_KEY, shouldShowBadgeWithNinePlus)
        outState.putFloat(VISIBILITY, this.translationY)

        if (badgeSaveInstanceHashMap!!.size > 0)
            outState.putSerializable(BADGES_ITEM_BUNDLE_KEY, badgeSaveInstanceHashMap)
        if (changedItemAndIconHashMap!!.size > 0)
            outState.putSerializable(CHANGED_ICON_AND_TEXT_BUNDLE_KEY, changedItemAndIconHashMap)
    }

    fun setCentreButtonId(@IdRes id: Int) {
        this.centreButtonId = id
    }

    /**
     * Set centre circle button background color
     *
     * @param centreButtonColor target color
     */
    fun setCentreButtonColor(@ColorInt centreButtonColor: Int) {
        this.centreButtonColor = centreButtonColor
    }

    /**
     * Set main background color
     *
     * @param spaceBackgroundColor target color
     */
    fun setSpaceBackgroundColor(@ColorInt spaceBackgroundColor: Int) {
        this.spaceBackgroundColor = spaceBackgroundColor
    }

    /**
     * Set centre button icon
     *
     * @param centreButtonIcon target icon
     */
    fun setCentreButtonIcon(centreButtonIcon: Int) {
        this.centreButtonIcon = centreButtonIcon
    }

    /**
     * Set active centre button color
     *
     * @param activeCentreButtonBackgroundColor color to change
     */
    fun setActiveCentreButtonBackgroundColor(@ColorInt activeCentreButtonBackgroundColor: Int) {
        this.activeCentreButtonBackgroundColor = activeCentreButtonBackgroundColor
    }

    /**
     * Set active item text color
     *
     * @param activeSpaceItemColor color to change
     */
    fun setActiveSpaceItemColor(@ColorInt activeSpaceItemColor: Int) {
        this.activeSpaceItemColor = activeSpaceItemColor
    }

    /**
     * Set inactive item text color
     *
     * @param inActiveSpaceItemColor color to change
     */
    fun setInActiveSpaceItemColor(@ColorInt inActiveSpaceItemColor: Int) {
        this.inActiveSpaceItemColor = inActiveSpaceItemColor
    }

    /**
     * Set item icon size
     *
     * @param spaceItemIconSize target size
     */
    fun setSpaceItemIconSize(spaceItemIconSize: Int) {
        this.spaceItemIconSize = spaceItemIconSize
    }

    /**
     * Set item icon size if showIconOnly() called
     *
     * @param spaceItemIconOnlySize target size
     */
    fun setSpaceItemIconSizeInOnlyIconMode(spaceItemIconOnlySize: Int) {
        this.spaceItemIconOnlySize = spaceItemIconOnlySize
    }

    /**
     * Set item text size
     *
     * @param spaceItemTextSize target size
     */
    fun setSpaceItemTextSize(spaceItemTextSize: Int) {
        this.spaceItemTextSize = spaceItemTextSize
    }

    /**
     * Set centre button pressed state color
     *
     * @param centreButtonRippleColor Target color
     */
    fun setCentreButtonRippleColor(centreButtonRippleColor: Int) {
        this.centreButtonRippleColor = centreButtonRippleColor
    }

    /**
     * Show only text in item
     */
    fun showTextOnly() {
        isTextOnlyMode = true
    }

    /**
     * Show only icon in item
     */
    fun showIconOnly() {
        isIconOnlyMode = true
    }

    /**
     * Makes centre button selectable
     */
    fun setCentreButtonSelectable(isSelectable: Boolean) {
        this.isCentreButtonSelectable = isSelectable
    }

    /**
     * Add space item to navigation
     *
     * @param spaceItem item to add
     */
    fun addSpaceItem(spaceItem: SpaceItem) {
        spaceItems.add(spaceItem)
    }

    /**
     * Change current selected item to centre button
     */
    fun setCentreButtonSelected() {
        if (!isCentreButtonSelectable)
            throw ArrayIndexOutOfBoundsException("Please be more careful, you must set the centre button selectable")
        else
            updateSpaceItems(-1)
    }

    /**
     * Set space item and centre click
     *
     * @param spaceOnClickListener space click listener
     */
    fun setSpaceOnClickListener(spaceOnClickListener: SpaceOnClickListener) {
        this.spaceOnClickListener = spaceOnClickListener
    }

    /**
     * Set space item and centre button long click
     *
     * @param spaceOnLongClickListener space long click listener
     */
    fun setSpaceOnLongClickListener(spaceOnLongClickListener: SpaceOnLongClickListener) {
        this.spaceOnLongClickListener = spaceOnLongClickListener
    }

    /**
     * Change current selected item to given index
     * Note: -1 represents the centre button
     *
     * @param indexToChange given index
     */
    fun changeCurrentItem(indexToChange: Int) {
        if (indexToChange < -1 || indexToChange > spaceItems.size)
            throw ArrayIndexOutOfBoundsException("Please be more careful, we do't have such item : $indexToChange")
        else {
            updateSpaceItems(indexToChange)
        }
    }

    /**
     * Show badge at index
     *
     * @param itemIndex index
     * @param badgeText badge count text
     */
    fun showBadgeAtIndex(itemIndex: Int, badgeText: Int, @ColorInt badgeColor: Int) {
        if (itemIndex < 0 || itemIndex > spaceItems.size) {
            throwArrayIndexOutOfBoundsException(itemIndex)
        } else {
            val badgeView = badgeList[itemIndex]

            /**
             * Set circle background to badge view
             */
            badgeView.background = BadgeHelper.makeShapeDrawable(badgeColor)

            val badgeItem = BadgeItem(itemIndex, badgeText, badgeColor)
            BadgeHelper.showBadge(badgeView, badgeItem, shouldShowBadgeWithNinePlus)
            badgeSaveInstanceHashMap!![itemIndex] = badgeItem
        }
    }

    /**
     * Restore translation height from saveInstance
     */
    private fun restoreTranslation() {
        val restoredBundle = savedInstanceState

        if (restoredBundle != null) {
            if (restoredBundle.containsKey(VISIBILITY)) {
                this.translationY = restoredBundle.getFloat(VISIBILITY)
            }

        }
    }

    /**
     * Hide badge at index
     *
     * @param index badge index
     */
    @Deprecated("Use {@link #hideBadgeAtIndex(int index)} instead.")
    fun hideBudgeAtIndex(index: Int) {
        if (badgeList[index].visibility == View.GONE) {
            Log.d(TAG, "Badge at index: $index already hidden")
        } else {
            BadgeHelper.hideBadge(badgeList[index])
            badgeSaveInstanceHashMap!!.remove(index)
        }
    }

    /**
     * Hide badge at index
     *
     * @param index badge index
     */
    fun hideBadgeAtIndex(index: Int) {
        if (badgeList[index].visibility == View.GONE) {
            Log.d(TAG, "Badge at index: $index already hidden")
        } else {
            BadgeHelper.hideBadge(badgeList[index])
            badgeSaveInstanceHashMap!!.remove(index)
        }
    }

    /**
     * Hiding all available badges
     */
    @Deprecated("Use {@link #hideAllBadges()} instead.")
    fun hideAllBudges() {
        for (badge in badgeList) {
            if (badge.visibility == View.VISIBLE)
                BadgeHelper.hideBadge(badge)
        }
        badgeSaveInstanceHashMap!!.clear()
    }

    /**
     * Hiding all available badges
     */
    fun hideAllBadges() {
        for (badge in badgeList) {
            if (badge.visibility == View.VISIBLE)
                BadgeHelper.hideBadge(badge)
        }
        badgeSaveInstanceHashMap!!.clear()
    }

    /**
     * Change badge text at index
     *
     * @param badgeIndex target index
     * @param badgeText  badge count text to change
     */
    fun changeBadgeTextAtIndex(badgeIndex: Int, badgeText: Int) {
        if (badgeSaveInstanceHashMap!![badgeIndex] != null && (badgeSaveInstanceHashMap!![badgeIndex] as BadgeItem).intBadgeText != badgeText) {
            val currentBadgeItem = badgeSaveInstanceHashMap!![badgeIndex] as BadgeItem?
            val badgeItemForSave = BadgeItem(badgeIndex, badgeText, currentBadgeItem!!.badgeColor)
            BadgeHelper.forceShowBadge(
                badgeList[badgeIndex],
                badgeItemForSave,
                shouldShowBadgeWithNinePlus
            )
            badgeSaveInstanceHashMap!![badgeIndex] = badgeItemForSave
        }
    }

    /**
     * Set custom font for space item textView
     *
     * @param customFont custom font
     */
    fun setFont(customFont: Typeface) {
        isCustomFont = true
        this.customFont = customFont
    }

    fun setCentreButtonIconColorFilterEnabled(enabled: Boolean) {
        isCentreButtonIconColorFilterEnabled = enabled
    }

    /**
     * Change centre button icon if space navigation already set up
     *
     * @param icon Target icon to change
     */
    fun changeCenterButtonIcon(icon: Int) {
        if (centerButton == null) {
            Log.e(
                TAG,
                "You should call setCentreButtonIcon() instead, " + "changeCenterButtonIcon works if space navigation already set up"
            )
        } else {
            centerButton!!.setImageResource(icon)
            centreButtonIcon = icon
        }
    }

    /**
     * Change item icon if space navigation already set up
     *
     * @param itemIndex Target position
     * @param newIcon   Icon to change
     */
    fun changeItemIconAtPosition(itemIndex: Int, newIcon: Int) {
        if (itemIndex < 0 || itemIndex > spaceItems.size) {
            throwArrayIndexOutOfBoundsException(itemIndex)
        } else {
            val spaceItem = spaceItems[itemIndex]
            val textAndIconContainer = spaceItemList[itemIndex] as RelativeLayout
            val spaceItemIcon =
                textAndIconContainer.findViewById<View>(R.id.space_icon) as ImageView
            spaceItemIcon.setImageResource(newIcon)
            spaceItem.itemIcon = newIcon
            changedItemAndIconHashMap!![itemIndex] = spaceItem
        }
    }

    /**
     * Change item text if space navigation already set up
     *
     * @param itemIndex Target position
     * @param newText   Text to change
     */
    fun changeItemTextAtPosition(itemIndex: Int, newText: String) {
        if (itemIndex < 0 || itemIndex > spaceItems.size) {
            throwArrayIndexOutOfBoundsException(itemIndex)
        } else {
            val spaceItem = spaceItems[itemIndex]
            val textAndIconContainer = spaceItemList[itemIndex] as RelativeLayout
            val spaceItemIcon = textAndIconContainer.findViewById<View>(R.id.space_text) as TextView
            spaceItemIcon.text = newText
            spaceItem.itemName = newText
            changedItemAndIconHashMap!![itemIndex] = spaceItem
        }
    }

    /**
     * Change space background color if space view already set up
     *
     * @param color Target color to change
     */
    fun changeSpaceBackgroundColor(@ColorInt color: Int) {
        if (color == spaceBackgroundColor) {
            Log.d(TAG, "changeSpaceBackgroundColor: color already changed")
            return
        }

        spaceBackgroundColor = color
        setBackgroundColors()
        centreContent!!.changeBackgroundColor(color)
    }


    /**
     * If you want to show full badge text or show 9+
     *
     * @param shouldShowBadgeWithNinePlus false for full text
     */
    fun shouldShowFullBadgeText(shouldShowBadgeWithNinePlus: Boolean) {
        this.shouldShowBadgeWithNinePlus = shouldShowBadgeWithNinePlus
    }

    /**
     * set active centre button color
     *
     * @param color target color
     */
    fun setActiveCentreButtonIconColor(@ColorInt color: Int) {
        activeCentreButtonIconColor = color
    }

    /**
     * set inactive centre button color
     *
     * @param color target color
     */
    fun setInActiveCentreButtonIconColor(@ColorInt color: Int) {
        inActiveCentreButtonIconColor = color
    }

    companion object {

        private const val TAG = "SpaceNavigationView"

        private const val CURRENT_SELECTED_ITEM_BUNDLE_KEY = "currentItem"

        private const val BADGES_ITEM_BUNDLE_KEY = "badgeItem"

        private const val CHANGED_ICON_AND_TEXT_BUNDLE_KEY = "changedIconAndText"

        private const val CENTRE_BUTTON_ICON_KEY = "centreButtonIconKey"

        private const val CENTRE_BUTTON_COLOR_KEY = "centreButtonColorKey"

        private const val SPACE_BACKGROUND_COLOR_KEY = "backgroundColorKey"

        private const val BADGE_FULL_TEXT_KEY = "badgeFullTextKey"

        private const val VISIBILITY = "visibilty"

        private const val NOT_DEFINED = -777 //random number, not - 1 because it is Color.WHITE

        private const val MAX_SPACE_ITEM_SIZE = 4

        private const val MIN_SPACE_ITEM_SIZE = 2

        private const val SHADOW_HEIGHT = 8
    }
}
/**
 * Constructors
 */
