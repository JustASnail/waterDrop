package com.drops.waterdrop.ui.session.presenter;

import android.support.annotation.IdRes;
import android.view.View;

import com.drops.waterdrop.R;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.session.view.IAddFriendV2View;
import com.drops.waterdrop.util.contact.ContactDataManager;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.guideview.Component;
import com.netease.nim.uikit.guideview.Guide;
import com.netease.nim.uikit.guideview.GuideBuilder;
import com.netease.nim.uikit.guideview.component.AddFriendComponent;

/**
 * CREATE BY DAOHEN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/09/17 06:52
 */

public class AddFriendV2Presenter extends BasePresenter<IAddFriendV2View> {

    public AddFriendV2Presenter(BaseActivity context) {
        super(context);
    }


    public void loadData(boolean isRegistered){
        getView().setSelected(isRegistered);

        if (!getView().hasPermission()){
            getView().showNoPermission();
            return;
        }

        getView().setData(isRegistered ? ContactDataManager.get().getRegisteredContancts() : ContactDataManager.get().getUnRegisteredContancts());
    }

    public void checkGuide(final View view, @IdRes final int rid){
        if (!MyUserCache.getGuide()) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    showGuideView(view, rid);
                }
            });
        }
    }

    public void showGuideView(View tagView, int containerId) {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(tagView)
                .setFullingViewId(containerId)
                .setAlpha(200)
                .setHighTargetCorner(50)
                .setAutoDismiss(false)
                .setHighTargetPadding(0)
                .setHighTargetGraphStyle(Component.ROUNDRECT)
                .setEnterAnimationId(R.anim.fade_entry)
                .setExitAnimationId(R.anim.hold)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
                MyUserCache.saveGuide(true);
            }
        });
        AddFriendComponent component = new AddFriendComponent();
        builder.addComponent(component);
        final Guide guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(false);
        guide.show(mContext);

        component.setListener(new AddFriendComponent.OnViewClickListener() {
            @Override
            public void onClickListener() {

                guide.dismiss();

            }
        });
    }
}
