package com.brief.lighthouse.JSBridge;

import com.housenkui.sdbridgejava.WebViewJavascriptBridge;

import java.util.HashMap;

public class NativeMethod {
    private static final String TAG = "LightHouseNativeMethod";
    private WebViewJavascriptBridge bridge_;
    public NativeMethod(WebViewJavascriptBridge bridge){
        bridge_ = bridge;
    }

    // 所有要被js调用的函数在这里注册
    public void registerMethod(){
        bridge_.register("DeviceLoadJavascriptSuccess", (map, callback) -> {
            System.out.println("Next line is javascript data->>>");
            System.out.println(map);
            HashMap<String,String> result = new HashMap<>();
            result.put("result","Android");
            callback.call(result);
        });
    }
}
