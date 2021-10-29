package com.example.nisumtest

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nisumtest.core.util.DataState
import com.example.nisumtest.data.repository.AbbreviationRepository
import com.example.nisumtest.ui.MainViewModel
import com.example.nisumtest.ui.state.FirstStateEvent
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    @Inject
    private lateinit var abbRepo: AbbreviationRepository

    @InternalCoroutinesApi
    @Test
    suspend fun findAbbreviations(){
        val flow = abbRepo.fetchAbbreviations("Hm")
        flow.collect {
            when(it){
                DataState.Empty -> {
                    //
                }
                is DataState.Error -> {
                    //
                }
                DataState.Loading -> {
                    //
                }
                is DataState.Success -> {
                    //
                }
            }
        }
    }
}