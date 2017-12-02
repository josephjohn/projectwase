package com.aryabhata.onstore.settings;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aryabhata.onstore.R;
import com.aryabhata.onstore.shopping.Welcome;

/**
 * Created by Sainath on 2/27/2015.
 */
public class Settings extends Fragment{

    private WebView webView;
    String url = "http://onstore-apphack.github.io/issue/";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.webview, container, false);
        webView = (WebView) rootView.findViewById(R.id.web_view);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new HelloWebViewClient());
        webView.getSettings().setBuiltInZoomControls(false);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(Welcome view, int progress)
            {
                if(progress == 100)
                    getActivity().setTitle(R.string.app_name);
            }
        });

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
