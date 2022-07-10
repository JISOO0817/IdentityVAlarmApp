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


@Database(entities = [CharacInfo::class],version = 1,exportSchema = false)

/**
 *
 * 데이터베이스를 보유하고 앱의 영구 데이터와의 기본 연결을 위한
 * 기본 액세스 포인트 역할을 함함 * */
abstract class AlarmDatabase : RoomDatabase() {

    abstract fun alarmDao(): AlarmDao
//    abstract fun timeDao(): TimeDao

    /**
     * 데이터베이스와 연결된 DAO 인스턴스를 앱에 제공함
     * 앱은 DAO를 사용하여 인스턴스로 검색할 수 있게됨
     * */

    companion object {
        private var INSTANCE: AlarmDatabase? = null
//        val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("UPDATE CharacInfo SET birth = '09월09일' WHERE birth= '05월10일'")
//            }


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
                for (data in CHARACTER_DATA) {
                    getInstance(context).alarmDao().insert(data)
                }

//                for (time in TIME_DATA) {
//                    getInstance(context).timeDao().insert(time)
//                }
            }
        }
    }
}

//private val TIME_DATA = arrayListOf(
//    Time(1,13,0)
//)

private val CHARACTER_DATA = arrayListOf(
    CharacInfo(1, 0, R.drawable.enchantress, "주술사", "0103"),
    CharacInfo(2, 0, R.drawable.lawyer, "변호사", "0112"),
    CharacInfo(3, 0, R.drawable.eye, "맹인", "0113"),
    CharacInfo(4, 0, R.drawable.priestess, "샤먼", "0201"),
    CharacInfo(5, 0, R.drawable.gravekeeper, "묘지기", "0213"),
    CharacInfo(6, 0, R.drawable.barmaid, "바텐더", "0214"),
    CharacInfo(7, 0, R.drawable.explorer, "모험가", "0301"),
    CharacInfo(8, 0, R.drawable.doctor, "의사", "0317"),
    CharacInfo(9, 0, R.drawable.prospector, "탐사원", "0319"),
    CharacInfo(10, 0, R.drawable.coordinator, "공군", "0403"),
    CharacInfo(11, 0, R.drawable.wildling, "야만인", "0430"),
    CharacInfo(12, 0, R.drawable.thief, "도둑", "0507"),
    CharacInfo(13, 0, R.drawable.embalmer, "납관사", "0511"),
    CharacInfo(14, 0, R.drawable.forward, "포워드", "0515"),
    CharacInfo(15, 0, R.drawable.acrobat, "곡예사", "0601"),
    CharacInfo(16, 0, R.drawable.dancer, "무희", "0609"),
    CharacInfo(17, 0, R.drawable.magician, "마술사", "0704"),
    CharacInfo(18, 0, R.drawable.prisoner, "죄수", "0710"),
    CharacInfo(19, 0, R.drawable.mercenary, "용병", "0723"),
    CharacInfo(20, 0, R.drawable.perfumer, "조향사", "0825"),
    CharacInfo(21, 0, R.drawable.mechanic, "기계공", "0913"),
    CharacInfo(22, 0, R.drawable.first_officer, "항해사", "0924"),
    CharacInfo(23, 0, R.drawable.seer, "선지자", "1031"),
    CharacInfo(24, 0, R.drawable.lucky_guy, "행운아", "1122"),
    CharacInfo(25, 0, R.drawable.gardner, "정원사", "1221"),
    CharacInfo(26, 0, R.drawable.entomologis, "곤충학자", "1222"),
    CharacInfo(27, 0, R.drawable.postman, "우편배달부", "1225"),
    CharacInfo(28, 0, R.drawable.cowboy, "카우보이", "1227"),

    CharacInfo(100, 1, R.drawable.spider, "거미", "0102"),
    CharacInfo(101, 1, R.drawable.haster, "하스터", "0124"),
    CharacInfo(102, 1, R.drawable.michiko, "미치코", "0218"),
    CharacInfo(103, 1, R.drawable.photo, "사진사", "0311"),
    CharacInfo(104, 1, R.drawable.ulbo, "울보", "0425"),
    CharacInfo(105, 1, R.drawable.noru, "사냥터지기", "0521"),
    CharacInfo(106, 1, R.drawable.umbrella, "우산의영혼", "0715"),
    CharacInfo(107, 1, R.drawable.rhkdeo, "광대", "0804"),
    CharacInfo(108, 1, R.drawable.ripper, "리퍼", "0807"),
    CharacInfo(109, 1, R.drawable.bongbog, "수위 26호", "0808"),
    CharacInfo(110, 1, R.drawable.sado, "'사도'", "0811"),
    CharacInfo(111, 1, R.drawable.kkumma, "꿈의 마녀", "1002"),
    CharacInfo(112, 1, R.drawable.byeall, "바이올리니스트", "1027"),
    CharacInfo(113, 1, R.drawable.mari, "블러디 퀸", "1102"),
    CharacInfo(114, 1, R.drawable.domabaem, "재앙의 도마뱀", "1113"),
    CharacInfo(115, 1, R.drawable.jogak, "조각가", "1117"),
    CharacInfo(116, 1, R.drawable.halbae, "광기의 눈", "1127"),
    CharacInfo(117, 1, R.drawable.gong, "공장장", "1221")
)
