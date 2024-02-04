package com.teampophory.pophory.common.time

import android.content.Context
import android.text.format.DateUtils
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Instant.Companion.systemNow(): Instant = Clock.System.now()

fun Instant.toDefaultLocalDate(): LocalDate = toLocalDateTime(TimeZone.currentSystemDefault()).date

fun Long.formatDate(context: Context): String = DateUtils.formatDateTime(
    context,
    this,
    DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_DATE,
)

fun Instant.formatDate(context: Context): String = toEpochMilliseconds().formatDate(context)

fun Long.formatNumericDate(context: Context): String = DateUtils.formatDateTime(
    context,
    this,
    DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_NUMERIC_DATE,
)

fun Instant.formatNumericDate(context: Context): String =
    toEpochMilliseconds().formatNumericDate(context)
