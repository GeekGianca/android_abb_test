package com.example.nisumtest.data.repository

import com.example.nisumtest.core.util.DataState
import com.example.nisumtest.data.local.entity.LfEntity
import com.example.nisumtest.data.local.entity.relation.AbbreviationWithLf
import com.example.nisumtest.ui.state.MainEventState
import kotlinx.coroutines.flow.Flow

interface AbbreviationRepository {
    suspend fun fetchAbbreviations(query: String): Flow<DataState<MainEventState>>
    suspend fun fetchRecent(): Flow<DataState<MainEventState>>
    suspend fun fetchDetailAbbreviation(id: Long): Flow<DataState<MainEventState>>
}