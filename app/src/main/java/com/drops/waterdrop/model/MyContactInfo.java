package com.drops.waterdrop.model;

import java.io.Serializable;

/**
 * 手机通讯录实体类
 * Created by dengxiaolei on 2017/4/11.
 */

public class MyContactInfo implements Serializable {

    public String id;
    public String name;
    public String sort_key_primary;
//    public ArrayList<String> phoneNumbers;

    private int contactId;
    public String[] mobile;
    //获得一个联系人名字的首字符。
    //比如一个人的名字叫“安卓”，那么这个人联系人的首字符是：A。
    public  String  firstLetterOfName(){
        String s=sort_key_primary.charAt(0)+"";
        return  s.toUpperCase();
    }

/*
    public String getPhoneNumbers() {
        String phones = " ";
        for (int i = 0; i < phoneNumbers.size(); i++) {
            phones += "号码" + i + ":" + phoneNumbers.get(i);
        }

        return phones;
    }
*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSort_key_primary() {
        return sort_key_primary;
    }

    public void setSort_key_primary(String sort_key_primary) {
        this.sort_key_primary = sort_key_primary;
    }

    public String[] getMobile() {
        return mobile;
    }

    public void setMobile(String[] mobile) {
        this.mobile = mobile;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
