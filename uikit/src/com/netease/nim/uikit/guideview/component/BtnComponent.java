package com.netease.nim.uikit.guideview.component;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.guideview.Component;


/**
 * Created by binIoter on 16/6/17.
 */
public class BtnComponent implements Component {


  @Override public View getView(LayoutInflater inflater) {

    ImageView mImageView = new ImageView(inflater.getContext());
    mImageView.setImageResource(R.drawable.quan);

    mImageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (mListener != null) {
          mListener.onClickListener();
        }
      }
    });
    return mImageView;
  }

  @Override public int getAnchor() {
    return Component.ANCHOR_OVER;
  }

  @Override public int getFitPosition() {
    return Component.FIT_CENTER;
  }

  @Override public int getXOffset() {
    return 0;
  }

  @Override public int getYOffset() {
//    return ScreenUtil.getStatusBarHeight(mImageView.getContext());
    return 0;
  }

  public interface OnViewClickListener{
    void onClickListener();
  }

  private OnViewClickListener mListener;

  public void setListener(OnViewClickListener listener) {
    mListener = listener;
  }
}
