package com.example.nisumtest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nisumtest.data.local.entity.VarEntity

@Dao
interface VarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: VarEntity): Long

    @Query("SELECT * FROM var WHERE var_id =:id")
    suspend fun selectById(id: Long): VarEntity
}