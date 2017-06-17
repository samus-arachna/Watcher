package io.github.umren.watcher

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.AdapterView
import io.github.umren.watcher.Adapters.FavoritesAdapter
import io.github.umren.watcher.Fragments.AboutFragment
import io.github.umren.watcher.Models.WatcherDatabaseHelper
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.content_favorites.*


class FavoritesActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

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
        navigationView.menu.getItem(1).isChecked = true

        loadFavorites()
    }

    override fun onResume() {
        super.onResume()

        nav_view.menu.getItem(1).isChecked = true
        loadFavorites()
    }

    fun loadFavorites() {
        val items = WatcherDatabaseHelper.getInstance(this).getFavorites()
        val itemsAdapter = FavoritesAdapter(this, items)
        favorites_list.adapter = itemsAdapter

        val listener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val i = Intent(this, MainActivity::class.java)
            i.putExtra("movie_id", view.tag.toString())
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.get_movie -> {
                val i = Intent(this, MainActivity::class.java)
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
