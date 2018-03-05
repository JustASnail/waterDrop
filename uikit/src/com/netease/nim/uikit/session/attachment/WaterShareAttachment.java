package com.netease.nim.uikit.session.attachment;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by dengxiaolei on 2017/5/18.
 * 个人名片
 */

public class WaterShareAttachment extends CustomAttachment {
    private String mShareId;//水塘、水宝、水贴的id
    private String mSharePhoto;//分享的图像
    private String mShareTitle;//分享的标题
    private String mShareContent;//水塘、水宝、水贴的内容
    private String mShareFrom;//分享的种类   水塘、水宝、水贴

    private long mPoolId;//水塘id 只有在分享水宝的时候才有值
    private long mTipId;//水贴id 只有在分享水宝的时候才有值

    private String mFromName;//如果是水贴就是来自哪个水塘， 如果是水宝就是来自哪个水贴
    private String mTipUrl;//分享水贴时需要传水贴的地址url

    public WaterShareAttachment() {
        super(CustomAttachmentType.WaterShare);
    }

    @Override
    protected void parseData(JSONObject data) {
        mShareId=data.getString("shareId");
        mSharePhoto=data.getString("sharePhoto");
        mShareTitle=data.getString("shareTitle");
        mShareContent=data.getString("shareContent");
        mShareFrom=data.getString("shareFrom");
        mFromName = data.getString("fromName");
        mTipUrl = data.getString("tipUrl");

        mPoolId=data.getLong("poolId");
        mTipId=data.getLong("tipId");

    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("shareId",mShareId);
        data.put("sharePhoto",mSharePhoto);
        data.put("shareTitle",mShareTitle);
        data.put("shareContent",mShareContent);
        data.put("shareFrom",mShareFrom);
        data.put("fromName",mFromName);
        data.put("tipUrl",mTipUrl);

        data.put("poolId",mPoolId);
        data.put("tipId",mTipId);
        return data;
    }

    public String getSharePhoto() {
        return mSharePhoto;
    }

    public void setSharePhoto(String sharePhoto) {
        mSharePhoto = sharePhoto;
    }

    public String getShareId() {
        return mShareId;
    }

    public void setShareId(String shareId) {
        mShareId = shareId;
    }

    public String getShareTitle() {
        return mShareTitle;
    }

    public void setShareTitle(String shareTitle) {
        mShareTitle = shareTitle;
    }

    public String getShareContent() {
        return mShareContent;
    }

    public void setShareContent(String shareContent) {
        mShareContent = shareContent;
    }

    public String getShareFrom() {
        return mShareFrom;
    }

    public void setShareFrom(String shareFrom) {
        mShareFrom = shareFrom;
    }

    public long getPoolId() {
        return mPoolId;
    }

    public void setPoolId(long poolId) {
        mPoolId = poolId;
    }

    public long getTipId() {
        return mTipId;
    }

    public void setTipId(long tipId) {
        mTipId = tipId;
    }

    public String getFromName() {
        return mFromName;
    }

    public void setFromName(String fromName) {
        mFromName = fromName;
    }

    public String getTipUrl() {
        return mTipUrl;
    }

    public void setTipUrl(String tipUrl) {
        mTipUrl = tipUrl;
    }
}
