package br.com.adrianorazzini.widgetchallenge.ui.home.model

import br.com.adrianorazzini.remote.model.Identifier
import br.com.adrianorazzini.remote.model.WidgetContent
import br.com.adrianorazzini.remote.model.WidgetData
import br.com.adrianorazzini.widgetchallenge.R
import br.com.adrianorazzini.widgetchallenge.WidgetApplication

data class CardViewItem(
    val identifier: String,
    val title: String,
    val secondaryInfo: String,
    var secondaryValue: String?,
    var actionButton: String?
) {

    companion object {
        fun fromWidget(widget: WidgetData)
                : CardViewItem {
            return CardViewItem(
                identifier = widget.identifier,
                title = parseTitle(widget.content.title),
                secondaryInfo = parseSecondaryInfo(widget.content),
                secondaryValue = widget.content.balance?.value,
                actionButton = parseActionButtonLabel(widget.identifier)
            )
        }

        private fun parseTitle(title: String?): String {
            return title?.let {
                it
            } ?: run {
                WidgetApplication.appContext.getString(R.string.unavailable)
            }
        }

        private fun parseSecondaryInfo(content: WidgetContent): String {
            return content.cardNumber?.let {
                it
            } ?: run {
                content.balance?.label
            } ?: run {
                WidgetApplication.appContext.getString(R.string.unavailable)
            }
        }

        private fun parseActionButtonLabel(identifier: String): String? {
            return when {
                Identifier.fromString(identifier) == Identifier.HOME_CARD_WIDGET -> {
                    WidgetApplication.appContext.getString(R.string.home_card_button_details)
                }
                Identifier.fromString(identifier) == Identifier.HOME_STATEMENT_WIDGET -> {
                    WidgetApplication.appContext.getString(R.string.home_card_button_statement)
                }
                else -> null
            }
        }
    }
}
