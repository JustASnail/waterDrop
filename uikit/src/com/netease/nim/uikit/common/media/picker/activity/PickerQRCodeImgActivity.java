package com.netease.nim.uikit.common.media.picker.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.media.picker.adapter.PickerPreviewPagerAdapter;
import com.netease.nim.uikit.common.media.picker.model.PhotoInfo;
import com.netease.nim.uikit.common.media.picker.model.PickerContract;
import com.netease.nim.uikit.common.ui.imageview.BaseZoomableImageView;
import com.netease.nim.uikit.common.util.media.BitmapDecoder;
import com.netease.nim.uikit.common.util.media.ImageUtil;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.session.constant.Extras;
import com.netease.nim.uikit.session.constant.RequestCode;

import java.util.ArrayList;
import java.util.List;

public class PickerQRCodeImgActivity extends UI implements OnClickListener, OnPageChangeListener {

    public static final int RESULT_FROM_USER = RESULT_FIRST_USER + 1;

    public static void start(Activity activity, List<PhotoInfo> photos, int position, List<PhotoInfo> selectPhotoList, int mutiSelectLimitSize) {
        Intent intent = PickerContract.makePreviewDataIntent(photos, selectPhotoList);
        intent.setClass(activity, PickerQRCodeImgActivity.class);
        intent.putExtra(Extras.EXTRA_PREVIEW_CURRENT_POS, position);
        intent.putExtra(Extras.EXTRA_MUTI_SELECT_SIZE_LIMIT, mutiSelectLimitSize);
        activity.startActivityForResult(intent, RequestCode.PICKER_IMAGE_PREVIEW);
    }


    private ViewPager imageViewPager;

    private PickerPreviewPagerAdapter imageViewPagerAdapter;

    private List<PhotoInfo> selectPhotoList = new ArrayList<PhotoInfo>();

    private List<PhotoInfo> photoLists = new ArrayList<PhotoInfo>();

    private int firstDisplayImageIndex = 0;

    private int currentPosition = -1;

    private int totalSize;

    private BaseZoomableImageView currentImageView;

    private int tempIndex = -1;

    @SuppressWarnings("unused")
    private LinearLayout previewOperationBar;

    private TextView tvOk;

    private int mutiSelectLimitSize;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nim_picker_qr_code_image_activity);

        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.toolbar, options);

        proceedExtras();
        initActionBar();
        initUI();
    }

    private void proceedExtras() {
        Intent intent = getIntent();
        firstDisplayImageIndex = intent.getIntExtra(Extras.EXTRA_PREVIEW_CURRENT_POS, 0);
        mutiSelectLimitSize = intent.getIntExtra(Extras.EXTRA_MUTI_SELECT_SIZE_LIMIT, 9);

        photoLists.addAll(PickerContract.getPhotos(intent));
        totalSize = photoLists.size();

        selectPhotoList.clear();
        selectPhotoList.addAll(PickerContract.getSelectPhotos(intent));
    }

    private void initActionBar() {
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvOk.setOnClickListener(this);
    }

    private void initUI() {

        imageViewPager = (ViewPager) findViewById(R.id.picker_image_preview_viewpager);
        imageViewPager.setOnPageChangeListener(this);
        imageViewPager.setOffscreenPageLimit(2);
        imageViewPagerAdapter = new PickerPreviewPagerAdapter(this, photoLists, getLayoutInflater(),
                imageViewPager.getLayoutParams().width, imageViewPager.getLayoutParams().height, this);
        imageViewPager.setAdapter(imageViewPagerAdapter);

        setTitleIndex(firstDisplayImageIndex);
        updateTitleSelect(firstDisplayImageIndex);
        imageViewPager.setCurrentItem(firstDisplayImageIndex);
    }

    private void updateTitleSelect(int index) {
        if (photoLists == null || index >= photoLists.size())
            return;

        PhotoInfo photo = photoLists.get(index);

    }

    private void setTitleIndex(int index) {
        if (totalSize <= 0) {
            setTitle("");
        } else {
            index++;
            setTitle(index + "/" + totalSize);
        }
    }

    public void updateCurrentImageView(final int position) {
        if (photoLists == null
                || (position > 0
                && position >= photoLists.size()))
            return;

        if (currentPosition == position) {
            return;
        } else {
            currentPosition = position;
        }

        LinearLayout currentLayout = (LinearLayout) imageViewPager.findViewWithTag(position);
        if (currentLayout == null) {
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateCurrentImageView(position);
                }
            }, 300);
            return;
        }
        currentImageView = (BaseZoomableImageView) currentLayout.findViewById(R.id.imageView);
        currentImageView.setViewPager(imageViewPager);

        setImageView(photoLists.get(position));
    }

    public void setImageView(PhotoInfo info) {
        if (info == null) {
            return;
        }

        if (info.getAbsolutePath() == null) {
            return;
        }

        Bitmap bitmap = BitmapDecoder.decodeSampledForDisplay(info.getAbsolutePath());
        if (bitmap == null) {
            currentImageView.setImageBitmap(ImageUtil.getDefaultBitmapWhenGetFail());
            Toast.makeText(this, R.string.picker_image_error, Toast.LENGTH_LONG).show();
        } else {
            try {
                bitmap = ImageUtil.rotateBitmapInNeeded(info.getAbsolutePath(), bitmap);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
            currentImageView.setImageBitmap(bitmap);
        }
    }

    private void restoreList() {
        if (tempIndex != -1) {
            imageViewPager.setAdapter(imageViewPagerAdapter);
            setTitleIndex(tempIndex);
            imageViewPager.setCurrentItem(tempIndex);
            tempIndex = -1;
        }
    }


    @Override
    public void onResume() {
        // restore the data source
        restoreList();

        super.onResume();
    }

    @Override
    public void onPause() {
        // save the data source and recycle all bitmaps
        imageViewPager.setAdapter(null);
        tempIndex = currentPosition;
        currentPosition = -1;

        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_ok) {
            if (photoLists == null || currentPosition >= photoLists.size())
                return;
            PhotoInfo selectPhoto = photoLists.get(currentPosition);
            if (selectPhoto != null) {
                Intent intent = new Intent();
                intent.putExtra(Extras.EXTRA_SCAN_SELECT_MODE, selectPhoto.getAbsolutePath());
                setResult(RESULT_OK, intent);
                finish();

            }

        }
    }



    @Override
    public void onBackPressed() {
        setResult(RESULT_FROM_USER, PickerContract.makePreviewDataIntent(photoLists, selectPhotoList,
                false));
        finish();
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
        setTitleIndex(arg0);
        updateTitleSelect(arg0);
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }
}
