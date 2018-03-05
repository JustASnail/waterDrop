package com.drops.waterdrop.ui.other.view;

import android.widget.EditText;

/**
 * Created by dengxiaolei on 2017/4/24.
 */

public interface ILoginView {

    EditText getEtPhone();

    EditText getEtPwd();

    void onGetSMSCodeSucceed();
}
