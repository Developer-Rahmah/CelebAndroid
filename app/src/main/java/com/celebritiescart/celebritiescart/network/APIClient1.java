package com.celebritiescart.celebritiescart.network;


import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * APIClient handles all the Network API Requests using Retrofit Library
 **/

public class APIClient1 {


    // Base URL for API Requests
    public static String BASE_URL = ConstantValues.ECOMMERCE_BASE_URL;

    private static APIRequests apiRequests;
    private static Retrofit retrofit;
    private static Gson gson;

    // Singleton Instance of APIRequests
    public static APIRequests getInstance() {
        if (apiRequests == null) {

            OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
//            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                    .connectTimeout(60, TimeUnit.SECONDS)
//                    .readTimeout(120, TimeUnit.SECONDS)
//                    .writeTimeout(120, TimeUnit.SECONDS)
//                    .build();

            if (ConstantValues.LANGUAGE_ID == 1) {
                BASE_URL = ConstantValues.ECOMMERCE_BASE_URL;
            } else {
                BASE_URL = ConstantValues.ECOMMERCE_BASE_URL;

            }

            try {
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }});

                X509TrustManager[] trustManager = new X509TrustManager[]{new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }};


                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, trustManager, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(
                        context.getSocketFactory());


                okHttpClient.sslSocketFactory(context.getSocketFactory(),trustManager[0])
                        .hostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return hostname.equals("celebritiescart.com");
                            }
                        });
            } catch (Exception e) { // should never happen
                e.printStackTrace();
            }
            gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();


            apiRequests = retrofit.create(APIRequests.class);

            return apiRequests;
        } else {
            return apiRequests;
        }
    }

    public static void createNewRetrofitInstance(String BASE_URL) {
        retrofit = null;

        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();


        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});

            X509TrustManager[] trustManager = new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};


            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, trustManager, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());


            okHttpClient.sslSocketFactory(context.getSocketFactory(),trustManager[0])
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return hostname.equals("celebritiescart.com");
                        }
                    });
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }

        gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        apiRequests = retrofit.create(APIRequests.class);
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static void setRetrofit(Retrofit retrofit) {
        APIClient1.retrofit = retrofit;
    }
}


