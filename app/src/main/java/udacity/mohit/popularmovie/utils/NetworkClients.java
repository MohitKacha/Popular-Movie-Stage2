package udacity.mohit.popularmovie.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkClients {

    private OkHttpClient okHttpClient;
    private Context context;
    private NetworkResponse networkResponse;
    public NetworkClients(Context context, NetworkResponse networkResponse){
        okHttpClient = new OkHttpClient();
        this.context = context;
        this.networkResponse = networkResponse;

    }



    public void makeNetworkCall(String url, final String id) {

        final Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("NetworkClients","Error - onFailure"+e);

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //Log.d("NetworkClients", response.body().string());

                networkResponse.responseFromNetwork(response.body().string(), id);

            }
        });





    }

    public boolean isInternetAvailable(){
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
