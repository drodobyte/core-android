package drodobyte.android.api

import android.util.Log
import com.squareup.moshi.Moshi
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Logger
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy.ACCEPT_ALL

object Api {

    fun <T> create(url: String, clazz: Class<T>, log: (String) -> Unit = defaultLogger): T =
        Retrofit.Builder()
            .baseUrl(url)
            .client(client(log))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(clazz)

    private fun interceptor(log: (String) -> Unit) =
        HttpLoggingInterceptor(Logger(log)).apply { level = BODY }

    private fun client(log: (String) -> Unit) =
        OkHttpClient.Builder()
            .addInterceptor(interceptor(log))
            .cookieJar(cookies)
            .build()

    private val cookies =
        JavaNetCookieJar(CookieManager().apply { setCookiePolicy(ACCEPT_ALL) })

    private val moshi =
        Moshi.Builder()
            .add(DateAdapter)
            .add(IdAdapter)
            .build()

    private val defaultLogger: (String) -> Unit = {
        if (it.contains("error", ignoreCase = true)) {
            Log.e("retrofit", it)
        } else {
            Log.i("retrofit", it)
        }
    }
}
