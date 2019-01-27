package com.toshiro97.talkwithme.untils

import android.provider.Settings

class Helper {
    fun subTime(time: String): String {
        val str = StringBuilder(time)
        str.insert(4,"-")
        str.insert(6,"-")
        str.insert(8,"/t")
        str.insert(10,":")

        return str.toString()
    }
}