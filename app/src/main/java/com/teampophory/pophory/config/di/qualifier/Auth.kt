package com.teampophory.pophory.config.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Secured

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Unsecured
