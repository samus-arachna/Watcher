package io.github.umren.watcher

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import io.github.umren.watcher.Entities.Movie
import io.github.umren.watcher.Interactors.Db.WatcherDatabaseHelper

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumentation test, which will execute on an Android device.

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    @Throws(Exception::class)
    fun useAppContext() {

        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("io.github.umren.watcher", appContext.packageName)
    }

    @Test
    @Throws(Exception::class)
    fun dbAddFavorite() {

        val appContext = InstrumentationRegistry.getTargetContext()

        val newFav = Movie(
                id = 32,
                poster_path = "http://image.tmdb.org/t/p/w300/6zdPIbSF9vxCBZpntNvishPo5ol.jpg",
                title = "Awesome Movie",
                genres = "Action, Drama, History",
                release_date = "2017",
                overview = "Here will be a discription of some awesome movie that this software can recommend.",
                director = "John Doe",
                cast = arrayListOf("Tom Cruise, Rebecca Ferguson, Brad Pitt", "Blake Lively", "Ryan Reynolds",
                        "Emilia Clarke", "Leonardo DiCaprio", "Jessica Alba"))

        val db = WatcherDatabaseHelper.getInstance(appContext)

        val id = db.addFavorite(newFav)

        assertTrue(id > -1)
    }

    @Test
    @Throws(Exception::class)
    fun dbSelectFavorite() {
        val appContext = InstrumentationRegistry.getTargetContext()

        val movie = WatcherDatabaseHelper.getInstance(appContext).getFavorite(1)

        println(movie)
    }
}
