package com.teampophory.pophory.data.network.model.auth

enum class UserAccountState(val value: Boolean) {
    REGISTERED(true),
    UNREGISTERED(false);

    companion object {
        fun of(value: Boolean) = values().first { it.value == value }
    }
}
