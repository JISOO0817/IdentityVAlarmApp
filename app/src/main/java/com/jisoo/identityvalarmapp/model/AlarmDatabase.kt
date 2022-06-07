package com.jisoo.identityvalarmapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bumptech.glide.Glide
import com.jisoo.identityvalarmapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


@Database(entities = [CharacInfo::class], version = 2, exportSchema = false)

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
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("UPDATE CharacInfo SET birth = '09월09일' WHERE birth= '05월10일'")
            }

        }

        fun getInstance(context: Context): AlarmDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context, AlarmDatabase::class.java, context.packageName
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        initInfo(context)
                    }
                }).addMigrations(MIGRATION_1_2)
                    .build()
//                addMigrations(MIGRATION_1_2)  값 변경시 버전도 올려주어야함

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

    CharacInfo(1, 0, R.drawable.enchantress, "주술사", "01월03일", true),
    CharacInfo(2, 0, R.drawable.lawyer, "변호사", "01월12일", true),
    CharacInfo(3, 0, R.drawable.eye, "맹인", "01월13일", true),
    CharacInfo(4, 0, R.drawable.priestess, "샤먼", "02월01일", true),
    CharacInfo(5,0, R.drawable.gravekeeper,"묘지기","02월13일",true),
    CharacInfo(6,0,R.drawable.barmaid,"바텐더","02월14일",true),
    CharacInfo(7, 0, R.drawable.explorer, "모험가", "03월01일", true),
    CharacInfo(8, 0, R.drawable.doctor, "의사", "03월17일", true),
    CharacInfo(9, 0, R.drawable.prospector, "탐사원", "03월19일", true),
    CharacInfo(10, 0, R.drawable.coordinator, "공군", "04월03일", true),
    CharacInfo(11, 0, R.drawable.wildling, "야만인", "04월30일", true),
    CharacInfo(12, 0, R.drawable.thief, "도둑", "05월07일", true),
    CharacInfo(13, 0, R.drawable.embalmer, "납관사", "05월11일", true),
    CharacInfo(14, 0, R.drawable.forward, "포워드", "05월15일", true),
    CharacInfo(15, 0, R.drawable.acrobat, "곡예사", "06월01일", true),
    CharacInfo(16, 0, R.drawable.dancer, "무희", "06월09일", true),
    CharacInfo(17, 0, R.drawable.magician, "마술사", "07월04일", true),
    CharacInfo(18,0, R.drawable.prisoner,"죄수","07월10일",true),
    CharacInfo(19, 0, R.drawable.mercenary, "용병", "07월23일", true),
    CharacInfo(20, 0, R.drawable.perfumer, "조향사", "08월25일", true),
    CharacInfo(21, 0, R.drawable.mechanic, "기계공", "09월13일", true),
    CharacInfo(22, 0, R.drawable.first_officer, "항해사", "09월24일", true),
    CharacInfo(23, 0, R.drawable.seer, "선지자", "10월31일", true),
    CharacInfo(24,0,R.drawable.lucky_guy,"행운아","11월22일",true),
    CharacInfo(25, 0, R.drawable.gardner, "정원사", "12월21일", true),
    CharacInfo(26,0, R.drawable.entomologis,"곤충학자","12월22일",true),
    CharacInfo(27,0,R.drawable.postman,"우편배달부","12월25일",true),
    CharacInfo(28, 0, R.drawable.cowboy, "카우보이", "12월27일", true),

)
