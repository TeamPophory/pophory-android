package com.teampophory.pophory.bottomnavigation

import java.io.Serializable

internal class BadgeItem(val badgeIndex: Int, val intBadgeText: Int, val badgeColor: Int) :
    Serializable {

    val fullBadgeText: String
        get() = intBadgeText.toString()

    val badgeText: String
        get() {
            return if (intBadgeText > BADGE_TEXT_MAX_NUMBER) {
                "$BADGE_TEXT_MAX_NUMBER+"
            } else {
                intBadgeText.toString()
            }
        }

    companion object {

        private val BADGE_TEXT_MAX_NUMBER = 9
    }
}
