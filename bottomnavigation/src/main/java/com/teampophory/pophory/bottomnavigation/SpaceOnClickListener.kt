package com.teampophory.pophory.bottomnavigation

interface SpaceOnClickListener {
    fun onCentreButtonClick()
    fun onItemClick(itemIndex: Int, itemName: String)
    fun onItemReselected(itemIndex: Int, itemName: String)
}
