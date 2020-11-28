package br.com.adrianorazzini.widgetchallenge.ui.main

import br.com.adrianorazzini.widgetchallenge.common.databinding.BaseViewState
import br.com.adrianorazzini.widgetchallenge.ui.home.model.CardViewItem

data class MainViewState(
    val cardItems: List<CardViewItem>? = null,
    val loading: Boolean = false
) : BaseViewState