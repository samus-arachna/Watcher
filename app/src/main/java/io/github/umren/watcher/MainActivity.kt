package io.github.umren.watcher

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import io.github.umren.watcher.Models.Movie
import io.github.umren.watcher.Models.WatcherDatabaseHelper
import io.github.umren.watcher.Tasks.LoadMovieTask
import android.R.attr.duration
import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var loadedMovie: Movie? = null
    var isLoading: Boolean = false
    var menuFavBtn: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // set drawer
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.menu.getItem(0).isChecked = true

        loadMovie()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val movieId = intent?.getStringExtra("movie_id")?.toInt() ?: return
        val db = WatcherDatabaseHelper.getInstance(this)
        val movie = db.getFavorite(movieId) ?: return

        loadView(movie)
        setBtnFavoriteIcon(movie)
    }

    override fun onResume() {
        super.onResume()
        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.menu.getItem(0).isChecked = true
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            moveTaskToBack(true)
            //super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        menuFavBtn = menu.findItem(R.id.action_favorite)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (isLoading) return false

        when (item.itemId) {
            R.id.action_favorite -> {
                clickBtnFavorite(item)
                return true
            }
            R.id.action_refresh -> {
                val view = findViewById(R.id.action_refresh)
                val rotation = AnimationUtils.loadAnimation(this, R.anim.rotation)
                view.startAnimation(rotation)
                loadMovie()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.get_movie -> {

            }
            R.id.favorites -> {
                val i = Intent(this, FavoritesActivity::class.java)
                startActivity(i)
            }
            R.id.donate -> {

            }
            R.id.about -> {

            }
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun loadMovie() {
        val task = LoadMovieTask(this)

        task.execute()
    }

    fun clickBtnFavorite(item: MenuItem) {
        val movie = loadedMovie as Movie
        val db = WatcherDatabaseHelper.getInstance(this)

        if (db.isFavorite(movie.id)) {
            db.removeFavorite(movie.id)
            item.setIcon(R.drawable.ic_favorite_border_white_24dp)
            Toast.makeText(this, "Movie removed from favorites", duration).show()
        } else {
            db.addFavorite(movie)
            item.setIcon(R.drawable.ic_favorite_white_24dp)
            Toast.makeText(this, "Movie added to favorites", duration).show()
        }
    }

    fun loadView(movie: Movie) {
        movie_actors_list.removeAllViews()

        movie_title.text = movie.title
        movie_genre.text = movie.genres
        movie_year.text = movie.release_date
        movie_desc.text = movie.overview
        movie_director.text = movie.director
        movie.cast.forEach {
            val tv = TextView(this)
            tv.text = it
            movie_actors_list.addView(tv)
        }
        Picasso.with(this).load(movie.poster_path).into(movie_preview_image)

        showView()

        loadedMovie = movie
    }

    fun setBtnFavoriteIcon(movie: Movie) {
        val db = WatcherDatabaseHelper.getInstance(this)

        if (db.isFavorite(movie.id)) {
            menuFavBtn?.setIcon(R.drawable.ic_favorite_white_24dp)
        } else {
            menuFavBtn?.setIcon(R.drawable.ic_favorite_border_white_24dp)
        }
    }

    fun hideView() {
        movie_layout.visibility = View.INVISIBLE
        movie_progress.visibility = View.VISIBLE
        isLoading = true
    }

    fun showView() {
        movie_layout.visibility = View.VISIBLE
        movie_progress.visibility = View.INVISIBLE
        isLoading = false
    }

    fun cleanView() {
        movie_genre.text = getString(R.string.default_value)
        movie_desc.text = resources.getString(R.string.default_movie_desc)
        movie_actors_list.removeAllViews()
    }
}
