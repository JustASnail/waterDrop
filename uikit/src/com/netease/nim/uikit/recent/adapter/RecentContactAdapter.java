package com.netease.nim.uikit.recent.adapter;

import android.support.v7.widget.RecyclerView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemQuickAdapter;
import com.netease.nim.uikit.common.ui.recyclerview.holder.BaseViewHolder;
import com.netease.nim.uikit.recent.RecentContactsCallback;
import com.netease.nim.uikit.recent.holder.CommonRecentViewHolder;
import com.netease.nim.uikit.recent.holder.SystemRecentViewHolder;
import com.netease.nim.uikit.recent.holder.TeamRecentViewHolder;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

import static com.netease.nim.uikit.recent.adapter.RecentContactAdapter.ViewType.VIEW_TYPE_COMMON;
import static com.netease.nim.uikit.recent.adapter.RecentContactAdapter.ViewType.VIEW_TYPE_SYSTEM;

/**
 * 最近联系人的列表种类
 * Created by huangjun on 2016/12/11.
 */

public class RecentContactAdapter extends BaseMultiItemQuickAdapter<RecentContact, BaseViewHolder> {

    interface ViewType {
        int VIEW_TYPE_COMMON = 1;
        int VIEW_TYPE_TEAM = 2;
        int VIEW_TYPE_SYSTEM = 3;
    }

    private RecentContactsCallback callback;

    public RecentContactAdapter(RecyclerView recyclerView, List<RecentContact> data) {
        super(recyclerView, data);
        addItemType(ViewType.VIEW_TYPE_COMMON, R.layout.nim_recent_contact_list_item, CommonRecentViewHolder.class);
        addItemType(ViewType.VIEW_TYPE_TEAM, R.layout.nim_recent_contact_list_item, TeamRecentViewHolder.class);
        addItemType(VIEW_TYPE_SYSTEM, R.layout.nim_recent_contact_list_system_item, SystemRecentViewHolder.class);
    }

    @Override
    protected int getViewType(RecentContact item) {
        if (item.getSessionType() == SessionTypeEnum.Team) {
            return ViewType.VIEW_TYPE_TEAM;
        } else if (item.getSessionType() == SessionTypeEnum.System) {
            return VIEW_TYPE_SYSTEM;
        } else {
            return VIEW_TYPE_COMMON;
        }

    }

    @Override
    protected String getItemKey(RecentContact item) {
        StringBuilder sb = new StringBuilder();
        sb.append(item.getSessionType().getValue()).append("_").append(item.getContactId());

        return sb.toString();
    }

    public RecentContactsCallback getCallback() {
        return callback;
    }

    public void setCallback(RecentContactsCallback callback) {
        this.callback = callback;
    }
}
