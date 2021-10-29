package com.example.nisumtest.data.local.entity.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.nisumtest.data.local.entity.LfEntity
import com.example.nisumtest.data.local.entity.VarEntity

data class LfWithVar(
    @Embedded val lf: LfEntity,
    @Relation(
        parentColumn = "lf_id",
        entityColumn = "var_id",
        associateBy = Junction(LfVarCrossRef::class)
    )
    val vars: List<VarEntity>
)
