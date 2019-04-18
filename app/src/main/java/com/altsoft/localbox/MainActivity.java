package com.altsoft.localbox;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.altsoft.framework.Global;
import com.altsoft.framework.hander.BackPressCloseHandler;
import com.altsoft.framework.hander.ChromeClient;
import com.altsoft.framework.module.BaseActivity;



public class MainActivity extends BaseActivity {
    private WebView mWebView;
    public static Activity activity;
    private BackPressCloseHandler backPressCloseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        ComponentInit();
    //  mWebView.getSettings().setUserAgentString(DESKTOP_USER_AGENT);
        mWebView.loadUrl(  "https://altsoft.ze.am/device/localboxview/" + Global.getDeviceInfo().DEVICE_CODE.toString());

    }


    private void ComponentInit()
    {
        mWebView = findViewById(R.id.webView);
        Global.getCommon().fullScreen();
        mWebView.setInitialScale(1);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setPluginState(WebSettings.PluginState.ON);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setEnableSmoothTransition(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        backPressCloseHandler = new BackPressCloseHandler(this);

        mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);

        mWebView.addJavascriptInterface(new WebBridge(),"java");
        // 뷰 가속 - 가속하지 않으면 영상실행 안됨, 소리만 나온다
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.setWebChromeClient(new ChromeClient(this){});

    }



    @Override
    public void onBackPressed() { //super.onBackPressed();
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }else{
            backPressCloseHandler.onBackPressed();
        }
    }

    class WebBridge{
        @JavascriptInterface
        public void getMethod(final int num){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (num)
                    {
                        case 0:
                            ActivityCompat.finishAffinity(Global.getCurrentActivity());
                            break;
                    }
                }
            });
        }
    }
}
