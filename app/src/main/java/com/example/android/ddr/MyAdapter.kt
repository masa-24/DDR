package com.example.android.ddr

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.android.ddr.model.MusicData

class MyAdapter(private val context: Context, private val data: List<MusicData>) : BaseAdapter() {
    data class ViewHolder(val musicName: TextView,
                          val beginner: TextView,
                          val basic: TextView,
                          val difficult: TextView,
                          val expert: TextView,
                          val challenge: TextView,
                          val imageButton: ImageButton
    )

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
            (it as ImageButton).setImageResource(R.drawable.baseline_star_black_24)
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
