package com.teampophory.pophory.data.local

interface PophoryDataStore {
    var accessToken: String
    var refreshToken: String
    var userName: String
    var userId: String

    fun clear()
}
