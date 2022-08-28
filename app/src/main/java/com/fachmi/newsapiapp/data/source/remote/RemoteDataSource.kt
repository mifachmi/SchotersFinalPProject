package com.fachmi.newsapiapp.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fachmi.newsapiapp.data.source.remote.network.ApiService
import com.fachmi.newsapiapp.data.source.remote.response.ApiResponse
import com.fachmi.newsapiapp.data.source.remote.response.ArticleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class RemoteDataSource private constructor(
    private val apiService: ApiService,
) {
    companion object {
        private val TAG = RemoteDataSource::class.java.simpleName.toString()
        private const val ERROR_MESSAGE_IO_EXCEPTION = "Periksa koneksi internet Anda"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource {
            return instance ?: synchronized(this) {
                instance ?: RemoteDataSource(apiService)
            }
        }
    }

    fun getNews(): LiveData<ApiResponse<ArticleResponse>> {
        val result = MutableLiveData<ApiResponse<ArticleResponse>>().apply {
            value = ApiResponse.Loading
        }

        apiService.getArticle().enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                val data = response.body()
                result.value = if (response.isSuccessful && data != null) {
                    if (data.status == "ok") {
                        if (data.articles?.isNotEmpty() == true) {
                            ApiResponse.Success(data)
                        } else {
                            ApiResponse.Empty
                        }
                    } else {
                        ApiResponse.Error(data.status.orEmpty())
                    }
                } else {
                    ApiResponse.Error(response.message())
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                val errorMessage =
                    if (t is IOException) ERROR_MESSAGE_IO_EXCEPTION
                    else t.message.toString()
                result.value = ApiResponse.Error(errorMessage)
                Log.e(TAG, "error ${call.request().url()}\n${t.stackTraceToString()}")
            }

        })

        return result
    }
}