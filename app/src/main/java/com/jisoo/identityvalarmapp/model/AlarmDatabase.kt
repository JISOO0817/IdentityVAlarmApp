package com.jisoo.identityvalarmapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jisoo.identityvalarmapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [CharacInfo::class], version = 1, exportSchema = false)

/**
 *
 * 데이터베이스를 보유하고 앱의 영구 데이터와의 기본 연결을 위한
 * 기본 액세스 포인트 역할을 함함 * */

abstract class AlarmDatabase : RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

    /**
     * 데이터베이스와 연결된 DAO 인스턴스를 앱에 제공함
     * 앱은 DAO를 사용하여 인스턴스로 검색할 수 있게됨
     * */

    companion object {
        private var INSTANCE: AlarmDatabase? = null

        fun getInstance(context: Context): AlarmDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context, AlarmDatabase::class.java, context.packageName
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        initInfo(context)
                    }
                }).build()
            }

            return INSTANCE as AlarmDatabase
        }

        fun initInfo(context: Context) {
            CoroutineScope(Dispatchers.IO).launch {
                for (data in TEST_DATA) {
                    getInstance(context).alarmDao().insert(data)
                }
            }
        }
    }
}

private val TEST_DATA = arrayListOf(
    CharacInfo(1, 0, R.drawable.ic_launcher_background, "의사", "05월10일", true),
    CharacInfo(2, 1, R.drawable.ic_launcher_background, "광대", "07월02일", true),
    CharacInfo(3, 1, R.drawable.ic_launcher_background, "미치코", "07월09일", true),
    CharacInfo(4, 0, R.drawable.ic_launcher_background, "도둑", "04월02일", true),
    CharacInfo(5, 1, R.drawable.ic_launcher_background, "우산의영혼", "08월02일", true),
    CharacInfo(6, 0, R.drawable.ic_launcher_background, "여자아이", "12월02일", true),
    CharacInfo(7, 0, R.drawable.ic_launcher_background, "바텐더", "05월11일", true)
)