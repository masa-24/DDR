package com.example.android.ddr.model

data class MusicDataBean(val musicName: String,
                         val difficulty: Difficulty)

data class Difficulty(val beginner: String,
                      val basic: String,
                      val difficult: String,
                      val expert: String,
                      val challenge: String)