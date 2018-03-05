package com.drops.waterdrop.model;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by dengxiaolei on 2017/6/16.
 */
public class CityJsonBean implements IPickerViewData {
    private String name;
    private String value;
    private String parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
