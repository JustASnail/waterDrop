package com.drops.waterdrop.util.sys;

import com.drops.waterdrop.util.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mr.Smile on 2017/7/14.
 */

public class TextLimitUtil {
    public static boolean isIllegal(String str){
        String limitEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);

        if (m.find()) {
            ToastUtil.showShort("不允许输入特殊字符");
            return false;
        } else {
            return true;
        }
    }
}
