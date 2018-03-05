package com.netease.nim.uikit.model;

import java.io.Serializable;

/**
 * Created by dengxiaolei on 2017/6/12.
 */

public class BaseRequestBody implements Serializable {
    private String mobile;

    private String password;

    private String new_password;

    private String open_id;
    private String login_type;

    private String sms_code;
    private long uid;
    private String user_token;
    private String friend_mobile;
    private String note;
    private long f_uid;
    private long friend_uid;
    private long drop_id;

    private String mobiles;
    private String size;
    private int type;
    private String photo;
    private String nick_name;
    private String user_desc;
    private String collect_id;
    private String name;
    private String prov;
    private String city;
    private String district;
    private String detail;
    private String default_flag;
    private long address_id;
    private String idcard_front;
    private String idcard_back;
    private int sex;
    private int category_id;
    private int category_type;
    private long tip_id;
    private int order_status;

    private String search_word;
    private String search_start;
    private String good_id;
    private String collect_id_json;
    private int footprint_type;
    private String ticket_id;
    private String id_number;
    private String id_number_photo_back;
    private String id_number_photo_front;
    private String distinct;
    private String full_address;
    private Long order_id;
    private Long join_uid;
    private int score;
    private String phone;
    private String pre_browser_time;

    private String pay_info;
    private String bill_company;
    private String bill_tfn;
    private String good_info_json;
    private int need_bill;
    private int bill_type;

    private String category_like_json;
    private String union_id;


    public String getUnion_id() {
        return union_id;
    }

    public void setUnion_id(String union_id) {
        this.union_id = union_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSms_code() {
        return sms_code;
    }

    public void setSms_code(String sms_code) {
        this.sms_code = sms_code;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public String getFriend_mobile() {
        return friend_mobile;
    }

    public void setFriend_mobile(String friend_mobile) {
        this.friend_mobile = friend_mobile;
    }

    public long getF_uid() {
        return f_uid;
    }

    public void setF_uid(long f_uid) {
        this.f_uid = f_uid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getUser_desc() {
        return user_desc;
    }

    public void setUser_desc(String user_desc) {
        this.user_desc = user_desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getFriend_uid() {
        return friend_uid;
    }

    public void setFriend_uid(long friend_uid) {
        this.friend_uid = friend_uid;
    }

    public long getDrop_id() {
        return drop_id;
    }

    public void setDrop_id(long drop_id) {
        this.drop_id = drop_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public long getTip_id() {
        return tip_id;
    }

    public void setTip_id(long tip_id) {
        this.tip_id = tip_id;
    }

    public String getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(String collect_id) {
        this.collect_id = collect_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDefault_flag() {
        return default_flag;
    }

    public void setDefault_flag(String default_flag) {
        this.default_flag = default_flag;
    }

    public String getIdcard_front() {
        return idcard_front;
    }

    public void setIdcard_front(String idcard_front) {
        this.idcard_front = idcard_front;
    }

    public String getIdcard_back() {
        return idcard_back;
    }

    public void setIdcard_back(String idcard_back) {
        this.idcard_back = idcard_back;
    }

    public long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(long address_id) {
        this.address_id = address_id;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getCollect_id_json() {
        return collect_id_json;
    }

    public void setCollect_id_json(String collect_id_json) {
        this.collect_id_json = collect_id_json;
    }

    public int getFootprint_type() {
        return footprint_type;
    }

    public void setFootprint_type(int footprint_type) {
        this.footprint_type = footprint_type;
    }


    public String getSearch_word() {
        return search_word;
    }

    public void setSearch_word(String search_word) {
        this.search_word = search_word;
    }

    public String getSearch_start() {
        return search_start;
    }

    public void setSearch_start(String search_start) {
        this.search_start = search_start;
    }

    public String getGood_id() {
        return good_id;
    }

    public void setGood_id(String good_id) {
        this.good_id = good_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getId_number_photo_back() {
        return id_number_photo_back;
    }

    public void setId_number_photo_back(String id_number_photo_back) {
        this.id_number_photo_back = id_number_photo_back;
    }

    public String getId_number_photo_front() {
        return id_number_photo_front;
    }

    public void setId_number_photo_front(String id_number_photo_front) {
        this.id_number_photo_front = id_number_photo_front;
    }

    public String getDistinct() {
        return distinct;
    }

    public void setDistinct(String distinct) {
        this.distinct = distinct;
    }

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getPay_info() {
        return pay_info;
    }

    public void setPay_info(String pay_info) {
        this.pay_info = pay_info;
    }

    public String getBill_company() {
        return bill_company;
    }

    public void setBill_company(String bill_company) {
        this.bill_company = bill_company;
    }

    public String getBill_tfn() {
        return bill_tfn;
    }

    public void setBill_tfn(String bill_tfn) {
        this.bill_tfn = bill_tfn;
    }

    public String getGood_info_json() {
        return good_info_json;
    }

    public void setGood_info_json(String good_info_json) {
        this.good_info_json = good_info_json;
    }

    public int getNeed_bill() {
        return need_bill;
    }

    public void setNeed_bill(int need_bill) {
        this.need_bill = need_bill;
    }

    public int getBill_type() {
        return bill_type;
    }

    public void setBill_type(int bill_type) {
        this.bill_type = bill_type;
    }

    public String getCategory_like_json() {
        return category_like_json;
    }

    public void setCategory_like_json(String category_like_json) {
        this.category_like_json = category_like_json;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPre_browser_time() {
        return pre_browser_time;
    }

    public void setPre_browser_time(String pre_browser_time) {
        this.pre_browser_time = pre_browser_time;
    }

    public Long getJoin_uid() {
        return join_uid;
    }

    public void setJoin_uid(Long join_uid) {
        this.join_uid = join_uid;
    }

    public int getCategory_type() {
        return category_type;
    }

    public void setCategory_type(int category_type) {
        this.category_type = category_type;
    }

    public BaseRequestBody() {}

    public BaseRequestBody(String mobile, String password, String new_password, String open_id, String login_type, String sms_code, long uid, String user_token, String friend_mobile, String note, long f_uid, long friend_uid, long drop_id, String mobiles, String size, int type, String photo, String nick_name, String user_desc, String collect_id, String name, String prov, String city, String district, String detail, String default_flag, long address_id, String idcard_front, String idcard_back, int sex, int category_id, long tip_id, int order_status, String search_word, String search_start, String good_id, String collect_id_json, int footprint_type, String ticket_id, String id_number, String id_number_photo_back, String id_number_photo_front, String distinct, String full_address, Long order_id, int score, String phone, String pre_browser_time, String pay_info, String bill_company, String bill_tfn, String good_info_json, int need_bill, int bill_type, String category_like_json) {
        this.mobile = mobile;
        this.password = password;
        this.new_password = new_password;
        this.open_id = open_id;
        this.login_type = login_type;
        this.sms_code = sms_code;
        this.uid = uid;
        this.user_token = user_token;
        this.friend_mobile = friend_mobile;
        this.note = note;
        this.f_uid = f_uid;
        this.friend_uid = friend_uid;
        this.drop_id = drop_id;
        this.mobiles = mobiles;
        this.size = size;
        this.type = type;
        this.photo = photo;
        this.nick_name = nick_name;
        this.user_desc = user_desc;
        this.collect_id = collect_id;
        this.name = name;
        this.prov = prov;
        this.city = city;
        this.district = district;
        this.detail = detail;
        this.default_flag = default_flag;
        this.address_id = address_id;
        this.idcard_front = idcard_front;
        this.idcard_back = idcard_back;
        this.sex = sex;
        this.category_id = category_id;
        this.tip_id = tip_id;
        this.order_status = order_status;
        this.search_word = search_word;
        this.search_start = search_start;
        this.good_id = good_id;
        this.collect_id_json = collect_id_json;
        this.footprint_type = footprint_type;
        this.ticket_id = ticket_id;
        this.id_number = id_number;
        this.id_number_photo_back = id_number_photo_back;
        this.id_number_photo_front = id_number_photo_front;
        this.distinct = distinct;
        this.full_address = full_address;
        this.order_id = order_id;
        this.score = score;
        this.phone = phone;
        this.pre_browser_time = pre_browser_time;
        this.pay_info = pay_info;
        this.bill_company = bill_company;
        this.bill_tfn = bill_tfn;
        this.good_info_json = good_info_json;
        this.need_bill = need_bill;
        this.bill_type = bill_type;
        this.category_like_json = category_like_json;
    }
}
