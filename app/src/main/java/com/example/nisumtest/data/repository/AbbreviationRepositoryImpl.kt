package com.example.nisumtest.data.repository

import android.util.Log
import com.example.nisumtest.core.ErrorState
import com.example.nisumtest.core.mapper.AbbreviationMapper
import com.example.nisumtest.core.util.DataState
import com.example.nisumtest.data.local.dao.AbbreviationDao
import com.example.nisumtest.data.local.dao.LfDao
import com.example.nisumtest.data.local.dao.VarDao
import com.example.nisumtest.data.local.entity.LfEntity
import com.example.nisumtest.data.local.entity.relation.AbbreviationWithLf
import com.example.nisumtest.data.local.entity.relation.LfVarCrossRef
import com.example.nisumtest.data.model.Some
import com.example.nisumtest.network.AbbreviationClient
import com.example.nisumtest.ui.state.MainEventState
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AbbreviationRepositoryImpl @Inject constructor(
    private val abbreviationDao: AbbreviationDao,
    private val lfDao: LfDao,
    private val varDao: VarDao,
    private val abbreviationClient: AbbreviationClient,
    private val abbreviationMapper: AbbreviationMapper
) : AbbreviationRepository {

    companion object {
        private const val TAG = "AbbreviationRepositoryI"
    }

    override suspend fun fetchAbbreviations(query: String): Flow<DataState<MainEventState>> =
        flow {
            emit(DataState.Loading)
            try {
                val abbreviations = abbreviationClient.getAbbreviation(query)
                val builder = GsonBuilder()
                val gson = builder.create()
                val toList = gson.fromJson(abbreviations, Some::class.java)
                val abbreviationsModel = abbreviationMapper.mapFromList(toList)
                if (abbreviationsModel.isNotEmpty()) {
                    abbreviationsModel.forEach {
                        val id = abbreviationDao.insert(it.abb)
                        it.lfs.forEach { lf ->
                            val idLfs = lfDao.insert(abbreviationMapper.mapToLfsWithId(id, lf.lf))
                            lf.vars.forEach { v ->
                                val idVar =
                                    varDao.insert(abbreviationMapper.mapToVarWithLfsId(idLfs, v))
                                lfDao.insert(LfVarCrossRef(idLfs, idVar))
                            }
                        }
                    }
                    val cacheAbbreviations = abbreviationDao.findAbbrByLike(query)
                    if (cacheAbbreviations.isEmpty())
                        emit(DataState.Empty)
                    else
                        emit(DataState.Success(MainEventState(searchFragment = MainEventState.SearchFragmentViews(abbreviationMapper.toMapEntity(cacheAbbreviations)))))
                } else {
                    emit(DataState.Empty)
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
                e.message?.let {
                    emit(DataState.Error(ErrorState(it)))
                }
            }
        }

    override suspend fun fetchRecent(): Flow<DataState<MainEventState>> = flow {
        emit(DataState.Loading)
        try {
            val listLfsRecent = lfDao.selectAllRecent()
            emit(DataState.Success(MainEventState(
                listFragment = MainEventState.ListFragmentViews(listLfsRecent)
            )))
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            e.message?.let {
                emit(DataState.Error(ErrorState(it)))
            }
        }
    }

    override suspend fun fetchDetailAbbreviation(id: Long): Flow<DataState<MainEventState>> =
        flow {
            emit(DataState.Loading)
            try {
                val abbreviationDetail = abbreviationDao.findAbbrById(id)
                if (abbreviationDetail != null)
                    emit(DataState.Success(MainEventState(detailFragment = MainEventState.DetailFragmentViews(abbreviationDetail))))
                else
                    emit(DataState.Empty)
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
                e.message?.let {
                    emit(DataState.Error(ErrorState(it)))
                }
            }
        }
}