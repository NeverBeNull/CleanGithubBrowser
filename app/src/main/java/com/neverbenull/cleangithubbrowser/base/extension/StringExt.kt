package com.neverbenull.cleangithubbrowser.base.extension

import java.text.SimpleDateFormat
import java.util.*

fun String?.toDateWithTimeZone() : Date? {
    return this?.let { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(it) }
}