package com.example.nisumtest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nisumtest.data.local.entity.LfEntity
import com.example.nisumtest.data.local.entity.relation.LfVarCrossRef

@Dao
interface LfDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: LfEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: LfVarCrossRef): Long

    @Query("SELECT * FROM lf")
    suspend fun selectAllRecent(): List<LfEntity>
}