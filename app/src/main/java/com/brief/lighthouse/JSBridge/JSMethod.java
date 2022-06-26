package com.brief.lighthouse.JSBridge;

import android.annotation.SuppressLint;

import com.housenkui.sdbridgejava.Callback;
import com.housenkui.sdbridgejava.Handler;
import com.housenkui.sdbridgejava.WebViewJavascriptBridge;

import java.util.HashMap;

public class JSMethod {
    private WebViewJavascriptBridge bridge_;

    public JSMethod(WebViewJavascriptBridge bridge){
        bridge_ = bridge;
    }


    // 跟js函数一一对应
    public void GetToken(HashMap<String,String> data, Callback callback){
        bridge_.call("GetToken", data,callback);
    }
    public void AsyncCall(HashMap<String,String> data, Callback callback){
        bridge_.call("AsyncCall", data,callback);
    }
    public void OnChooseFile(HashMap<String,String> data, Callback callback){
        bridge_.call("OnChooseFile", data,callback);
    }
}
