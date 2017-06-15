package io.github.umren.watcher.Utils

import android.content.Context
import android.widget.Toast


fun toast(ctx: Context, text: String) {
    Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show()
}
