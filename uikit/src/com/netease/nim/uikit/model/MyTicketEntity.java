package com.netease.nim.uikit.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Smile on 2017/7/4.
 */

public class MyTicketEntity implements Serializable{

        private int pageSize;
        private int totalSize;
        private List<ResultsBean> results;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalSize() {
            return totalSize;
        }

        public void setTotalSize(int totalSize) {
            this.totalSize = totalSize;
        }

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

        public static class ResultsBean implements Serializable{
            /**
             * activityId : 666673
             * activityPicBig : http://cdn001.waterdrop.xin/mtv%E7%A5%A8.jpg
             * activityTitle : 2017MTV全球华语音乐盛典
             * createTime : 1496320594
             * desc : 您从2017-04-25的活动中取得1张vip票
             * headimgurl : http://wx.qlogo.cn/mmopen/85ICPHqZNYPvKkmAaVRSM9MmjRB06ibUkia84gJTxvtHrEdLVf3ialVUE0iaicibMFFOajncKAgmG53L7dLolAyxPGEKrCt2qy8cz3/0
             * id : 1
             * isReceived : 0
             * joinId : 1
             * nickname : 镭珩汽车贷款__.金  亮
             * orderId : 0
             * phone :
             * ticketId : 1
             * ticketLevel : 0
             * ticketName : vip票
             * ticketNum : 1
             * ticketRank : 1
             * updateTime : 0
             * uuid : 590022c611e5b01d2b0bff43
             * address : {"id_number":"142201197610166465","id_number_photo_back":"http://cdn001.waterdrop.xin/1497508962.jpg","id_number_photo_front":"http://cdn001.waterdrop.xin/1497508951.jpg","name":"班慧萍","userArea":"天津市 市辖区 北辰区","userAreaId":["120000","120100","120113"],"userFullAddress":"汀江西路1号天士力花园-金悦花园2-2104"}
             */

            private String activityId;
            private String activityPicBig;
            private String activityTitle;
            private String createTime;
            private String desc;
            private String headimgurl;
            private String id;
            private String isReceived;
            private String joinId;
            private String nickname;
            private String orderId;
            private String phone;
            private String ticketId;
            private String ticketLevel;
            private String ticketName;
            private String ticketNum;
            private String ticketRank;
            private String updateTime;
            private String uuid;
            private AddressBean address;

            public String getActivityId() {
                return activityId;
            }

            public void setActivityId(String activityId) {
                this.activityId = activityId;
            }

            public String getActivityPicBig() {
                return activityPicBig;
            }

            public void setActivityPicBig(String activityPicBig) {
                this.activityPicBig = activityPicBig;
            }

            public String getActivityTitle() {
                return activityTitle;
            }

            public void setActivityTitle(String activityTitle) {
                this.activityTitle = activityTitle;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getHeadimgurl() {
                return headimgurl;
            }

            public void setHeadimgurl(String headimgurl) {
                this.headimgurl = headimgurl;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIsReceived() {
                return isReceived;
            }

            public void setIsReceived(String isReceived) {
                this.isReceived = isReceived;
            }

            public String getJoinId() {
                return joinId;
            }

            public void setJoinId(String joinId) {
                this.joinId = joinId;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getTicketId() {
                return ticketId;
            }

            public void setTicketId(String ticketId) {
                this.ticketId = ticketId;
            }

            public String getTicketLevel() {
                return ticketLevel;
            }

            public void setTicketLevel(String ticketLevel) {
                this.ticketLevel = ticketLevel;
            }

            public String getTicketName() {
                return ticketName;
            }

            public void setTicketName(String ticketName) {
                this.ticketName = ticketName;
            }

            public String getTicketNum() {
                return ticketNum;
            }

            public void setTicketNum(String ticketNum) {
                this.ticketNum = ticketNum;
            }

            public String getTicketRank() {
                return ticketRank;
            }

            public void setTicketRank(String ticketRank) {
                this.ticketRank = ticketRank;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public AddressBean getAddress() {
                return address;
            }

            public void setAddress(AddressBean address) {
                this.address = address;
            }

            public static class AddressBean implements Serializable{
                /**
                 * id_number : 142201197610166465
                 * id_number_photo_back : http://cdn001.waterdrop.xin/1497508962.jpg
                 * id_number_photo_front : http://cdn001.waterdrop.xin/1497508951.jpg
                 * name : 班慧萍
                 * userArea : 天津市 市辖区 北辰区
                 * userAreaId : ["120000","120100","120113"]
                 * userFullAddress : 汀江西路1号天士力花园-金悦花园2-2104
                 */

                private String id_number;
                private String id_number_photo_back;
                private String id_number_photo_front;
                private String name;
                private String userArea;
                private String userFullAddress;
                private List<String> userAreaId;

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

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUserArea() {
                    return userArea;
                }

                public void setUserArea(String userArea) {
                    this.userArea = userArea;
                }

                public String getUserFullAddress() {
                    return userFullAddress;
                }

                public void setUserFullAddress(String userFullAddress) {
                    this.userFullAddress = userFullAddress;
                }

                public List<String> getUserAreaId() {
                    return userAreaId;
                }

                public void setUserAreaId(List<String> userAreaId) {
                    this.userAreaId = userAreaId;
                }
            }
    }
}
