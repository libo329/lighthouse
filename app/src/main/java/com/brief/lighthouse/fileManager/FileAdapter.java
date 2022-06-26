package com.brief.lighthouse.fileManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
//import com.rdsa.singleselectedfileorfolder.R;
import com.brief.lighthouse.R;

import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends BaseAdapter {
    private Context context;
    private Event event;
    private ArrayList<String> selectData;
    private ArrayList<FileOrDirBeans> fileOrDirList;
    private LayoutInflater mLayoutInflater;      //   将 Item 的 XML 转换成 View
    private final int enterVar;

    public FileAdapter(Context context, final ArrayList<FileOrDirBeans> fileOrDirList, int enterVar) {
        this.context = context;
        this.selectData = new ArrayList<>();
        this.mLayoutInflater = LayoutInflater.from(context);     // context 必须是界面所在的 context

        this.enterVar = enterVar;
        this.fileOrDirList = fileOrDirList;
    }

    @Override
    public int getCount() {
        return this.fileOrDirList.size();
    }

    @Override     // 数据项
    public Object getItem(int position) {
        return fileOrDirList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override    // 显示的内容
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.filechoose_layout_fileitme, null);
            viewHolder = new ViewHolder();
            viewHolder.icon = convertView.findViewById(R.id.icon);
            viewHolder.fileName = convertView.findViewById(R.id.fileName);
            viewHolder.checkBox = convertView.findViewById(R.id.checkBox);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final FileOrDirBeans bean = fileOrDirList.get(position);
        final String absPath = bean.getAbsolutePath();

        bean.setIsChecked("false");    //   初始化一个boolean值 来判断是否被选中
        for (String s : selectData) {    // 遍历选中的集合
            if (s.trim().equals(absPath)) {    // 如果集合中的子元素与适配其中的路径相同
                bean.setIsChecked("true");     // 判断已被选中
                break; //终止循环
            }
        }

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {//设置checkBox的点击事件
                if (isChecked) {//选中状态
                    if (selectData.contains(absPath)) {//如果集合中包含了该元素则直接返回
                        if (enterVar == ChooseFileOrDirActivity.SingleFile || enterVar == ChooseFileOrDirActivity.SingleFolder) {
                            selectData.clear();
                            selectData.add(absPath);
                        }
                    } else {//否则添加
                        if(enterVar == ChooseFileOrDirActivity.SingleFile || enterVar == ChooseFileOrDirActivity.SingleFolder) {
                            selectData.clear();
                        }
                        selectData.add(absPath);
                    }
                } else {//未选中状态
                    if (selectData.contains(absPath)) {//如果集合中包含了该元素则移除
                        selectData.remove(absPath);
                    } else {//否则 返回
                        return;
                    }
                }

                notifyDataSetChanged();
            }
        });


        viewHolder.icon.setImageDrawable(Boolean.parseBoolean(bean.getIsFile()) ?  context.getResources().getDrawable(R.drawable._16_file) : context.getResources().getDrawable(R.drawable.file));
        viewHolder.fileName.setText(bean.getName());
        viewHolder.checkBox.setVisibility((Boolean.parseBoolean(bean.isCheckVisible))?  View.VISIBLE : View.GONE);
        viewHolder.checkBox.setChecked(Boolean.parseBoolean(bean.getIsChecked()));

        viewHolder.fileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Boolean.parseBoolean(bean.getIsFile())) {
                    event.enterNextDir(fileOrDirList.get(position).getAbsolutePath());
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        private ImageView icon;
        private TextView fileName;
        private CheckBox checkBox;
    }

    public interface Event {
        void enterNextDir(String path);//进入下一个文件夹
    }

    public void setData(List<String> data, ArrayList<FileOrDirBeans> fileOrDirList) {
        this.fileOrDirList.clear();
        this.fileOrDirList.addAll(fileOrDirList);

        notifyDataSetChanged();
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ArrayList<String> getSelectData() {
        return selectData;
    }

}