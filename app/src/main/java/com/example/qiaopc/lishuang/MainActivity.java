package com.example.qiaopc.lishuang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_name;
    private Button btn_insert;
    private Button btn_delete;
    private Button btn_query;
    private Button btn_update;
    private ListView lv_data;
    private String mname;
    private int mphone;
    private ArrayList<Map> mdataList;
    Map<String, Integer> person;
    private  MyAdapter mAdapter;
    private EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mnameList = new ArrayList<>();

        initView();



        btn_insert.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        btn_update.setOnClickListener(this);
    }

    private void initView() {
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        btn_insert = findViewById(R.id.btn_insert);
        btn_delete = findViewById(R.id.btn_delete);
        btn_query = findViewById(R.id.btn_query);
        btn_update = findViewById(R.id.btn_update);
        lv_data = findViewById(R.id.lv_data);
        mAdapter = new MyAdapter();
        lv_data.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_delete:
                mname = et_name.getText().toString().trim();
                if (null != mname) {
                    mnameList.remove(mname);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.btn_query:
                break;
            case R.id.btn_update:
                break;
        }
    }

    private void insert() {
        mname = et_name.getText().toString().trim();
        mphone = et_phone.getText().toString().trim();
        if (null != mname && null != mphone) {
            person.put(et_name, et_phone);
            mdataList.add(person);

            mAdapter.notifyDataSetChanged();
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mnameList.size();
        }

        @Override
        public Object getItem(int position) {
            return mnameList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.listview_name_item, null);
                holder = new ViewHolder();
                holder.tv_name = convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder =(ViewHolder) convertView.getTag();
            }

            holder.tv_name.setText(mnameList.get(position));
            holder.tv_phone.setText();
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_phone;
    }

}
