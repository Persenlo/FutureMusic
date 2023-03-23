package com.pslpro.futuremusic.utils

import kotlin.math.roundToInt

object FormatUtil {
    fun formatMill(duration: Long):String{
        var time = ""
        var minute = duration / 60000
        var seconds: Double = (duration % 60000).toDouble()
        var second = (seconds / 1000).roundToInt()
        if( minute < 10 ){
            time += "0" ;
        }
        time += "$minute:"
        if( second < 10 ){
            time += "0"
        }
        time += second
        return time ;
    }
}