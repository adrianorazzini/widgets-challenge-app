package br.com.adrianorazzini.remote.model

import com.google.gson.annotations.SerializedName

data class WidgetResponse(
    @SerializedName("widgets") val widgets: List<WidgetData>
)

data class WidgetData(
    @SerializedName("identifier") val identifier: String,
    @SerializedName("content") val content: WidgetContent
)

data class WidgetContent(
    @SerializedName("title") val title: String,
    @SerializedName("cardNumber") val cardNumber: String?,
    @SerializedName("balance") val balance: Balance?,
    @SerializedName("button") val button: WidgetButton?
)

data class WidgetButton(
    @SerializedName("text") val title: String,
    @SerializedName("action") val action: ButtonAction
)

data class ButtonAction(
    @SerializedName("identifier") val identifier: String,
    @SerializedName("content") val content: ActionContent
)

data class ActionContent(
    @SerializedName("cardId") val cardId: String?,
    @SerializedName("accountId") val accountId: String?
)