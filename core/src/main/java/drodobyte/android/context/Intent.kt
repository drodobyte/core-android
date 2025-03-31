package drodobyte.android.context

import android.app.Activity
import android.content.Intent

/**
 * Send text content to another app (e.g: mail)
 */
fun Activity.actionSend(title: String, subject: String, text: String, vararg recipients: String) =
    startActivity(
        Intent.createChooser(
            Intent(Intent.ACTION_SEND).apply {
                setType("text/plain")
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, text)
                putExtra(Intent.EXTRA_EMAIL, recipients)
                // You can also attach multiple items by passing an ArrayList of Uris
                // intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
            },
            title
        )
    )
