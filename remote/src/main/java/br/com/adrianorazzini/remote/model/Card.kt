package br.com.adrianorazzini.remote.model

import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("cardNumber") val number: String,
    @SerializedName("cardName") val name: String,
    @SerializedName("expirationDate") val expDate: String,
    @SerializedName("availableLimit") val availableLimit: String,
    @SerializedName("totalLimit") val totalLimit: String
) {
    companion object {
        const val DEFAULT_CARD_ID = "123"
    }
}