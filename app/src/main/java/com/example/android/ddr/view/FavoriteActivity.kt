package com.example.android.ddr.view

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import com.example.android.ddr.MyAdapter
import com.example.android.ddr.R
import com.example.android.ddr.model.MusicData
import com.example.android.ddr.viewmodel.FavoriteViewModel
import com.google.android.material.navigation.NavigationView

class FavoriteActivity : AppCompatActivity() {
    lateinit var viewModel: FavoriteViewModel
    lateinit var listView: ListView
    lateinit var favoriteMusicData: List<MusicData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        // ツールバーをセット
        val toolbar = findViewById<Toolbar>(R.id.app_bar)
        setSupportActionBar(toolbar)

        // ドロワーをセット
        val drawer = findViewById<DrawerLayout>(R.id.drawer)
        val actionBarToggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)
        drawer.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
        val navigation = findViewById<NavigationView>(R.id.navigationView)
        navigation.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.musicList -> {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    false
                }
            }
        }

        // ViewModelをセット
        viewModel = ViewModelProviders.of(this)[FavoriteViewModel::class.java]

        listView = this.findViewById<ListView>(R.id.list_view)
        favoriteMusicData = viewModel.getFavoriteMusicData()
        listView.adapter = MyAdapter(this, favoriteMusicData)
    }
}