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


@Database(entities = [CharacInfo::class], version = 4, exportSchema = false)

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

        val Migration2_3 = object: Migration(2,3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("INSERT INTO CharacInfo(uid,category,img,job,birth) VALUES (29,0,${R.drawable.painter}, ${R.string.Painter_txt},0423)")
                database.execSQL("INSERT INTO CharacInfo(uid,category,img,job,birth) VALUES (30,0,${R.drawable.batter},${R.string.Batter_txt},0529)")
                database.execSQL("INSERT INTO CharacInfo(uid,category,img,job,birth) VALUES (31,0,${R.drawable.weepingclown},${R.string.WeepingClown_txt},0804)")
            }
        }

        val Migration3_4 = object: Migration(3,4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE IF EXISTS CharacInfo_0")
                database.execSQL("ALTER TABLE CharacInfo RENAME TO CharacInfo_0")
                database.execSQL("CREATE TABLE IF NOT EXISTS CharacInfo(uid INTEGER PRIMARY KEY NOT NULL, category INTEGER NOT NULL, img TEXT NOT NULL, job INTEGER NOT NULL, birth TEXT NOT NULL)")
                database.execSQL("INSERT INTO CharacInfo SELECT * FROM CharacInfo_0")
                database.execSQL("DROP TABLE IF EXISTS CharacInfo_0")

                database.execSQL("UPDATE CharacInfo SET img ='enchantress' WHERE uid =1")
                database.execSQL("UPDATE CharacInfo SET img ='lawyer' WHERE uid = 2")
                database.execSQL("UPDATE CharacInfo SET img ='eye' WHERE uid = 3")
                database.execSQL("UPDATE CharacInfo SET img ='priestess' WHERE uid = 4")
                database.execSQL("UPDATE CharacInfo SET img ='gravekeeper' WHERE uid = 5")
                database.execSQL("UPDATE CharacInfo SET img ='barmaid' WHERE uid = 6")
                database.execSQL("UPDATE CharacInfo SET img ='explorer' WHERE uid = 7")
                database.execSQL("UPDATE CharacInfo SET img ='doctor' WHERE uid = 8")
                database.execSQL("UPDATE CharacInfo SET img ='prospector' WHERE uid = 9")
                database.execSQL("UPDATE CharacInfo SET img ='coordinator' WHERE uid = 10")
                database.execSQL("UPDATE CharacInfo SET img ='wildling' WHERE uid = 11")
                database.execSQL("UPDATE CharacInfo SET img ='thief' WHERE uid = 12")
                database.execSQL("UPDATE CharacInfo SET img ='embalmer' WHERE uid = 13")
                database.execSQL("UPDATE CharacInfo SET img ='forward' WHERE uid = 14")
                database.execSQL("UPDATE CharacInfo SET img ='acrobat' WHERE uid = 15")
                database.execSQL("UPDATE CharacInfo SET img ='dancer' WHERE uid = 16")
                database.execSQL("UPDATE CharacInfo SET img ='magician' WHERE uid = 17")
                database.execSQL("UPDATE CharacInfo SET img ='prisoner' WHERE uid = 18")
                database.execSQL("UPDATE CharacInfo SET img ='mercenary' WHERE uid = 19")
                database.execSQL("UPDATE CharacInfo SET img ='perfumer' WHERE uid = 20")
                database.execSQL("UPDATE CharacInfo SET img ='mechanic' WHERE uid = 21")
                database.execSQL("UPDATE CharacInfo SET img ='first_officer' WHERE uid = 22")
                database.execSQL("UPDATE CharacInfo SET img ='seer' WHERE uid = 23")
                database.execSQL("UPDATE CharacInfo SET img ='lucky_guy' WHERE uid = 24")
                database.execSQL("UPDATE CharacInfo SET img ='gardner' WHERE uid = 25")
                database.execSQL("UPDATE CharacInfo SET img ='entomologis' WHERE uid = 26")
                database.execSQL("UPDATE CharacInfo SET img ='postman' WHERE uid = 27")
                database.execSQL("UPDATE CharacInfo SET img ='cowboy' WHERE uid = 28")
                database.execSQL("UPDATE CharacInfo SET img ='painter' WHERE uid = 29")
                database.execSQL("UPDATE CharacInfo SET img ='batter' WHERE uid = 30")
                database.execSQL("UPDATE CharacInfo SET img ='weepingclown' WHERE uid = 31")
                database.execSQL("UPDATE CharacInfo SET img ='spider' WHERE uid = 100")
                database.execSQL("UPDATE CharacInfo SET img ='haster' WHERE uid = 101")
                database.execSQL("UPDATE CharacInfo SET img ='michiko' WHERE uid = 102")
                database.execSQL("UPDATE CharacInfo SET img ='photo' WHERE uid = 103")
                database.execSQL("UPDATE CharacInfo SET img ='ulbo' WHERE uid = 104")
                database.execSQL("UPDATE CharacInfo SET img ='noru' WHERE uid = 105")
                database.execSQL("UPDATE CharacInfo SET img ='umbrella' WHERE uid = 106")
                database.execSQL("UPDATE CharacInfo SET img ='rhkdeo' WHERE uid = 107")
                database.execSQL("UPDATE CharacInfo SET img ='ripper' WHERE uid = 108")
                database.execSQL("UPDATE CharacInfo SET img ='bongbog' WHERE uid = 109")
                database.execSQL("UPDATE CharacInfo SET img ='sado' WHERE uid = 110")
                database.execSQL("UPDATE CharacInfo SET img ='kkumma' WHERE uid = 111")
                database.execSQL("UPDATE CharacInfo SET img ='byeall' WHERE uid = 112")
                database.execSQL("UPDATE CharacInfo SET img ='mari' WHERE uid = 113")
                database.execSQL("UPDATE CharacInfo SET img ='domabaem' WHERE uid = 114")
                database.execSQL("UPDATE CharacInfo SET img ='jogak' WHERE uid = 115")
                database.execSQL("UPDATE CharacInfo SET img ='halbae' WHERE uid = 116")
                database.execSQL("UPDATE CharacInfo SET img ='gong' WHERE uid = 117")
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
                }).addMigrations(Migration2_3, Migration3_4)
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
            CharacInfo(1, 0, "enchantress", R.string.Enchantress_txt, "0103"),
            CharacInfo(2, 0, "lawyer", R.string.Lawyer_txt, "0112"),
            CharacInfo(3, 0, "eye", R.string.MindsEye_txt, "0113"),
            CharacInfo(4, 0, "priestess", R.string.Priestess_txt, "0201"),
            CharacInfo(5, 0, "gravekeeper", R.string.GraveKeeper_txt, "0213"),
            CharacInfo(6, 0, "barmaid", R.string.Barmaid_txt, "0214"),
            CharacInfo(7, 0, "explorer", R.string.Explorer_txt, "0301"),
            CharacInfo(8, 0, "doctor", R.string.Doctor_txt, "0317"),
            CharacInfo(9, 0, "prospector", R.string.Prospector_txt, "0319"),
            CharacInfo(10, 0, "coordinator", R.string.Coordinator_txt, "0403"),
            CharacInfo(11, 0, "wildling", R.string.Wildling_txt, "0430"),
            CharacInfo(12, 0, "thief", R.string.Thief_txt, "0507"),
            CharacInfo(13, 0, "embalmer", R.string.Embalmer_txt, "0511"),
            CharacInfo(14, 0, "forward", R.string.Forward_txt, "0515"),
            CharacInfo(15, 0, "acrobat", R.string.Acrobat_txt, "0601"),
            CharacInfo(16, 0, "dancer", R.string.FemaleDancer_txt, "0609"),
            CharacInfo(17, 0, "magician", R.string.Magician_txt, "0704"),
            CharacInfo(18, 0, "prisoner", R.string.Prisoner_txt, "0710"),
            CharacInfo(19, 0, "mercenary", R.string.Mercenary_txt, "0723"),
            CharacInfo(20, 0, "perfumer", R.string.Perfumer_txt, "0825"),
            CharacInfo(21, 0, "mechanic", R.string.Mechanic_txt, "0913"),
            CharacInfo(22, 0, "first_officer", R.string.FirstOfficer_txt, "0924"),
            CharacInfo(23, 0, "seer", R.string.Seer_txt, "1031"),
            CharacInfo(24, 0, "lucky_guy", R.string.LuckyGuy_txt, "1122"),
            CharacInfo(25, 0, "gardner", R.string.Gardener_txt, "1221"),
            CharacInfo(26, 0, "entomologis", R.string.Entomologist_txt, "1222"),
            CharacInfo(27, 0, "postman", R.string.Postman_txt, "1225"),
            CharacInfo(28, 0, "cowboy", R.string.Cowboy_txt, "1227"),
            CharacInfo(29,0,"painter", R.string.Painter_txt, "0423"),
            CharacInfo(30,0,"batter",R.string.Batter_txt, "0529"),
            CharacInfo(31,0,"weepingclown",R.string.WeepingClown_txt,"0804"),

            CharacInfo(100, 1, "spider", R.string.SoulWeaver_txt, "0102"),
            CharacInfo(101, 1, "haster", R.string.Feaster_txt, "0124"),
            CharacInfo(102, 1, "michiko", R.string.Geisha_txt, "0218"),
            CharacInfo(103, 1, "photo", R.string.Photographer_txt, "0311"),
            CharacInfo(104, 1, "ulbo", R.string.AxeBoy_txt, "0425"),
            CharacInfo(105, 1, "noru", R.string.GraveKeeper_txt, "0521"),
            CharacInfo(106, 1, "umbrella", R.string.WuChang_txt, "0715"),
            CharacInfo(107, 1, "rhkdeo", R.string.SmileyFace_txt, "0804"),
            CharacInfo(108, 1, "ripper", R.string.Ripper_txt, "0807"),
            CharacInfo(109, 1, "bongbog", R.string.Guard26_txt, "0808"),
            CharacInfo(110, 1, "sado", R.string.Disciple_txt, "0811"),
            CharacInfo(111, 1, "kkumma", R.string.DreamWitch_txt, "1002"),
            CharacInfo(112, 1, "byeall", R.string.Violinist_txt, "1027"),
            CharacInfo(113, 1, "mari", R.string.BloodyQueen_txt, "1102"),
            CharacInfo(114, 1, "domabaem", R.string.EvilReptilian_txt, "1113"),
            CharacInfo(115, 1, "jogak", R.string.Sculptor_txt, "1117"),
            CharacInfo(116, 1, "halbae", R.string.MadEyes_txt, "1127"),
            CharacInfo(117, 1, "gong", R.string.HellEmber_txt, "1221")
        )
    }
}

