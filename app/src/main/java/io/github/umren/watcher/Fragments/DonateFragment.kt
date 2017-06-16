package io.github.umren.watcher.Fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import io.github.umren.watcher.R


class DonateFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setTitle("Donate")
                .setMessage(resources.getString(R.string.fragment_donate))
                .setPositiveButton(android.R.string.ok, null)
                .create()
    }
}
