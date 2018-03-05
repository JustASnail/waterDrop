package com.netease.nim.uikit.guideview.component;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.guideview.Component;


/**
 * Created by binIoter on 16/6/17.
 */
public class AddFriendComponent implements Component {



  @Override public View getView(LayoutInflater inflater) {
    RelativeLayout rl = new RelativeLayout(inflater.getContext());
    LinearLayout.LayoutParams param =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
    rl.setLayoutParams(param);
    RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    lp1.addRule(RelativeLayout.CENTER_HORIZONTAL);
    ImageView iv1 = new ImageView(inflater.getContext());
    iv1.setImageResource(R.drawable.xsyd_3_s);
    iv1.setLayoutParams(lp1);


    RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    lp2.addRule(RelativeLayout.CENTER_HORIZONTAL);
    lp2.setMargins(0, ScreenUtil.dip2px(360),0,0);
    ImageView iv2 = new ImageView(inflater.getContext());
    iv2.setImageResource(R.drawable.xsyd_3_x);
    iv2.setLayoutParams(lp2);

    iv2.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (mListener != null) {
          mListener.onClickListener();
        }
      }
    });

    rl.addView(iv1);
    rl.addView(iv2);
    return rl;
  }

  @Override public int getAnchor() {
    return Component.ANCHOR_BOTTOM;
  }

  @Override public int getFitPosition() {
    return Component.FIT_CENTER;
  }

  @Override public int getXOffset() {
    return 0;
  }

  @Override public int getYOffset() {
    return 16;
  }

  public interface OnViewClickListener{
    void onClickListener();
  }

  private OnViewClickListener mListener;

  public void setListener(OnViewClickListener listener) {
    mListener = listener;
  }
}
