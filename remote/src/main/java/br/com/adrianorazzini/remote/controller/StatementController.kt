package br.com.adrianorazzini.remote.controller

import br.com.adrianorazzini.remote.model.Statement
import br.com.adrianorazzini.remote.request.RequestApi

interface StatementController {
    suspend fun getStatement(cardId: String): Statement?
}

class StatementControllerImp : StatementController {
    override suspend fun getStatement(cardId: String): Statement? {
        return RequestApi.create().getStatement(cardId)
    }
}