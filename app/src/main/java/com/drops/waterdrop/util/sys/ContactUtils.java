package com.drops.waterdrop.util.sys;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.drops.waterdrop.model.MyContactInfo;
import com.drops.waterdrop.util.NumberUtil;
import com.drops.waterdrop.util.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactUtils {

    public static ContactUtils get(){
        return gDefault.get();
    }

   /* private Cursor mCursor;

    *//**
     * 获取联系人数据
     *
     * @param context
     * @return
     *//*
    public  List<MyContactInfo> getAllContacts(Context context) {
        ArrayList<MyContactInfo> contacts = new ArrayList<>();
        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        ContentResolver reslover = context.getContentResolver();

        // 在这里我们给query传递进去一个SORT_KEY_PRIMARY。
        // 告诉ContentResolver获得的结果安装联系人名字的首字母有序排列。
        mCursor = reslover.query(uri, null, null, null,
                ContactsContract.Contacts.SORT_KEY_PRIMARY);
        Logger.d("手机通讯录Cursor：" + mCursor);

        if (mCursor != null) {
            while (mCursor.moveToNext()) {

                // 联系人ID
                String id = mCursor
                        .getString(mCursor
                                .getColumnIndex(android.provider.ContactsContract.Contacts._ID));

                // Sort Key，读取的联系人按照姓名从 A->Z 排序分组。
                String sort_key_primary = mCursor
                        .getString(mCursor
                                .getColumnIndex(android.provider.ContactsContract.Contacts.SORT_KEY_PRIMARY));

                // 获得联系人姓名
                String name = mCursor
                        .getString(mCursor
                                .getColumnIndex(android.provider.ContactsContract.Contacts.DISPLAY_NAME));

                MyContactInfo mContact = new MyContactInfo();
                mContact.id = id;
                mContact.name = name;
                mContact.sort_key_primary = sort_key_primary;

                // 获得联系人手机号码
                Cursor phone = reslover.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                                + id, null, null);
                // 取得电话号码(可能存在多个号码)
                // 因为同一个名字下，用户可能存有一个以上的号，
                // 遍历。
                ArrayList<String> phoneNumbers = new ArrayList<String>();

                if (phone != null) {
                    while (phone.moveToNext()) {
                        int phoneFieldColumnIndex = phone
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String phoneNumber = phone.getString(phoneFieldColumnIndex);
                        phoneNumbers.add(phoneNumber);
                    }
                }

                mContact.phoneNumbers = phoneNumbers;

                contacts.add(mContact);
            }
            mCursor.close();
            mCursor = null;
        }

        return contacts;
    }

    public void closeCursor(){
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }
    }*/

    //获取系统联系人，获取1000个联系人0.2秒，最快速
    public List<MyContactInfo> getPhoneContacts(Context context) {
        //联系人集合
        List<MyContactInfo> data = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        //搜索字段
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Contacts.DISPLAY_NAME};
        // 获取手机联系人
        Cursor contactsCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, null, null, null);
        if (contactsCursor != null) {
            //key: contactId,value: 该contactId在联系人集合data的index
            Map<Integer, Integer> contactIdMap = new HashMap<>();
            while (contactsCursor.moveToNext()) {
                //获取联系人的ID
                int contactId = contactsCursor.getInt(0);
                //获取联系人的姓名
                String name = contactsCursor.getString(2);
                //获取联系人的号码
                String phoneNumber = contactsCursor.getString(1);
                //号码处理
                String replace = phoneNumber.replace(" ", "").replace("-", "").replace("+", "");
                //判断号码是否符合手机号
                if (NumberUtil.Instance.checkPhoneNumber(replace)) {
                    //如果联系人Map已经包含该contactId
                    if (contactIdMap.containsKey(contactId)) {
                        //得到该contactId在data的index
                        Integer index = contactIdMap.get(contactId);
                        //重新设置号码数组
                        MyContactInfo contacts = data.get(index);
                        String[] mobile = contacts.getMobile();
                        String[] mobileCopy = new String[mobile.length + 1];
                        for (int i = 0; i < mobile.length; i++) {
                            mobileCopy[i] = mobile[i];
                        }
                        mobileCopy[mobileCopy.length - 1] = replace;
                        contacts.setMobile(mobileCopy);
                    } else {
                        //如果联系人Map不包含该contactId
                        MyContactInfo contacts = new MyContactInfo();
                        contacts.setContactId(contactId);
                        contacts.setName(name);
                        String[] strings = new String[1];
                        strings[0] = replace;
                        contacts.setMobile(strings);
                        data.add(contacts);
                        contactIdMap.put(contactId, data.size() - 1);
                    }
                }
            }
            contactsCursor.close();
        }

        return data;
    }

    private static final Singleton<ContactUtils> gDefault = new Singleton<ContactUtils>() {
        @Override
        protected ContactUtils create() {
            return new ContactUtils();
        }
    };

}  