package io.github.umren.watcher.Views.View

import io.github.umren.watcher.Views.Activities.MainActivity


interface MainView : BasicView {
    fun getActivity(): MainActivity
}
