package com.example.android.ddr.viewmodel

import androidx.lifecycle.ViewModel
import com.example.android.ddr.model.MusicData

class FavoriteViewModel : ViewModel() {
    /**
     * お気に入りの曲リストを返す
     */
    fun getFavoriteMusicData(): List<MusicData> {
        val list = ArrayList<MusicData>()
        return list
    }
}