package com.example.nisumtest.core.util

import com.example.nisumtest.data.local.entity.AbbreviationEntity
import kotlinx.coroutines.flow.Flow

interface StateEvent {
    fun findAbbreviationsByQuery(query: String):Flow<List<AbbreviationEntity>>
}