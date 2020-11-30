package br.com.adrianorazzini.widgetchallenge.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import br.com.adrianorazzini.remote.controller.CardController
import br.com.adrianorazzini.remote.controller.StatementController
import br.com.adrianorazzini.remote.controller.WidgetController
import br.com.adrianorazzini.remote.model.*
import br.com.adrianorazzini.widgetchallenge.R
import br.com.adrianorazzini.widgetchallenge.WidgetApplication
import br.com.adrianorazzini.widgetchallenge.common.databinding.BaseViewModel
import br.com.adrianorazzini.widgetchallenge.common.dependencies.getCardController
import br.com.adrianorazzini.widgetchallenge.common.dependencies.getStatementController
import br.com.adrianorazzini.widgetchallenge.common.dependencies.getWidgetController
import br.com.adrianorazzini.widgetchallenge.ui.home.model.CardViewItem
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<MainViewState>() {

    private val widgetController: WidgetController = getWidgetController()
    private val cardController: CardController = getCardController()
    private val statementController: StatementController = getStatementController()

    private var homeHeaderInfo: WidgetData? = null
    private var widgetsInfo: List<WidgetData>? = null
    private var selectedCardId: String? = null
    private var cardInfo: Card? = null
    private var selectedAccountId: String? = null
    private var statementInfo: Statement? = null

    private var homeCardItems = ArrayList<CardViewItem>()

    val homeHeaderField: ObservableField<String> = ObservableField("")
    val cardInfoField: ObservableField<Card> = ObservableField()
    val balanceInfoField: ObservableField<Balance> = ObservableField()

    fun clear() {
        homeHeaderInfo = null
        widgetsInfo = null
        cardInfo = null

        homeCardItems.clear()

        homeHeaderField.set("")
        cardInfoField.set(null)
        balanceInfoField.set(null)
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
            updateViewState(MainViewState(cardItems = homeCardItems))
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
                    updateViewState(MainViewState(cardInfo = cardInfo))
                } else {
                    updateViewState(MainViewState(error = StateError.INVALID_CARD_ID))
                }
            } ?: run {
                updateViewState(MainViewState(error = StateError.INVALID_CARD_INFO))
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

    fun setSelectedAccountId(position: Int) {
        widgetsInfo?.let { widgets ->
            if (widgets.size > position) {
                selectedAccountId = widgets[position].content.button?.action?.content?.accountId
            }
        }
    }

    fun loadStatementInfo() {
        viewModelScope.launch {
            selectedAccountId?.let {
                if (it == Statement.DEFAULT_ACCOUNT_ID) {
                    statementInfo = statementController.getStatement(it)
                    balanceInfoField.set(statementInfo?.balance)

                    updateViewState(MainViewState(transactionItems = statementInfo?.transactions))
                } else {
                    updateViewState(MainViewState(error = StateError.INVALID_ACCOUNT_ID))
                }
            } ?: run {
                updateViewState(MainViewState(error = StateError.INVALID_STATEMENT_INFO))
            }
        }
    }

    fun clearStatementInfo() {
        selectedAccountId = null

        // clear error
        updateViewState(MainViewState(cardItems = homeCardItems))
    }
}