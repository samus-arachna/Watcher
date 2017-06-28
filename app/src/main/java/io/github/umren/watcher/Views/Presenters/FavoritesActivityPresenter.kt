package io.github.umren.watcher.Views.Presenters

import io.github.umren.watcher.Views.View.FavoritesView


class FavoritesActivityPresenter : Presenter<FavoritesView> {

    lateinit private var mainView: FavoritesView

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onPause() {

    }

    override fun attachView(view: FavoritesView) {
        this.mainView = view
    }

}
