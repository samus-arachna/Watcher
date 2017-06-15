package io.github.umren.watcher.Adapters

import android.widget.ArrayAdapter
import io.github.umren.watcher.Models.Movie
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.umren.watcher.R


class FavoritesAdapter(context: Context, movies: ArrayList<Movie>) : ArrayAdapter<Movie>(context, 0, movies) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val retView: View = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false)
        val movie = getItem(position)

        val tvName = retView.findViewById(R.id.movie_title) as TextView
        tvName.text = movie.title
        retView.tag = movie.id

        return retView
    }
}