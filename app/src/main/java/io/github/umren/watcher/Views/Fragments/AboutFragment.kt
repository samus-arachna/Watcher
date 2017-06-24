package io.github.umren.watcher.Views.Fragments

import android.app.Dialog
import android.support.v4.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.method.LinkMovementMethod
import android.widget.TextView
import io.github.umren.watcher.R


class AboutFragment : android.support.v4.app.DialogFragment() {

    override fun onCreateDialog(savedInstanceState: android.os.Bundle?): android.app.Dialog {

        val message = android.widget.TextView(context)
        message.setPadding(50, 20, 50, 10)
        message.textSize = 18.0F
        message.text = context.getText(io.github.umren.watcher.R.string.fragment_about)
        message.movementMethod = android.text.method.LinkMovementMethod.getInstance()

        return android.support.v7.app.AlertDialog.Builder(activity)
                .setTitle("About")
                .setView(message)
                .setPositiveButton(android.R.string.ok, null)
                .create()
    }
}
