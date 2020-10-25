package com.example.think.simplechatsystem;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Su on 2015/8/3.
 */
public class MyApplication extends Application {
    public static MyApplication application;
//    private static final String TAG = MyApplication.class.getName();
//    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";
    public static MyApplication getInstance(){
        return application;
    }
Handler handler;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

        private HttpURLConnection getSSLConnection(HttpURLConnection connection){
            try {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new X509TrustManager[]{new TrustAnyTrustManager()}, new SecureRandom());
                ((HttpsURLConnection)connection).setSSLSocketFactory(sslContext.getSocketFactory());
            } catch (KeyManagementException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return connection;
        }



    private static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }
}
