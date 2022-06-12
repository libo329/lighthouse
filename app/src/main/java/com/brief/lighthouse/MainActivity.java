package com.brief.lighthouse;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.brief.lighthouse.JSBridge.MainWebView;
import com.housenkui.sdbridgejava.WebViewJavascriptBridge;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LightHouse";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webview = findViewById(R.id.webView);
//        Button buttonSync = findViewById(R.id.buttonSync);
//        Button buttonAsync = findViewById(R.id.buttonAsync);
//        buttonSync.setOnClickListener(this);
//        buttonAsync.setOnClickListener(this);

        MainWebView.getInstance().setupView(webview,this);
    }


    @Override
    public void onClick(View view) {
//        if (view.getId() == R.id.buttonSync) {
//            Log.d(TAG,"buttonSync onClick");
//            HashMap<String, String> data = new HashMap<>();
//            data.put("AndroidKey00","AndroidValue00");
//            //call js sync function
//            MainWebView.getInstance().jsMethod_.GetToken(data, map -> {
//                System.out.println("Next line is javascript data->>>");
//                System.out.println(map);
//            });
//        }else if(view.getId() == R.id.buttonAsync){
//            Log.d(TAG,"buttonAsync onClick");
//            HashMap<String, String> data = new HashMap<>();
//            data.put("AndroidKey01","AndroidValue01");
//            //call js Async function
//            MainWebView.getInstance().jsMethod_.AsyncCall(data, map -> {
//                System.out.println("Next line is javascript data->>>");
//                System.out.println(map);
//            });
//        }
    }


}
