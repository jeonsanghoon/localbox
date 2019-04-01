package com.altsoft.localbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

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

        Global.getCommon().fullScreen();
        backPressCloseHandler = new BackPressCloseHandler(this);
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new WebBridge(),"java");
        // 뷰 가속 - 가속하지 않으면 영상실행 안됨, 소리만 나온다
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.setWebChromeClient(new ChromeClient(this){});
        mWebView.setWebViewClient(new WebViewClientClass(){});
    }



    @Override
    public void onBackPressed() { //super.onBackPressed();
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }else{
            backPressCloseHandler.onBackPressed();
        }
    }


    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL",url);
            view.loadUrl(url);
            return true;
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
