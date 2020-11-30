package br.com.adrianorazzini.widgetchallenge.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import br.com.adrianorazzini.remote.controller.CardController
import br.com.adrianorazzini.remote.controller.WidgetController
import br.com.adrianorazzini.remote.model.Card
import br.com.adrianorazzini.remote.model.Identifier
import br.com.adrianorazzini.remote.model.WidgetData
import br.com.adrianorazzini.widgetchallenge.R
import br.com.adrianorazzini.widgetchallenge.WidgetApplication
import br.com.adrianorazzini.widgetchallenge.common.databinding.BaseViewModel
import br.com.adrianorazzini.widgetchallenge.common.dependencies.getCardController
import br.com.adrianorazzini.widgetchallenge.common.dependencies.getWidgetController
import br.com.adrianorazzini.widgetchallenge.ui.home.model.CardViewItem
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<MainViewState>() {

    private val widgetController: WidgetController = getWidgetController()
    private val cardController: CardController = getCardController()

    private var homeHeaderInfo: WidgetData? = null
    private var widgetsInfo: List<WidgetData>? = null
    private var selectedCardId: String? = null
    private var cardInfo: Card? = null

    private var homeCardItems = ArrayList<CardViewItem>()

    val homeHeaderField: ObservableField<String> = ObservableField("")
    val cardInfoField: ObservableField<Card> = ObservableField()

    fun clear() {
        homeHeaderInfo = null
        widgetsInfo = null
        cardInfo = null

        homeCardItems.clear()

        homeHeaderField.set("")
        cardInfoField.set(null)
    }

    fun loadWidgets() {
        viewModelScope.launch {
            val result = widgetController.getWidgets()

            homeHeaderInfo = result?.find {
                Identifier.HOME_HEADER_WIDGET == Identifier.fromString(it.identifier)
            }

            homeCardItems.clear()
            widgetsInfo = result?.filter {
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
            homeHeaderInfo?.content?.title
                ?: WidgetApplication.appContext.getString(R.string.home_header_invalid)
        )
    }

    fun setSelectedCardId(position: Int) {
        widgetsInfo?.let { widgets ->
            if (widgets.size > position) {
                selectedCardId = widgets[position].content.button?.action?.content?.cardId
            }
        }
    }

    fun loadCardInfo() {
        viewModelScope.launch {
            selectedCardId?.let {
                if (it == Card.DEFAULT_CARD_ID) {
                    cardInfo = cardController.getCardInfo(it)
                    cardInfoField.set(cardInfo)
                    updateViewState(MainViewState(cardItems = homeCardItems, cardInfo = cardInfo))
                } else {
                    updateViewState(
                        MainViewState(
                            cardItems = homeCardItems,
                            error = StateError.INVALID_CARD_ID
                        )
                    )
                }
            } ?: run {
                updateViewState(
                    MainViewState(
                        cardItems = homeCardItems,
                        error = StateError.INVALID_CARD_INFO
                    )
                )
            }
        }
    }

    fun clearCardInfo() {
        selectedCardId = null
        cardInfo = null
        cardInfoField.set(null)

        // clear error
        updateViewState(MainViewState(cardItems = homeCardItems))
    }
}