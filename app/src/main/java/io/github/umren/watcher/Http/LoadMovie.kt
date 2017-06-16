package io.github.umren.watcher.Http

import android.util.Log
import io.github.umren.watcher.Models.Movie
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


// CALLS
fun load(): Movie? {
    val pageResult = randomInt(1, 1000)
    val movieResult = randomInt(0, 19)

    val retrofit = Retrofit.Builder()
            .baseUrl(MovieDbCred.url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val movieDb = retrofit.create(MovieDb::class.java)

    try {
        val movie = movieDb.loadMovie(
                api = MovieDbCred.apiKey, page = pageResult, vote_count = 10, vote_average = 5)
                .execute().body() ?: return null

        val credits = movieDb.loadCredits(
                api = MovieDbCred.apiKey, movie_id = movie.results[movieResult].id)
                .execute().body() ?: return null

        return transform(movie.results[movieResult], credits)
    } catch (e : Exception) {
        Log.d("LoadException", "Could not load movie: $e")
    }

    return null
}

fun transform(movie: DiscoverResult, credits: CreditsResponse): Movie {
    val cast = credits.cast.take(10).map { it.name }
    val genre = movie.genre_ids.map { genres[it] ?: "Unknown" }.joinToString(", ")
    val releaseDate = movie.release_date.split("-").first()
    val director = credits.crew.filter { it.job == "Director" }.first().name
    val imagePath = "http://image.tmdb.org/t/p/w300" + movie.poster_path

    val movieItem = Movie(
            id = movie.id,
            overview = movie.overview,
            title = movie.title,
            poster_path = imagePath,
            release_date = releaseDate,
            director = director,
            genres = genre,
            cast = cast
    )

    return movieItem
}

fun randomInt(min: Int, max: Int): Int {
    return min + (Math.random() * ((max - min) + 1)).toInt()
}

data class DiscoverResponse(
        val page: Int,
        val results: List<DiscoverResult>,
        val total_results: Int,
        val total_pages: Int)

data class DiscoverResult(
        val id: Int,
        val overview: String,
        val title: String,
        val poster_path: String,
        val release_date: String,
        val genre_ids: List<Int>)

data class CreditsResponse(
        val id: Int,
        val cast: List<CastResult>,
        val crew: List<CrewRsult>)

data class CastResult(
        val character: String,
        val name: String)

data class CrewRsult(
        val job: String,
        val name: String)

val genres = hashMapOf(
        28 to "Action",
        12 to "Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        9648 to "Mystery",
        10749 to "Romance",
        878 to "Science Fiction",
        10770 to "TV Favorite",
        53 to "Thriller",
        10752 to "War",
        37 to "Western")