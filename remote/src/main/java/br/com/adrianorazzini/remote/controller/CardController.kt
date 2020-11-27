package br.com.adrianorazzini.remote.controller

import br.com.adrianorazzini.remote.model.Card
import br.com.adrianorazzini.remote.request.RequestApi

interface CardController {
    suspend fun getCardInfo(cardId: String): Card
}

class CardControllerImp : CardController {
    override suspend fun getCardInfo(cardId: String): Card {
        return RequestApi.create().getCardInfo(cardId)
    }
}