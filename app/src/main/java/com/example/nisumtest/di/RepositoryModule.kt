package com.example.nisumtest.di

import com.example.nisumtest.core.mapper.AbbreviationMapper
import com.example.nisumtest.data.local.dao.AbbreviationDao
import com.example.nisumtest.data.local.dao.LfDao
import com.example.nisumtest.data.local.dao.VarDao
import com.example.nisumtest.data.repository.AbbreviationRepository
import com.example.nisumtest.data.repository.AbbreviationRepositoryImpl
import com.example.nisumtest.network.AbbreviationClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAbbreviationRepository(
        abbreviationDao: AbbreviationDao,
        lfDao: LfDao,
        varDao: VarDao,
        abbreviationClient: AbbreviationClient,
        abbreviationMapper: AbbreviationMapper
    ): AbbreviationRepository {
        return AbbreviationRepositoryImpl(
            abbreviationDao,
            lfDao,
            varDao,
            abbreviationClient,
            abbreviationMapper
        )
    }
}