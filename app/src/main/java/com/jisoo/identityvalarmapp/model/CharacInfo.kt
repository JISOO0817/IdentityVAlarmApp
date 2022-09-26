package com.jisoo.identityvalarmapp.model

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import android.text.TextUtils
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jisoo.identityvalarmapp.alarm.AlarmBroadcast
import com.jisoo.identityvalarmapp.alarm.App
import com.jisoo.identityvalarmapp.util.Const.Companion.JOB_KEY
import com.jisoo.identityvalarmapp.util.Const.Companion.TIME_SP
import com.jisoo.identityvalarmapp.util.Const.Companion.UID_KEY
import kotlinx.android.parcel.Parcelize
import java.util.*


/**
 * 앱 데이터베이스의 테이블을 나타냄
 * */

@Entity
@Parcelize
data class CharacInfo(
    @PrimaryKey val uid: Int,
    @ColumnInfo var category: Int,
    @ColumnInfo var img: String,
    @ColumnInfo var job: Int,
    @ColumnInfo var birth: String
) : Parcelable