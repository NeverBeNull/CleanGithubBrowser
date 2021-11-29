package com.neverbenull.cleangithubbrowser.data.remote.api.adapter

import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.util.regex.Pattern

sealed class ApiResponse<T> {

    data class ApiSuccessResponse<T>(val body: T, val links: Map<String, String>) : ApiResponse<T>() {
        constructor(body: T, linkHeader: String?) : this(
            body = body,
            links = linkHeader?.extractLinks() ?: emptyMap()
        )

        val nextPage: Int? by lazy(LazyThreadSafetyMode.NONE) {
            links[NEXT_LINK]?.let { next ->
                val matcher = PAGE_PATTERN.matcher(next)
                if (!matcher.find() || matcher.groupCount() != 1) {
                    null
                } else {
                    try {
                        Integer.parseInt(matcher.group(1)!!)
                    } catch (ex: NumberFormatException) {
                        Timber.w("cannot parse next page from %s", next)
                        null
                    }
                }
            }
        }

        companion object {
            private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
            private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
            private const val NEXT_LINK = "next"

            private fun String.extractLinks(): Map<String, String> {
                val links = mutableMapOf<String, String>()
                val matcher = LINK_PATTERN.matcher(this)

                while (matcher.find()) {
                    val count = matcher.groupCount()
                    if (count == 2) {
                        links[matcher.group(2)!!] = matcher.group(1)!!
                    }
                }
                return links
            }

        }
    }

    class ApiEmptyResponse<T> : ApiResponse<T>()

    sealed class ApiErrorResponse<T> : ApiResponse<T>() {
        abstract val error: Throwable
        abstract val errorMessage : String

        data class ApiHttpErrorResponse<T>(
            override val error: HttpException
        ) : ApiErrorResponse<T>() {
            override val errorMessage: String
                get() = error.message ?: "unknown http error"

            private val errorCode : Int
                get() = error.code()
        }

        data class ApiNetworkErrorResponse<T>(
            override val error: Throwable
        ) : ApiErrorResponse<T>() {
            override val errorMessage: String
                get() = error.message ?: "unknown network error"
        }

        data class ApiApplicationErrorResponse<T>(
            override val error: Throwable
        ) : ApiErrorResponse<T>() {
            override val errorMessage: String
                get() = error.message ?: "unknown application error"
        }

        data class ApiErrorBodyResponse<T>(
            private val response: Response<T>
        ) : ApiErrorResponse<T>() {
            override val errorMessage: String
                get() {
                    val msg = response.errorBody()?.string()
                    val errorMessage = if (msg.isNullOrEmpty()) {
                        response.message()
                    } else {
                        msg
                    }

                    return errorMessage ?: "unknown errorBody response error"
                }

            override val error: Throwable
                get() {
                    return IllegalStateException(errorMessage)
                }

            private val errorCode : Int
                get() = response.code()
        }

        companion object {
            fun <T> createApiErrorResponse(error: Throwable) : ApiErrorResponse<T> {
                return if(error is HttpException) {
                    ApiHttpErrorResponse(error)

                } else {
                    val cause = error.cause
                    if(cause != null) {
                        when(cause) {
                            is java.net.SocketException,
                            is java.net.UnknownHostException,
                            is javax.net.ssl.SSLException,
                            is java.io.InterruptedIOException -> {
                                ApiNetworkErrorResponse(error)
                            }
                            is org.json.JSONException,
                            is java.util.zip.ZipException,
                            is java.lang.IllegalArgumentException -> {
                                ApiApplicationErrorResponse(error)
                            }
                            else -> {
                                createApiErrorResponse(cause)
                            }
                        }

                    } else {
                        when(error) {
                            is java.net.SocketException,
                            is java.net.UnknownHostException,
                            is javax.net.ssl.SSLException,
                            is java.io.InterruptedIOException,
                            is java.net.SocketTimeoutException,
                            is java.net.ConnectException -> {
                                ApiNetworkErrorResponse(error)
                            }
                            else -> {
                                ApiApplicationErrorResponse(error)
                            }
                        }
                    }
                }
            }
        }
    }

}
