package com.drodobyte.coreandroid.x

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.onBackPressed(action: () -> Unit) {
    onBackPressedDispatcher.addCallback(
        this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                action()
            }
        }
    )
}
