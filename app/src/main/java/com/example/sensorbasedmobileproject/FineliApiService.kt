package com.example.sensorbasedmobileproject

import android.content.Context
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


interface FineliApiService {


    fun getSSLConfig(context: Context): SSLContext? {

        // Loading CAs from an InputStream
        var cf: CertificateFactory? = null
        cf = CertificateFactory.getInstance("X.509")
        var ca: Certificate
        context.resources.openRawResource(R.raw.mycert).use { cert ->
            ca = cf.generateCertificate(cert)
        }

        // Creating a KeyStore containing our trusted CAs
        val keyStoreType: String = KeyStore.getDefaultType()
        val keyStore: KeyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", ca)

        // Creating a TrustManager that trusts the CAs in our KeyStore.
        val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
        val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm)
        tmf.init(keyStore)

        // Creating an SSLSocketFactory that uses our TrustManager
        val sslContext: SSLContext = SSLContext.getInstance("TLS")
        sslContext.init(null, tmf.getTrustManagers(), null)
        return sslContext
    }


    @GET("foods")
    fun getFineliData(@Query("q") q: String): Observable<FineliResponse.FineliResponse>

    companion object {


        val BASE_API_URL = "https://www.fineli.fi/fineli/api/v1/"


        fun create(
//            context: Context
        ): FineliApiService {

//
//            var cf: CertificateFactory? = null
//            cf = CertificateFactory.getInstance("X.509")
//            var ca: Certificate
//            context.resources.openRawResource(R.raw.mycert).use { cert ->
//                ca = cf.generateCertificate(cert)
//            }
//
//            // Creating a KeyStore containing our trusted CAs
//            val keyStoreType = KeyStore.getDefaultType()
//            val keyStore = KeyStore.getInstance(keyStoreType)
//            keyStore.load(null, null)
//            keyStore.setCertificateEntry("ca", ca)
//
//
//            // Creating a TrustManager that trusts the CAs in our KeyStore.
//            val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
//            val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
//            tmf.init(keyStore)
//
//
//            // Creating an SSLSocketFactory that uses our TrustManager
//            val sslContext = SSLContext.getInstance("TLS")
//            sslContext.init(null, tmf.trustManagers, null)
//
//            Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Constant.BASE_API_URL);
//
//            OkHttpClient okHttp = new OkHttpClient();
//            okHttp.setSslSocketFactory(getSSLConfig(contex).getSocketFactory());
//
//            Retrofit retrofit = builder.client(okHttp).build();
//            retrofit.create(serviceClass)


            val trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm()
            )
            trustManagerFactory.init(null as KeyStore?)
            val trustManagers = trustManagerFactory.trustManagers
            check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                ("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers))
            }
            val trustManager = trustManagers[0] as X509TrustManager

            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
            val sslSocketFactory = sslContext.socketFactory

            // Retrofit
            val client = OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl(BASE_API_URL)
                .client(client)
                .build()

            return retrofit.create(FineliApiService::class.java)
        }
    }


}
