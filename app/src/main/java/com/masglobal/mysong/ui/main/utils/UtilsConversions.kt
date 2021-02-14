package com.masglobal.mysong.ui.main.utils

import java.util.concurrent.TimeUnit

object UtilsConversions {

    fun millisToString(time: Double): String? {

        if((time.equals(0))  or (time == null) ){
            return "0:00"
        }

        return String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(time.toLong())!!,
            TimeUnit.MILLISECONDS.toSeconds(time.toLong())!! - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time.toLong() )!!))!!
    }


}