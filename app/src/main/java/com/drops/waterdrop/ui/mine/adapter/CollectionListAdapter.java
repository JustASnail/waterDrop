package com.drops.waterdrop.ui.mine.adapter;

import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.drops.waterdrop.R;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.CollectionSTEntry;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/7/5.
 */

public class CollectionListAdapter extends BaseQuickAdapter<CollectionSTEntry.ResultsBean, BaseViewHolder> {

    private boolean isPrivateMode;
    private boolean isPrivateChange;
    private List<Long> hideFocusList = new ArrayList<>();
    private List<Long> showFocusList = new ArrayList<>();
    private int clickCountCancel = 0;
    private int clickCountSet = 0;

    public CollectionListAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_collection_st);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionSTEntry.ResultsBean item) {
        setLayoutVisible(helper, item);
    }

    private void setLayoutVisible(final BaseViewHolder helper, final CollectionSTEntry.ResultsBean item) {
        RelativeLayout rlCover = helper.getView(R.id.rl_cover);
        final RelativeLayout ivCheck = helper.getView(R.id.iv_check);
        final RelativeLayout tvUnselect = helper.getView(R.id.tv_unselect);
        //以上用于隐私设置
        ImageView ivImg = helper.getView(R.id.iv_img);
        HeadImageView ivAvatar = helper.getView(R.id.iv_avatar);
        ImageView ivDelete = helper.getView(R.id.iv_delete);
        ImageView ivPrivate = helper.getView(R.id.iv_private);
        TextView tvTime = helper.getView(R.id.tv_date);
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvDesc = helper.getView(R.id.tv_desc);

        GlideUtil.showImageView(mContext, R.drawable.img_qs_375x207,item.getCover(), ivImg);
        CollectionSTEntry.ResultsBean.CreatorBean creator = item.getCreator();
        if (creator != null) {
            GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_50x50, creator.getPhoto(), ivAvatar);
        }
        tvTime.setText(item.getCreateTime());
        tvDesc.setText(item.getTipContent());
        tvName.setText(item.getTipTitle());

        final int collectStatus = item.getCollectShowStatus();
        ivPrivate.setVisibility(collectStatus == 0 ? View.VISIBLE : View.GONE);
        if (isPrivateMode) {
            rlCover.setVisibility(View.VISIBLE);
            if (collectStatus == 1) {
                ivCheck.setVisibility(View.GONE);
                tvUnselect.setVisibility(View.VISIBLE);
            } else if (collectStatus == 0) {
                ivCheck.setVisibility(View.VISIBLE);
                tvUnselect.setVisibility(View.GONE);
            }
        } else {
            rlCover.setVisibility(View.GONE);
            //隐私列表更改的时候
            if (isPrivateChange) {
                String json = getJson(showFocusList);
                String json1 = getJson(hideFocusList);
                if (showFocusList.size() > 0) {
                    cancelPrivate(json, 1);
                    clearList();
                }
                if (hideFocusList.size() > 0) {
                    setPrivate(json1, 1);
                    clearList();
                }
            }
        }

        //删除
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "[" + item.getTipId() + "]";
                int position = helper.getPosition();
                deleteCollection(s, 1,position);
            }
        });

        //取消隐秘收藏
        ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + collectStatus);
                clickCountCancel++;
                if (collectStatus == 0) {
                    showFocusList.add(item.getTipId());
                    item.setCollectShowStatus(1);
                } else if (collectStatus == 1) {
                    hideFocusList.remove(item.getTipId());
                    item.setCollectShowStatus(0);
                }

                isPrivateChange = true;
                ivCheck.setVisibility(View.GONE);
                tvUnselect.setVisibility(View.VISIBLE);
            }
        });

        //设置隐秘收藏
        tvUnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCountSet++;
                Log.d(TAG, "onClick: " + collectStatus);
                if (collectStatus == 0) {
                    showFocusList.remove(item.getTipId());
                    item.setCollectShowStatus(1);
                } else if (collectStatus == 1) {
                    item.setCollectShowStatus(0);
                    hideFocusList.add(item.getTipId());
                }

                isPrivateChange = true;
                tvUnselect.setVisibility(View.GONE);
                ivCheck.setVisibility(View.VISIBLE);
            }
        });


    }

    private void setPrivate(String collectIdJson, int type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.collect_id_json, collectIdJson);
        map.put(RequestParams.type, type);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.setPrivate(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object dataBean) {
                ToastUtil.showShort("隐秘收藏成功");
                if (mOnUpdateListener != null) {
                    mOnUpdateListener.onUpdate();
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void clearList() {
        if (hideFocusList != null) {
            hideFocusList.clear();
        }
        if (showFocusList != null) {
            showFocusList.clear();
        }
    }

    private void cancelPrivate(String collectIdJson, int type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.collect_id_json, collectIdJson);
        map.put(RequestParams.type, type);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.cancelPrivate(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object dataBean) {
                ToastUtil.showShort("取消隐秘收藏成功");
                if (mOnUpdateListener != null) {
                    mOnUpdateListener.onUpdate();
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void deleteCollection(String collectIdJson, int type, final int pos) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.collect_id_json, collectIdJson);
        map.put(RequestParams.type, type);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.deleteCollectPost(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object dataBean) {
                ToastUtil.showShort("删除成功");
                if (onDeleteListener != null) {
                    onDeleteListener.onDelete(pos);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private String getJson(List<Long> list) {
        StringBuilder json = new StringBuilder("[");
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    json.append(list.get(i));
                    json.append(",");
                } else if (i == list.size() - 1) {
                    json.append(list.get(i));
                    json.append("]");
                }
            }
        } else {
            return "";
        }
        return json.toString();
    }

    public void setPrivateMode(boolean isPrivateMode) {
        this.isPrivateMode = isPrivateMode;
    }


    private AddressManageAdapter.onUpdateListener mOnUpdateListener;

    public void setOnUpdateListener(AddressManageAdapter.onUpdateListener onUpdateListener) {
        mOnUpdateListener = onUpdateListener;
    }

    private void setCollectionStatus(int status, int position){
        getData().get(position).setCollectStatus(status);
    }

    private OnDeleteListener onDeleteListener;

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public interface OnDeleteListener {
        void onDelete(int pos);
    }

}
