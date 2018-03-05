package com.drops.waterdrop.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.drops.waterdrop.ui.find.activity.GoodsDetailsActivity;
import com.drops.waterdrop.ui.find.activity.PoolDetailPageActivity;
import com.drops.waterdrop.ui.find.activity.CommonWebActivity;
import com.google.gson.Gson;
import com.microquation.linkedme.android.LinkedME;
import com.microquation.linkedme.android.indexing.LMUniversalObject;
import com.microquation.linkedme.android.util.LinkProperties;
import com.netease.nim.uikit.model.JsInteractionParamsEntity;
import com.netease.nim.uikit.model.LinkedMeModel;

import java.util.HashMap;

import static android.R.attr.tag;
import static com.drops.waterdrop.ui.find.adapter.PoolListAdapter.url;

/**
 * <p>中转页面</p>
 * <p>
 * Created by LinkedME06 on 16/11/17.
 */

public class MiddleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LinkedME.TAG, "onCreate: MiddleActivity is called.");
        Toast.makeText(this, "MiddleActivity 被调用了", Toast.LENGTH_SHORT).show();
        //获取与深度链接相关的值
        LinkProperties linkProperties = getIntent().getParcelableExtra(LinkedME.LM_LINKPROPERTIES);
        LMUniversalObject lmUniversalObject = getIntent().getParcelableExtra(LinkedME.LM_UNIVERSALOBJECT);
        //LinkedME SDK初始化成功，获取跳转参数，具体跳转参数在LinkProperties中，和创建深度链接时设置的参数相同；
        if (linkProperties != null) {
            Log.i("LinkedME-Demo", "Channel " + linkProperties.getChannel());
            Log.i("LinkedME-Demo", "control params " + linkProperties.getControlParams());
            Log.i("LinkedME-Demo", "link(深度链接) " + linkProperties.getLMLink());
            //获取自定义参数封装成的HashMap对象
            HashMap<String, String> hashMap = linkProperties.getControlParams();

            JsInteractionParamsEntity.ParamsBean paramsBean = new JsInteractionParamsEntity.ParamsBean();

            //获取传入的参数
            String json = hashMap.get("Params");
            String page = hashMap.get("VC");
            System.out.println("分享：" + json);
            Gson gson = new Gson();
            LinkedMeModel linkedMeModel = gson.fromJson(json, LinkedMeModel.class);
            if (linkedMeModel == null) {
                return;
            }
            long dropId = linkedMeModel.getDropId();
            String goodsId = linkedMeModel.getGoodsId();
            long tipId = linkedMeModel.getTipId();
            String dropTitle = linkedMeModel.getDropTitle();
            String tipUrl = linkedMeModel.getTipUrl();
            System.out.println(tag + "分享：" + url);

//            url = "http://api.waterdrop.xin/drops_wechat/app_h5/goods-detail.html?action=tipInfo&tip_id=10008970";
/*
            if (!TextUtils.isEmpty(url) && url.contains("&")) {
                String[] split = url.split("&");

                for (String s : split) {
                    if (!TextUtils.isEmpty(s) && s.contains("drop_id=")) {
                        int index = s.indexOf("=");
                        System.out.println("分享：拆分：：" + s+ "索引"+index );
                        if (s.length() > 8) {
                            dropId = s.substring(index + 1);
                        }
                    } else if (!TextUtils.isEmpty(s) && s.contains("tip_id=")) {
                        int index = s.indexOf("=");
                        if (s.length() > 7) {
                            tipId = s.substring(index + 1);
                        }
                    } else if (!TextUtils.isEmpty(s) && s.contains("goods_id=")) {
                        int index = s.indexOf("=");
                        if (s.length() > 9) {
                            goodsId = s.substring(index + 1);
                        }
                    }
                }

               */
/*
                int index = url.indexOf("drop_id=");
                String substring = url.substring(index);*//*


            }
*/
            System.out.println("分享：截取之后：" + tipId + "---" + dropId + "---" + goodsId);

            if (TextUtils.equals("POOL", page)) {
                PoolDetailPageActivity.start(MiddleActivity.this, dropId);
            }else  if (TextUtils.equals("TIP", page)) {
                CommonWebActivity.start(MiddleActivity.this, tipId, tipUrl);
            }else  if (TextUtils.equals("GOODS", page)) {
                GoodsDetailsActivity.start(MiddleActivity.this, goodsId,  tipId, dropId, tipUrl);
            }
            String title = "";
            String shareContent = "";
            String url_path = "";
/*
            if (view != null) {
                if (view.equals("POOL")) {
                    PoolDetailPageActivity.start(MiddleActivity.this, Long.parseLong(id));
                    return;
                } */
/*else if (view.equals(getString(R.string.str_h5_apps))) {
                    title = getString(R.string.str_apps_name);
                    shareContent = getString(R.string.str_share_content_apps);
                    url_path = getString(R.string.str_path_apps);
                } else if (view.equals(getString(R.string.str_h5_features))) {
                    title = getString(R.string.str_features_name);
                    shareContent = getString(R.string.str_share_content_features);
                    url_path = getString(R.string.str_path_features);
                } else if (view.equals(getString(R.string.str_h5_intro))) {
                    title = getString(R.string.str_intro_name);
                    shareContent = getString(R.string.str_share_content_intro);
                    url_path = getString(R.string.str_path_intro);
                }*//*

//                openActivity(title, view, shareContent, url_path, ShareActivity.class);
                finish();
            }
*/
            //清除跳转数据，该方法理论上不需要调用，因Android集成方式各种这样，若出现重复跳转的情况，可在跳转成功后调用该方法清除参数
            //LinkedME.getInstance().clearSessionParams();
        }

        if (lmUniversalObject != null) {
            Log.i("LinkedME-Demo", "title " + lmUniversalObject.getTitle());
            Log.i("LinkedME-Demo", "control " + linkProperties.getControlParams());
            Log.i("ContentMetaData", "metadata " + lmUniversalObject.getMetadata());
        }
        finish();
    }

/*
    public void openActivity(String title, String param_view, String shareContent, String url_path, Class clazz) {
        Intent intent = new Intent(this, clazz);
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra(ShareActivity.TITLE, title);
        }
        if (!TextUtils.isEmpty(param_view)) {
            intent.putExtra(ShareActivity.PARAM_VIEW, param_view);
        }
        if (!TextUtils.isEmpty(shareContent)) {
            intent.putExtra(ShareActivity.SHARE_CONTENT, shareContent);
        }
        if (!TextUtils.isEmpty(url_path)) {
            intent.putExtra(ShareActivity.URL_PATH, url_path);
        }
        startActivity(intent);
    }
*/
}
