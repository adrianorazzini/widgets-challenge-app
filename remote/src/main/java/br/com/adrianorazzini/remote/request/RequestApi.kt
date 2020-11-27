package br.com.adrianorazzini.remote.request

import br.com.adrianorazzini.remote.BuildConfig
import br.com.adrianorazzini.remote.model.Card
import br.com.adrianorazzini.remote.model.Statement
import br.com.adrianorazzini.remote.model.Widget
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestApi {

    @GET("home")
    suspend fun getWidgets(): List<Widget>

    @GET("card/cardId")
    suspend fun getCardInfo(@Path("cardId") cardId: String): Card

    @GET("statement/cardId")
    suspend fun getStatement(@Path("cardId") cardId: String): Statement

    companion object Factory {

        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val INTERNAL_SERVER_ERROR = 500
        const val CONTENT_TYPE = "Content-Type"
        const val APPLICATION_JSON = "application/json"
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer "

        fun create(): RequestApi {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build()
                .create(RequestApi::class.java)
        }

        private fun createOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder().build()
        }
    }
}