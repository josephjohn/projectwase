package com.aryabhata.onstore.onstore;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aryabhata.onstore.R;

/**
 * Created by Sainath on 2/27/2015.
 */
public class Offers extends Fragment{

    private WebView webView;

    String url = "http://onstore-apphack.github.io/github_cart/";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.webview, container, false);
        webView = (WebView) rootView.findViewById(R.id.web_view);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new HelloWebViewClient());
        webView.getSettings().setBuiltInZoomControls(false);

        webView.loadUrl(url);
        return rootView;
    }

    public class HelloWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }
    }
}
