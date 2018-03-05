package com.netease.nim.uikit.model;

import java.util.List;

/**
 * Created by dengxiaolei on 2017/6/15.
 */

public class FriendDetailEntity extends BaseResultEntity {
    /**
     * attentionDropSize : 2
     * collectTipSize : 2
     * createDropSize : 17
     * createTipSize : 21
     * idCode : 6674957
     * identifies : [{"identifyName":"塘主","level":5,"type":1,"uid":14967746218421},{"identifyName":"贴主","level":4,"type":1,"uid":14967746218421}]
     * level : 5
     * neteaseAccid : yuanshi_14967746218421
     * neteaseToken : 71af25bbefd1bace844673a633fffdba
     * nickName : 方烈
     * photo : http://v1.qzone.cc/avatar/201408/20/11/43/53f419541c31a667.jpg
     * relationStatus : 0
     * sameFriendNum : 0
     * sex : 1
     * starFlag : 1
     * uid : 14967746218421
     * userDesc : 描述描述
     */

    private int attentionDropSize;
    private int collectTipSize;
    private int createDropSize;
    private int createTipSize;
    private int idCode;
    private int level;
    private String neteaseAccid;
    private String neteaseToken;
    private String nickName;
    private String photo;
    private int relationStatus;
    private int sameFriendNum;
    private int sex;
    private int starFlag;
    private long uid;
    private String userDesc;
    private List<IdentifiesBean> identifies;

    public int getAttentionDropSize() {
        return attentionDropSize;
    }

    public void setAttentionDropSize(int attentionDropSize) {
        this.attentionDropSize = attentionDropSize;
    }

    public int getCollectTipSize() {
        return collectTipSize;
    }

    public void setCollectTipSize(int collectTipSize) {
        this.collectTipSize = collectTipSize;
    }

    public int getCreateDropSize() {
        return createDropSize;
    }

    public void setCreateDropSize(int createDropSize) {
        this.createDropSize = createDropSize;
    }

    public int getCreateTipSize() {
        return createTipSize;
    }

    public void setCreateTipSize(int createTipSize) {
        this.createTipSize = createTipSize;
    }

    public int getIdCode() {
        return idCode;
    }

    public void setIdCode(int idCode) {
        this.idCode = idCode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(int relationStatus) {
        this.relationStatus = relationStatus;
    }

    public int getSameFriendNum() {
        return sameFriendNum;
    }

    public void setSameFriendNum(int sameFriendNum) {
        this.sameFriendNum = sameFriendNum;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStarFlag() {
        return starFlag;
    }

    public void setStarFlag(int starFlag) {
        this.starFlag = starFlag;
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

    public List<IdentifiesBean> getIdentifies() {
        return identifies;
    }

    public void setIdentifies(List<IdentifiesBean> identifies) {
        this.identifies = identifies;
    }

    public static class IdentifiesBean {
        /**
         * identifyName : 塘主
         * level : 5
         * type : 1
         * uid : 14967746218421
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


/*
    *//**
     * dropTips : [{"categoryId":-1,"collectShowStatus":1,"collectStatus":0,"cover":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1750734146,1448049649&fm=80&w=179&h=119&img.JPEG","createTime":"2017-06-08","createUid":14967432761081,"creator":{"idCode":9370781,"mobile":"13816882342","neteaseAccid":"yuanshi_14973240537431","neteaseToken":"d5945e43c00f48a1e86b5fbd2619b7f6","nickName":"天啦噜？","photo":"http://oogijmhwg.bkt.clouddn.com/823a9785003ae6f06a4b864ac7601120.png","uid":14973240537431,"userDesc":"你好😊"},"dropId":14968253943581,"likeNum":0,"likeStatus":0,"photoNum":0,"showType":1,"status":99,"tipContent":"方烈content","tipId":14968663013081,"tipTitle":"方烈title"},{"categoryId":1,"collectShowStatus":1,"collectStatus":0,"createTime":"2017-06-08","createUid":14967432761081,"creator":{"idCode":9370781,"mobile":"13816882342","neteaseAccid":"yuanshi_14973240537431","neteaseToken":"d5945e43c00f48a1e86b5fbd2619b7f6","nickName":"天啦噜？","photo":"http://oogijmhwg.bkt.clouddn.com/823a9785003ae6f06a4b864ac7601120.png","uid":14973240537431,"userDesc":"你好😊"},"dropId":14968253943581,"likeNum":1,"likeStatus":0,"photoNum":2,"showType":1,"status":1,"tipContent":"方烈content","tipId":14968665446471,"tipTitle":"方烈title"},{"categoryId":1,"collectShowStatus":1,"collectStatus":0,"cover":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=867234467,881591747&fm=80&w=179&h=119&img.JPEG","createTime":"2017-06-08","createUid":14967432761081,"creator":{"idCode":9370781,"mobile":"13816882342","neteaseAccid":"yuanshi_14973240537431","neteaseToken":"d5945e43c00f48a1e86b5fbd2619b7f6","nickName":"天啦噜？","photo":"http://oogijmhwg.bkt.clouddn.com/823a9785003ae6f06a4b864ac7601120.png","uid":14973240537431,"userDesc":"你好😊"},"dropId":14968253943581,"likeNum":2,"likeStatus":0,"photoNum":5,"showType":1,"status":1,"tipContent":"方烈content","tipId":14968677743511,"tipTitle":"方烈title"},{"categoryId":1,"collectShowStatus":1,"collectStatus":0,"cover":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=867234467,881591747&fm=80&w=179&h=119&img.JPEG","createTime":"2017-06-08","createUid":14967432761081,"creator":{"idCode":9370781,"mobile":"13816882342","neteaseAccid":"yuanshi_14973240537431","neteaseToken":"d5945e43c00f48a1e86b5fbd2619b7f6","nickName":"天啦噜？","photo":"http://oogijmhwg.bkt.clouddn.com/823a9785003ae6f06a4b864ac7601120.png","uid":14973240537431,"userDesc":"你好😊"},"dropId":14968253943581,"likeNum":3,"likeStatus":0,"photoNum":5,"showType":1,"status":1,"tipContent":"方烈content","tipId":14968679089901,"tipTitle":"方烈title"}]
     * drops : [{"attentionNum":0,"attentionShowStatus":1,"attentionStatus":0,"categoryId":1,"categoryName":"","createUid":14967746218421,"dropCode":"98516","dropDesc":"爱藏在隐蔽的夹缝里，你用心找就会发现。","dropId":14992389245041,"dropName":"水塘1","dropPhoto":"http://7xp761.com1.z0.glb.clouddn.com/send-10.png","groupUserNum":1,"joinStatus":-1,"likeNum":0,"neteaseRoomId":"","starFlag":1,"status":1,"tipNum":0},{"attentionNum":0,"attentionShowStatus":1,"attentionStatus":0,"categoryId":1,"categoryName":"","createUid":14967746218421,"dropCode":"94256606","dropDesc":"转眼扑蝶的岁月都过去，只剩看山的岁月了。","dropId":14992389638051,"dropName":"水塘1","dropPhoto":"http://7xp761.com1.z0.glb.clouddn.com/send-171.png","groupUserNum":1,"joinStatus":-1,"likeNum":0,"neteaseRoomId":"","starFlag":1,"status":1,"tipNum":0},{"attentionNum":0,"attentionShowStatus":1,"attentionStatus":0,"categoryId":2,"categoryName":"","createUid":14967746218421,"dropCode":"92938892","dropDesc":"绿酒初尝人易醉，一枕小窗浓睡。","dropId":14992389951871,"dropName":"水塘1","dropPhoto":"http://7xp761.com1.z0.glb.clouddn.com/send-172.png","groupUserNum":1,"joinStatus":-1,"likeNum":0,"neteaseRoomId":"","starFlag":1,"status":1,"tipNum":0},{"attentionNum":0,"attentionShowStatus":1,"attentionStatus":0,"categoryId":2,"categoryName":"df","createUid":14967746218421,"dropCode":"48741115","dropDesc":"岁月。","dropId":14992390207971,"dropName":"水塘1","dropPhoto":"http://7xp761.com1.z0.glb.clouddn.com/send-173.png","groupUserNum":1,"joinStatus":-1,"likeNum":0,"neteaseRoomId":"","starFlag":1,"status":1,"tipNum":0}]
     * goods : []
     * idCode : 9370781
     * level : 5
     * neteaseAccid : yuanshi_14973240537431
     * neteaseToken : d5945e43c00f48a1e86b5fbd2619b7f6
     * nickName : 天啦噜？
     * photo : http:120.png
     * relationStatus : 0
     * sameFriendNum : 0
     * sex : 2
     * starFlag : 0
     * uid : 14973240537431
     * userDesc : 你好
     *//*

    private int idCode;
    private int level;
    private String neteaseAccid;
    private String neteaseToken;
    private String nickName;
    private String photo;
    private int relationStatus;
    private int sameFriendNum;
    private int sex;
    private int starFlag;
    private long uid;
    private String userDesc;
    private List<DropTipsBean> dropTips;
    private List<DropsBean> drops;
    private List<?> goods;

    public int getIdCode() {
        return idCode;
    }

    public void setIdCode(int idCode) {
        this.idCode = idCode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(int relationStatus) {
        this.relationStatus = relationStatus;
    }

    public int getSameFriendNum() {
        return sameFriendNum;
    }

    public void setSameFriendNum(int sameFriendNum) {
        this.sameFriendNum = sameFriendNum;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStarFlag() {
        return starFlag;
    }

    public void setStarFlag(int starFlag) {
        this.starFlag = starFlag;
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

    public List<DropTipsBean> getDropTips() {
        return dropTips;
    }

    public void setDropTips(List<DropTipsBean> dropTips) {
        this.dropTips = dropTips;
    }

    public List<DropsBean> getDrops() {
        return drops;
    }

    public void setDrops(List<DropsBean> drops) {
        this.drops = drops;
    }

    public List<?> getGoods() {
        return goods;
    }

    public void setGoods(List<?> goods) {
        this.goods = goods;
    }

    public static class DropTipsBean {
        *//**
         * categoryId : -1
         * collectShowStatus : 1
         * collectStatus : 0
         * cover : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1750734146,1448049649&fm=80&w=179&h=119&img.JPEG
         * createTime : 2017-06-08
         * createUid : 14967432761081
         * creator : {"idCode":9370781,"mobile":"13816882342","neteaseAccid":"yuanshi_14973240537431","neteaseToken":"d5945e43c00f48a1e86b5fbd2619b7f6","nickName":"天啦噜？","photo":"http://oogijmhwg.bkt.clouddn.com/823a9785003ae6f06a4b864ac7601120.png","uid":14973240537431,"userDesc":"你好😊"}
         * dropId : 14968253943581
         * likeNum : 0
         * likeStatus : 0
         * photoNum : 0
         * showType : 1
         * status : 99
         * tipContent : 方烈content
         * tipId : 14968663013081
         * tipTitle : 方烈title
         *//*

        private int categoryId;
        private int collectShowStatus;
        private int collectStatus;
        private String cover;
        private String createTime;
        private long createUid;
        private CreatorBean creator;
        private long dropId;
        private int likeNum;
        private int likeStatus;
        private int photoNum;
        private int showType;
        private int status;
        private String tipContent;
        private long tipId;
        private String tipTitle;

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getCollectShowStatus() {
            return collectShowStatus;
        }

        public void setCollectShowStatus(int collectShowStatus) {
            this.collectShowStatus = collectShowStatus;
        }

        public int getCollectStatus() {
            return collectStatus;
        }

        public void setCollectStatus(int collectStatus) {
            this.collectStatus = collectStatus;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public long getCreateUid() {
            return createUid;
        }

        public void setCreateUid(long createUid) {
            this.createUid = createUid;
        }

        public CreatorBean getCreator() {
            return creator;
        }

        public void setCreator(CreatorBean creator) {
            this.creator = creator;
        }

        public long getDropId() {
            return dropId;
        }

        public void setDropId(long dropId) {
            this.dropId = dropId;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public int getLikeStatus() {
            return likeStatus;
        }

        public void setLikeStatus(int likeStatus) {
            this.likeStatus = likeStatus;
        }

        public int getPhotoNum() {
            return photoNum;
        }

        public void setPhotoNum(int photoNum) {
            this.photoNum = photoNum;
        }

        public int getShowType() {
            return showType;
        }

        public void setShowType(int showType) {
            this.showType = showType;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTipContent() {
            return tipContent;
        }

        public void setTipContent(String tipContent) {
            this.tipContent = tipContent;
        }

        public long getTipId() {
            return tipId;
        }

        public void setTipId(long tipId) {
            this.tipId = tipId;
        }

        public String getTipTitle() {
            return tipTitle;
        }

        public void setTipTitle(String tipTitle) {
            this.tipTitle = tipTitle;
        }

        public static class CreatorBean {
            *//**
             * idCode : 9370781
             * mobile : 13816882342
             * neteaseAccid : yuanshi_14973240537431
             * neteaseToken : d5945e43c00f48a1e86b5fbd2619b7f6
             * nickName : 天啦噜？
             * photo : http://oogijmhwg.bkt.clouddn.com/823a9785003ae6f06a4b864ac7601120.png
             * uid : 14973240537431
             * userDesc : 你好😊
             *//*

            private int idCode;
            private String mobile;
            private String neteaseAccid;
            private String neteaseToken;
            private String nickName;
            private String photo;
            private long uid;
            private String userDesc;

            public int getIdCode() {
                return idCode;
            }

            public void setIdCode(int idCode) {
                this.idCode = idCode;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
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
        }
    }

    public static class DropsBean {
        *//**
         * attentionNum : 0
         * attentionShowStatus : 1
         * attentionStatus : 0
         * categoryId : 1
         * categoryName :
         * createUid : 14967746218421
         * dropCode : 98516
         * dropDesc : 爱藏在隐蔽的夹缝里，你用心找就会发现。
         * dropId : 14992389245041
         * dropName : 水塘1
         * dropPhoto : http://7xp761.com1.z0.glb.clouddn.com/send-10.png
         * groupUserNum : 1
         * joinStatus : -1
         * likeNum : 0
         * neteaseRoomId :
         * starFlag : 1
         * status : 1
         * tipNum : 0
         *//*

        private int attentionNum;
        private int attentionShowStatus;
        private int attentionStatus;
        private int categoryId;
        private String categoryName;
        private long createUid;
        private String dropCode;
        private String dropDesc;
        private long dropId;
        private String dropName;
        private String dropPhoto;
        private int groupUserNum;
        private int joinStatus;
        private int likeNum;
        private String neteaseRoomId;
        private int starFlag;
        private int status;
        private int tipNum;

        public int getAttentionNum() {
            return attentionNum;
        }

        public void setAttentionNum(int attentionNum) {
            this.attentionNum = attentionNum;
        }

        public int getAttentionShowStatus() {
            return attentionShowStatus;
        }

        public void setAttentionShowStatus(int attentionShowStatus) {
            this.attentionShowStatus = attentionShowStatus;
        }

        public int getAttentionStatus() {
            return attentionStatus;
        }

        public void setAttentionStatus(int attentionStatus) {
            this.attentionStatus = attentionStatus;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public long getCreateUid() {
            return createUid;
        }

        public void setCreateUid(long createUid) {
            this.createUid = createUid;
        }

        public String getDropCode() {
            return dropCode;
        }

        public void setDropCode(String dropCode) {
            this.dropCode = dropCode;
        }

        public String getDropDesc() {
            return dropDesc;
        }

        public void setDropDesc(String dropDesc) {
            this.dropDesc = dropDesc;
        }

        public long getDropId() {
            return dropId;
        }

        public void setDropId(long dropId) {
            this.dropId = dropId;
        }

        public String getDropName() {
            return dropName;
        }

        public void setDropName(String dropName) {
            this.dropName = dropName;
        }

        public String getDropPhoto() {
            return dropPhoto;
        }

        public void setDropPhoto(String dropPhoto) {
            this.dropPhoto = dropPhoto;
        }

        public int getGroupUserNum() {
            return groupUserNum;
        }

        public void setGroupUserNum(int groupUserNum) {
            this.groupUserNum = groupUserNum;
        }

        public int getJoinStatus() {
            return joinStatus;
        }

        public void setJoinStatus(int joinStatus) {
            this.joinStatus = joinStatus;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public String getNeteaseRoomId() {
            return neteaseRoomId;
        }

        public void setNeteaseRoomId(String neteaseRoomId) {
            this.neteaseRoomId = neteaseRoomId;
        }

        public int getStarFlag() {
            return starFlag;
        }

        public void setStarFlag(int starFlag) {
            this.starFlag = starFlag;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTipNum() {
            return tipNum;
        }

        public void setTipNum(int tipNum) {
            this.tipNum = tipNum;
        }
    }*/
}
