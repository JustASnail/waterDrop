package com.netease.nim.uikit.common.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.model.InterestChekedEntity;
import com.netease.nim.uikit.model.InterestEntity;

import java.util.List;

public class GridViewDialog extends Dialog implements View.OnClickListener {
    private ImageView iconCancel;
    private TextView titleTxt;
    private GridView gridView;
    private TextView tvOk;

    private Context mContext;
    private List<InterestEntity.ResultsBean> interests;

    private OnOkClickListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private GridViewAdapter mAdapter;

    public GridViewDialog(Context context, String title , List<InterestEntity.ResultsBean> results, OnOkClickListener listener) {
        super(context, R.style.easy_dialog_style);
        this.mContext = context;
        this.title = title;
        this.interests = results;
        this.listener = listener;

    }

    public GridViewDialog(Context context, int themeResId, String title) {
        super(context, themeResId);
        this.mContext = context;
    }

    public GridViewDialog(Context context, int themeResId, OnOkClickListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
    }

    protected GridViewDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public void setTitle(String title) {
        titleTxt.setText(title);
    }

    public GridViewDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public GridViewDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_interest_selector);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        iconCancel = (ImageView) findViewById(R.id.icon_top);
        titleTxt = (TextView) findViewById(R.id.tv_title);
        gridView = (GridView) findViewById(R.id.grid_view);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvOk.setOnClickListener(this);
        iconCancel.setOnClickListener(this);

        titleTxt.setText(title);
        mAdapter = new GridViewAdapter(interests, getContext());
        gridView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_ok) {
            if (listener != null) {
                List<InterestChekedEntity> checked = mAdapter.getSelecteds();
                listener.onClick(this, checked);
            }
        } else if (v.getId() == R.id.icon_top) {
            dismiss();
        }
    }

    public interface OnOkClickListener {
        void onClick(Dialog dialog, List<InterestChekedEntity> lists);
    }
}