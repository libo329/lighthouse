package com.brief.lighthouse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.webkit.WebView;

import com.brief.lighthouse.JSBridge.MainWebView;
import com.brief.lighthouse.fileManager.ChooseFileOrDirActivity;
import com.brief.lighthouse.fileManager.FileChooseUtil;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LightHouse";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webview = findViewById(R.id.webView);
        //支持javascript
        webview.getSettings().setJavaScriptEnabled(true);
// 设置可以支持缩放
        webview.getSettings().setSupportZoom(true);
// 设置出现缩放工具
        webview.getSettings().setBuiltInZoomControls(true);
//扩大比例的缩放
        webview.getSettings().setUseWideViewPort(true);
//自适应屏幕
//        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webview.getSettings().setLoadWithOverviewMode(true);
//        Button buttonSync = findViewById(R.id.buttonSync);
//        Button buttonAsync = findViewById(R.id.buttonAsync);
//        buttonSync.setOnClickListener(this);
//        buttonAsync.setOnClickListener(this);

        MainWebView.getInstance().setupView(webview,this);

        FileChooseUtil.getInstance(this).checkPermission();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            switch (requestCode) {
              case ChooseFileOrDirActivity.SingleFile:
              Uri uri=data.getData();
              String chooseFilePath= FileChooseUtil.getInstance(this).getChooseFileResultPath(uri);
              Log.d(TAG,"选择文件返回："+chooseFilePath);
              HashMap<String,String> result = new HashMap<>();
              result.put("file",chooseFilePath);
              MainWebView.getInstance().jsMethod_.OnChooseFile(result,map -> {
                System.out.println("Next line is javascript data->>>");
                System.out.println(map);
            });
             // sendFileMessage(chooseFilePath);
              break;
            }
        }
    }
}
