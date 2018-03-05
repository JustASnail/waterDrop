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
import com.drops.waterdrop.util.NumberUtil;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.PoolListEntity;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Mr.Smile on 2017/7/4.
 */

public class FocusPoolAdapter extends BaseQuickAdapter<PoolListEntity.ResultsBean, BaseViewHolder> {
    private ImageView ivImg;
    private ImageView ivPrivate;
    private TextView tvTime;
    private TextView tvFocusNum;
    private HeadImageView ivAvatar;
    private TextView tvNickname;
    private TextView tvDelete;
    private TextView tvTitle;
    private RelativeLayout ivCheck;
    private RelativeLayout tvUnselect;
    private RelativeLayout rlCover;
    private TextView tvContent;
    private boolean isPrivateMode;
    private List<Long> hideFocusList = new ArrayList<>();
    private List<Long> showFocusList = new ArrayList<>();
    private boolean isPrivateChange;

    public FocusPoolAdapter(@LayoutRes int layoutResId) {
        super(R.layout.item_focus_pool_list);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final PoolListEntity.ResultsBean item) {

        final RelativeLayout tvUnselect = helper.getView(R.id.tv_unselect);
        final RelativeLayout ivCheck = helper.getView(R.id.iv_check);
        RelativeLayout rlCover = helper.getView(R.id.rl_cover);

        ImageView ivImg = helper.getView(R.id.iv_img);
        ImageView ivPrivate = helper.getView(R.id.iv_private);
        HeadImageView ivAvatar = helper.getView(R.id.iv_avatar);
        TextView tvTime = helper.getView(R.id.tv_time);
        TextView tvContent = helper.getView(R.id.tv_content);
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvFocusNum = helper.getView(R.id.tv_focus_num);
        TextView tvNickname = helper.getView(R.id.tv_nickname);
        TextView tvDelete = helper.getView(R.id.tv_delete);

        GlideUtil.showImageView(mContext, R.drawable.img_qs_343x158,item.getHeadImg(), ivImg);
        PoolListEntity.ResultsBean.CreatorBean creator = item.getCreator();
        if (creator != null) {
            GlideUtil.showImageViewToCircle(mContext, R.drawable.img_qs_33x33, creator.getPhoto(), ivAvatar);
            tvNickname.setText(creator.getNickName());
        }
        tvTime.setText(item.getCreateTime());
        tvFocusNum.setText(NumberUtil.Instance.formatNumber(item.getAttentionNum()));
        tvTitle.setText(item.getDropName());
        tvContent.setText(item.getDropDesc());

        final int attentionStatus = item.getAttentionShowStatus();    //关注的类型 1代表非隐私 0代表隐私
        ivPrivate.setVisibility(attentionStatus == 1 ? View.GONE : View.VISIBLE);

        if (isPrivateMode) {
            rlCover.setVisibility(View.VISIBLE);
            if (attentionStatus == 1) {
                ivCheck.setVisibility(View.GONE);
                tvUnselect.setVisibility(View.VISIBLE);
            } else if (attentionStatus == 0) {
                ivCheck.setVisibility(View.VISIBLE);
                tvUnselect.setVisibility(View.GONE);
            }
        } else {
            rlCover.setVisibility(View.GONE);
            if (isPrivateChange) {
                String json = getJson(showFocusList);
                String json1 = getJson(hideFocusList);
                Log.d(TAG, "showFocusList: " + showFocusList.size());
                Log.d(TAG, "hideFocusList: " + hideFocusList.size());
                if (showFocusList.size() > 0) {
                    cancelPrivate(json);
                    clearList();
                }
                if (hideFocusList.size() > 0) {
                    setPrivate(json1);
                    clearList();
                }
            }
        }

        //删除 也就是取消关注
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCollection(item.getDropId(), helper.getPosition());
            }
        });

        //取消隐秘收藏
        ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attentionStatus == 0) {
                    showFocusList.add(item.getDropId());
                    item.setAttentionShowStatus(1);
                } else if (attentionStatus == 1) {
                    hideFocusList.remove(item.getDropId());
                    item.setAttentionShowStatus(0);
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
                if (attentionStatus == 0) {
                    showFocusList.remove(item.getDropId());
                    item.setAttentionShowStatus(1);
                } else if (attentionStatus == 1) {
                    item.setAttentionShowStatus(0);
                    hideFocusList.add(item.getDropId());
                }

                isPrivateChange = true;
                tvUnselect.setVisibility(View.GONE);
                ivCheck.setVisibility(View.VISIBLE);
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

    private void setPrivate(String collectIdJson) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.drop_id_json, collectIdJson);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.hideFocusPool(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object dataBean) {
                clearList();
                ToastUtil.showShort("隐秘收藏成功");
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void cancelPrivate(String collectIdJson) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.drop_id_json, collectIdJson);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.showFocusPool(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object dataBean) {
                ToastUtil.showShort("取消隐秘收藏成功");
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    private void deleteCollection(long dropId, final int pos) {
        Map<String, Object> map = new HashMap<>();
        map.put(RequestParams.drop_id, dropId);
        Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.cancelFocusPool(RequestBodyUtils.build(map));
        HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(mContext) {
            @Override
            protected void _onNext(Object dataBean) {
                ToastUtil.showShort("删除成功");
                if (listener != null) {
                    listener.onCancelFocusPool(pos);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showShort(message);
            }
        });
    }

    public void setPrivateMode(boolean privateMode) {
        isPrivateMode = privateMode;
    }

    private CancelFocusPoolListener listener;

    public void setListener(CancelFocusPoolListener listener) {
        this.listener = listener;
    }

    public interface CancelFocusPoolListener {
        void onCancelFocusPool(int pos);
    }
}
