package com.drops.waterdrop.util.contact;

import com.drops.waterdrop.util.Singleton;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.model.LocalContactEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/17 09:44
 */

public class ContactDataManager {

    public static ContactDataManager get(){
        return gDefault.get();
    }

    private List<LocalContactEntity> beans;
    private List<LocalContactEntity> registeredBeans;
    private List<LocalContactEntity> unRegisteredBeans;

    public List<LocalContactEntity> getAllContancts(){
        return MyUserCache.getContactCacheData();
    }

    public List<LocalContactEntity> getRegisteredContancts(){
        if (registeredBeans == null){
            fetchData();
        }

        return registeredBeans;
    }

    public List<LocalContactEntity> getUnRegisteredContancts(){
        if (unRegisteredBeans == null){
            fetchData();
        }

        return unRegisteredBeans;
    }

    public void reload(){
        if (registeredBeans != null)
            registeredBeans.clear();
        if (unRegisteredBeans != null)
            unRegisteredBeans.clear();

        fetchData();
    }

    private void fetchData(){
        beans = getAllContancts();
        if (registeredBeans == null)
            registeredBeans = new ArrayList<>();
        if (unRegisteredBeans == null)
            unRegisteredBeans = new ArrayList<>();

        if (beans == null)
            return;

        for (LocalContactEntity bean : beans){
            if (bean.isRegisteredStatus()){
                registeredBeans.add(bean);
            } else {
                unRegisteredBeans.add(bean);
            }
        }
    }

    private static final Singleton<ContactDataManager> gDefault = new Singleton<ContactDataManager>() {
        @Override
        protected ContactDataManager create() {
            return new ContactDataManager();
        }
    };
}
