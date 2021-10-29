package com.example.nisumtest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "var")
data class VarEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "var_id")
    var varId: Long,
    @ColumnInfo(name = "freq")
    var frequency: Int,
    @ColumnInfo(name = "lf")
    var lf: String,
    @ColumnInfo(name = "since")
    var since: Int,
    @ColumnInfo(name = "lf_id")
    var lfId: Long
)