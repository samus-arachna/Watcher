package io.github.umren.watcher.Fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.text.method.LinkMovementMethod
import android.widget.TextView
import io.github.umren.watcher.R


class DonateFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val message = TextView(context)
        message.text = context.getText(R.string.fragment_donate)
        message.movementMethod = LinkMovementMethod.getInstance()

        return AlertDialog.Builder(activity)
                .setTitle("Donate")
                .setView(message)
                .setPositiveButton(android.R.string.ok, null)
                .create()
    }
}
