package com.netease.nim.uikit.session.helper;

import android.app.Activity;
import android.view.View;

import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.dialog.EditDialog;
import com.netease.nim.uikit.model.ShareCardModel;
import com.netease.nim.uikit.session.attachment.BusinessCardAttachment;
import com.netease.nim.uikit.session.attachment.WaterShareAttachment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dengxiaolei on 2017/7/22.
 */

public class CardShareHelper {

    /**
     * 分享卡片
     * @param activity
     * @param shareCardModel
     */
    public static void showMyDialog(final Activity activity, final ShareCardModel shareCardModel, final OnShareSucceedListener listener) {


        String cardName = "";
        switch (shareCardModel.getShareType()) {
            case Constants.SHARE_TYPE_USER:
                    cardName = "[个人名片] " + shareCardModel.getCardUserNickName();
                break;
            case Constants.SHARE_TYPE_GOODS:
                cardName = "[水宝] " + shareCardModel.getShareTitle();
                break;
            case Constants.SHARE_TYPE_POST:
                cardName = "[水帖] " + shareCardModel.getShareTitle();
                break;
            case Constants.SHARE_TYPE_POOL:
                cardName = "[水塘] " + shareCardModel.getShareTitle();
                break;
        }


        final EditDialog editDialog = new EditDialog(activity, cardName, shareCardModel.getReceiveUserName(), shareCardModel.getReceiveUserPhoto());

        editDialog.setTitle("发送给：");


        editDialog.setLeftText("暂不");
        editDialog.setRightText("分享");


        editDialog.setCanceledOnTouchOutside(false);


        editDialog.addLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
            }
        });
        editDialog.addRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                switch (shareCardModel.getShareType()) {
                    case Constants.SHARE_TYPE_USER:
                        sendBusinessCard(activity, shareCardModel.getCardUserUid(), shareCardModel.getCardUserNickName(), shareCardModel.getCardUserPhoto(), shareCardModel.getCardUserSex(), shareCardModel.getReceiveAccount(), shareCardModel.getSessionTypeEnum(), listener);

                        break;
                    case Constants.SHARE_TYPE_GOODS:
                    case Constants.SHARE_TYPE_POST:
                    case Constants.SHARE_TYPE_POOL:
                        sendShareCard(activity,shareCardModel.getShareType(), shareCardModel.getShareTitle(), shareCardModel.getSharePhoto(),
                                shareCardModel.getShareContent(), shareCardModel.getShareId(), shareCardModel.getShareFrom(),
                                shareCardModel.getFromName(), shareCardModel.getTipUrl(), shareCardModel.getPoolId(), shareCardModel.getTipId(), shareCardModel.getReceiveAccount(), shareCardModel.getSessionTypeEnum(), listener);
                        break;
                }

                editDialog.dismiss();

            }
        });
        editDialog.show();


    }


    /**
     * 发送个人名片
     * @param activity
     * @param cardUid   名片拥有者的uid
     * @param name      名片的个人名字
     * @param avatar    名片的个人头像
     * @param sex       名片的个人性别
     * @param account   接收名片者的账号（im）
     * @param sessionType   发送消息的type（单聊、群聊）
     */
    public static void sendBusinessCard(Activity activity, long cardUid, String name, String avatar, int sex, String account, SessionTypeEnum sessionType, OnShareSucceedListener listener) {

        BusinessCardAttachment attachment = new BusinessCardAttachment();
        attachment.setSubTitle(String.valueOf(cardUid));
        attachment.setCardUid(cardUid);
        attachment.setCardName(name);
        attachment.setSex(sex);
        attachment.setCardAvatar(avatar);

        IMMessage message = MessageBuilder.createCustomMessage(account, sessionType, attachment);
        Map<String, Object> data = new HashMap<>();
        data.put(Constants.KEY_AVATAR, MyUserCache.getUserPhoto());
        data.put(Constants.KEY_NICK_NAME, MyUserCache.getUserNickName());
        data.put(Constants.KEY_UID, MyUserCache.getUserUid());
        message.setRemoteExtension(data); // 设置服务器扩展字段

        CustomMessageConfig config = new CustomMessageConfig();
        config.enableUnreadCount = true; // 该消息不计入未读数
        config.enableHistory = true;//该消息是否允许在消息历史中拉取，如果为 false，通过 MsgService#pullMessageHistory 拉取的结果将不包含该条消息。默认为 true 。
        config.enableRoaming = true;//该消息是否需要漫游。
        config.enableSelfSync = true;//多端同时登录时，发送一条消息后，是否要同步到其他同时登录的客户端。默认为 true 。
        config.enableUnreadCount = true;//该消息是否要计入未读数，如果为 true ，那么对方收到消息后，最近联系人列表中未读数加1。默认为 true 。
        config.enablePush = true;//该消息是否进行推送（消息提醒）

        message.setConfig(config);
        NIMClient.getService(MsgService.class).sendMessage(message, false);

        listener.onShareSucceed();
    }


    /**
     * 发送水宝、 水帖、 水塘的分享卡片
     * @param activity
     * @param shareType     分享的种类
     * @param shareTitle    分享的标题
     * @param sharePhoto    分享的图片
     * @param shareContent  分享显示的内容
     * @param shareId       分享的id（分享水宝就是水宝id， 分享水帖就是水帖id， 分享水塘就是水塘id）
     * @param shareFrom     分享的东西（水宝、 水帖、 水塘）
     * @param fromName      分享的东西来自哪里（分享水宝就是来自的水帖名字， 分享水帖就是来自的水塘的名字， 分享水塘就是空）
     * @param tipUrl        分享水帖时附加的水帖ur
     * @param poolId        分享水宝时附加的该水宝来自的水塘id
     * @param tipId         分享水宝时附加的该水宝来自的水帖id
     * @param account       接收卡片的账号（im）
     * @param sessionType   发送卡片的消息类型（单聊、 群聊）
     */
    public static void sendShareCard(Activity activity,  int shareType, String shareTitle, String sharePhoto,
                               String shareContent, String shareId, String shareFrom, String fromName, String tipUrl,
                                     long poolId, long tipId , String account, SessionTypeEnum sessionType,
                                     OnShareSucceedListener listener) {
        Logger.d("分享：tipUrl:" +tipUrl + "----poolId"  + poolId + "---tipId:" + tipId);
        WaterShareAttachment attachment = new WaterShareAttachment();
        attachment.setShareTitle(shareTitle);
        attachment.setSharePhoto(sharePhoto);
        attachment.setShareContent(shareContent);
        attachment.setShareId(shareId);
        attachment.setShareFrom(shareFrom);
        if (shareType == Constants.SHARE_TYPE_GOODS) {
            attachment.setPoolId(poolId);
            attachment.setTipId(tipId);
            attachment.setFromName(fromName);
        } else if (shareType == Constants.SHARE_TYPE_POST) {
            attachment.setTipUrl(tipUrl);
            attachment.setFromName(fromName);
        }

        IMMessage message = MessageBuilder.createCustomMessage(account, sessionType, attachment);
        Map<String, Object> data = new HashMap<>();
        data.put(Constants.KEY_AVATAR, MyUserCache.getUserPhoto());
        data.put(Constants.KEY_NICK_NAME, MyUserCache.getUserNickName());
        data.put(Constants.KEY_UID, MyUserCache.getUserUid());
        message.setRemoteExtension(data); // 设置服务器扩展字段

        CustomMessageConfig config = new CustomMessageConfig();
        config.enableUnreadCount = true; // 该消息不计入未读数
        config.enableHistory = true;//该消息是否允许在消息历史中拉取，如果为 false，通过 MsgService#pullMessageHistory 拉取的结果将不包含该条消息。默认为 true 。
        config.enableRoaming = true;//该消息是否需要漫游。
        config.enableSelfSync = true;//多端同时登录时，发送一条消息后，是否要同步到其他同时登录的客户端。默认为 true 。
        config.enableUnreadCount = true;//该消息是否要计入未读数，如果为 true ，那么对方收到消息后，最近联系人列表中未读数加1。默认为 true 。
        config.enablePush = true;//该消息是否进行推送（消息提醒）

        message.setConfig(config);
        NIMClient.getService(MsgService.class).sendMessage(message, false);

        listener.onShareSucceed();
    }

    public interface OnShareSucceedListener{
        void onShareSucceed();
    }



}
