package com.brief.lighthouse.JSBridge;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.brief.lighthouse.R;
import com.housenkui.sdbridgejava.WebViewJavascriptBridge;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class MainWebView {
    private static final String TAG = "LightHouseMainWebView";
    private WebViewJavascriptBridge bridge;
    public NativeMethod nativeMethod_;
    public JSMethod jsMethod_;

    public static MainWebView getInstance(){
        return Inner.instance;
    }
    private static  class Inner{
        private static final MainWebView instance= new MainWebView();
    }

    public void setupView(WebView webview, Context ctx ) {
        setAllowUniversalAccessFromFileURLs(webview);
        bridge = new WebViewJavascriptBridge(ctx,webview);
        bridge.consolePipe(string -> {
            System.out.println("Next line is javascript console.log->>>");
            System.out.println(string);
        });
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG,"shouldOverrideUrlLoading");
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d(TAG,"onPageStarted");
                bridge.injectJavascript();
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG,"onPageFinished");
            }
        });

        nativeMethod_ = new NativeMethod(bridge,webview);
        nativeMethod_.registerMethod();

        jsMethod_ = new JSMethod(bridge);

        // Loading html in local ，This way maybe meet cross domain. So You should not forget to set
        // /*...setAllowUniversalAccessFromFileURLs... */
        // If you loading remote web server,That can be ignored.
        webview.loadUrl("file:///android_asset/lighthouse.html");

//      index.html use SDBridge.js. This js file was create by webpack.
//      webview.loadUrl("file:///android_asset/index.html");
    }
    //Allow Cross Domain
    private void setAllowUniversalAccessFromFileURLs (WebView webView){
        try {
            Class<?> clazz = webView.getSettings().getClass();
            Method method = clazz.getMethod(
                    "setAllowUniversalAccessFromFileURLs", boolean.class);
            method.invoke(webView.getSettings(), true);
        } catch (IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
