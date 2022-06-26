package com.brief.lighthouse.fileManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

//import com.rdsa.singleselectedfileorfolder.R;
import com.brief.lighthouse.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChooseFileOrDirActivity extends Activity {
    public static final String EnterVar = "EnterVar";
    public static final String ResultData = "SELECTED";
    public static final int RequestCode = 888;
    public static final int ResultCode = 999;
    public static final int SingleFile = 1000;
    public static final int SingleFolder = 2000;
    public static final int MultiFile = 3000;
    public static final int MultiFolder = 2001;


    private String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    private static final int REQUESCODE = 5;
    private static final String NEED_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private ListView listView4File;
    private TextView noDataTip;
    private Button btChose;
    private List<String> data;
    private ArrayList<FileOrDirBeans> fileOrDirList;
    private FileAdapter fileAdapter;



    private int enterVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_choose_file_or_folder);

        this.enterVar = this.getIntent().getIntExtra(EnterVar, SingleFile);

        this.initView();
        this.initData();
        this.initEvent();

    }

    private void initData() {
        this.listView4File.setEmptyView(noDataTip);

        this.data = new ArrayList<>();
        this.fileOrDirList = new ArrayList<>();
        this.fileAdapter = new FileAdapter(this, fileOrDirList, enterVar);
        this.listView4File.setAdapter(fileAdapter);

        if (Build.VERSION.SDK_INT >= 23) {      // 判断系统版本是否大于6.0
            if (checkSelfPermission(NEED_PERMISSION) == PackageManager.PERMISSION_GRANTED) {  //检查是否有读写权限
                loadDataFromPath(rootPath, enterVar);//从路径中加载数据
            } else {
                requestPermissions(new String[]{NEED_PERMISSION}, REQUESCODE); // 申请权限
            }
        } else {
            loadDataFromPath(rootPath, enterVar);//系统版本小于6.0直接加载数据
        }
    }

    private void loadDataFromPath(final String mPath, final int type) {
        this.data.clear();    // data为 ListView 中要显示的数据
        this.fileOrDirList.clear();

        new Thread() {
            public void run() {
                super.run();

                data = orderByName(mPath, type == SingleFolder || type == MultiFolder);
                fileOrDirList = getBeans(data, type);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fileAdapter.setData(data, fileOrDirList);//将数据载入适配器当中
                    }
                });
            }
        }.start();
    }

    private ArrayList<FileOrDirBeans> getBeans(List<String> fileList, final int type) {
        ArrayList<FileOrDirBeans> resultList = new ArrayList<>();
        for(String fileItem : fileList) {
            File file = new File(fileItem);

            String isFile = String.valueOf(file.isFile());
            String isCheckVisible = (file.isFile() || type == SingleFolder || type == MultiFolder) ?  "true" : "false";;

            FileOrDirBeans bean = new FileOrDirBeans(isFile, file.getName(), isCheckVisible, "false", fileItem);
            resultList.add(bean);
        }
        return resultList;
    }

    private void initView() {
        listView4File = findViewById(R.id.rvFileView);
        noDataTip = findViewById(R.id.noDataTip);
    }

    private void initEvent() {

        fileAdapter.setEvent(new FileAdapter.Event() {
            @Override
            public void enterNextDir(String path) {
                rootPath = path;
                loadDataFromPath(rootPath, enterVar);
            }
        });

        findViewById(R.id.btChose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra(ResultData, fileAdapter.getSelectData());
                setResult(ResultCode, intent);
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESCODE && permissions[0].equals(NEED_PERMISSION)) {
            loadDataFromPath(rootPath, enterVar);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {//点击的是返回键
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {//按键的按下事件
                if (rootPath.trim().equals(Environment.getExternalStorageDirectory().getAbsolutePath())) {
                    return super.onKeyDown(keyCode, event);
                } else {
                    File file = new File(rootPath);
                    if (!file.exists()) {
                        return super.onKeyDown(keyCode, event);
                    }
                    rootPath = file.getParent();
                    loadDataFromPath(rootPath, enterVar);
                    return false;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    public static ArrayList<String> orderByName(String filePath, boolean excludeFile) {
        ArrayList<String> FileNameList = new ArrayList<String>();
        File file = new File(filePath);
        File[] files = file.listFiles();
        List fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (File itemFile : files) {
            if(itemFile.getName().startsWith(".")) {
                continue;
            } else {
                if(itemFile.isFile() && excludeFile) {
                    continue;
                }
                FileNameList.add(itemFile.getAbsolutePath());  //往集合中添加符合条件的数据
            }
        }
        return FileNameList;
    }
}