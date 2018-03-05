package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.cache.MyUserCache;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.GlideUtil;

import java.util.Hashtable;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.graphics.Color.BLACK;

/**
 * Created by Mr.Smile on 2017/8/1.
 */

public class MineCodeActivity extends BaseActivity {

    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.iv_head)
    HeadImageView ivHead;
    @Bind(R.id.iv_gender_tag)
    ImageView ivGenderTag;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.iv_code)
    ImageView ivCode;
    private Bitmap qrCode;

    public static void start(Context context) {
        Intent starter = new Intent(context, MineCodeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        initTitle();

        Glide.with(this).load(MyUserCache.getUserPhoto()).error(R.drawable.img_qs_60x60).into(ivHead);
        tvName.setText(MyUserCache.getUserNickName());
        int userSex = MyUserCache.getUserSex();
        ivGenderTag.setVisibility(View.VISIBLE);
        if (userSex == 1) {
            ivGenderTag.setImageResource(R.mipmap.icon_wd_xb_2);
        } else {
            ivGenderTag.setImageResource(R.mipmap.icon_wd_xb_1);
        }
    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "我的二维码";
        setMyToolbar(options);
    }

    @Override
    protected void initData() {
        String str = Constants.QRCODE_MINE + MyUserCache.getUserUid();
        try {
            qrCode = createQRCode(str, 400);
            ivCode.setImageBitmap(qrCode);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_mine_code;
    }


    //生成二维码图片（不带图片）
    public static Bitmap createQRCode(String str, int widthAndHeight)
            throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix matrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);

        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        //画黑点
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK; //0xff000000
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        qrCode.recycle();
    }
}
