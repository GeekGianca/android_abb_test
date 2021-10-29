package com.example.nisumtest.ui.state

sealed class FirstStateEvent {
    class FindAbbreviationsEvent(val query: String) : FirstStateEvent()
    object RecentAbbreviations : FirstStateEvent()
    class DetailAbbreviations(val id: Long) : FirstStateEvent()
}