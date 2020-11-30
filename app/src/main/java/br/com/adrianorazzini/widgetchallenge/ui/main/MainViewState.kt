package br.com.adrianorazzini.widgetchallenge.ui.main

import br.com.adrianorazzini.remote.model.Card
import br.com.adrianorazzini.widgetchallenge.common.databinding.BaseViewState
import br.com.adrianorazzini.widgetchallenge.ui.home.model.CardViewItem

data class MainViewState(
    val cardItems: List<CardViewItem>? = null,
    val cardInfo: Card? = null,
    val error: StateError? = null,
    val loading: Boolean = false
) : BaseViewState

enum class StateError {
    INVALID_CARD_ID,
    INVALID_CARD_INFO
}