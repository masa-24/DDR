package com.example.android.ddr.model

class PlayerScoreBean(var isclear: Boolean, var score: Int) {
    fun rank(): String {
        if(isclear) {
            if (score > 990000) {
                return "AAA"
            } else if (score > 950000) {
                return "AA+"
            } else if (score > 900000) {
                return "AA"
            } else if (score > 890000) {
                return "AA-"
            } else if (score > 850000) {
                return "A+"
            } else if (score > 800000) {
                return "A"
            } else if (score > 790000) {
                return "A-"
            } else if (score > 750000) {
                return "B+"
            } else if (score > 700000) {
                return "B"
            } else if (score > 690000) {
                return "B-"
            } else if (score > 650000) {
                return "C+"
            } else if (score > 600000) {
                return "C"
            } else if (score > 590000) {
                return "C-"
            } else if (score > 550000) {
                return "D+"
            } else {
                return "D"
            }
        } else {
            return "Failed"
        }
    }
}