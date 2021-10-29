package com.example.nisumtest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lf")
data class LfEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "lf_id")
    var lfId: Long,
    @ColumnInfo(name = "freq")
    var frequency: Int,
    @ColumnInfo(name = "lf")
    var lf: String,
    @ColumnInfo(name = "since")
    var since: Int,
    @ColumnInfo(name = "abbreviation_id")
    var abbreviationId: Long
)