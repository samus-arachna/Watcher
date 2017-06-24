package io.github.umren.watcher.Interactors.Db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import io.github.umren.watcher.Entities.Movie


class WatcherDatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        var sInstance: WatcherDatabaseHelper? = null

        internal val DB_NAME = "watcher"
        internal val DB_VERSION = 1
        internal val TAG = "WatcherDatabaseHelper"

        val TABLE_FAVORITE = "FAVORITES"
        val FIELD_ID = "_id"
        val FIELD_MOVIE_ID = "MOVIE_ID"
        val FIELD_IMAGELINK = "IMAGELINK"
        val FIELD_TITLE = "TITLE"
        val FIELD_GENRE = "GENRE"
        val FIELD_YEAR = "YEAR"
        val FIELD_DESC = "DESC"
        val FIELD_DIRECTOR = "DIRECTOR"
        val FIELD_ACTORS = "ACTORS"

        fun getInstance(context: Context): WatcherDatabaseHelper {

            if (sInstance == null) {
                sInstance = WatcherDatabaseHelper(context)
            }

            return sInstance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE ${TABLE_FAVORITE} (" +
                "${FIELD_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${FIELD_MOVIE_ID} INTEGER UNIQUE, " +
                "${FIELD_IMAGELINK} TEXT, " +
                "${FIELD_TITLE} TEXT, " +
                "${FIELD_GENRE} TEXT, " +
                "${FIELD_YEAR} INTEGER, " +
                "${FIELD_DESC} TEXT, " +
                "${FIELD_DIRECTOR} TEXT, " +
                "${FIELD_ACTORS} TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${TABLE_FAVORITE}")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${TABLE_FAVORITE}")
        onCreate(db)
    }

    fun addFavorite(movie: Movie): Long {
        var id: Long = 0
        val db = writableDatabase

        try {
            val values = ContentValues()
            values.put(FIELD_MOVIE_ID, movie.id)
            values.put(FIELD_IMAGELINK, movie.poster_path)
            values.put(FIELD_TITLE, movie.title)
            values.put(FIELD_GENRE, movie.genres)
            values.put(FIELD_YEAR, movie.release_date)
            values.put(FIELD_DESC, movie.overview)
            values.put(FIELD_DIRECTOR, movie.director)
            values.put(FIELD_ACTORS, movie.cast.joinToString(", "))

            id = db.insertOrThrow(TABLE_FAVORITE, null, values)
        } catch (e: Exception) {
            Log.d(TAG, "Error while trying to insert into favorites: $e")
        }

        return id
    }

    fun removeFavorite(id: Int): Boolean {
        val db = writableDatabase

        return db.delete(TABLE_FAVORITE, "${FIELD_MOVIE_ID} = $id", null) > 0
    }

    fun isFavorite(id: Int): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM ${TABLE_FAVORITE} WHERE ${FIELD_MOVIE_ID} = $id"
        val cursor = db.rawQuery(query, null)

        try {
            if (cursor.count > 0) {
                return true
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error while trying to check if favorite exists: $e")
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }

        return false
    }

    fun getFavorites(): ArrayList<Movie> {
        val db = readableDatabase
        val query = "SELECT * FROM ${TABLE_FAVORITE}"
        val cursor = db.rawQuery(query, null)

        val movies = ArrayList<Movie>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val movieItem = Movie(
                            id = cursor.getInt(cursor.getColumnIndex(FIELD_MOVIE_ID)),
                            overview = cursor.getString(cursor.getColumnIndex(FIELD_DESC)),
                            title = cursor.getString(cursor.getColumnIndex(FIELD_TITLE)),
                            poster_path = cursor.getString(cursor.getColumnIndex(FIELD_IMAGELINK)),
                            release_date = cursor.getString(cursor.getColumnIndex(FIELD_YEAR)),
                            director = cursor.getString(cursor.getColumnIndex(FIELD_DIRECTOR)),
                            genres = cursor.getString(cursor.getColumnIndex(FIELD_GENRE)),
                            cast = cursor.getString(cursor.getColumnIndex(FIELD_ACTORS)).split(", "))

                    movies.add(movieItem)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error while trying to fetch favorites: $e")
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }

        return movies
    }

    fun getFavorite(id: Int): Movie? {
        val db = readableDatabase
        val query = "SELECT * FROM ${TABLE_FAVORITE} WHERE ${FIELD_MOVIE_ID} = $id"
        val cursor = db.rawQuery(query, null)
        var movie: Movie? = null

        try {
            cursor.moveToFirst()

            movie = Movie(
                    id = cursor.getInt(cursor.getColumnIndex(FIELD_MOVIE_ID)),
                    overview = cursor.getString(cursor.getColumnIndex(FIELD_DESC)),
                    title = cursor.getString(cursor.getColumnIndex(FIELD_TITLE)),
                    poster_path = cursor.getString(cursor.getColumnIndex(FIELD_IMAGELINK)),
                    release_date = cursor.getString(cursor.getColumnIndex(FIELD_YEAR)),
                    director = cursor.getString(cursor.getColumnIndex(FIELD_DIRECTOR)),
                    genres = cursor.getString(cursor.getColumnIndex(FIELD_GENRE)),
                    cast = cursor.getString(cursor.getColumnIndex(FIELD_ACTORS)).split(", "))

        } catch (e: Exception) {
            Log.d(TAG, "Error while trying to fetch favorite: $e")
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }

        return movie
    }
}