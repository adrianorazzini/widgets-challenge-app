package br.com.adrianorazzini.remote.model

import com.google.gson.annotations.SerializedName

data class Widget(
    @SerializedName("identifier") val identifier: String,
    @SerializedName("content") val content: WidgetContent
)

data class WidgetContent(
    @SerializedName("title") val title: String,
    @SerializedName("cardNumber") val cardNumber: String?,
    @SerializedName("button") val button: WidgetButton?
)

data class WidgetButton(
    @SerializedName("text") val title: String,
    @SerializedName("action") val action: ButtonAction
)

data class ButtonAction(
    @SerializedName("identifier") val identifier: String,
    @SerializedName("cardId") val cardId: String?,
    @SerializedName("cardId") val accountId: String?
)