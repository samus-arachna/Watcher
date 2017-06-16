package io.github.umren.watcher.Fragments

import android.app.Dialog
import android.support.v4.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import io.github.umren.watcher.R


class AboutFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setTitle("About")
                .setMessage(resources.getString(R.string.fragment_about))
                .setPositiveButton(android.R.string.ok, null)
                .create()
    }
}
