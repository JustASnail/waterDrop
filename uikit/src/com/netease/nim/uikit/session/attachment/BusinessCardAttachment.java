package com.netease.nim.uikit.session.attachment;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by dengxiaolei on 2017/5/18.
 * 个人名片
 */

public class BusinessCardAttachment extends CustomAttachment {

/*    public static final String CARD_TYPE_PERSONAL = "个人名片";
    public static final String CARD_TYPE_POOL = "水塘名片";
    public static final String CARD_TYPE_GOODS = "水宝名片";
    public static final String CARD_TYPE_TIP = "水贴名片";*/

    private String mCardName;//名片的用户名称
    private long mCardUid;//名片的用户uid
    private String mSubTitle;//名片附加标题
    private int mSex;//性别  1是男 2是女
    private String mCardAvatar;//名片的头像

    public BusinessCardAttachment() {
        super(CustomAttachmentType.BusinessCard);
    }

    @Override
    protected void parseData(JSONObject data) {
        mCardName=data.getString("cardName");
        mCardUid=data.getLong("cardUid");
        mSubTitle=data.getString("subTitle");
        mSex=data.getIntValue("sex");
        mCardAvatar=data.getString("cardAvatar");

    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("cardName",mCardName);
        data.put("cardUid",mCardUid);
        data.put("subTitle",mSubTitle);
        data.put("sex",mSex);
        data.put("cardAvatar",mCardAvatar);

        return data;
    }

    public void setCardName(String name) {
        this.mCardName = name;
    }

    public String getCardName() {
        return mCardName;
    }

    public long getCardUid() {
        return mCardUid;
    }

    public void setCardUid(long cardUid) {
        mCardUid = cardUid;
    }

    public String getSubTitle() {
        return mSubTitle;
    }

    public void setSubTitle(String subTitle) {
        mSubTitle = subTitle;
    }

    public int getSex() {
        return mSex;
    }

    public void setSex(int sex) {
        mSex = sex;
    }

    public void setCardAvatar(String avatar) {
        this.mCardAvatar = avatar;
    }

    public String getCardAvatar() {
        return mCardAvatar;
    }
}
