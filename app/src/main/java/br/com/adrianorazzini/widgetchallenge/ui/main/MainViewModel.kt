package br.com.adrianorazzini.widgetchallenge.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import br.com.adrianorazzini.remote.controller.WidgetController
import br.com.adrianorazzini.remote.model.Identifier
import br.com.adrianorazzini.remote.model.WidgetData
import br.com.adrianorazzini.widgetchallenge.R
import br.com.adrianorazzini.widgetchallenge.WidgetApplication
import br.com.adrianorazzini.widgetchallenge.common.databinding.BaseViewModel
import br.com.adrianorazzini.widgetchallenge.common.dependencies.getWidgetController
import br.com.adrianorazzini.widgetchallenge.ui.home.model.CardViewItem
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<MainViewState>() {

    private val widgetController: WidgetController = getWidgetController()

    private var header: WidgetData? = null
    private var widgets: List<WidgetData>? = null

    val homeHeaderField: ObservableField<String> = ObservableField("")

    override fun onResume() {
        super.onResume()

        updateViewState(MainViewState(loading = true))
        clear()
        loadWidgets()
    }

    private fun clear() {
        header = null
        widgets = null

        homeHeaderField.set("")
    }

    private fun loadWidgets() {
        viewModelScope.launch {
            val result = widgetController.getWidgets()
            val homeCardItems = ArrayList<CardViewItem>()
            header = result?.find {
                Identifier.HOME_HEADER_WIDGET == Identifier.fromString(it.identifier)
            }
            widgets = result?.filter {
                Identifier.HOME_HEADER_WIDGET != Identifier.fromString(it.identifier)
            }?.onEach {
                homeCardItems.add(CardViewItem.fromWidget(it))
            }

            setupHomeHeader()
            updateViewState(MainViewState(homeCardItems))
        }
    }

    private fun setupHomeHeader() {
        homeHeaderField.set(
            header?.content?.title
                ?: WidgetApplication.appContext.getString(R.string.home_header_invalid)
        )
    }
}