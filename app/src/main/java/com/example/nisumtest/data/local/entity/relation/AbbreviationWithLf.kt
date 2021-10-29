package com.example.nisumtest.data.local.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.nisumtest.data.local.entity.AbbreviationEntity
import com.example.nisumtest.data.local.entity.LfEntity

data class AbbreviationWithLf(
    @Embedded val abb: AbbreviationEntity,
    @Relation(
        entity = LfEntity::class,
        parentColumn = "id",
        entityColumn = "abbreviation_id"
    )
    val lfs: List<LfWithVar>
)
