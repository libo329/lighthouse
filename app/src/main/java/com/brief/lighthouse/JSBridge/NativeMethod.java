package com.brief.lighthouse.JSBridge;

import android.webkit.WebView;

import com.housenkui.sdbridgejava.Callback;
import com.housenkui.sdbridgejava.WebViewJavascriptBridge;

import java.util.AbstractMap;
import java.util.HashMap;

public class NativeMethod {
    private static final String TAG = "LightHouseNativeMethod";
    private WebViewJavascriptBridge bridge_;
    private WebView webView_;
    public NativeMethod(WebViewJavascriptBridge bridge, WebView view){
        bridge_ = bridge;
        webView_ = view;
    }

    // 所有要被js调用的函数在这里注册
    public void registerMethod(){
        bridge_.register("DeviceLoadJavascriptSuccess", (map, callback) -> {
            DeviceLoadJavascriptSuccess(map,callback);
        });
    }

    private void  DeviceLoadJavascriptSuccess(AbstractMap<String, Object> map, Callback callback){
        System.out.println(map);
        HashMap<String,Object> result = new HashMap<>();
        result.put("system","Android");
        result.put("width",webView_.getWidth());
        result.put("height",webView_.getHeight());
        System.out.println(result);
        callback.call(result);
    }

}
