package com.teampophory.pophory.analytics

interface Analytics {
    fun trackEvent(
        name: String,
        args: Map<String, *>? = null,
    )
}
