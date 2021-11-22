package com.gdsc_jss.evento.util

import android.content.Context

fun Int.toDp(context: Context): Int {
    return (context.resources.displayMetrics.density * this).toInt()
}