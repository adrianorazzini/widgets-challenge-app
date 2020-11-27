package br.com.adrianorazzini.remote.model

import com.google.gson.annotations.SerializedName

data class Statement(
    @SerializedName("balance") val balance: Balance,
    @SerializedName("transactions") val transactions: List<Transactions>
)

data class Balance(
    @SerializedName("label") val label: String,
    @SerializedName("value") val value: String
)

data class Transactions(
    @SerializedName("label") val label: String,
    @SerializedName("value") val value: String,
    @SerializedName("description") val description: String
)