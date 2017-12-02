package com.aryabhata.onstore.shopping;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aryabhata.onstore.R;

/**
 * Created by Sainath on 2/28/2015.
 */
public class Welcome extends Fragment{

    private WebView webView;

    String url_data = "http://onstore-apphack.github.io/mobileui/";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.webview, container, false);
        webView = (WebView) rootView.findViewById(R.id.web_view);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new HelloWebViewClient());
        webView.getSettings().setBuiltInZoomControls(false);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(Welcome view, int progress)
            {
                if(progress == 100)
                    getActivity().setTitle(R.string.app_name);
            }
        });

        webView.loadUrl(this.url_data);

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
