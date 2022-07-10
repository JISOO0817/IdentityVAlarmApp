package com.jisoo.identityvalarmapp.util

import java.util.*
import com.ibm.icu.util.ChineseCalendar

class CalendarHelper {

    /**
     * 음력 -> 양력 변환
     * **/
    fun lunar2Solar(i: String): String {
        val cc = ChineseCalendar()
        val cal = Calendar.getInstance()

        var date = i.trim()
        if (date.length != 8) {
            if (date.length == 4) {
                date += "0101"
            } else if (date.length == 6) {
                date += "01"
            } else if (date.length > 8) {
                date = date.substring(0, 8)
            } else {
                return ""
            }
        }

        cc.set(ChineseCalendar.EXTENDED_YEAR, Integer.parseInt(date.substring(0, 4)) + 2637);
        cc.set(ChineseCalendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
        cc.set(ChineseCalendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6)));

        cal.timeInMillis = cc.timeInMillis

        val y = cal[Calendar.YEAR]
        val m = cal[Calendar.MONTH] + 1
        val d = cal[Calendar.DAY_OF_MONTH]

        val ret = StringBuffer()
        ret.append(String.format("%04d",y))
        ret.append(String.format("%02d",m))
        ret.append(String.format("%02d",d))

        return ret.toString()
    }

}