package com.drops.waterdrop.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.drops.waterdrop.model.CityJsonBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/4.
 */

public class AddressPickerViewUtil {

    private ArrayList<CityJsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<CityJsonBean>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<CityJsonBean>>> options3Items = new ArrayList<>();

    private boolean hasDistrict;

    private static AddressPickerViewUtil instance;  //静态变量
    private AddressPickerViewUtil (Context context, boolean hasDistrict){
        this.hasDistrict = hasDistrict;
        initCityJsonData(context);
    }  //私有构造函数

    public static AddressPickerViewUtil getInstance(Context context){
        return getInstance(context, true);
    }

    public static AddressPickerViewUtil getInstance(Context context, boolean hasDistrict){
        return new AddressPickerViewUtil(context, hasDistrict);
    }

    // TODO: 2017/8/31 暂时取消单例模式,所以，在用的时候，最好在当前页面缓存本对象
    /*public static AddressPickerViewUtil getInstance(Context context, boolean hasDistrict) {
        if (instance == null) {  //第一层校验
            synchronized (AddressPickerViewUtil.class) {
                if (instance == null) {  //第二层校验
                    instance = new AddressPickerViewUtil(context, hasDistrict);
                }
            }
        }
        return instance;
    }*/

    public interface OnPickerListener{
        void onPickerListener(String prov, String city, String district);
    }

    public void ShowPickerView(final OnPickerListener listener, Context context) {// 弹出选择器
        if (options1Items.size() <= 0) {
            initCityJsonData(context);
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String prov = options1Items.get(options1).getPickerViewText();
                String city = options2Items.get(options1).get(options2).getPickerViewText();
                String district = null;

                if (hasDistrict){
                    district = options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                }
//                getView().onCityPicked(tx);
                listener.onPickerListener(prov, city, district);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        if (hasDistrict){
            pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        } else {
            pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        }
        pvOptions.show();
    }


    public void initCityJsonData(Context context) {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = getJson(context, "province.json");//获取assets目录下的json文件数据

        Gson gson = new Gson();
        List<CityJsonBean> jsonBean = gson.fromJson(JsonData, new TypeToken<List<CityJsonBean>>() {
        }.getType());

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */

        for (CityJsonBean cityJsonBean : jsonBean) {
            if (TextUtils.isEmpty(cityJsonBean.getParent())) {
                options1Items.add(cityJsonBean);
            }
        }

        for (CityJsonBean shi : options1Items) {//遍历省份
            ArrayList<CityJsonBean> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<CityJsonBean>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (CityJsonBean cityJsonBean : jsonBean) {
                if (cityJsonBean.getParent() != null && TextUtils.equals(cityJsonBean.getParent(), shi.getValue())) {
                    CityList.add(cityJsonBean);

                    if (!hasDistrict)
                        continue;

                    ArrayList<CityJsonBean> areaList = new ArrayList<>();
                    for (CityJsonBean area : jsonBean) {
                        if (area.getParent() != null && TextUtils.equals(cityJsonBean.getValue(), area.getParent())) {
                            if (!TextUtils.equals("--", area.getName())) {
                                areaList.add(area);
                            }
                        }

                    }
                    Province_AreaList.add(areaList);


                }


            }
            options2Items.add(CityList);
            options3Items.add(Province_AreaList);
        }
    }

    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
