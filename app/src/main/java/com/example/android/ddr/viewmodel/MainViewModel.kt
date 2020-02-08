package com.example.android.ddr.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
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
                    list.add(MusicDataBean(it[0], it[1], it[2], it[3], it[4], it[5]))
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

}