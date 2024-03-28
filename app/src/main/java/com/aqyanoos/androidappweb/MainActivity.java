package com.aqyanoos.androidappweb;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebViewAssetLoader;
import androidx.webkit.WebViewClientCompat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView mywebView = findViewById(R.id.my_web_view);

        mywebView.getSettings().setJavaScriptEnabled(true);
        mywebView.setWebChromeClient(new WebChromeClient());
        mywebView.addJavascriptInterface(new WebInterface(this), "Android");

        mywebView.setWebContentsDebuggingEnabled(false);

        final WebViewAssetLoader assetLoader = new WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this))
                .addPathHandler("/res/", new WebViewAssetLoader.AssetsPathHandler(this))
                .build();

        mywebView.setWebViewClient(new LocalContentWebViewClient(assetLoader));

        mywebView.loadUrl("https://appassets.androidplatform.net/assets/www/index.html");

    }

    private static class LocalContentWebViewClient extends WebViewClientCompat {
        private final WebViewAssetLoader myAssetLoader;

        LocalContentWebViewClient(WebViewAssetLoader assetLoader){
            myAssetLoader = assetLoader;
        }

        @Override
        @RequiresApi(21)
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request){
            return myAssetLoader.shouldInterceptRequest(request.getUrl());
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return myAssetLoader.shouldInterceptRequest(Uri.parse(url));
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            String[] links = {"aqyanoos", "twitter", "facebook", "instagram", "youtube"};

            for(int i = 0; i < links.length; i++) {
                if(url.contains(links[i])){
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    );
                    return true;
                }
            }


            return false;
        }

    }

    public class WebInterface {
        Context mainContext;

        WebInterface(Context _context) {
            mainContext = _context;
        }

        @JavascriptInterface
        public String SocialMediaLinks() {
            return "https://www.youtube.com/@coding-aqyanoos&YouTube;"+
                    "https://www.facebook.com/CodingAqyanoos&Facebook;"+
                    "https://www.instagram.com/coding_aqyanoos&Instagram;"+
                    "https://www.twitter.com/Coding_Aqyanoos&Twitter";
        }
    }
}