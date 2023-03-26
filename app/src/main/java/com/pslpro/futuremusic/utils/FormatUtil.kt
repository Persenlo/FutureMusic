package com.pslpro.futuremusic.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

object FormatUtil {
    //毫秒转分钟
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

    //纯数字转为以万为单位
    fun formatToTenThousand(number: Int):String{
        if (number/10000 < 0){
            return number.toString()
        }else{
            val format = DecimalFormat("0.##")
            //未保留小数的舍弃规则，RoundingMode.FLOOR表示直接舍弃。
            format.roundingMode = RoundingMode.FLOOR
            return (format.format(number.toFloat()/10000)).toString()+"万"
        }
    }
}