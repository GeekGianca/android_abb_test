package com.example.nisumtest.ui.state

import com.example.nisumtest.data.local.entity.LfEntity
import com.example.nisumtest.data.local.entity.relation.AbbreviationWithLf

data class MainEventState(
    var listFragment: ListFragmentViews = ListFragmentViews(),
    var searchFragment: SearchFragmentViews = SearchFragmentViews(),
    var detailFragment: DetailFragmentViews = DetailFragmentViews()
) {
    data class ListFragmentViews(var listRecent: List<LfEntity>? = null)

    data class SearchFragmentViews(var listAbbreviations: List<LfEntity>? = null)

    data class DetailFragmentViews(var abbDetail: AbbreviationWithLf? = null)
}