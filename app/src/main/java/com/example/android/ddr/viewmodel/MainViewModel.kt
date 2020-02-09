package com.example.android.ddr.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.android.ddr.R
import com.example.android.ddr.model.Difficulty
import com.example.android.ddr.model.MusicData
import java.io.*

class MainViewModel : ViewModel() {
    fun getMusicDataList(context: Context): List<MusicData> {
        val list: MutableList<MusicData> = ArrayList()
        try {
            val inputStream: InputStream = context.assets.open("file")
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var receiveString: String? = ""
            while (bufferedReader.readLine().also({ receiveString = it }) != null) {
                val tempList = receiveString?.split("_")
                tempList?.let {
                    list.add(MusicData(it[0], Difficulty(it[1], it[2], it[3], it[4], it[5])))
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
     * @param context
     * @param level レベル指定
     * @return レベルで絞り込まれた楽曲リスト
     */
    fun narrowDownLevel(context: Context, level: List<String>): List<MusicData> {
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

    /**
     * 難易度で絞る
     * @param context
     * @param list 楽曲リスト
     * @param difficulty 難易度指定
     * @param level レベル指定
     * @return 難易度で絞り込まれた楽曲リスト
     */
    fun narrowDownDifficulty(context: Context, list: List<MusicData>, difficulty: List<String>, level: List<String>): List<MusicData>  {
        val narrowDownList: MutableList<MusicData> = ArrayList()
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
        // expertとchallengeみたいに難易度が同じことがあるかもしれないので一応重複削除
        narrowDownList.distinct()
        return narrowDownList
    }

}