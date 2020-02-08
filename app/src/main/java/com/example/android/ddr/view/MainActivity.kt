package com.example.android.ddr.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.example.android.ddr.R
import com.example.android.ddr.model.MusicDataBean
import com.example.android.ddr.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var listView: ListView
    lateinit var musicData: List<MusicDataBean>
    private val selectedDifficultyList: ArrayList<String> = ArrayList()
    private val selectedLevelList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.app_bar)
        setSupportActionBar(toolbar)

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
     */
    private fun selectDifficulty() {
        val difficultyArray = arrayOf(
            getString(R.string.all),
            getString(R.string.BEGINNER),
            getString(R.string.BASIC),
            getString(R.string.DIFFICULT),
            getString(R.string.EXPERT),
            getString(R.string.CHALLENGE)
        )
        val checkedItem = booleanArrayOf(false, false, false, false, false, false)
        AlertDialog.Builder(this)
            .setTitle(R.string.select_difficulty)
            .setMultiChoiceItems(difficultyArray, checkedItem){ dialog, which, check ->
                checkedItem[which] = check
            }
            .setPositiveButton(R.string.next){ dialog, which ->
                selectedDifficultyList.clear()
                for(i in difficultyArray.indices) {
                    if(checkedItem[i]) {
                        selectedDifficultyList.add(difficultyArray[i])
                    }
                }
                musicData = viewModel.narrowDownDifficulty(this, musicData, selectedDifficultyList, selectedLevelList)
                listView.adapter = MyAdapter(this, musicData)
            }
            .setNegativeButton(R.string.cancel){ dialog, which ->
                dialog.cancel()
            }
            .show()
    }

    /**
     * レベルで絞るを押されたとき
     */
    private fun selectLevel() {
        val levelArray = arrayOf(
            getString(R.string.all),
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19")
        val checkedItem = booleanArrayOf(
            false, false, false, false, false,
            false, false, false, false, false,
            false, false, false, false, false,
            false, false, false, false, false)
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
                musicData = viewModel.narrowDownLevel(this, musicData, selectedLevelList)
                listView.adapter = MyAdapter(this, musicData)
            }
            .setNegativeButton(R.string.cancel){ dialog, which ->
            }
            .show()
    }
}

class MyAdapter(private val context: Context, private val data: List<MusicDataBean>) : BaseAdapter() {
    data class ViewHolder(val musicName: TextView,
                          val beginner: TextView,
                          val basic: TextView,
                          val difficult: TextView,
                          val expert: TextView,
                          val challenge: TextView,
                          val imageButton: ImageButton)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if(convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.list_item, null)
            viewHolder = ViewHolder(
                view.findViewById(R.id.text),
                view.findViewById(R.id.beginner),
                view.findViewById(R.id.basic),
                view.findViewById(R.id.difficult),
                view.findViewById(R.id.expert),
                view.findViewById(R.id.challenge),
                view.findViewById(R.id.button)
            )
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.musicName.text = data[position].musicName
        viewHolder.beginner.text = data[position].difficulty.beginner
        viewHolder.basic.text = data[position].difficulty.basic
        viewHolder.difficult.text = data[position].difficulty.difficult
        viewHolder.expert.text = data[position].difficulty.expert
        viewHolder.challenge.text = data[position].difficulty.challenge
        viewHolder.imageButton.setOnClickListener {
            // TODO ふぁぼれるようにする
        }
        return view
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getItem(position: Int): Any? {
        return data[position]
    }

    override fun getCount(): Int {
        return data.size
    }
}
