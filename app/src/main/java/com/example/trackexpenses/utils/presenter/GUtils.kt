package com.example.trackexpenses.utils.presenter

import android.text.Editable

fun String.editableToString(): Editable? {
    return Editable.Factory.getInstance().newEditable(this)
}