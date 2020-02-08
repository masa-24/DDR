package com.example.android.ddr

import android.content.Context
import android.util.Log
import com.example.android.ddr.model.MusicDataBean
import java.io.*


class Util {
    companion object {
        fun readFromFile(context: Context): List<MusicDataBean> {
            val list: List<MusicDataBean> = ArrayList()
            var ret = ""
            try {
                val inputStream: InputStream = context.openFileInput(".txt")
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = ""
                val stringBuilder = StringBuilder()
                while (bufferedReader.readLine().also({ receiveString = it }) != null) {
                    stringBuilder.append("\n").append(receiveString)
                }
                inputStream.close()
                ret = stringBuilder.toString()
            } catch (e: FileNotFoundException) {
                Log.e("read from file", "File not found: ${e.printStackTrace()}")
            } catch (e: IOException) {
                Log.e("read from file", "Can not read file: ${e.printStackTrace()}")
            }
            return list
        }
    }
}