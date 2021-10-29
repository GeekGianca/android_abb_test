package com.example.nisumtest.data.local.entity.relation

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "lf_var_cross_ref", primaryKeys = ["lf_id", "var_id"])
data class LfVarCrossRef(
    @ColumnInfo(name = "lf_id", index = true)
    val lfId: Long,
    @ColumnInfo(name = "var_id", index = true)
    val varId: Long
)