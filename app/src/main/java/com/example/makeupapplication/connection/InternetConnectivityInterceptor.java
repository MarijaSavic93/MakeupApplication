package com.example.makeupapplication.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class InternetConnectivityInterceptor implements Interceptor {

    private Context context;

    public InternetConnectivityInterceptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if(!isInternetAvailable()){
            throw new NoConnectivityException();
        }
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

    public boolean isInternetAvailable() {
        return isConnected() && checkInternetPingGoogle();
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork == null){
                return false;
            }
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
            return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        } else {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }

    public boolean checkInternetPingGoogle(){
        Socket socket = new Socket();
        InetSocketAddress ipAddress = new InetSocketAddress("www.google.com", 443);
        int timeoutMilliseconds = 1500;
        try {
            socket.connect(ipAddress, timeoutMilliseconds);
            socket.close();
            return true;
        } catch (IOException e){
            return false;
        }

    }
}
