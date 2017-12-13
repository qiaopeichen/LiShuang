package com.example.qiaopc.lishuang;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiaopc.database.dao.PersonDao;
import com.example.qiaopc.database.domain.PersonInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private Button btn_insert;

    private ListView lv_data;
    private String mname;
    private String mphone;
    private List<PersonInfo> mPersonList;
    private MyAdapter mAdapter;
    private EditText et_phone;

    private PersonDao mDao;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mAdapter == null) {
                mAdapter = new MyAdapter();
                lv_data.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);



        initView();
        initData();

        btn_insert.setOnClickListener(this);

    }

    private void initData() {
        new Thread() {
            public void run() {
                mDao = PersonDao.getInstance(getApplicationContext());
                mPersonList = mDao.findAll();
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    private void initView() {
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        btn_insert = findViewById(R.id.btn_insert);
        lv_data = findViewById(R.id.lv_data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                mname = et_name.getText().toString().trim();
                mphone = et_phone.getText().toString().trim();
                if (!TextUtils.isEmpty(mname) && !TextUtils.isEmpty(mphone)) {
                    mDao.insert(mname, mphone);
                    PersonInfo personInfo = new PersonInfo();
                    personInfo.name = mname;
                    personInfo.phone = mphone;
                    mPersonList.add(0, personInfo);
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "请输入姓名或电话号码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mPersonList.size();
        }

        @Override
        public Object getItem(int position) {
            return mPersonList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.listview_name_item, null);
                holder = new ViewHolder();
                holder.tv_name = convertView.findViewById(R.id.tv_name);
                holder.tv_phone = convertView.findViewById(R.id.tv_phone);
                holder.btn_delete = convertView.findViewById(R.id.btn_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDao.delete(mPersonList.get(position).name);
                    mPersonList.remove(position);
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                }
            });

            holder.tv_name.setText(mPersonList.get(position).name);
            holder.tv_phone.setText(mPersonList.get(position).phone);

            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_phone;
        Button btn_delete;
    }
}
