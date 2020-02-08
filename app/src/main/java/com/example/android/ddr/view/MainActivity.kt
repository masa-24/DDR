package com.example.android.ddr.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.example.android.ddr.R
import com.example.android.ddr.model.MusicDataBean
import com.example.android.ddr.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]

        val listView = findViewById<ListView>(R.id.list_view)
        listView.adapter = MyAdapter(this, viewModel.getMusicDataList(this))
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
        viewHolder.beginner.text = data[position].beginner.toString()
        viewHolder.basic.text = data[position].basic.toString()
        viewHolder.difficult.text = data[position].difficult.toString()
        viewHolder.expert.text = data[position].expert.toString()
        viewHolder.challenge.text = data[position].challenge.toString()
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
