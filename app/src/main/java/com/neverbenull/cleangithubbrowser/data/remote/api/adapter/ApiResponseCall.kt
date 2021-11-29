package com.neverbenull.cleangithubbrowser.data.remote.api.adapter

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiResponseCall<T>(
    private val delegate: Call<T>
) : Call<ApiResponse<T>> {

    override fun enqueue(callback: Callback<ApiResponse<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body == null || response.code() == 204) {
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiResponse.ApiEmptyResponse())
                        )

                    } else {
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(
                                ApiResponse.ApiSuccessResponse(
                                body = body,
                                linkHeader = response.headers()["link"])
                            )
                        )
                    }

                } else {
                    callback.onResponse(
                        this@ApiResponseCall,
                        Response.success(ApiResponse.ApiErrorResponse.ApiErrorBodyResponse(response))
                    )
                }
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                callback.onResponse(
                    this@ApiResponseCall,
                    Response.success(ApiResponse.ApiErrorResponse.createApiErrorResponse(throwable))
                )
            }
        })
    }

    override fun clone(): Call<ApiResponse<T>> {
        return ApiResponseCall(delegate.clone())
    }

    override fun execute(): Response<ApiResponse<T>> {
        throw UnsupportedOperationException("ApiResponseCall doesn't support execute")
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }

}