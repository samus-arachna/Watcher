package io.github.umren.watcher.Http

import io.github.umren.watcher.movieDbKey
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object MovieDbCred {
    val apiKey = movieDbKey
    val url = "https://api.themoviedb.org/3/"
}

interface MovieDb {
    @GET("discover/movie")
    fun loadMovie(@Query("api_key") api: String,
                  @Query("page") page: Int,
                  @Query("vote_count.gte") vote_count: Int,
                  @Query("vote_average.gte") vote_average: Int): Call<DiscoverResponse>

    @GET("movie/{movie_id}/credits")
    fun loadCredits(@Path("movie_id") movie_id: Int?,
                    @Query("api_key") api: String): Call<CreditsResponse>
}