package com.netease.nim.uikit.contact;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.LoginSyncDataStatusObserver;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.UIKitLogTag;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.common.ui.liv.LivIndex;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.contact.core.item.AbsContactItem;
import com.netease.nim.uikit.contact.core.item.ContactItem;
import com.netease.nim.uikit.contact.core.item.ItemTypes;
import com.netease.nim.uikit.contact.core.model.ContactDataAdapter;
import com.netease.nim.uikit.contact.core.model.ContactGroupStrategy;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.MyFriendEntity;
import com.netease.nim.uikit.model.MyFriendsListEntity;
import com.netease.nim.uikit.uinfo.UserInfoHelper;
import com.netease.nim.uikit.uinfo.UserInfoObservable;
import com.netease.nimlib.sdk.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.yokeyword.indexablerv.IndexableHeaderAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import rx.Observable;


/**
 * 通讯录Fragment
 * <p/>
 * Created by huangjun on 2015/9/7.
 */
public class ContactsFragment extends TFragment {

    private ContactDataAdapter adapter;

    private ListView listView;

    private TextView countText;

    private LivIndex litterIdx;

    private View loadingFrame;

    private ContactsCustomization customization;

    private ReloadFrequencyControl reloadControl = new ReloadFrequencyControl();
    private ContactAdapter mAdapter;

    private List<MyFriendEntity> mFriends;
    private IndexableLayout mIndexableLayout;


    public void setContactsCustomization(ContactsCustomization customization) {
        this.customization = customization;
    }

    private static final class ContactsGroupStrategy extends ContactGroupStrategy {
        public ContactsGroupStrategy() {
            add(ContactGroupStrategy.GROUP_NULL, -1, "");
            addABC(0);
        }
    }

    /**
     * ***************************************** 生命周期 *****************************************
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nim_contacts, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 界面初始化
        initAdapter();

//        getFriends();

        // 注册观察者
        registerObserver(true);

        // 加载本地数据
        reload(false);
    }

    private void getFriends() {
        HashMap<String, Object> map = new HashMap<>();
        Observable<BaseResponse<MyFriendsListEntity>> observable = HttpUtil.getInstance().sApi.getMyFriends(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<MyFriendsListEntity>(getContext(), "正在加载...") {


            @Override
            protected void _onNext(MyFriendsListEntity myFriendsListEntity) {
                mFriends = myFriendsListEntity.getFriends();

                mAdapter.setDatas(mFriends);

            }

            @Override
            protected void _onError(String message) {
                Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        registerObserver(false);
    }

    private void initAdapter() {

        mIndexableLayout = findView(R.id.indexableLayout);
        mIndexableLayout.setLayoutManager(new LinearLayoutManager(getContext()));
        mIndexableLayout.showAllLetter(false);
        List<String> headList = new ArrayList<>();
        headList.add("");
        mIndexableLayout.addHeaderAdapter(new HeadAdapter("↑", null, headList));


        // set Material Design OverlayView
        mIndexableLayout.setOverlayStyle_MaterialDesign(Color.parseColor("#0EC7F0"));

        // 全字母排序。  排序规则设置为：每个字母都会进行比较排序；速度较慢
        mIndexableLayout.setCompareMode(IndexableLayout.MODE_ALL_LETTERS);
        mAdapter = new ContactAdapter(getContext());
        mIndexableLayout.setAdapter(mAdapter);

    }



    class HeadAdapter extends IndexableHeaderAdapter {

        public HeadAdapter(String index, String indexTitle, List datas) {
            super(index, indexTitle, datas);
        }

        @Override
        public int getItemViewType() {
            return 0;
        }

        @Override
        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_contact_header, parent, false);
            return new HeadAdapter.HeadContentVH(inflate);
        }

        @Override
        public void onBindContentViewHolder(RecyclerView.ViewHolder holder, Object entity) {
            HeadAdapter.HeadContentVH vh = (HeadAdapter.HeadContentVH) holder;


        }

  /* private class HeadVH extends RecyclerView.ViewHolder {
               public HeadVH(View itemView) {
                   super(itemView);

               }
           }*/

        private class HeadContentVH extends RecyclerView.ViewHolder {
            RelativeLayout rlNewFriend;
            RelativeLayout rlMyPool;
            RelativeLayout rlStar;

            public HeadContentVH(View itemView) {
                super(itemView);
                rlNewFriend = (RelativeLayout) itemView.findViewById(R.id.rl_new_friend);
                rlMyPool = (RelativeLayout) itemView.findViewById(R.id.rl_my_qun);
            }
        }

    }

/*
    private void buildLitterIdx(View view) {
        LetterIndexView livIndex = (LetterIndexView) view.findViewById(R.id.liv_index);
        livIndex.setNormalColor(getResources().getColor(R.color.contacts_letters_color));
        ImageView imgBackLetter = (ImageView) view.findViewById(R.id.img_hit_letter);
        TextView litterHit = (TextView) view.findViewById(R.id.tv_hit_letter);
        litterIdx = adapter.createLivIndex(listView, livIndex, litterHit, imgBackLetter);

        litterIdx.show();
    }
*/

    private final class ContactItemClickListener implements OnItemClickListener, OnItemLongClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            AbsContactItem item = (AbsContactItem) adapter.getItem(position);
            if (item == null) {
                return;
            }

            int type = item.getItemType();

            if (type == ItemTypes.FUNC && customization != null) {
                customization.onFuncItemClick(item);
                return;
            }

            if (type == ItemTypes.FRIEND && item instanceof ContactItem && NimUIKit.getContactEventListener() != null) {
                NimUIKit.getContactEventListener().onItemClick(getActivity(), (((ContactItem) item).getContact()).getContactId());
            }
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            AbsContactItem item = (AbsContactItem) adapter.getItem(position);
            if (item == null) {
                return false;
            }

            if (item instanceof ContactItem && NimUIKit.getContactEventListener() != null) {
                NimUIKit.getContactEventListener().onItemLongClick(getActivity(), (((ContactItem) item).getContact()).getContactId());
            }

            return true;
        }
    }

    public void scrollToTop() {
        if (listView != null) {
            int top = listView.getFirstVisiblePosition();
            int bottom = listView.getLastVisiblePosition();
            if (top >= (bottom - top)) {
                listView.setSelection(bottom - top);
                listView.smoothScrollToPosition(0);
            } else {
                listView.smoothScrollToPosition(0);
            }
        }
    }

    /**
     * *********************************** 通讯录加载控制 *******************************
     */

    /**
     * 加载通讯录数据并刷新
     *
     * @param reload true则重新加载数据；false则判断当前数据源是否空，若空则重新加载，不空则不加载
     */
    private void reload(boolean reload) {
        if (!reloadControl.canDoReload(reload)) {
            return;
        }

        if (mAdapter == null) {
            if (getActivity() == null) {
                return;
            }
            initAdapter();
        }

        // 开始加载
        if (!load(reload)) {
            // 如果不需要加载，则直接当完成处理
            onReloadCompleted();
        }
    }

    private boolean load(boolean reload) {
        if (!reload && mFriends != null && !mFriends.isEmpty()) {
                return false;
        }
        LogUtil.i(UIKitLogTag.CONTACT, "contact load data");
        getFriends();

        return true;
    }

    private void onReloadCompleted() {
        if (reloadControl.continueDoReloadWhenCompleted()) {
            // 计划下次加载，稍有延迟
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean reloadParam = reloadControl.getReloadParam();
                    Log.i(UIKitLogTag.CONTACT, "continue reload " + reloadParam);
                    reloadControl.resetStatus();
                    reload(reloadParam);
                }
            }, 50);
        } else {
            // 本次加载完成
            reloadControl.resetStatus();
        }

        LogUtil.i(UIKitLogTag.CONTACT, "contact load completed");
    }

    /**
     * 通讯录加载频率控制
     */
    class ReloadFrequencyControl {
        boolean isReloading = false;
        boolean needReload = false;
        boolean reloadParam = false;

        boolean canDoReload(boolean param) {
            if (isReloading) {
                // 正在加载，那么计划加载完后重载
                needReload = true;
                if (param) {
                    // 如果加载过程中又有多次reload请求，多次参数只要有true，那么下次加载就是reload(true);
                    reloadParam = true;
                }
                LogUtil.i(UIKitLogTag.CONTACT, "pending reload task");

                return false;
            } else {
                // 如果当前空闲，那么立即开始加载
                isReloading = true;
                return true;
            }
        }

        boolean continueDoReloadWhenCompleted() {
            return needReload;
        }

        void resetStatus() {
            isReloading = false;
            needReload = false;
            reloadParam = false;
        }

        boolean getReloadParam() {
            return reloadParam;
        }
    }

    /**
     * *********************************** 用户资料、好友关系变更、登录数据同步完成观察者 *******************************
     */

    private void registerObserver(boolean register) {
        if (register) {
            UserInfoHelper.registerObserver(userInfoObserver);
        } else {
            UserInfoHelper.unregisterObserver(userInfoObserver);
        }

        FriendDataCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver, register);

        LoginSyncDataStatusObserver.getInstance().observeSyncDataCompletedEvent(loginSyncCompletedObserver);
    }

    FriendDataCache.FriendDataChangedObserver friendDataChangedObserver = new FriendDataCache.FriendDataChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
            reloadWhenDataChanged(accounts, "onAddedOrUpdatedFriends", true);
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
            reloadWhenDataChanged(accounts, "onDeletedFriends", true);
        }

        @Override
        public void onAddUserToBlackList(List<String> accounts) {
            reloadWhenDataChanged(accounts, "onAddUserToBlackList", true);
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> accounts) {
            reloadWhenDataChanged(accounts, "onRemoveUserFromBlackList", true);
        }
    };

    private UserInfoObservable.UserInfoObserver userInfoObserver = new UserInfoObservable.UserInfoObserver() {
        @Override
        public void onUserInfoChanged(List<String> accounts) {
            reloadWhenDataChanged(accounts, "onUserInfoChanged", true, false); // 非好友资料变更，不用刷新界面
        }
    };

    private Observer<Void> loginSyncCompletedObserver = new Observer<Void>() {
        @Override
        public void onEvent(Void aVoid) {
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    reloadWhenDataChanged(null, "onLoginSyncCompleted", false);
                }
            }, 50);
        }
    };

    private void reloadWhenDataChanged(List<String> accounts, String reason, boolean reload) {
        reloadWhenDataChanged(accounts, reason, reload, true);
    }

    private void reloadWhenDataChanged(List<String> accounts, String reason, boolean reload, boolean force) {
        if (accounts == null || accounts.isEmpty()) {
            return;
        }

        boolean needReload = false;
        if (!force) {
            // 非force：与通讯录无关的（非好友）变更通知，去掉
            for (String account : accounts) {
                if (FriendDataCache.getInstance().isMyFriend(account)) {
                    needReload = true;
                    break;
                }
            }
        } else {
            needReload = true;
        }

        if (!needReload) {
            Log.d(UIKitLogTag.CONTACT, "no need to reload contact");
            return;
        }

        // log
        StringBuilder sb = new StringBuilder();
        sb.append("ContactFragment received data changed as [" + reason + "] : ");
        if (accounts != null && !accounts.isEmpty()) {
            for (String account : accounts) {
                sb.append(account);
                sb.append(" ");
            }
            sb.append(", changed size=" + accounts.size());
        }
        Log.i(UIKitLogTag.CONTACT, sb.toString());

        // reload
        reload(reload);
    }
}
