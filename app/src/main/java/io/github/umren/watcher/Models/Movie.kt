package io.github.umren.watcher.Models


data class Movie(
        val id: Int,
        val overview: String,
        val title: String,
        val poster_path: String,
        val release_date: String,
        val director: String,
        val genres: String,
        val cast: List<String>)