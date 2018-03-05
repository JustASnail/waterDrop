package com.netease.nim.uikit.team.adapter;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;

/**
 * Created by dengxiaolei on 2017/7/17.
 */

public class MyTeamMemberAdapter extends BaseQuickAdapter<TeamMember, BaseViewHolder> {
    public MyTeamMemberAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_team_member_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamMember item) {
//        helper.setText(R.id.tv_name, item.getTeamNick());

        TextView tvName = helper.getView(R.id.tv_name);
       HeadImageView ivHead = helper.getView(R.id.iv_head);
//        ivHead.loadBuddyAvatar(item.getAccount());
        final UserInfoProvider.UserInfo userInfo = NimUIKit.getUserInfoProvider().getUserInfo(item.getAccount());
        GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_33x33, userInfo.getAvatar(), ivHead);
        tvName.setText(TeamDataCache.getInstance().getTeamMemberDisplayName(item.getTid(), item.getAccount()));

    }
}
