package com.netease.nim.uikit.recent.holder;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.common.ui.drop.DropFake;
import com.netease.nim.uikit.common.ui.drop.DropManager;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseQuickAdapter;
import com.netease.nim.uikit.common.ui.recyclerview.holder.BaseViewHolder;
import com.netease.nim.uikit.common.ui.recyclerview.holder.RecyclerViewHolder;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.common.util.sys.TimeUtil;
import com.netease.nim.uikit.recent.RecentContactsCallback;
import com.netease.nim.uikit.recent.RecentContactsFragment;
import com.netease.nim.uikit.recent.adapter.RecentContactAdapter;
import com.netease.nim.uikit.session.emoji.MoonUtil;
import com.netease.nim.uikit.uinfo.UserInfoHelper;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;

import java.util.Map;

public abstract class RecentViewHolder extends RecyclerViewHolder<BaseQuickAdapter, BaseViewHolder, RecentContact> {

    public RecentViewHolder(BaseQuickAdapter adapter) {
        super(adapter);
    }

    private int lastUnreadCount = 0;

    protected FrameLayout portraitPanel;

    protected HeadImageView imgHead;

    protected TextView tvNickname;

    protected TextView tvMessage;

    protected TextView tvDatetime;

    // 消息发送错误状态标记，目前没有逻辑处理
    protected ImageView imgMsgStatus;

    protected View bottomLine;

    protected View topLine;

    // 未读红点（一个占坑，一个全屏动画）
    protected DropFake tvUnread;

    private ImageView imgUnreadExplosion;

    // 子类覆写
    protected abstract String getContent(RecentContact recent);

    @Override
    public void convert(BaseViewHolder holder, RecentContact data, int position, boolean isScrolling) {
        inflate(holder, data);
        refresh(holder, data, position);
    }

    public void inflate(BaseViewHolder holder, final RecentContact recent) {
        this.portraitPanel = holder.getView(R.id.portrait_panel);
        this.imgHead = holder.getView(R.id.img_head);
        this.tvNickname = holder.getView(R.id.tv_nickname);
        this.tvMessage = holder.getView(R.id.tv_message);
        this.tvUnread = holder.getView(R.id.unread_number_tip);
        this.imgUnreadExplosion = holder.getView(R.id.unread_number_explosion);
        this.tvDatetime = holder.getView(R.id.tv_date_time);
        this.imgMsgStatus = holder.getView(R.id.img_msg_status);
        this.bottomLine = holder.getView(R.id.bottom_line);
        this.topLine = holder.getView(R.id.top_line);

        holder.addOnClickListener(R.id.unread_number_tip);

        this.tvUnread.setTouchListener(new DropFake.ITouchListener() {
            @Override
            public void onDown() {
                DropManager.getInstance().setCurrentId(recent);
                DropManager.getInstance().down(tvUnread, tvUnread.getText());
            }

            @Override
            public void onMove(float curX, float curY) {
                DropManager.getInstance().move(curX, curY);
            }

            @Override
            public void onUp() {
                DropManager.getInstance().up();
            }
        });
    }

    public void refresh(BaseViewHolder holder, RecentContact recent, final int position) {
        // unread count animation
        boolean shouldBoom = lastUnreadCount > 0 && recent.getUnreadCount() == 0; // 未读数从N->0执行爆裂动画;
        lastUnreadCount = recent.getUnreadCount();
        System.out.println("消息：1");
        if (recent.getSessionType() == SessionTypeEnum.System) {
            System.out.println("消息：2");

            Map<String, Object> map = recent.getExtension();
            System.out.println("消息：3");

            int msgType = (int) map.get(Constants.KEY_MESSAGE_TYPE);
            System.out.println("消息：4" + msgType);

            switch (msgType) {
                case Constants.MESSAGE_TYPE_YAN_ZHENG:
                    imgHead.setImageResource(R.drawable.icon_im_yzxx);
                    tvNickname.setText("验证申请");
                    break;
                case Constants.MESSAGE_TYPE_ORDER:
                    imgHead.setImageResource(R.drawable.icon_im_wlxx);
                    tvNickname.setText("交易物流信息");

                    break;
                case Constants.MESSAGE_TYPE_JIN_DOU:
                    imgHead.setImageResource(R.drawable.icon_im_jdzhbd);
                    tvNickname.setText("金豆账户变动");

                    break;
                case Constants.MESSAGE_TYPE_JING_PIN:
                    imgHead.setImageResource(R.drawable.icon_im_xtxx);
                    tvNickname.setText("精品推荐");

                    break;
                case Constants.MESSAGE_TYPE_BALANCE:
                    imgHead.setImageResource(R.drawable.icon_im_yezhbd);
                    tvNickname.setText("余额变动");

                    break;
                case Constants.MESSAGE_TYPE_RECOMMEND_FRIEND:
                    imgHead.setImageResource(R.drawable.icon_xxlb_hytj);
                    tvNickname.setText("好友推荐");
                    break;
            }

            String timeString = TimeUtil.getTimeShowString(recent.getTime(), true);
            tvDatetime.setText(timeString);
            tvMessage.setText(recent.getContent());
            updateBackground(holder, recent, position);

        } else {
            System.out.println("消息：5" );

            updateBackground(holder, recent, position);

         /*   Map<String, Object> data = recent.getExtension();
            if (data != null) {
                String avatar = (String) data.get("avatar");
                String nickName = (String) data.get("nickName");
                GlideUtil.showImageViewToCircle(holder.getContext(), R.drawable.icon_default_head_60dp, avatar, imgHead);
                updateNickLabel(TextUtils.isEmpty(nickName) ? recent.getContactId() : nickName);

            } else */
            {
                updateNickLabel(UserInfoHelper.getUserTitleName(recent.getContactId(), recent.getSessionType()));
                loadPortrait(holder, recent);
            }


            updateMsgLabel(holder, recent);

        }

        updateNewIndicator(recent);

        if (shouldBoom) {
            Object o = DropManager.getInstance().getCurrentId();
            if (o instanceof String && o.equals("0")) {
                imgUnreadExplosion.setImageResource(R.drawable.explosion);
                imgUnreadExplosion.setVisibility(View.VISIBLE);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        ((AnimationDrawable) imgUnreadExplosion.getDrawable()).start();
                        // 解决部分手机动画无法播放的问题（例如华为荣耀）
                        getAdapter().notifyItemChanged(getAdapter().getViewHolderPosition(position));
                    }
                });
            }
        } else {
            imgUnreadExplosion.setVisibility(View.GONE);
        }
    }

    private void updateBackground(BaseViewHolder holder, RecentContact recent, int position) {
        topLine.setVisibility(getAdapter().isFirstDataItem(position) ? View.GONE : View.VISIBLE);
        bottomLine.setVisibility(getAdapter().isLastDataItem(position) ? View.VISIBLE : View.GONE);
        if ((recent.getTag() & RecentContactsFragment.RECENT_TAG_STICKY) == 0) {
            holder.getConvertView().setBackgroundResource(R.drawable.touch_bg);
        } else {
            holder.getConvertView().setBackgroundResource(R.drawable.nim_recent_contact_sticky_selecter);
        }
    }

    protected void loadPortrait(BaseViewHolder holder, RecentContact recent) {
        // 设置头像
        if (recent.getSessionType() == SessionTypeEnum.P2P) {
//            imgHead.loadBuddyAvatar(recent.getContactId());
            UserInfoProvider.UserInfo userInfo = NimUIKit.getUserInfoProvider().getUserInfo(recent.getContactId());
            String avatar = userInfo == null ? "" : userInfo.getAvatar();
            Glide.with(holder.getContext()).load(avatar).error(R.drawable.img_qs_50x50).into(imgHead);


        } else if (recent.getSessionType() == SessionTypeEnum.Team) {
            Team team = TeamDataCache.getInstance().getTeamById(recent.getContactId());
//            imgHead.loadTeamIconByTeam(team);
            String teamHead = "";
            if (team != null) {
                teamHead = team.getIcon();
            }
                Glide.with(holder.getContext()).load(teamHead).error(R.drawable.img_qs_50x50).into(imgHead);
        }
    }

    private void updateNewIndicator(RecentContact recent) {
        int unreadNum = recent.getUnreadCount();
        tvUnread.setVisibility(unreadNum > 0 ? View.VISIBLE : View.GONE);
        tvUnread.setText(unreadCountShowRule(unreadNum));
    }

    private void updateMsgLabel(BaseViewHolder holder, RecentContact recent) {
        // 显示消息具体内容
        MoonUtil.identifyRecentVHFaceExpressionAndTags(holder.getContext(), tvMessage, getContent(recent), ImageSpan.ALIGN_BOTTOM, 0.45f);
        //tvMessage.setText(getContent());

        MsgStatusEnum status = recent.getMsgStatus();
        switch (status) {
            case fail:
                imgMsgStatus.setImageResource(R.drawable.nim_g_ic_failed_small);
                imgMsgStatus.setVisibility(View.VISIBLE);
                break;
            case sending:
                imgMsgStatus.setImageResource(R.drawable.nim_recent_contact_ic_sending);
                imgMsgStatus.setVisibility(View.VISIBLE);
                break;
            default:
                imgMsgStatus.setVisibility(View.GONE);
                break;
        }

        String timeString = TimeUtil.getTimeShowString(recent.getTime(), true);
        tvDatetime.setText(timeString);
    }

    protected void updateNickLabel(String nick) {
        int labelWidth = ScreenUtil.screenWidth;
        labelWidth -= ScreenUtil.dip2px(50 + 70); // 减去固定的头像和时间宽度

        if (labelWidth > 0) {
            tvNickname.setMaxWidth(labelWidth);
        }

        tvNickname.setText(nick);
    }

    protected String unreadCountShowRule(int unread) {
        unread = Math.min(unread, 99);
        return String.valueOf(unread);
    }

    protected RecentContactsCallback getCallback() {
        return ((RecentContactAdapter) getAdapter()).getCallback();
    }
}
