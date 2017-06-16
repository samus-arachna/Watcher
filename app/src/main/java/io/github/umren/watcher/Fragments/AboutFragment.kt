package io.github.umren.watcher.Fragments

import android.app.Dialog
import android.support.v4.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.method.LinkMovementMethod
import android.widget.TextView
import io.github.umren.watcher.R


class AboutFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val message = TextView(context)
        message.setPadding(50, 20, 50, 10)
        message.textSize = 18.0F
        message.text = context.getText(R.string.fragment_about)
        message.movementMethod = LinkMovementMethod.getInstance()

        return AlertDialog.Builder(activity)
                .setTitle("About")
                .setView(message)
                .setPositiveButton(android.R.string.ok, null)
                .create()
    }
}
