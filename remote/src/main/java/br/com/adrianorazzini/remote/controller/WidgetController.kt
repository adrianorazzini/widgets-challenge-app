package br.com.adrianorazzini.remote.controller

import br.com.adrianorazzini.remote.model.WidgetData
import br.com.adrianorazzini.remote.request.RequestApi

interface WidgetController {
    suspend fun getWidgets(): List<WidgetData>?
}

class WidgetControllerImp : WidgetController {
    override suspend fun getWidgets(): List<WidgetData>? {
        val response = RequestApi.create().getWidgets()
        return response?.widgets
    }
}