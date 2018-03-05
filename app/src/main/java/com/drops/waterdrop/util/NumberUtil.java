package com.drops.waterdrop.util;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Wonder on 2016/7/23.
 */
public enum NumberUtil {

    Instance;

    /**
     * 格式化价格，强制保留2位小数
     *
     * @param price
     * @return
     */
    public String formatPrice(double price) {
        DecimalFormat df = new DecimalFormat("0.00");
        String format = "¥" + df.format(price);
        return format;
    }

    /* 1.判断当前字符是一个数字 */
    private boolean isDigitA(char ch) {
        return Character.isDigit(ch);
    }

    public String formatPrice(String strPrice) {
        try {
            double price = Double.valueOf(strPrice);
            DecimalFormat df = new DecimalFormat("0.00");
            return "¥" + df.format(price);
        } catch (NumberFormatException e) {
            String formatStr = checkCharIsNumeric(strPrice);
            if (TextUtils.isEmpty(formatStr)) {
                return "价格异常";
            } else {
                return "¥" + formatStr;
            }
        }
    }

    public Double formatPriceNotAddSymbol(String strPrice) {
        try {
            double price = Double.valueOf(strPrice);
            DecimalFormat df = new DecimalFormat("0.00");
            return Double.valueOf(df.format(price));
        } catch (NumberFormatException e) {
            String formatStr = checkCharIsNumeric(strPrice);
            if (TextUtils.isEmpty(formatStr)) {
                return 0.00;
            } else {
                return Double.valueOf(formatStr);
            }
        }
    }

    /**
     * 检查当前字符串如50-80等,中间非法数字时,取最前面的数字
     *
     * @param s
     * @return
     */
    private String checkCharIsNumeric(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (!isDigitA(c)) {
                return s.substring(0, i);
            }
        }
        return s;
    }

    /**
     * 格式化店铺距离--->保留俩位
     *
     * @param range
     * @return
     */
    public String formatRange(double range) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(range);
    }

    /**
     * 格式化价格,保留2位小数
     *
     * @param price
     * @param unitPrice 价格单位(￥，$，元)
     * @param isUnitEnd 价格单位在最前or末尾
     * @return
     */
    public String formatPrice(double price, String unitPrice, boolean isUnitEnd) {
        DecimalFormat df = new DecimalFormat("0.00");
        String format;
        if (isUnitEnd) {
            format = df.format(price) + unitPrice;
        } else {
            format = unitPrice + df.format(price);
        }
        return format;
    }

    public String formatPrice(String strPrice, String unitPrice, boolean isUnitEnd) {
        double price = Double.valueOf(strPrice);
        DecimalFormat df = new DecimalFormat("0.00");
        String format;
        if (isUnitEnd) {
            format = df.format(price) + unitPrice;
        } else {
            format = unitPrice + df.format(price);
        }
        return format;
    }

    /**
     * @return
     */
    public String formatRating(double rating) {
        try {
            DecimalFormat df = new DecimalFormat("0.0");
            return df.format(rating);
        } catch (NumberFormatException e) {
            return "0.00";
        }
    }

    public String formatPriceSymbols(String strPrice) {
        try {
            double price = Double.valueOf(strPrice);
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(price);
        } catch (NumberFormatException e) {
            return "0.00";
        }
    }

    /**
     * 格式化评分
     *
     * @param rating
     * @return
     */
    public float formatRating(float rating) {
        float rater;
        try {
            int round = Math.round(rating * 10);
            rater = round / 10f;
            return rater;
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    /**
     * 校验身份证号码
     *
     * @param idCardNumber
     * @return
     */
    public boolean isIdCardNumber(String idCardNumber) {
        Pattern p = Pattern.compile("/^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$/");
        Matcher m = p.matcher(idCardNumber);
        boolean matches = m.matches();
        return matches;

    }

    //判断手机格式是否正确
    public boolean isMobileNo(String mobiles) {
       /* Pattern p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(18[0-9])|(17[6-8]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();*/


        if (!TextUtils.isEmpty(mobiles) && mobiles.startsWith("1")) {

            if (isNumeric(mobiles)) {

                if (mobiles.length() == 11) {
                    return true;

                }
            }
        }
        return false;
    }

    //判断是否全是数字
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 格式化数字， 以万为单位 保留以为小数
     * @param num
     * @return
     */
    public String formatNumber(int num){
        if (num >= 10000) {
            float i = num / 10000f;
            DecimalFormat df = new DecimalFormat("0.0");
            String format = df.format(i);
            return format + "万";
        } else {
            return num + "";
        }

    }

    /**
         手机号开头集合
         176，177，178,
         180，181，182,183,184,185，186，187,188。，189。
         145，147
         130，131，132，133，134,135,136,137, 138,139
         150,151, 152,153，155，156，157,158,159,
     */
    public final static String PHONE_PATTERN = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";;

    public boolean checkPhoneNumber(String mobile) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(mobile);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
}
