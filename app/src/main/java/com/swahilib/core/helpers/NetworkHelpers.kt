package com.swahilib.core.helpers

import android.util.Log
import io.ktor.client.call.DoubleReceiveException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

sealed interface NetworkResult<out T> {
    data class Failure(val exception: Exception) : NetworkResult<Nothing>

    data class Success<T>(val data: T) : NetworkResult<T>
}

suspend inline fun <reified T> safeApiCall(block: () -> HttpResponse): NetworkResult<T> {
    return try {
        val response = block.invoke()
        NetworkResult.Success(data = response.body())
    } catch (e: NoTransformationFoundException) {
        Log.d("TAG", e.toString())
        NetworkResult.Failure(exception = e)
    } catch (e: DoubleReceiveException) {
        Log.d("TAG", e.toString())
        NetworkResult.Failure(exception = e)
    } catch (e: Exception) {
        Log.d("TAG", e.toString())
        NetworkResult.Failure(exception = e)
    }
}