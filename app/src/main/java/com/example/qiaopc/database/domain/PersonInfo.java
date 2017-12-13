package com.example.qiaopc.database.domain;

/**
 * Created by qiaopc on 2017/12/12 0012.
 */

public class PersonInfo {

    public String name;
    public String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "PersonInfo [name=" + name + ",phone=" + phone + "]";
    }
}
