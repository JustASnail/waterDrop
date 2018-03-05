package com.drops.waterdrop.ui.session.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.netease.nim.uikit.model.LocalContactEntity;
import com.drops.waterdrop.ui.session.activity.VerifyActivity;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.AddFriendForUid;
import com.netease.nim.uikit.model.BaseResponse;

import java.util.HashMap;

import me.yokeyword.indexablerv.IndexableAdapter;
import rx.Observable;


/**
 * Created by YoKey on 16/10/8.
 */
public class AddContactAdapter extends IndexableAdapter<LocalContactEntity> {
    private LayoutInflater mInflater;
    private Context mContext;

    public AddContactAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_contact, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, final LocalContactEntity entity) {
        ContentVH vh = (ContentVH) holder;
        vh.tvName.setText(entity.getName());
        vh.tvMobile.setText(entity.getMobile());
        GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_33x33, entity.getPhoto(), vh.ivHead);

        if (entity.getRegisterStatus() == 1) {

            int status = entity.getRelationStatus();
            if (status == 1) {
                vh.btnAdd.setEnabled(false);
                vh.btnAdd.setText("已添加");
            } else if (status == 2) {
                vh.btnAdd.setEnabled(false);
                vh.btnAdd.setText("待验证");
            } else {
                vh.btnAdd.setEnabled(true);
                vh.btnAdd.setText("添加");
            }

            vh.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddFriendForUid searchFriendEntity = new AddFriendForUid();
                    searchFriendEntity.setNickName(entity.getNickName());
                    searchFriendEntity.setPhoto(entity.getPhoto());
                    searchFriendEntity.setRelationStatus(entity.getRelationStatus());
                    searchFriendEntity.setUid(entity.getUid());
                    VerifyActivity.start(mContext, searchFriendEntity);
                }
            });

        } else {
            if (MyUserCache.getUserInviteStatus(entity.getMobile())) {
                vh.btnAdd.setText("已邀请");
                vh.btnAdd.setEnabled(false);
            } else {
                vh.btnAdd.setText("邀请");
                vh.btnAdd.setEnabled(true);
                vh.btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendSMS(entity);
                    }
                });
            }

        }

    }


    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        public IndexVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_index);
        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tvName, tvMobile;
        Button btnAdd;
        HeadImageView ivHead;

        public ContentVH(View itemView) {

            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvMobile = (TextView) itemView.findViewById(R.id.tv_mobile);
            btnAdd = (Button) itemView.findViewById(R.id.btn_add);
            ivHead = (HeadImageView) itemView.findViewById(R.id.img_avatar);


        }
    }

/*
    private void getUserInfo(String account) {
        NimUserInfoCache.getInstance().getUserInfoFromRemote(account, new RequestCallback<NimUserInfo>() {
            @Override
            public void onSuccess(NimUserInfo user) {
                if (user == null) {
                    ToastUtil.showShort("添加失败， 改用户不存在");
                } else {
//                    VerifyActivity.start(mContext, user);

                }
            }

            @Override
            public void onFailed(int code) {
                if (code == 408) {
                    UIUtils.showToast(UIUtils.getString(R.string.network_is_not_available));

                } else {
                    UIUtils.showToast("on failed:" + code);

                }
            }

            @Override
            public void onException(Throwable exception) {
                UIUtils.showToast("on exception:" + exception.getMessage());

            }
        });

    }
*/

    private void sendSMS(final LocalContactEntity entity) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", entity.getMobile());
        map.put("nick_name", TextUtils.isEmpty(MyUserCache.getUserNickName()) ? MyUserCache.getUserUid() : MyUserCache.getUserNickName());

        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.sendInviteSms(RequestBodyUtils.getBody(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object o) {
                ToastUtil.showShort("发送邀请成功");
                MyUserCache.saveUserInviteStatus(entity.getMobile(), true);
                notifyDataSetChanged();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
