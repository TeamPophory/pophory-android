package com.teampophory.pophory.bottomnavigation

import java.io.Serializable

class SpaceItem : Serializable {

    var id = -1

    internal var itemName: String? = null

    internal var itemIcon: Int = 0

    constructor(itemName: String, itemIcon: Int) {
        this.itemName = itemName
        this.itemIcon = itemIcon
    }

    constructor(id: Int, itemIcon: Int) {
        this.id = id
        this.itemIcon = itemIcon
    }

    constructor(id: Int, itemName: String, itemIcon: Int) : this(itemName, itemIcon) {
        this.id = id
    }
}
