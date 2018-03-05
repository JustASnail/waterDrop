package com.netease.nim.uikit.recent.holder;

import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseQuickAdapter;
import com.netease.nimlib.sdk.msg.model.RecentContact;

public class SystemRecentViewHolder extends RecentViewHolder {

    public SystemRecentViewHolder(BaseQuickAdapter adapter) {
        super(adapter);
    }

    @Override
    protected String getContent(RecentContact recent) {
        return descOfMsg(recent);
    }

    protected String descOfMsg(RecentContact recent) {

        return recent.getContent();
    }
}
