package br.com.adrianorazzini.remote.model

import com.google.gson.annotations.SerializedName

data class Statement(
    @SerializedName("balance") val balance: Balance,
    @SerializedName("transactions") val transactions: List<Transaction>
) {
    companion object {
        const val DEFAULT_ACCOUNT_ID = "123"
    }
}

data class Balance(
    @SerializedName("label") val label: String,
    @SerializedName("value") val value: String
)

data class Transaction(
    @SerializedName("label") val label: String,
    @SerializedName("value") val value: String,
    @SerializedName("description") val description: String
)