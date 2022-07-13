package com.jisoo.identityvalarmapp.setting

import android.widget.TimePicker

class TimePickerUtil {
    companion object {
        fun getHour(timePicker: TimePicker) : Int {
            return timePicker.hour
        }

        fun getMinute(timePicker: TimePicker) : Int {
            return timePicker.minute
        }
    }
}