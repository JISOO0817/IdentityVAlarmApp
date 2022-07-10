package com.jisoo.identityvalarmapp.setting

import android.os.Build
import android.widget.TimePicker

class TimePickerUtil {
    companion object {
        fun getHour(timePicker: TimePicker) : Int {
            return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.hour
            } else {
                timePicker.currentHour
            }
        }

        fun getMinute(timePicker: TimePicker) : Int {
            return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.minute
            }else {
                timePicker.currentMinute
            }
        }
    }
}