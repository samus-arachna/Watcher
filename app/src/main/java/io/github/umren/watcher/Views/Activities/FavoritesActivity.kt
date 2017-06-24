package io.github.umren.watcher.Views.Activities

import io.github.umren.watcher.R
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.content_favorites.*


class FavoritesActivity : android.support.v7.app.AppCompatActivity(), android.support.design.widget.NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(io.github.umren.watcher.R.layout.activity_favorites)

        // set toolbar
        val toolbar = findViewById(io.github.umren.watcher.R.id.toolbar) as android.support.v7.widget.Toolbar
        setSupportActionBar(toolbar)

        // set drawer
        val drawer = findViewById(io.github.umren.watcher.R.id.drawer_layout) as android.support.v4.widget.DrawerLayout
        val toggle = android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById(io.github.umren.watcher.R.id.nav_view) as android.support.design.widget.NavigationView
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
        val items = io.github.umren.watcher.Models.WatcherDatabaseHelper.Companion.getInstance(this).getFavorites()
        val itemsAdapter = io.github.umren.watcher.Adapters.FavoritesAdapter(this, items)
        favorites_list.adapter = itemsAdapter

        val listener = android.widget.AdapterView.OnItemClickListener { _, view, _, _ ->
            val i = android.content.Intent(this, MainActivity::class.java)
            i.putExtra("movie_id", view.tag.toString())
            startActivity(i)
        }

        favorites_list.onItemClickListener = listener
    }

    override fun onBackPressed() {
        val drawer = findViewById(io.github.umren.watcher.R.id.drawer_layout) as android.support.v4.widget.DrawerLayout
        if (drawer.isDrawerOpen(android.support.v4.view.GravityCompat.START)) {
            drawer.closeDrawer(android.support.v4.view.GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return true
    }

    override fun onNavigationItemSelected(item: android.view.MenuItem): Boolean {
        when (item.itemId) {
            io.github.umren.watcher.R.id.get_movie -> {
                val i = android.content.Intent(this, MainActivity::class.java)
                startActivity(i)
            }
            io.github.umren.watcher.R.id.favorites -> {

            }
            io.github.umren.watcher.R.id.about -> {
                val dialog = io.github.umren.watcher.Fragments.AboutFragment()
                dialog.show(this.supportFragmentManager, "About")
            }
        }

        val drawer = findViewById(io.github.umren.watcher.R.id.drawer_layout) as android.support.v4.widget.DrawerLayout
        drawer.closeDrawer(android.support.v4.view.GravityCompat.START)
        return true
    }
}
