package com.drops.waterdrop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dengxiaolei on 2017/5/16.
 */

public class StarListBean implements Serializable {
    public String name;

    public String sign;//签名

    public int HeaderImgId;

    public boolean isSuperIp;//是否是超级ip

    public int likes;//关注人数

    public String account;//账号

    public boolean isLiked;//是否已经关注

    public List<PoolEntity> pools;//当前明星的水塘

    public List<StoreEntity> stores;//当前明星代言的店铺


    public enum PoolState {
        ADDED, REVIEWED, ADD
    }

    public StarListBean(String name, String account, String desc, int headerImgId, boolean isSuperIp, boolean isLiked, int likes, List<PoolEntity> pools, List<StoreEntity> stores) {
        this.sign = desc;
        this.name = name;
        this.HeaderImgId = headerImgId;
        this.isSuperIp = isSuperIp;
        this.isLiked = isLiked;
        this.likes = likes;
        this.account = account;
        this.pools = pools;
        this.stores = stores;
    }

    public static class PoolEntity implements Serializable {
        public int bgImgId;
        public String poolName;

        public String poolAccount;

        public int headImgId;

        public String poolSign;

        public int peopleNum;

        public boolean isSuperIp;

        public PoolState state = PoolState.ADD;//水塘的状态 是否已经加入


        public PoolEntity(int bgImgId, String poolName,String poolAccount,  int headImgId, String poolSign, int peopleNum, boolean isSuperIp, PoolState state) {
            this.bgImgId = bgImgId;
            this.poolName = poolName;
            this.poolAccount = poolAccount;
            this.headImgId = headImgId;
            this.poolSign = poolSign;
            this.peopleNum = peopleNum;
            this.isSuperIp = isSuperIp;
            this.state = state;
        }
    }

    public static class StoreEntity implements Serializable {
        public String storeName;

        public int storeImgId;

        public StoreEntity(String storeName, int storeImgId) {
            this.storeName = storeName;
            this.storeImgId = storeImgId;
        }
    }
}
