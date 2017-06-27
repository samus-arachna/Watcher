package io.github.umren.watcher.Views

import android.view.View


interface Presenter<T : View> {
    fun onCreate()
    fun onStart()
    fun onStop()
    fun onPause()
    fun attachView(view: T)
}
