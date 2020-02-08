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
        setSupportActionBar(findViewById(R.id.app_bar))

        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]

        var listView = findViewById<ListView>(R.id.list_view)
        listView.adapter = MyAdapter(this, )
    }
}

class MyAdapter(private val context: Context, private val data: List<MusicDataBean>) : BaseAdapter() {
    data class ViewHolder(val textView: TextView, val imageButton: ImageButton)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if(convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.list_item, parent)
            viewHolder = ViewHolder(
                view.findViewById(R.id.text),
                view.findViewById(R.id.button)
            )
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.textView.text = data[position].musicName
        viewHolder.imageButton.setOnClickListener {

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
