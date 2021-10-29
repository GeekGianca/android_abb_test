package com.example.nisumtest.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nisumtest.core.ErrorState
import com.example.nisumtest.core.util.DataState
import com.example.nisumtest.data.local.entity.LfEntity
import com.example.nisumtest.data.local.entity.relation.AbbreviationWithLf
import com.example.nisumtest.data.repository.AbbreviationRepository
import com.example.nisumtest.ui.state.FirstStateEvent
import com.example.nisumtest.ui.state.MainEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val abbRepo: AbbreviationRepository) :
    ViewModel() {
    companion object {
        private const val TAG = "FirstViewModel"
    }

    private val _dataState: MutableLiveData<MainEventState> = MutableLiveData()
    val dataState: LiveData<MainEventState>
        get() = _dataState

    private val _loadingState: MutableLiveData<Boolean> = MutableLiveData()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private val _errorState: MutableLiveData<ErrorState> = MutableLiveData()
    val errorState: LiveData<ErrorState>
        get() = _errorState

    private val _emptyState: MutableLiveData<Boolean> = MutableLiveData()
    val emptyState: LiveData<Boolean>
        get() = _emptyState

    fun setStateEvent(firstStateEvent: FirstStateEvent) {
        viewModelScope.launch {
            when (firstStateEvent) {
                is FirstStateEvent.FindAbbreviationsEvent -> {
                    abbRepo.fetchAbbreviations(firstStateEvent.query)
                        .onEach { state ->
                            Log.d(TAG, "$state")
                            handleNewState(state)
                        }
                        .launchIn(viewModelScope)
                }
                is FirstStateEvent.RecentAbbreviations -> {
                    abbRepo.fetchRecent()
                        .onEach { state ->
                            handleNewState(state)
                        }
                        .launchIn(viewModelScope)
                }
                is FirstStateEvent.DetailAbbreviations -> {
                    abbRepo.fetchDetailAbbreviation(firstStateEvent.id)
                        .onEach { state ->
                            handleNewState(state)
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

    private fun initNewViewState(): MainEventState {
        return MainEventState()
    }

    private fun getCurrentViewStateOrNew(): MainEventState {
        return dataState.value ?: initNewViewState()
    }

    private fun handleNewState(state: DataState<MainEventState>) {
        when (state) {
            DataState.Loading -> {
                _loadingState.value = true
            }
            DataState.Empty -> {
                _loadingState.value = false
            }
            is DataState.Success -> {
                _loadingState.value = false
                handleNewData(state.data)
            }
            is DataState.Error -> {
                _loadingState.value = false
                _errorState.value = state.ex
            }
        }
    }

    private fun handleNewData(data: MainEventState) {
        data.let { viewState ->
            viewState.listFragment.listRecent?.let {
                setRecentList(it)
            }
            viewState.searchFragment.listAbbreviations?.let {
                setCurrentFindAbbreviations(it)
            }
            viewState.detailFragment.abbDetail?.let {
                setDetailAbbreviation(it)
            }
        }
    }

    private fun setDetailAbbreviation(abbreviationDetail: AbbreviationWithLf) {
        val update = getCurrentViewStateOrNew()
        update.detailFragment.abbDetail = abbreviationDetail
        setViewState(update)
    }

    private fun setCurrentFindAbbreviations(search: List<LfEntity>) {
        val update = getCurrentViewStateOrNew()
        update.searchFragment.listAbbreviations = search
        setViewState(update)
    }

    private fun setRecentList(recent: List<LfEntity>) {
        val update = getCurrentViewStateOrNew()
        update.listFragment.listRecent = recent
        setViewState(update)
    }

    private fun setViewState(update: MainEventState) {
        _dataState.value = update
    }
}