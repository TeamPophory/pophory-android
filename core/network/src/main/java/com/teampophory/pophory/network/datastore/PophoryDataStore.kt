package com.teampophory.pophory.network.datastore

interface PophoryDataStore {
    var accessToken: String
    var refreshToken: String
    var userName: String
    var userId: String
    var autoLoginConfigured: Boolean
    fun clear()
}
