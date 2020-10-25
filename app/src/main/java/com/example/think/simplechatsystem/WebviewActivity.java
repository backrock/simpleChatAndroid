package com.example.think.simplechatsystem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

public class WebviewActivity extends BaseActivity {
    WebView webView;
     private ArrayList<String> loadHistoryUrls = new ArrayList<String>();
    public static void startActivity(Activity activity, String url){
        Intent intent = new Intent(activity, WebviewActivity.class);
        intent.putExtra("url",url);
        activity.startActivity(intent);
    }
    @Override
    protected void initData(){}
    @Override
    protected void initView(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {        //不支持WebSocket

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
       String url = getIntent().getStringExtra("url");
       // boolean bsysBrser = getIntent().getBooleanExtra("UseSystemBrowser",false);
        webView = (WebView) findViewById(R.id.webView);

        WebSettings wSet = webView.getSettings();

        wSet.setJavaScriptEnabled(true);
        //wSet.setRenderPriority(WebSettings.RenderPriority.HIGH);
        wSet.setJavaScriptCanOpenWindowsAutomatically(true);
        wSet.setAllowFileAccess(true);
        wSet.setSupportZoom(true);
        wSet.setAllowContentAccess(true);
        wSet.setAllowFileAccessFromFileURLs(true);
        wSet.setPluginState(WebSettings.PluginState.ON);
      //  wSet.setSaveFormData(false);
       // wSet.setLoadsImagesAutomatically(true);
       wSet.setDomStorageEnabled(true);
       wSet.setAppCacheEnabled(true);
        // 设置出现缩放工具
        wSet.setBuiltInZoomControls(true);
//扩大比例的缩放
        wSet.setUseWideViewPort(true);
//自适应屏幕
        wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wSet.setLoadWithOverviewMode(true);
        wSet.setBlockNetworkImage(false);//解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wSet.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); //minSdkVersion 21
        }
       CookieManager.getInstance().setAcceptCookie(true);
      // webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
               // Log.d(TAG,"onPageStarted");
                WebviewActivity.this.showIndeterminateDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //Log.d(TAG,"onPageFinished");
                if (WebviewActivity.this.isShowIndeterminateDialog())
                    WebviewActivity.this.dismissIndeterminateDialog();
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loadHistoryUrls.add(url);
                // 在APP内部打开链接，不要调用系统浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  // 接受所有网站的证书
               // super.onReceivedSslError(view, handler, error);
            }
        });

        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
