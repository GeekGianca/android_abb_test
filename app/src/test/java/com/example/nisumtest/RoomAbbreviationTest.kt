package com.example.nisumtest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nisumtest.data.local.DatabaseSchema
import com.example.nisumtest.data.local.dao.VarDao
import com.example.nisumtest.data.local.entity.VarEntity
import okio.IOException
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomAbbreviationTest {

    private lateinit var varDao: VarDao
    private lateinit var db: DatabaseSchema

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseSchema::class.java
        ).build()
        varDao = db.varDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    suspend fun writeVarAndReadInList() {
        val vard = VarEntity(1, 234, "sm", 1923, 0)
        varDao.insert(vard)
        val byLf = varDao.selectById(1)
        assertThat(byLf.lf, equals(vard.lf))
    }
}