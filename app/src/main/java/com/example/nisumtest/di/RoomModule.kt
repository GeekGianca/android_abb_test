package com.example.nisumtest.di

import android.content.Context
import com.example.nisumtest.data.local.DatabaseSchema
import com.example.nisumtest.data.local.dao.AbbreviationDao
import com.example.nisumtest.data.local.dao.LfDao
import com.example.nisumtest.data.local.dao.VarDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext ctx: Context): DatabaseSchema =
        DatabaseSchema.getInstance(ctx)

    @Singleton
    @Provides
    fun provideAbbreviationDao(schema: DatabaseSchema): AbbreviationDao =
        schema.abbreviationDao()

    @Singleton
    @Provides
    fun provideLfDao(schema: DatabaseSchema): LfDao =
        schema.lfDao()

    @Singleton
    @Provides
    fun provideVarDao(schema: DatabaseSchema): VarDao =
        schema.varDao()
}