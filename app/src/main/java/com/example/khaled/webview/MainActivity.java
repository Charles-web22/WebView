package com.example.khaled.webview;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="Mainactivity" ;
    WebView webView ;
    TextView interntTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        webView = (WebView) findViewById(R.id.webView);
        interntTV = (TextView)findViewById(R.id.nointernet_textview);

        String url = "https://www.youm7.com/";
        webView.loadUrl(url);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        if (!isConnected()){
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
            enableHTML5AppCache();

           /* if (webView.canGoForward()){
                webView.setVisibility(View.INVISIBLE);
                interntTv.setVisibility(View.VISIBLE);}*/

        }



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    private void enableHTML5AppCache() {

        webView.getSettings().setDomStorageEnabled(true);

        // This next one is crazy. It's the DEFAULT location for your app's cache
        // But it didn't work for me without this line
        webView.getSettings().setAppCachePath("/data/data/"+ getPackageName() +"/cache");
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);


        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        if (webView.getSettings().getCacheMode()==WebSettings.LOAD_NO_CACHE){

        }

    }

    public boolean isConnected(){
        ConnectivityManager icheck = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        boolean wifi = (icheck.getActiveNetworkInfo() != null);
        if(wifi) {
            return true;
        }
        return false;
    }

}
