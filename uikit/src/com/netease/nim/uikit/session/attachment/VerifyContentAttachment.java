package com.netease.nim.uikit.session.attachment;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by dengxiaolei on 2017/5/18.
 * 添加好友身份验证的招呼
 */

public class VerifyContentAttachment extends CustomAttachment {

    private String verify;

    public VerifyContentAttachment() {
        super(CustomAttachmentType.Verify);
    }

    @Override
    protected void parseData(JSONObject data) {
        verify=data.getString("verify");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("verify",verify);
        return data;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getVerify() {
        return verify;
    }
}
