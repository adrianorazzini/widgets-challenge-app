package br.com.adrianorazzini.remote.controller

import br.com.adrianorazzini.remote.model.Widget
import br.com.adrianorazzini.remote.request.RequestApi

interface WidgetController {
    suspend fun getWidgets(): List<Widget>
}

class WidgetControllerImp : WidgetController {
    override suspend fun getWidgets(): List<Widget> {
        return RequestApi.create().getWidgets()
    }
}