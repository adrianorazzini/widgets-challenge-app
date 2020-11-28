package br.com.adrianorazzini.remote.model

enum class Identifier constructor(val value: String) {

    HOME_HEADER_WIDGET("HOME_HEADER_WIDGET"),

    HOME_CARD_WIDGET("HOME_CARD_WIDGET"),

    HOME_STATEMENT_WIDGET("HOME_STATEMENT_WIDGET"),

    UNKNOWN("UNKNOWN");

    override fun toString(): String {
        return this.value
    }

    companion object {
        fun fromString(value: String): Identifier? {
            for (status in Identifier.values()) {
                if (status.value.equals(value, ignoreCase = true)) {
                    return status
                }
            }

            return null
        }
    }
}