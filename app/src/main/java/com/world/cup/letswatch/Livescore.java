package com.world.cup.letswatch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

public class Livescore extends AppCompatActivity {
    private WebView mWebView,mWebView1;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livescore);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading livescore data...");
        progressDialog.setCancelable(false);
        mWebView = findViewById(R.id.webview2);
        mWebView1 = findViewById(R.id.webview3);

        String vdUrl = "<html><body>" + ApiUrl.livescore + "</body></html>";
        mWebView.requestFocus();
        mWebView.getSettings().setLightTouchEnabled(true);
        mWebView.setBackgroundColor(getResources().getColor(R.color.secondaryTextColor));

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setGeolocationEnabled(true);
        mWebView.setSoundEffectsEnabled(true);

        mWebView.loadData(vdUrl, "text/html", "UTF-8");
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url != null && url.startsWith("https://www.scorebat.com")) {
                    //do what you want here
//                    String vdUrl = "<html><body>" + url + "</body></html>";
                    mWebView.setVisibility(view.GONE);
                    mWebView1.getSettings().setJavaScriptEnabled(true); // enable javascript


                    mWebView1.setWebViewClient(new WebViewClient() {
                        @SuppressWarnings("deprecation")
                        @Override
                        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        }
                        @TargetApi(android.os.Build.VERSION_CODES.M)
                        @Override
                        public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                            // Redirect to deprecated method, so you can use it in all SDK versions
                            onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                        }
                    });

                    mWebView1.loadUrl(url);
                    //setContentView(mWebView1);
                    mWebView1.setWebChromeClient(new WebChromeClient() {
                        public void onProgressChanged(WebView view, int progress) {
                            if (progress < 100) {
                                progressDialog.show();
                            }
                            if (progress == 100) {
                                progressDialog.dismiss();
                            }
                        }
                    });
                    mWebView1.setVisibility(View.VISIBLE);

                    return true;
                } else {
                    return false;
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        if(mWebView.getVisibility()== View.GONE){
            mWebView1.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
        }
       else {
        StartAppAd.onBackPressed(this);
        super.onBackPressed();
        }
    }
}