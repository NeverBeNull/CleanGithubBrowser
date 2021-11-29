package com.neverbenull.cleangithubbrowser.data.remote.api.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ApiCallAdapter<T>(
    private val responseType: Type,
) : CallAdapter<T, Call<ApiResponse<T>>>{

    override fun responseType() = responseType

    override fun adapt(call: Call<T>): Call<ApiResponse<T>> {
        return ApiResponseCall(call)
    }

}