package io.github.umren.watcher.Views.Adapters

import android.widget.ArrayAdapter
import io.github.umren.watcher.Entities.Movie
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.umren.watcher.R
import android.app.Activity




class FavoritesAdapter(context: Context, private val movies: ArrayList<Movie>) : ArrayAdapter<Movie>(context, 0, movies) {

    internal class ViewHolder {
        var txtItem: TextView? = null
        var movieNumber: TextView? = null
    }

    override fun getItem(position: Int): Movie {
        return movies[position]
    }

    override fun getCount(): Int {
        return movies.size
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val retView: View
        val viewHolder: ViewHolder

        if (convertView == null) {

            // inflate the layout
            val inflater = (context as Activity).layoutInflater
            retView = inflater.inflate(R.layout.item_favorite, parent, false)

            // well set up the ViewHolder
            viewHolder = ViewHolder()
            viewHolder.txtItem = retView.findViewById(R.id.movie_title) as TextView
            viewHolder.movieNumber = retView.findViewById(R.id.movie_number) as TextView
            retView.setTag(R.id.tag_viewholder, viewHolder)
        } else {
            retView = convertView
            viewHolder = retView.getTag(R.id.tag_viewholder) as ViewHolder
        }

        viewHolder.txtItem?.text = getItem(position).title
        viewHolder.movieNumber?.text = (position + 1).toString()
        retView.setTag(R.id.tag_movie_id, getItem(position).id)

        return retView
    }
}