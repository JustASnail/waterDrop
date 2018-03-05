package com.drops.waterdrop.ui.session.presenter;

import android.text.TextUtils;
import android.view.View;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyContactInfo;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.session.view.IAddFriendView;
import com.drops.waterdrop.util.sys.ContactUtils;
import com.google.gson.Gson;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.guideview.Component;
import com.netease.nim.uikit.guideview.Guide;
import com.netease.nim.uikit.guideview.GuideBuilder;
import com.netease.nim.uikit.guideview.component.AddFriendComponent;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.model.AddressBookFriendsEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.LocalContactEntity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by dengxiaolei on 2017/6/13.
 */

public class AddFriendPresenter extends BasePresenter<IAddFriendView> {

    private List<MyContactInfo> mContacts;

    private ArrayList<AddressBookFriendsEntity.ResultsBean> mResultList = new ArrayList<>();
    private List<List<String>> mLists;
    private ContactUtils mContactUtils;


    public AddFriendPresenter(BaseActivity context) {
        super(context);
    }

    public void setContactsView() {
        if (getView() != null) {
            getView().showLoading();
        }
        Logger.d("通讯录：getView().showLoading()");

        if (mContactUtils == null) {
            mContactUtils = new ContactUtils();
        }

        mContacts = mContactUtils.getPhoneContacts(mContext);

        String  size = mContacts == null ? "空" : mContacts.size() + "";
        Logger.d("通讯录数量：" + size);
        if (mContacts == null || mContacts.size() <= 0) {
            if (DialogMaker.isShowing()) {
                DialogMaker.dismissProgressDialog();
            }
            getView().setNoContacts();

            return;
        }

        final ArrayList<String> phoneList = new ArrayList<>();
        for (MyContactInfo contact : mContacts) {
//            ArrayList<String> phoneNumbers = contact.phoneNumbers;
            String[] phoneNumbers = contact.getMobile();
            for (String phoneNumber : phoneNumbers) {
                String moblie = StringUtil.removeBlanks(phoneNumber);
                if (moblie.length() >= 11) {
                    if (moblie.length() != 11) {
                        String substring = moblie.substring(moblie.length() - 11);
                        phoneList.add(substring);
                    } else {
                        phoneList.add(moblie);
                    }
                }
            }
        }
//
        partialQuery(phoneList);


    }


    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分
     *
     * @param list
     * @param len
     * @return
     */
    public static List<List<String>> splitList(List<String> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            if (DialogMaker.isShowing()) {
                DialogMaker.dismissProgressDialog();
            }
            return null;
        }

        List<List<String>> result = new ArrayList<>();


        int size = list.size();
        int count = (size + len - 1) / len;


        for (int i = 0; i < count; i++) {
            List<String> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }


    public void partialQuery(ArrayList<String> phoneList) {
        Logger.d("手机通讯录总数：" + phoneList.size());

        mLists = splitList(phoneList, 200);


        if (mLists == null || mLists.size() < 1) {
            getView().dissMissLoading();
            return;
        }

        Gson gson = new Gson();
        mTotalPage = (int) Math.ceil((double) phoneList.size() / (double) 200);

        getFriendInfo(gson.toJson(mLists.get(0)));
    }

    private int mLoadPage = 0;
    private int mTotalPage = 0;

    private void getFriendInfo(String moblieJson) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobiles", moblieJson);
        Observable<BaseResponse<AddressBookFriendsEntity>> observable = HttpUtil.getInstance().sApi.getAddressBookFriends(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new Subscriber<AddressBookFriendsEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (mLoadPage > 0 && mResultList.size() > 0) {
                    initContactData(mResultList, true);
                }
            }

            @Override
            public void onNext(AddressBookFriendsEntity addressBookFriendsEntity) {
                List<AddressBookFriendsEntity.ResultsBean> results = addressBookFriendsEntity.getResults();
                if (results != null && results.size() > 0) {
                    mLoadPage++;
                    mResultList.addAll(results);

                    if (mLoadPage == mTotalPage) {
                        initContactData(mResultList, false);
                    } else {
                        if (mLists != null) {
                            List<String> strings = mLists.get(mLoadPage);
                            Gson gson = new Gson();
                            getFriendInfo(gson.toJson(strings));
                        }
                    }

                }
            }
        });
    }

    private void initContactData(List<AddressBookFriendsEntity.ResultsBean> results, boolean isError) {

        ArrayList<LocalContactEntity> localContactEntities = new ArrayList<>();
        for (AddressBookFriendsEntity.ResultsBean friend : results) {
            if (friend.getUid() != MyUserCache.getUserUid()) {
                LocalContactEntity entity = new LocalContactEntity();
                entity.setMobile(friend.getMobile());
                entity.setNickName(friend.getNickName());
                entity.setPhoto(friend.getPhoto());
                entity.setRelationStatus(friend.getRelationStatus());
                entity.setUid(friend.getUid());
                entity.setRegisterStatus(friend.getRegisterStatus());
                localContactEntities.add(entity);
            }
        }


        for (MyContactInfo contact : mContacts) {
//            ArrayList<String> phoneNumbers = contact.phoneNumbers;
            String[] phoneNumbers = contact.getMobile();
            for (String phoneNumber : phoneNumbers) {
                String moblie = StringUtil.removeBlanks(phoneNumber);
                if (moblie.length() >= 11) {
                    if (moblie.length() != 11) {
                        moblie = moblie.substring(moblie.length() - 11);
                    }
                }

                for (LocalContactEntity localContactEntity : localContactEntities) {
                    if (TextUtils.equals(localContactEntity.getMobile(), moblie)) {
                        localContactEntity.setName(contact.name);
                    }
                }
            }
        }

        if (getView() != null) {
            getView().setContactsList(localContactEntities);

        }
        if (!isError) {
            InsertDB(localContactEntities);
        }
    }

    public void initContactView() {
        List<LocalContactEntity> mobileContact = MyUserCache.getMobileContact();

        if (mobileContact != null && mobileContact.size() > 0) {
            List<LocalContactEntity> localContactEntities = new ArrayList<>();
            for (LocalContactEntity localContactEntity : mobileContact) {
                if (!TextUtils.equals(MyUserCache.getUserMobile(), localContactEntity.getMobile())) {
                    localContactEntities.add(localContactEntity);
                }
            }
            if (getView() != null) {
                getView().setContactsList(localContactEntities);
            }
            Logger.d("展示手机通讯录：" + mobileContact.size());
        }
    }


    private void InsertDB(List<LocalContactEntity> results) {
        MyUserCache.saveMobileContact(results);
        List<LocalContactEntity> mobileContact = MyUserCache.getMobileContact();
        Logger.d("数据库插入手机通讯录：" + mobileContact.size());

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

/*
    public void closeCursor() {
        if (mContactUtils != null) {
            mContactUtils.closeCursor();
        }
    }
*/

}
