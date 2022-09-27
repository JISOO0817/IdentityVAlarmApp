package com.jisoo.identityvalarmapp.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.jisoo.identityvalarmapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AlarmDaoTest {

    @get:Rule
    var instantTaskExecutorRunWith = InstantTaskExecutorRule()

    private lateinit var database: AlarmDatabase
    private lateinit var dao: AlarmDao

    @Before
    fun setup() {

        //테스트에서는 단일스레드에서 실행되는것을 명시하며 메인스레드에서 실행
        //각 테스트에서의 완전한 독립성

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AlarmDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.alarmDao()
    }

    // 테스트 케이스 이후 데이터베이스를 닫기
    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertCharacterInfo() = runBlockingTest {
        val characinfo = CharacInfo(1111,0,"테스트",1,"0927")
        dao.insert(characinfo)

        val allcharacInfo = dao.getAllDataList().getOrAwaitValue()

        assertThat(allcharacInfo).contains(characinfo)
    }

    @Test
    fun deleteCharacInfo() = runBlockingTest {
        val characinfo = CharacInfo(1111,0,"테스트",1,"0927")
        dao.insert(characinfo)
        dao.delete(characinfo)

        val allcharacInfo = dao.getAllDataList().getOrAwaitValue()

        assertThat(allcharacInfo).doesNotContain(characinfo)

    }
}