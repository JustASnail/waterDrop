package com.netease.nim.uikit.team.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.constant.TeamFieldEnum;

/**
 * 群资料
 * Created by dengxiao on 2015/4/10.
 */
public class TeamNameSettingActivity extends UI implements View.OnClickListener {

    private static final String EXTRA_TID = "EXTRA_TID";
    public static final String EXTRA_DATA = "EXTRA_DATA";

    // view
    private EditText editText;

    // data
    private String teamId;
    private String initialValue;


    /**
     * 修改群名称
     *
     * @param context
     * @param teamId
     * @param initialValue
     */
    public static void start(Context context, String teamId, String initialValue) {
        Intent intent = new Intent();
        intent.setClass(context, TeamNameSettingActivity.class);
        intent.putExtra(EXTRA_TID, teamId);
        intent.putExtra(EXTRA_DATA, initialValue);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_name_edit);



        findViews();
        parseIntent();

    }

    private void parseIntent() {
        teamId = getIntent().getStringExtra(EXTRA_TID);
        initialValue = getIntent().getStringExtra(EXTRA_DATA);
        if (!TextUtils.isEmpty(initialValue) && initialValue.length() > 30) {
            initialValue = initialValue.substring(0, 30);
        }

        initData();
    }

    private void initData() {
        int limit = 0;
        editText.setHint(R.string.team_settings_set_name);
        limit = 20;


        if (!TextUtils.isEmpty(initialValue)) {
            editText.setText(initialValue);
            editText.setSelection(initialValue.length());
        }
//        editText.addTextChangedListener(new StringTextWatcher(limit, editText));
    }

    private void findViews() {
        ImageView ivLeft = (ImageView) findViewById(R.id.iv_left);
        TextView tvTitle = (TextView) findViewById(R.id.tv_commn_title);
        TextView tvRight = (TextView) findViewById(R.id.tv_right);
        ivLeft.setVisibility(View.VISIBLE);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("保存");
        ivLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);

        tvTitle.setText("群名称修改");



        findViewById(R.id.iv_clear).setOnClickListener(this);
        editText = (EditText) findViewById(R.id.et_name);
        editText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP;
            }

        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    complete();
                    return true;
                } else {
                    return false;
                }
            }
        });
        showKeyboardDelayed(editText);

        LinearLayout backgroundLayout = (LinearLayout) findViewById(R.id.background);
        backgroundLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showKeyboard(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_right) {
            showKeyboard(false);
            complete();
        } else if (i == R.id.iv_clear) {
            editText.setText("");
        } else if (i == R.id.iv_left) {
            finish();
        }
    }

    /**
     * 点击保存
     */
    private void complete() {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            Toast.makeText(this, R.string.not_allow_empty, Toast.LENGTH_SHORT).show();
        } else {
            /*char[] s = editText.getText().toString().toCharArray();
            int i;
            for (i = 0; i < s.length; i++) {
                if (String.valueOf(s[i]).equals(" ")) {
                    Toast.makeText(this, R.string.now_allow_space, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            if (i == s.length) {
                saveTeamProperty();
            }*/
            saveTeamProperty();

        }
    }

    private void saved() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATA, editText.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        showKeyboard(false);
        finish();
    }

    /**
     * 保存设置
     */
    private void saveTeamProperty() {
        NIMClient.getService(TeamService.class).updateTeam(teamId, TeamFieldEnum.Name, editText.getText().toString()).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                Toast.makeText(TeamNameSettingActivity.this, R.string.update_success, Toast.LENGTH_SHORT).show();
                saved();
            }

            @Override
            public void onFailed(int code) {
                if (code == ResponseCode.RES_TEAM_ENACCESS) {
                    Toast.makeText(TeamNameSettingActivity.this, R.string.no_permission, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TeamNameSettingActivity.this, String.format(getString(R.string.update_failed), code),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        showKeyboard(false);
        super.onBackPressed();
    }
}
