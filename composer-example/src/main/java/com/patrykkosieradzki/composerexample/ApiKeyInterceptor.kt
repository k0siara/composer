package com.patrykkosieradzki.composerexample

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
//            .newBuilder()
//            .addHeader(API_KEY_HEADER, BuildConfig.API_KEY)
//            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val API_KEY_HEADER = "x-access-token"
    }
}