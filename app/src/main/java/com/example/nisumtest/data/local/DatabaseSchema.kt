package com.example.nisumtest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nisumtest.R
import com.example.nisumtest.data.local.dao.AbbreviationDao
import com.example.nisumtest.data.local.dao.LfDao
import com.example.nisumtest.data.local.dao.VarDao
import com.example.nisumtest.data.local.entity.AbbreviationEntity
import com.example.nisumtest.data.local.entity.LfEntity
import com.example.nisumtest.data.local.entity.VarEntity
import com.example.nisumtest.data.local.entity.relation.LfVarCrossRef

@Database(
    entities = [AbbreviationEntity::class, LfEntity::class, VarEntity::class, LfVarCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseSchema : RoomDatabase() {

    abstract fun abbreviationDao(): AbbreviationDao
    abstract fun lfDao(): LfDao
    abstract fun varDao(): VarDao

    companion object {

        @Volatile
        private var INSTANCE: DatabaseSchema? = null

        fun getInstance(context: Context): DatabaseSchema {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseSchema::class.java,
                    context.resources.getString(R.string.database_name)
                )
                    .addCallback(sRoomDatabaseCallback)
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }

        private val sRoomDatabaseCallback: Callback =
            object : Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                }
            }
    }
}