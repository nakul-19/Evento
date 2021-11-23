package com.gdsc_jss.evento.util

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun Int.toDp(context: Context): Int {
    return (context.resources.displayMetrics.density * this).toInt()
}

fun handleErrorsWithSnackbar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}