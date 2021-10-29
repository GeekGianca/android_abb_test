package com.example.nisumtest.data.local.dao

import androidx.room.*
import com.example.nisumtest.data.local.entity.AbbreviationEntity
import com.example.nisumtest.data.local.entity.relation.AbbreviationWithLf

@Dao
interface AbbreviationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: AbbreviationEntity): Long

    @Transaction
    @Query("SELECT * FROM abbreviation WHERE sf LIKE :search")
    suspend fun findAbbrByLike(search: String): List<AbbreviationWithLf>

    @Transaction
    @Query("SELECT * FROM abbreviation WHERE id =:id")
    suspend fun findAbbrById(id: Long): AbbreviationWithLf?
}