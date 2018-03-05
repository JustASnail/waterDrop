package com.drops.waterdrop.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.drops.waterdrop.R;
import com.drops.waterdrop.model.MyToolBarOptions;
import com.drops.waterdrop.ui.base.BaseActivity;
import com.drops.waterdrop.ui.base.BasePresenter;
import com.drops.waterdrop.util.ToastUtil;
import com.netease.nim.uikit.Constants;
import com.netease.nim.uikit.common.util.RequestBodyUtils;
import com.netease.nim.uikit.http.HttpUtil;
import com.netease.nim.uikit.http.ProgressSubscriber;
import com.netease.nim.uikit.model.BaseRequestBody;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.request_body.RequestParams;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Observable;

/**
 * Created by Mr.Smile on 2017/7/13.
 */

public class CustomerServiceActivity extends BaseActivity {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.btn_commit)
    Button btnCommit;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.tv_order_no)
    TextView tvOrderNo;
    @Bind(R.id.tv_text_num)
    TextView tvTextNum;
    private long mOrderId;

    public static void start(Context context, Long orderId) {
        Intent starter = new Intent(context, CustomerServiceActivity.class);
        starter.putExtra(Constants.ORDER_ID, orderId);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        mOrderId = getIntent().getLongExtra(Constants.ORDER_ID, 0);
        initTitle();
        tvOrderNo.setText(mOrderId + "");
    }

    @Override
    protected void initData() {

    }

    private void initTitle() {
        MyToolBarOptions options = new MyToolBarOptions();
        options.isNeedNavigate = true;
        options.titleString = "售后服务";
        setMyToolbar(options);
    }

    @Override
    protected void initListener() {
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
        etContent.addTextChangedListener(mTextWatcher);
    }

    private void commit() {
        String phone = etPhone.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        if (checkValid(phone, name, content)) {
            Map<String, Object> map = new HashMap<>();
            map.put(RequestParams.mobile, phone);
            map.put(RequestParams.name, name);
            map.put(RequestParams.note, content);
            map.put(RequestParams.order_id, mOrderId);
            Observable<BaseResponse<Object>> observable = HttpUtil.getInstance().sApi.feedBack(RequestBodyUtils.getBody(map));
            HttpUtil.getInstance().execute(observable, new ProgressSubscriber<Object>(this) {
                @Override
                protected void _onNext(Object entity2) {
                    ToastUtil.showShort("反馈已提交");
                    btnCommit.setText("已提交");
                    btnCommit.setEnabled(false);
                }

                @Override
                protected void _onError(String message) {
                    ToastUtil.showShort(message);
                }
            });
        }
    }

    private boolean checkValid(String phone, String name, String content) {
        BindPhoneActivity.checkPhone(phone);

        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort("姓名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showShort("您还未留下你的意见或建议");
            return false;
        }
        return true;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_customer;
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
//          mTextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = etContent.getSelectionStart();
            editEnd = etContent.getSelectionEnd();
            tvTextNum.setText(temp.length() + "/200");
        }
    };
}
