package com.jisoo.identityvalarmapp.model

import android.annotation.SuppressLint
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
import java.util.*
import kotlin.collections.ArrayList
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
                database.execSQL("DROP TABLE IF EXISTS CharacInfo_0")
                database.execSQL("ALTER TABLE CharacInfo RENAME TO CharacInfo_0")
                /* SQL ON NEXT LINE COPIED FROM GENERATED JAVA */
                database.execSQL("CREATE TABLE IF NOT EXISTS CharacInfo(uid INTEGER PRIMARY KEY NOT NULL, category INTEGER NOT NULL, img INTEGER NOT NULL, job INTEGER NOT NULL, birth TEXT NOT NULL)")
                database.execSQL("INSERT INTO CharacInfo SELECT * FROM CharacInfo_0")
                database.execSQL("DROP TABLE IF EXISTS CharacInfo_0")
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
            }

            return INSTANCE as AlarmDatabase
        }

//        @SuppressLint("ConstantLocale")
//        val language = Locale.getDefault().language

        fun initInfo(context: Context) {
            CoroutineScope(Dispatchers.IO).launch {
                for (data in CHARACTER_DATA) {
                    getInstance(context).alarmDao().insert(data)
                }
            }
        }

//        private fun checkLanguage():ArrayList<CharacInfo> {
//            return when (language) {
//                "ko" -> CHARACTER_DATA_ko
//                "en" -> CHARACTER_DATA_en
//                "ja" -> CHARACTER_DATA_ja
//                else -> CHARACTER_DATA_en
//            }
//        }


        private val CHARACTER_DATA = arrayListOf(
            CharacInfo(1, 0, R.drawable.enchantress, R.string.Enchantress_txt, "0103"),
            CharacInfo(2, 0, R.drawable.lawyer, R.string.Lawyer_txt, "0112"),
            CharacInfo(3, 0, R.drawable.eye, R.string.MindsEye_txt, "0113"),
            CharacInfo(4, 0, R.drawable.priestess, R.string.Priestess_txt, "0201"),
            CharacInfo(5, 0, R.drawable.gravekeeper, R.string.GraveKeeper_txt, "0213"),
            CharacInfo(6, 0, R.drawable.barmaid, R.string.Barmaid_txt, "0214"),
            CharacInfo(7, 0, R.drawable.explorer, R.string.Explorer_txt, "0301"),
            CharacInfo(8, 0, R.drawable.doctor, R.string.Doctor_txt, "0317"),
            CharacInfo(9, 0, R.drawable.prospector, R.string.Prospector_txt, "0319"),
            CharacInfo(10, 0, R.drawable.coordinator, R.string.Coordinator_txt, "0403"),
            CharacInfo(11, 0, R.drawable.wildling, R.string.Wildling_txt, "0430"),
            CharacInfo(12, 0, R.drawable.thief, R.string.Thief_txt, "0507"),
            CharacInfo(13, 0, R.drawable.embalmer, R.string.Embalmer_txt, "0511"),
            CharacInfo(14, 0, R.drawable.forward, R.string.Forward_txt, "0515"),
            CharacInfo(15, 0, R.drawable.acrobat, R.string.Acrobat_txt, "0601"),
            CharacInfo(16, 0, R.drawable.dancer, R.string.FemaleDancer_txt, "0609"),
            CharacInfo(17, 0, R.drawable.magician, R.string.Magician_txt, "0704"),
            CharacInfo(18, 0, R.drawable.prisoner, R.string.Prisoner_txt, "0710"),
            CharacInfo(19, 0, R.drawable.mercenary, R.string.Mercenary_txt, "0723"),
            CharacInfo(20, 0, R.drawable.perfumer, R.string.Perfumer_txt, "0825"),
            CharacInfo(21, 0, R.drawable.mechanic, R.string.Mechanic_txt, "0913"),
            CharacInfo(22, 0, R.drawable.first_officer, R.string.FirstOfficer_txt, "0924"),
            CharacInfo(23, 0, R.drawable.seer, R.string.Seer_txt, "1031"),
            CharacInfo(24, 0, R.drawable.lucky_guy, R.string.LuckyGuy_txt, "1122"),
            CharacInfo(25, 0, R.drawable.gardner, R.string.Gardener_txt, "1221"),
            CharacInfo(26, 0, R.drawable.entomologis, R.string.Entomologist_txt, "1222"),
            CharacInfo(27, 0, R.drawable.postman, R.string.Postman_txt, "1225"),
            CharacInfo(28, 0, R.drawable.cowboy, R.string.Cowboy_txt, "1227"),

            CharacInfo(100, 1, R.drawable.spider, R.string.SoulWeaver_txt, "0102"),
            CharacInfo(101, 1, R.drawable.haster, R.string.Feaster_txt, "0124"),
            CharacInfo(102, 1, R.drawable.michiko, R.string.Geisha_txt, "0218"),
            CharacInfo(103, 1, R.drawable.photo, R.string.Photographer_txt, "0311"),
            CharacInfo(104, 1, R.drawable.ulbo, R.string.AxeBoy_txt, "0425"),
            CharacInfo(105, 1, R.drawable.noru, R.string.GraveKeeper_txt, "0521"),
            CharacInfo(106, 1, R.drawable.umbrella, R.string.WuChang_txt, "0715"),
            CharacInfo(107, 1, R.drawable.rhkdeo, R.string.SmileyFace_txt, "0804"),
            CharacInfo(108, 1, R.drawable.ripper, R.string.Ripper_txt, "0807"),
            CharacInfo(109, 1, R.drawable.bongbog, R.string.Guard26_txt, "0808"),
            CharacInfo(110, 1, R.drawable.sado, R.string.Disciple_txt, "0811"),
            CharacInfo(111, 1, R.drawable.kkumma, R.string.DreamWitch_txt, "1002"),
            CharacInfo(112, 1, R.drawable.byeall, R.string.Violinist_txt, "1027"),
            CharacInfo(113, 1, R.drawable.mari, R.string.BloodyQueen_txt, "1102"),
            CharacInfo(114, 1, R.drawable.domabaem, R.string.EvilReptilian_txt, "1113"),
            CharacInfo(115, 1, R.drawable.jogak, R.string.Sculptor_txt, "1117"),
            CharacInfo(116, 1, R.drawable.halbae, R.string.MadEyes_txt, "1127"),
            CharacInfo(117, 1, R.drawable.gong, R.string.HellEmber_txt, "1221")
        )
    }
}

//private val CHARACTER_DATA_en = arrayListOf(
//    CharacInfo(1, 0, R.drawable.enchantress, "Enchantress", "0103"),
//    CharacInfo(2, 0, R.drawable.lawyer, "Lawyer", "0112"),
//    CharacInfo(3, 0, R.drawable.eye, "Mind's Eye", "0113"),
//    CharacInfo(4, 0, R.drawable.priestess, "Priestess", "0201"),
//    CharacInfo(5, 0, R.drawable.gravekeeper, "Grave Keeper", "0213"),
//    CharacInfo(6, 0, R.drawable.barmaid, "Barmaid", "0214"),
//    CharacInfo(7, 0, R.drawable.explorer, "Explorer", "0301"),
//    CharacInfo(8, 0, R.drawable.doctor, "Doctor", "0317"),
//    CharacInfo(9, 0, R.drawable.prospector, "Prospector", "0319"),
//    CharacInfo(10, 0, R.drawable.coordinator, "Coordinator", "0403"),
//    CharacInfo(11, 0, R.drawable.wildling, "Wildling", "0430"),
//    CharacInfo(12, 0, R.drawable.thief, "Thief", "0507"),
//    CharacInfo(13, 0, R.drawable.embalmer, "Embalmer", "0511"),
//    CharacInfo(14, 0, R.drawable.forward, "Forward", "0515"),
//    CharacInfo(15, 0, R.drawable.acrobat, "Acrobat", "0601"),
//    CharacInfo(16, 0, R.drawable.dancer, "Female Dancer", "0609"),
//    CharacInfo(17, 0, R.drawable.magician, "Magician", "0704"),
//    CharacInfo(18, 0, R.drawable.prisoner, "'Prisoner'", "0710"),
//    CharacInfo(19, 0, R.drawable.mercenary, "Mercenary", "0723"),
//    CharacInfo(20, 0, R.drawable.perfumer, "Perfumer", "0825"),
//    CharacInfo(21, 0, R.drawable.mechanic, "Mechanic", "0913"),
//    CharacInfo(22, 0, R.drawable.first_officer, "First Officer", "0924"),
//    CharacInfo(23, 0, R.drawable.seer, "Seer", "1031"),
//    CharacInfo(24, 0, R.drawable.lucky_guy, "Lucky Guy", "1122"),
//    CharacInfo(25, 0, R.drawable.gardner, "Gardener", "1221"),
//    CharacInfo(26, 0, R.drawable.entomologis, "Entomologist", "1222"),
//    CharacInfo(27, 0, R.drawable.postman, "Postman", "1225"),
//    CharacInfo(28, 0, R.drawable.cowboy, "Cowboy", "1227"),
//
//    CharacInfo(100, 1, R.drawable.spider, "Soul Weaver", "0102"),
//    CharacInfo(101, 1, R.drawable.haster, "Feaster", "0124"),
//    CharacInfo(102, 1, R.drawable.michiko, "Geisha", "0218"),
//    CharacInfo(103, 1, R.drawable.photo, "Photographer", "0311"),
//    CharacInfo(104, 1, R.drawable.ulbo, "Axe Boy", "0425"),
//    CharacInfo(105, 1, R.drawable.noru, "Gamekeeper", "0521"),
//    CharacInfo(106, 1, R.drawable.umbrella, "Wu Chang", "0715"),
//    CharacInfo(107, 1, R.drawable.rhkdeo, "Smiley Face", "0804"),
//    CharacInfo(108, 1, R.drawable.ripper, "Ripper", "0807"),
//    CharacInfo(109, 1, R.drawable.bongbog, "Guard 26", "0808"),
//    CharacInfo(110, 1, R.drawable.sado, "'Disciple'", "0811"),
//    CharacInfo(111, 1, R.drawable.kkumma, "Dream Witch", "1002"),
//    CharacInfo(112, 1, R.drawable.byeall, "Violinist", "1027"),
//    CharacInfo(113, 1, R.drawable.mari, "Bloody Queen", "1102"),
//    CharacInfo(114, 1, R.drawable.domabaem, "Evil Reptilian", "1113"),
//    CharacInfo(115, 1, R.drawable.jogak, "Sculptor", "1117"),
//    CharacInfo(116, 1, R.drawable.halbae, "Mad Eyes", "1127"),
//    CharacInfo(117, 1, R.drawable.gong, "Hell Ember", "1221")
//)
//
//private val CHARACTER_DATA_ja = arrayListOf(
//    CharacInfo(1, 0, R.drawable.enchantress, "呪術師", "0103"),
//    CharacInfo(2, 0, R.drawable.lawyer, "弁護士", "0112"),
//    CharacInfo(3, 0, R.drawable.eye, "心眼", "0113"),
//    CharacInfo(4, 0, R.drawable.priestess, "祭司", "0201"),
//    CharacInfo(5, 0, R.drawable.gravekeeper, "墓守", "0213"),
//    CharacInfo(6, 0, R.drawable.barmaid, "バーメイド", "0214"),
//    CharacInfo(7, 0, R.drawable.explorer, "冒険家", "0301"),
//    CharacInfo(8, 0, R.drawable.doctor, "医師", "0317"),
//    CharacInfo(9, 0, R.drawable.prospector, "探鉱者", "0319"),
//    CharacInfo(10, 0, R.drawable.coordinator, "空軍", "0403"),
//    CharacInfo(11, 0, R.drawable.wildling, "野人", "0430"),
//    CharacInfo(12, 0, R.drawable.thief, "泥棒", "0507"),
//    CharacInfo(13, 0, R.drawable.embalmer, "納棺師", "0511"),
//    CharacInfo(14, 0, R.drawable.forward, "オフェンス", "0515"),
//    CharacInfo(15, 0, R.drawable.acrobat, "曲芸師", "0601"),
//    CharacInfo(16, 0, R.drawable.dancer, "踊り子", "0609"),
//    CharacInfo(17, 0, R.drawable.magician, "マジシャン", "0704"),
//    CharacInfo(18, 0, R.drawable.prisoner, "囚人", "0710"),
//    CharacInfo(19, 0, R.drawable.mercenary, "傭兵", "0723"),
//    CharacInfo(20, 0, R.drawable.perfumer, "調香師", "0825"),
//    CharacInfo(21, 0, R.drawable.mechanic, "機械技師", "0913"),
//    CharacInfo(22, 0, R.drawable.first_officer, "一等航海士", "0924"),
//    CharacInfo(23, 0, R.drawable.seer, "占い師", "1031"),
//    CharacInfo(24, 0, R.drawable.lucky_guy, "幸運児", "1122"),
//    CharacInfo(25, 0, R.drawable.gardner, "庭師", "1221"),
//    CharacInfo(26, 0, R.drawable.entomologis, "昆虫学者", "1222"),
//    CharacInfo(27, 0, R.drawable.postman, "ポストマン", "1225"),
//    CharacInfo(28, 0, R.drawable.cowboy, "カウボーイ", "1227"),
//
//    CharacInfo(100, 1, R.drawable.spider, "結魂者", "0102"),
//    CharacInfo(101, 1, R.drawable.haster, "黄衣の王", "0124"),
//    CharacInfo(102, 1, R.drawable.michiko, "芸者", "0218"),
//    CharacInfo(103, 1, R.drawable.photo, "写真家", "0311"),
//    CharacInfo(104, 1, R.drawable.ulbo, "泣き虫", "0425"),
//    CharacInfo(105, 1, R.drawable.noru, "断罪狩人", "0521"),
//    CharacInfo(106, 1, R.drawable.umbrella, "白黒無常", "0715"),
//    CharacInfo(107, 1, R.drawable.rhkdeo, "道化師", "0804"),
//    CharacInfo(108, 1, R.drawable.ripper, "リッパー", "0807"),
//    CharacInfo(109, 1, R.drawable.bongbog, "ガードNo.26", "0808"),
//    CharacInfo(110, 1, R.drawable.sado, "'使徒'", "0811"),
//    CharacInfo(111, 1, R.drawable.kkumma, "夢の魔女", "1002"),
//    CharacInfo(112, 1, R.drawable.byeall, "ヴァイオリニスト", "1027"),
//    CharacInfo(113, 1, R.drawable.mari, "血の女王", "1102"),
//    CharacInfo(114, 1, R.drawable.domabaem, "魔トカゲ", "1113"),
//    CharacInfo(115, 1, R.drawable.jogak, "彫刻家", "1117"),
//    CharacInfo(116, 1, R.drawable.halbae, "狂眼", "1127"),
//    CharacInfo(117, 1, R.drawable.gong, "復讐者", "1221")
//)
