package net.jfun.legato;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebviewActivity extends BaseActivity {

    ProgressBar progress;
    WebView web;

    Context mContext;

    String starturl;

    String pakegename;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);

        mContext = WebviewActivity.this;

        pakegename = this.getPackageName();

        web = (WebView)findViewById(R.id.web);
        progress=(ProgressBar)findViewById(R.id.progress);

        starturl = getIntent().getStringExtra("url");


        web.setWebViewClient(new myWebClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setBuiltInZoomControls(false);
        web.getSettings().setSupportZoom(true);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setPluginState(WebSettings.PluginState.ON);
        web.getSettings().setSupportMultipleWindows(true);
        web.loadUrl(starturl);

    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        web.saveState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        web.restoreState(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
        if (web != null) {
            if (web.canGoBack()) {
                web.goBack();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.web_btn_back :
                if (web != null) {
                    if (web.canGoBack()) {
                        web.goBack();
                    } else {
                        finish();
                    }
                } else {
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }

    public class myWebClient extends WebViewClient
    {
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress.setVisibility(web.VISIBLE);
        }
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(web.INVISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("kakaolink:") || url.startsWith("market:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }else if (url.startsWith("tel:") || url.startsWith("sms:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                startActivity(intent);
            }else{
                view.loadUrl(url);
            }
            return true;
        }
    }

}
