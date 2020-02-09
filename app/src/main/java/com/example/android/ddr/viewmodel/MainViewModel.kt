package com.example.android.ddr.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.android.ddr.R
import com.example.android.ddr.model.Difficulty
import com.example.android.ddr.model.MusicDataBean
import java.io.*

class MainViewModel : ViewModel() {
    fun getMusicDataList(context: Context): List<MusicDataBean> {
        val list: MutableList<MusicDataBean> = ArrayList()
        try {
            val inputStream: InputStream = context.assets.open("file")
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var receiveString: String? = ""
            while (bufferedReader.readLine().also({ receiveString = it }) != null) {
                val tempList = receiveString?.split("_")
                tempList?.let {
                    list.add(MusicDataBean(it[0], Difficulty(it[1], it[2], it[3], it[4], it[5])))
                }
            }
            inputStream.close()
        } catch (e: FileNotFoundException) {
            Log.e("read from file", "File not found: ${e.printStackTrace()}")
        } catch (e: IOException) {
            Log.e("read from file", "Can not read file: ${e.printStackTrace()}")
        }
        return list
    }

    /**
     * レベルで絞る
     */
    fun narrowDownLevel(context: Context, level: List<String>): List<MusicDataBean> {
        val list = getMusicDataList(context)
        if(level.isEmpty()) {
            return list
        }
        return list.filter {
            level.contains(it.difficulty.beginner) ||
                    level.contains(it.difficulty.basic) ||
                    level.contains(it.difficulty.difficult) ||
                    level.contains(it.difficulty.expert) ||
                    level.contains(it.difficulty.challenge) }
    }

    fun narrowDownDifficulty(context: Context, list: List<MusicDataBean>, difficulty: List<String>, level: List<String>): List<MusicDataBean>  {
        val narrowDownList: MutableList<MusicDataBean> = ArrayList()
        if(difficulty.isEmpty() || level.isEmpty()) {
            return list
        }
        if(difficulty.contains(context.getString(R.string.beginner))) {
            narrowDownList.addAll(list.filter { level.contains(it.difficulty.beginner) })
        }
        if(difficulty.contains(context.getString(R.string.basic))) {
            narrowDownList.addAll(list.filter { level.contains(it.difficulty.basic) })
        }
        if(difficulty.contains(context.getString(R.string.difficult))) {
            narrowDownList.addAll(list.filter { level.contains(it.difficulty.difficult) })
        }
        if(difficulty.contains(context.getString(R.string.expert))) {
            narrowDownList.addAll(list.filter { level.contains(it.difficulty.expert) })
        }
        if(difficulty.contains(context.getString(R.string.challenge))) {
            narrowDownList.addAll(list.filter { level.contains(it.difficulty.challenge) })
        }
        return narrowDownList
    }

}