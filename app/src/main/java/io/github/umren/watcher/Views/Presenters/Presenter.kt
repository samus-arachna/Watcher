package io.github.umren.watcher.Views.Presenters

import io.github.umren.watcher.Views.View.BasicView


interface Presenter<T : BasicView> {
    fun onCreate()
    fun onStart()
    fun onStop()
    fun onPause()
    fun attachView(view: T)
}
