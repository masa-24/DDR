package com.example.android.ddr.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import com.example.android.ddr.MyAdapter
import com.example.android.ddr.R
import com.example.android.ddr.model.MusicData
import com.example.android.ddr.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var listView: ListView
    lateinit var musicData: List<MusicData>
    private val selectedDifficultyList: ArrayList<String> = ArrayList()
    private val selectedLevelList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                R.id.favorite -> {
                    val intent = Intent(application, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    false
                }
            }
        }

        // ViewModelをセット
        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]

        listView = this.findViewById<ListView>(R.id.list_view)
        musicData = viewModel.getMusicDataList(this)
        listView.adapter = MyAdapter(this, musicData)
    }

    /**
     * AppBarにメニューを追加する
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    /**
     * メニューが押されたときの動作
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.sort -> {
                // 初期ソート済みだしいらない
                Toast.makeText(applicationContext, "test", Toast.LENGTH_LONG).show()
            }
            R.id.search -> {
                val parent = findViewById<View>(R.id.search)
                val popup = PopupMenu(this, parent)
                val inflater = popup.menuInflater
                inflater.inflate(R.menu.narrow_down, popup.menu)
                popup.setOnMenuItemClickListener{
                    when(it.itemId) {
                        R.id.difficulty -> {
                            selectDifficulty()
                            true
                        }
                        R.id.level -> {
                            selectLevel()
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
                popup.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 難易度で絞るを押されたとき
     * レベルで絞ったリストに難易度でフィルタする
     * 何も選択しないと何もしない
     */
    private fun selectDifficulty() {
        val difficultyArray = arrayOf(
            getString(R.string.beginner),
            getString(R.string.basic),
            getString(R.string.difficult),
            getString(R.string.expert),
            getString(R.string.challenge)
        )
        val checkedItem = booleanArrayOf(false, false, false, false, false)
        AlertDialog.Builder(this)
            .setTitle(R.string.select_difficulty)
            .setMultiChoiceItems(difficultyArray, checkedItem){ dialog, which, check ->
                checkedItem[which] = check
            }
            .setPositiveButton(R.string.next){ dialog, which ->
                if(selectedLevelList.size > 0) {
                    selectedDifficultyList.clear()
                    for (i in difficultyArray.indices) {
                        if (checkedItem[i]) {
                            selectedDifficultyList.add(difficultyArray[i])
                        }
                    }
                    val narrowDownList =
                        viewModel.narrowDownDifficulty(this, musicData, selectedDifficultyList, selectedLevelList)
                    listView.adapter = MyAdapter(this, narrowDownList)
                } else {
                    Toast.makeText(applicationContext, getString(R.string.please_select_level), Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton(R.string.cancel){ dialog, which ->
                dialog.cancel()
            }
            .show()
    }

    /**
     * レベルで絞るを押されたとき
     * 選択されたレベルを含む楽曲リストのみ表示する
     * 何も選択しないと全部表示する
     */
    private fun selectLevel() {
        val levelArray = arrayOf(
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19")
        val checkedItem = booleanArrayOf(
            false, false, false, false, false,
            false, false, false, false, false,
            false, false, false, false, false,
            false, false, false, false)
            AlertDialog.Builder(this)
            .setMultiChoiceItems(levelArray, checkedItem){ dialog, which, check ->
                checkedItem[which] = check
            }
            .setPositiveButton(R.string.ok){ dialog, which ->
                selectedLevelList.clear()
                for(i in levelArray.indices) {
                    if(checkedItem[i]) {
                        selectedLevelList.add(levelArray[i])
                    }
                }
                musicData = viewModel.narrowDownLevel(this, selectedLevelList)
                listView.adapter = MyAdapter(this, musicData)
            }
            .setNegativeButton(R.string.cancel){ dialog, which ->
            }
            .show()
    }
}