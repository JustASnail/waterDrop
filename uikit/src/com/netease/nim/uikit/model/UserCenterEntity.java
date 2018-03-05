package com.netease.nim.uikit.model;

import com.netease.nim.uikit.cache.MyUserCache;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Smile on 2017/7/7.
 */

public class UserCenterEntity implements Serializable {

    public boolean isCommonRole;
    public boolean isTangZhuRole;
    public boolean isTieZhuRole;
    public boolean isSuperTangZhuRole;
    public boolean isSupplierRole;
    public boolean isMemberRole;

    public int levelTangZhu;
    public int levelTieZhu;
    public int levelSuperTangZhu;

    public void parseData(){
        //清空存到本地的数据
        MyUserCache.saveVipOrSuply(-1);
        MyUserCache.saveUserIdentify("");
        MyUserCache.saveUserRemainMoney("");

        if (identifies == null || identifies.size() == 0){
            isCommonRole = true;
            MyUserCache.saveUserIdentify("");
            return;
        }

        StringBuilder identifyName = new StringBuilder("");
        for (IdentifiesBean bean : identifies){
            switch (bean.getType()){
                case 1:
                    isTangZhuRole = true;
                    levelTangZhu = bean.getLevel();
                    identifyName.append("/" + bean.getIdentifyName() + "Lv." + levelTangZhu);
                    break;
                case 2:
                    isTieZhuRole = true;
                    levelTieZhu = bean.getLevel();
                    identifyName.append("/" + bean.getIdentifyName() + "Lv." + levelTieZhu);
                    break;
                case 3:
                    isSuperTangZhuRole = true;
                    levelSuperTangZhu = bean.getLevel();
                    identifyName.append("/" + bean.getIdentifyName() + "Lv." + levelSuperTangZhu);
                    break;
                case 4:
                    isSupplierRole = true;
                    MyUserCache.saveVipOrSuply(1);
                    break;
                case 5:
                    isMemberRole = true;
                    //虽然理论上不会同时出现 ，但是万一VIP 供应商同时出现，则设为供应商
                    MyUserCache.saveVipOrSuply(MyUserCache.getVipOrSuply() == 1 ? 1 : 2);
                    break;
            }
        }

        if (identifyName.length() >= 1) {
            String identify = identifyName.substring(1);
            MyUserCache.saveUserIdentify(identify);
        } else {
            MyUserCache.saveUserIdentify("");
        }

        //是vip的时候 存VIP的钱
        if (MyUserCache.getVipOrSuply() == 2) {
            MyUserCache.saveUserRemainMoney(availableMemberMoney);
        } else {
            MyUserCache.saveUserRemainMoney(availableMoney);
        }
    }



    /**
     * beans : 0
     * birthday : 1994-10-22
     * footPrintNum : 3
     * idCode : 6674957
     * location :
     * loginType : 1
     * mobile : 13588706339
     * nationCode : 86
     * neteaseAccid : yuanshi_14967746218421
     * neteaseToken : 71af25bbefd1bace844673a633fffdba
     * nickName : 方烈
     * photo : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=875316266,1428855355&fm=80&w=179&h=119&img.JPEG
     * readyDeliveryNum : 0
     * readyFinishNum : 0
     * readyPayNum : 2
     * readyReceiveNum : 0
     * readyRestoreNum : 0
     * registerStatus : 1
     * role : 1
     * sex : 1
     * status : 1
     * uid : 14967746218421
     * userDesc : 描述描述描述描述描
     * userToken :
     * identifies : [{"identifyName":"塘主","level":5,"type":1,"uid":14967432761081},{"identifyName":"贴主","level":3,"type":2,"uid":14967432761081}]
     * userType : 1
     */

    private String availableMemberMoney;
    private String totalMoney;
    private String availableMoney;
    private String totalMemberMoney;
    private String availavleMemberMoney;
    private String memberDate;
    private String beans;
    private String birthday;
    private int footPrintNum;
    private int idCode;
    private String location;
    private int loginType;
    private String mobile;
    private String nationCode;
    private String neteaseAccid;
    private String neteaseToken;
    private String nickName;
    private String photo;
    private int readyDeliveryNum;
    private int readyFinishNum;
    private int readyPayNum;
    private int readyReceiveNum;
    private int readyRestoreNum;
    private int registerStatus;
    private int role;
    private int sex;
    private int status;
    private long uid;
    private String userDesc;
    private String userToken;
    private int userType;
    private List<IdentifiesBean> identifies;

    public String getAvailavleMemberMoney() {
        return availavleMemberMoney;
    }

    public String getBeans() {
        return beans;
    }

    public void setBeans(String beans) {
        this.beans = beans;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getFootPrintNum() {
        return footPrintNum;
    }

    public void setFootPrintNum(int footPrintNum) {
        this.footPrintNum = footPrintNum;
    }

    public int getIdCode() {
        return idCode;
    }

    public void setIdCode(int idCode) {
        this.idCode = idCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getNeteaseAccid() {
        return neteaseAccid;
    }

    public void setNeteaseAccid(String neteaseAccid) {
        this.neteaseAccid = neteaseAccid;
    }

    public String getNeteaseToken() {
        return neteaseToken;
    }

    public void setNeteaseToken(String neteaseToken) {
        this.neteaseToken = neteaseToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getReadyDeliveryNum() {
        return readyDeliveryNum;
    }

    public void setReadyDeliveryNum(int readyDeliveryNum) {
        this.readyDeliveryNum = readyDeliveryNum;
    }

    public int getReadyFinishNum() {
        return readyFinishNum;
    }

    public void setReadyFinishNum(int readyFinishNum) {
        this.readyFinishNum = readyFinishNum;
    }

    public int getReadyPayNum() {
        return readyPayNum;
    }

    public void setReadyPayNum(int readyPayNum) {
        this.readyPayNum = readyPayNum;
    }

    public int getReadyReceiveNum() {
        return readyReceiveNum;
    }

    public void setReadyReceiveNum(int readyReceiveNum) {
        this.readyReceiveNum = readyReceiveNum;
    }

    public int getReadyRestoreNum() {
        return readyRestoreNum;
    }

    public void setReadyRestoreNum(int readyRestoreNum) {
        this.readyRestoreNum = readyRestoreNum;
    }

    public int getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(int registerStatus) {
        this.registerStatus = registerStatus;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public List<IdentifiesBean> getIdentifies() {
        return identifies;
    }

    public void setIdentifies(List<IdentifiesBean> identifies) {
        this.identifies = identifies;
    }

    public String getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(String availableMoney) {
        this.availableMoney = availableMoney;
    }

    public String getAvailableMemberMoney() {
        return availableMemberMoney;
    }

    public void setAvailableMemberMoney(String availableMemberMoney) {
        this.availableMemberMoney = availableMemberMoney;
    }

    public String getMemberDate() {
        return memberDate;
    }

    public void setMemberDate(String memberDate) {
        this.memberDate = memberDate;
    }

    public String getTotalMemberMoney() {
        return totalMemberMoney;
    }

    public void setTotalMemberMoney(String totalMemberMoney) {
        this.totalMemberMoney = totalMemberMoney;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public static class IdentifiesBean implements Serializable{
        /**
         * identifyName : 塘主
         * level : 5
         * type : 1
         * uid : 14967432761081
         */

        private String identifyName;
        private int level;
        private int type;
        private long uid;

        public String getIdentifyName() {
            return identifyName;
        }

        public void setIdentifyName(String identifyName) {
            this.identifyName = identifyName;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }
    }
}
