package io.github.umren.watcher

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import io.github.umren.watcher.Http.randomInt
import io.github.umren.watcher.Models.Movie
import io.github.umren.watcher.Models.WatcherDatabaseHelper

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
                poster_path = "http://image.com",
                title = "Test Drama Movie",
                genres = "Drama",
                release_date = "1995",
                overview = "This is a test description for Test Drama Movie",
                director = "Some good director",
                cast = arrayListOf("Actor 1, Actor 2, Actor 3"))

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
