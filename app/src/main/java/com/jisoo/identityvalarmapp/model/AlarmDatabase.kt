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


@Database(entities = [CharacInfo::class], version = 3, exportSchema = false)

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
                database.execSQL("CREATE TABLE IF NOT EXISTS CharacInfo(uid INTEGER PRIMARY KEY NOT NULL, category INTEGER NOT NULL, img INTEGER NOT NULL, job INTEGER NOT NULL, birth TEXT NOT NULL)")
                database.execSQL("INSERT INTO CharacInfo SELECT * FROM CharacInfo_0")
                database.execSQL("DROP TABLE IF EXISTS CharacInfo_0")

                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Enchantress_txt} WHERE uid = 1")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Lawyer_txt} WHERE uid = 2")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.MindsEye_txt} WHERE uid = 3")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Priestess_txt} WHERE uid = 4")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.GraveKeeper_txt} WHERE uid = 5")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Barmaid_txt} WHERE uid = 6")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Explorer_txt} WHERE uid = 7")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Doctor_txt} WHERE uid = 8")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Prospector_txt} WHERE uid = 9")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Coordinator_txt} WHERE uid = 10")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Wildling_txt} WHERE uid = 11")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Thief_txt} WHERE uid = 12")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Embalmer_txt} WHERE uid = 13")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Forward_txt} WHERE uid = 14")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Acrobat_txt} WHERE uid = 15")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.FemaleDancer_txt} WHERE uid = 16")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Magician_txt} WHERE uid = 17")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Prisoner_txt} WHERE uid = 18")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Mercenary_txt} WHERE uid = 19")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Perfumer_txt} WHERE uid = 20")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Mechanic_txt} WHERE uid = 21")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.FirstOfficer_txt} WHERE uid = 22")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Seer_txt} WHERE uid = 23")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.LuckyGuy_txt} WHERE uid = 24")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Gardener_txt} WHERE uid = 25")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Entomologist_txt} WHERE uid = 26")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Postman_txt} WHERE uid = 27")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Cowboy_txt} WHERE uid = 28")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.SoulWeaver_txt} WHERE uid = 100")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Feaster_txt} WHERE uid = 101")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Geisha_txt} WHERE uid = 102")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Photographer_txt} WHERE uid = 103")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.AxeBoy_txt} WHERE uid = 104")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.GraveKeeper_txt} WHERE uid = 105")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.WuChang_txt} WHERE uid = 106")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.SmileyFace_txt} WHERE uid = 107")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Ripper_txt} WHERE uid = 108")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Guard26_txt} WHERE uid = 109")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Disciple_txt} WHERE uid = 110")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.DreamWitch_txt} WHERE uid = 111")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Violinist_txt} WHERE uid = 112")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.BloodyQueen_txt} WHERE uid = 113")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.EvilReptilian_txt} WHERE uid = 114")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.Sculptor_txt} WHERE uid = 115")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.MadEyes_txt} WHERE uid = 116")
                database.execSQL("UPDATE CharacInfo SET job = ${R.string.HellEmber_txt} WHERE uid = 117")

            }
        }

        val Migration2_3 = object: Migration(2,3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("INSERT INTO CharacInfo(uid,category,img,job,birth) VALUES (29,0,${R.drawable.painter}, ${R.string.Painter_txt},0423)")
                database.execSQL("INSERT INTO CharacInfo(uid,category,img,job,birth) VALUES (30,0,${R.drawable.batter},${R.string.Batter_txt},0529)")
                database.execSQL("INSERT INTO CharacInfo(uid,category,img,job,birth) VALUES (31,0,${R.drawable.weepingclown},${R.string.WeepingClown_txt},0804)")
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
                }).addMigrations(MIGRATION_1_2,Migration2_3)
                    .build()
            }

            return INSTANCE as AlarmDatabase
        }

        fun initInfo(context: Context) {
            CoroutineScope(Dispatchers.IO).launch {
                for (data in CHARACTER_DATA) {
                    getInstance(context).alarmDao().insert(data)
                }
            }
        }

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
            CharacInfo(29,0,R.drawable.painter, R.string.Painter_txt, "0423"),
            CharacInfo(30,0,R.drawable.batter,R.string.Batter_txt, "0529"),
            CharacInfo(31,0,R.drawable.weepingclown,R.string.WeepingClown_txt,"0804"),

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

