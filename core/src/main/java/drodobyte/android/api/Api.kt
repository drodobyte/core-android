package drodobyte.android.api

import android.annotation.SuppressLint
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
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object Api {

    fun <T> create(
        url: String,
        clazz: Class<T>,
        ignoreSSLErrors: Boolean = false,
        useUUID: Boolean = false,
        log: (String) -> Unit = defaultLogger
    ): T =
        Retrofit.Builder()
            .baseUrl(url)
            .client(client(ignoreSSLErrors, log))
            .addConverterFactory(MoshiConverterFactory.create(moshi(useUUID)))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(clazz)

    private fun interceptor(log: (String) -> Unit) =
        HttpLoggingInterceptor(Logger(log)).apply { level = BODY }

    private fun client(ignoreSSLErrors: Boolean, log: (String) -> Unit) =
        OkHttpClient.Builder()
            .addInterceptor(interceptor(log))
            .apply { if (ignoreSSLErrors) ignoreAllSSLErrors() }
            .cookieJar(cookies)
            .build()

    private val cookies =
        JavaNetCookieJar(CookieManager().apply { setCookiePolicy(ACCEPT_ALL) })

    private fun moshi(useUUID: Boolean) =
        Moshi.Builder()
            .add(DateAdapter)
            .apply { if (useUUID) add(UuidAdapter) }
            .build()

    private val defaultLogger: (String) -> Unit = {
        if (it.contains("error", ignoreCase = true)) {
            Log.e("retrofit", it)
        } else {
            Log.i("retrofit", it)
        }
    }

    private fun OkHttpClient.Builder.ignoreAllSSLErrors(): OkHttpClient.Builder {
        val naiveTrustManager = @SuppressLint("CustomX509TrustManager")
        object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
        }

        val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
            val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
            init(null, trustAllCerts, SecureRandom())
        }.socketFactory

        sslSocketFactory(insecureSocketFactory, naiveTrustManager)
        hostnameVerifier { _, _ -> true }
        return this
    }
}
