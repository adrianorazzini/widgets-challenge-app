@file:JvmName("Dependencies")

package br.com.adrianorazzini.widgetchallenge.common.dependencies

import br.com.adrianorazzini.remote.controller.*

fun getWidgetController(): WidgetController {
    return WidgetControllerImp()
}

fun getCardController(): CardController {
    return CardControllerImp()
}

fun getStatementController(): StatementController {
    return StatementControllerImp()
}