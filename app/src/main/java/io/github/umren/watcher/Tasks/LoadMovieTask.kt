package io.github.umren.watcher.Tasks


import android.os.AsyncTask
import io.github.umren.watcher.Models.Movie
import io.github.umren.watcher.Http.load
import io.github.umren.watcher.Views.Activities.MainActivity


class LoadMovieTask(val ctx: MainActivity) : AsyncTask<Void, Void, Void>() {

    var movie: Movie? = null

    override fun onPreExecute() {
        super.onPreExecute()

        ctx.cleanView()
        ctx.hideView()
    }

    override fun doInBackground(vararg params: Void?): Void? {
        movie = load()

        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)

        if (movie == null) {
            ctx.showError()
            return
        }
        val movie = movie as Movie

        ctx.loadView(movie)
        ctx.setBtnFavoriteIcon(movie)
    }
}