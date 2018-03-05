package com.drops.waterdrop.ui.session.presenter;

import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.ui.session.view.ISearchView;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AddFriendForUid;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.FriendDetailEntity;
import com.netease.nim.uikit.model.SearchFriendEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by dengxiaolei on 2017/4/26.
 */

public class SearchPresenter extends BasePresenter<ISearchView> {
    public SearchPresenter(BaseActivity context) {
        super(context);
    }

    public void queryContact(final String account) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.friend_mobile, account);
        Observable<BaseResponse<AddFriendForUid>> observable = HttpUtil.getInstance().sApi.searchFriend(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<AddFriendForUid>(mContext, "正在搜索...") {
            @Override
            protected void _onNext(AddFriendForUid searchFriendEntity) {

                if (searchFriendEntity == null || searchFriendEntity.getUid() == 0) {
                    try {
                        Long uid = Long.parseLong(account);
                        searchFriendForUid(uid);
                    } catch (NumberFormatException e) {
                        ToastUtil.showShort("该用户不存在");
                        getView().onQueryFailed();
                    }

                } else {
                    getView().onQuerySuccess(searchFriendEntity);
                }
            }

            @Override
            protected void _onError(String message) {
                try {
                    searchFriendForUid(Long.parseLong(account));
                } catch (NumberFormatException e) {
                    ToastUtil.showShort(message);
                    getView().onQueryFailed();
                }
            }
        });
    }

    private void searchFriendForUid(long uid) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.friend_uid, uid);
        Observable<BaseResponse<FriendDetailEntity>> observable = HttpUtil.getInstance().sApi.getFriendDetail(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<FriendDetailEntity>(mContext, "正在搜索...") {
            @Override
            protected void _onNext(FriendDetailEntity entity) {
                if (entity.getUid() > 0) {
                    AddFriendForUid friendEntity = new AddFriendForUid();
                    friendEntity.setNickName(entity.getNickName());
                    friendEntity.setPhoto(entity.getPhoto());
                    friendEntity.setUid(entity.getUid());
                    friendEntity.setRelationStatus(entity.getRelationStatus());
                    getView().onQuerySuccess(friendEntity);
                } else {
                    getView().onQueryResultNull();
                }

            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
                getView().onQueryFailed();
            }
        });
    }

    public void searchKeyword(final String keyword) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.keyword, keyword);
        Observable<BaseResponse<SearchFriendEntity>> observable = HttpUtil.getInstance().sApi.searchFriendKeyword(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<SearchFriendEntity>(mContext, "正在搜索...") {
            @Override
            protected void _onNext(SearchFriendEntity searchFriendEntity) {
                List<SearchFriendEntity.ResultsBean> results = searchFriendEntity.getResults();
                if (results.size() > 0) {
                    getView().onSearchSuccess(results);
                } else {
                    ToastUtil.showShort("该用户不存在");
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

}
