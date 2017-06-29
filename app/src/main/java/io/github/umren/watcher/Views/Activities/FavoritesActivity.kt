package io.github.umren.watcher.Views.Activities

import android.support.design.widget.NavigationView
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import io.github.umren.watcher.Interactors.Db.WatcherDatabaseHelper
import io.github.umren.watcher.R
import io.github.umren.watcher.Views.Adapters.FavoritesAdapter
import io.github.umren.watcher.Views.Fragments.AboutFragment
import io.github.umren.watcher.Views.Presenters.FavoritesActivityPresenter
import io.github.umren.watcher.Views.View.FavoritesView
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.content_favorites.*


class FavoritesActivity : AppCompatActivity(), OnNavigationItemSelectedListener, FavoritesView {

    lateinit internal var Presenter: FavoritesActivityPresenter

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        // set toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // set drawer
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.menu.getItem(1).isChecked = true

        // init presenter
        Presenter = FavoritesActivityPresenter()
        Presenter.attachView(this)

        loadFavorites()
    }

    override fun onResume() {
        super.onResume()

        nav_view.menu.getItem(1).isChecked = true
        loadFavorites()
    }

    fun loadFavorites() {
        val items = WatcherDatabaseHelper.Companion.getInstance(this).getFavorites()
        val itemsAdapter = FavoritesAdapter(this, items)
        favorites_list.adapter = itemsAdapter

        val listener = android.widget.AdapterView.OnItemClickListener { _, view, _, _ ->
            val i = android.content.Intent(this, MainActivity::class.java)
            i.putExtra("movie_id", view.getTag(R.id.tag_movie_id).toString())
            startActivity(i)
        }

        favorites_list.onItemClickListener = listener
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return true
    }

    override fun onNavigationItemSelected(item: android.view.MenuItem): Boolean {
        when (item.itemId) {
            R.id.get_movie -> {
                val i = android.content.Intent(this, MainActivity::class.java)
                startActivity(i)
            }
            R.id.favorites -> {

            }
            R.id.about -> {
                val dialog = AboutFragment()
                dialog.show(this.supportFragmentManager, "About")
            }
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
