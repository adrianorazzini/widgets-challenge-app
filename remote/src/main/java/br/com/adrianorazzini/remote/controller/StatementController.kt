package br.com.adrianorazzini.remote.controller

import br.com.adrianorazzini.remote.model.Statement
import br.com.adrianorazzini.remote.request.RequestApi

interface StatementController {
    suspend fun getStatement(accountId: String): Statement?
}

class StatementControllerImp : StatementController {
    override suspend fun getStatement(accountId: String): Statement? {
        return RequestApi.create().getStatement(accountId)
    }
}