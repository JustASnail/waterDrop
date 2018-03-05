package com.drops.waterdrop.ui.session.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.session.view.IFansGroupView;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nimlib.sdk.team.model.Team;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/7/11.
 */

public class FansGroupPresenter extends BasePresenter<IFansGroupView> {
    public FansGroupPresenter(BaseActivity context) {
        super(context);
    }

    public void getGroup() {
        List<Team> teamList = TeamDataCache.getInstance().getAllAdvancedTeams();
        getView().onLoadGroupSucceed(teamList);

    }
}
