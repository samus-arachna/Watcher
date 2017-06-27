package io.github.umren.watcher.Views.Presenters

import io.github.umren.watcher.Interactors.Tasks.LoadMovieTask
import io.github.umren.watcher.Views.View.MainView


class MainActivityPresenter : Presenter<MainView> {

    lateinit private var mainView: MainView

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onPause() {

    }

    override fun attachView(view: MainView) {
        this.mainView = view
    }

    fun loadMovie() {
        val task = LoadMovieTask(mainView.getActivity())
        task.execute()
    }
}
