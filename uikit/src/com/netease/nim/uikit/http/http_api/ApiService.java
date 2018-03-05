package com.netease.nim.uikit.http.http_api;


import com.netease.nim.uikit.model.AccessTokenWXEntity;
import com.netease.nim.uikit.model.AccountDetaiEntity;
import com.netease.nim.uikit.model.AddFriendForUid;
import com.netease.nim.uikit.model.AddressBookFriendsEntity;
import com.netease.nim.uikit.model.AddressEntity;
import com.netease.nim.uikit.model.AppInfoEntity;
import com.netease.nim.uikit.model.BankCardListEntity;
import com.netease.nim.uikit.model.BankCardNoListEntity;
import com.netease.nim.uikit.model.BaseResponse;
import com.netease.nim.uikit.model.BrandItemEntity;
import com.netease.nim.uikit.model.BrandListEntity;
import com.netease.nim.uikit.model.BrandTagEntity;
import com.netease.nim.uikit.model.CashGiftRecordEntity;
import com.netease.nim.uikit.model.CategoryEntity;
import com.netease.nim.uikit.model.CollectionSBEntry;
import com.netease.nim.uikit.model.CollectionSTEntry;
import com.netease.nim.uikit.model.ConfirmedFriendEntity;
import com.netease.nim.uikit.model.DropDetailsEntity;
import com.netease.nim.uikit.model.FansGroupEntity;
import com.netease.nim.uikit.model.FootprintShuiTangEntity;
import com.netease.nim.uikit.model.FootprintShuiTieEntity;
import com.netease.nim.uikit.model.FriendCollectEntity;
import com.netease.nim.uikit.model.FriendDetailEntity;
import com.netease.nim.uikit.model.GiftInfoEntity;
import com.netease.nim.uikit.model.GoodsDetailEntity;
import com.netease.nim.uikit.model.GoodsScoreListEntity;
import com.netease.nim.uikit.model.HotSearchEntity;
import com.netease.nim.uikit.model.IdCardEntity;
import com.netease.nim.uikit.model.InterestEntity;
import com.netease.nim.uikit.model.JinDouEntity;
import com.netease.nim.uikit.model.LogiticsEntity;
import com.netease.nim.uikit.model.MemberActiveEntitiy;
import com.netease.nim.uikit.model.MyFriendsListEntity;
import com.netease.nim.uikit.model.MyPostEntity;
import com.netease.nim.uikit.model.MyTicketEntity;
import com.netease.nim.uikit.model.OrderDetailEntity;
import com.netease.nim.uikit.model.OrderDetailEntity2;
import com.netease.nim.uikit.model.OrderEntity;
import com.netease.nim.uikit.model.OrderStatusEntity;
import com.netease.nim.uikit.model.PoolListEntity;
import com.netease.nim.uikit.model.PostEntity;
import com.netease.nim.uikit.model.PostForFriendCollectEntity;
import com.netease.nim.uikit.model.PostListEntity;
import com.netease.nim.uikit.model.QiNiuTokensEntity;
import com.netease.nim.uikit.model.RecentMessageListEntity;
import com.netease.nim.uikit.model.RecommendFriendEntity;
import com.netease.nim.uikit.model.RecommendPoolListEntity;
import com.netease.nim.uikit.model.RegisterEntity;
import com.netease.nim.uikit.model.SearchFriendEntity;
import com.netease.nim.uikit.model.SearchPoolEntity;
import com.netease.nim.uikit.model.SearchPostEntity;
import com.netease.nim.uikit.model.SeleBankAccountEntity;
import com.netease.nim.uikit.model.StarInfoEntity;
import com.netease.nim.uikit.model.StarListEntity;
import com.netease.nim.uikit.model.StoreHomePageEntity;
import com.netease.nim.uikit.model.TipBannerEntity;
import com.netease.nim.uikit.model.UpdateUserInfoEntity;
import com.netease.nim.uikit.model.UserAgreementUrlEntity;
import com.netease.nim.uikit.model.UserCenterEntity;
import com.netease.nim.uikit.model.UserInfoEntity;
import com.netease.nim.uikit.model.VerifyOpenId;
import com.netease.nim.uikit.model.VerifyPhoneEntity;
import com.netease.nim.uikit.model.VipAreaEntity;
import com.netease.nim.uikit.model.WXUserInfoEntity;
import com.netease.nim.uikit.model.WechatPayDetail;
import com.netease.nim.uikit.model.WithdrawHistoryEntity;
import com.netease.nim.uikit.model.WujieTradeConstructEntity;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Ｔａｍｉｃ on 2016-07-08.
 * {@link # https://github.com/NeglectedByBoss/RetrofitClient}
 */
public interface ApiService {

    /******************    get       *********************/
    /**
     * 微信登陆获取accessToken
     */
    @GET("/sns/oauth2/access_token")
    Observable<AccessTokenWXEntity> getWXAccessToken(@Query("appid") String appid,
                                                     @Query("secret") String secret,
                                                     @Query("code") String code,
                                                     @Query("grant_type") String grant_type);

    /**
     * 微信登陆获取个人信息
     */
    @GET("/sns/userinfo")
    Observable<WXUserInfoEntity> getWXUserInfo(@Query("access_token") String access_token,
                                               @Query("openid") String openid
    );


    /******************    post       *********************/


    /**
     * 微信第三方登陆
     */
    @POST("/service/user/third-party-login.htm")
    Observable<BaseResponse<UserInfoEntity>> loginForWX(@Body RequestBody body);


    /**
     * 普通写法
     */
    @POST("/service/nation-retrieve.htm")
    Observable<BaseResponse<Object>> getCountry(@Body RequestBody body);


    /**
     * 登陆
     */
    @POST("/service/user/user-login.htm")
    Observable<BaseResponse<UserInfoEntity>> login(@Body RequestBody body);

    /**
     * 登出
     */
    @POST("/service/user/user-logout.htm")
    Observable<BaseResponse<Object>> logout(@Body RequestBody body);

    /**
     * 发送手机验证码
     */
    @POST("/service/sms/security-code-generate.htm")
    Observable<BaseResponse<Object>> sendSMSCode(@Body RequestBody body);

    /**
     * 注册
     */
    @POST("/service/user/user-register.htm")
    Observable<BaseResponse<RegisterEntity>> register(@Body RequestBody body);

    /**
     * 重置密码
     */
    @POST("/service/user/password-reset.htm")
    Observable<BaseResponse<Object>> resetUserPwd(@Body RequestBody body);

    /**
     * 七牛附件上传需要的token
     */
    @POST("/service/image/token-generate.htm")
    Observable<BaseResponse<QiNiuTokensEntity>> getQiNiuToken(@Body RequestBody body);

    /**
     * 修改用户信息
     */
    @POST("/service/user/user-update.htm")
    Observable<BaseResponse<UpdateUserInfoEntity>> updateUserInfo(@Body RequestBody body);

    /**
     * 根据手机号码搜索好友
     */
    @POST("/service/relation/friend-retrieve.htm")
    Observable<BaseResponse<AddFriendForUid>> searchFriend(@Body RequestBody body);

    /**
     * 添加好友
     */
    @POST("/service/relation/relation-register.htm")
    Observable<BaseResponse<Object>> addFriend(@Body RequestBody body);


    /**
     * 查看待确认的新的好友列表
     */
    @POST("/service/relation/relation-register-retrieve.htm")
    Observable<BaseResponse<ConfirmedFriendEntity>> getNotConfirmedFriends(@Body RequestBody body);

    /**
     * 确认好友关系
     */
    @POST("/service/relation/relation-register-confirm.htm")
    Observable<BaseResponse<Object>> confirmedFriend(@Body RequestBody body);

    /**
     * 解除好友关系
     */
    @POST("/service/relation/relation-remove.htm")
    Observable<BaseResponse<Object>> removeFriends(@Body RequestBody body);

    /**
     * 根据通讯录获取好友信息
     */
    @POST("/service/relation/address-relation-retrieve.htm")
    Observable<BaseResponse<AddressBookFriendsEntity>> getAddressBookFriends(@Body RequestBody body);

    /**
     * 查看好友列表
     */
    @POST("/service/relation/common-relation-retrieve.htm")
    Observable<BaseResponse<MyFriendsListEntity>> getMyFriends(@Body RequestBody body);

    /**
     * 查看关注的明星列表
     */
    @POST("/service/relation/star-relation-retrieve.htm")
    Observable<BaseResponse<StarListEntity>> getMyStars(@Body RequestBody body);

    /**
     * 查看超级ip列表
     */
    @POST("/service/relation/discovery-retrieve.htm")
    Observable<BaseResponse<StarListEntity>> getSuperStars(@Body RequestBody body);

    /**
     *获取明星用户详情信息
     */
    @POST("/service/relation/star-friend-detail.htm")
    Observable<BaseResponse<StarInfoEntity>> getStarDetails(@Body RequestBody body);

    /**
     *根据水塘id获取水塘详情
     */
    @POST("/service/drop/drop-detail.htm")
    Observable<BaseResponse<DropDetailsEntity>> getPoolDetals(@Body RequestBody body);

    /**
     *申请加入水塘
     */
    @POST("/service/drop/drop-join.htm")
    Observable<BaseResponse<Object>> joinPool(@Body RequestBody body);

    /**
     *根据类别ID获取水塘列表
     */
    @POST("/service/drop/drop-retrieve.htm")
    Observable<BaseResponse<RecommendPoolListEntity>> getRecommendPoolList(@Body RequestBody body);

    /**
     *查看水贴信息
     */
    @POST("/service/drop/tip/tip-detail.htm")
    Observable<BaseResponse<PostEntity>> getPostDetails(@Body RequestBody body);

    /**
     *添加收藏
     */
    @POST("/service/user/collect-insert.htm")
    Observable<BaseResponse<Object>> insertCollect(@Body RequestBody body);

    /**
     *删除收藏
     */
    @POST("/service/user/collect-remove.htm")
    Observable<BaseResponse<Object>> removeCollect(@Body RequestBody body);


    /**
     *新增用户地址
     */
    @POST("/service/address/address-insert.htm")
    Observable<BaseResponse<AddressEntity.ResultsBean>> insertAddress(@Body RequestBody body);

    /**
     *删除用户地址列表
     */
    @POST("/service/address/address-remove.htm")
    Observable<BaseResponse<AddressEntity.ResultsBean>> removeAddress(@Body RequestBody body);

    /**
     *更新用户地址列表
     */
    @POST("/service/address/address-update.htm")
    Observable<BaseResponse<AddressEntity.ResultsBean>> updateAddress(@Body RequestBody body);

    /**
     *查询用户地址列表
     */
    @POST("/service/address/address-retrieve.htm")
    Observable<BaseResponse<AddressEntity>> getAddress(@Body RequestBody body);

    /**
     *查询收藏水贴
     */
    @POST("/service/user/collect-tip-retrieve.htm")
    Observable<BaseResponse<CollectionSTEntry>> getCollectionSTList(@Body RequestBody body);

    /**
     * 查询收藏水宝
     */
    @POST("/service/user/collect-good-retrieve.htm")
    Observable<BaseResponse<CollectionSBEntry>> getCollectionSBList(@Body RequestBody body);


    /**
     *查询订单
     */
    @POST("/service/order/order-retrieve.htm")
    Observable<BaseResponse<OrderEntity>> getOrderList(@Body RequestBody body);

    /**
     *获取用户详细信息
     */
    @POST("/service/relation/friend-detail.htm")
    Observable<BaseResponse<FriendDetailEntity>> getFriendDetail(@Body RequestBody body);

    /**
     * 我的金豆
     */
    @POST("/service/user/user-bean-retrieve.htm")
    Observable<BaseResponse<JinDouEntity>> getJinDouList(@Body RequestBody body);

    /**
     * 我的足迹水贴列表
     */
    @POST("/service/footprint/footprint-tip-retrieve.htm")
    Observable<BaseResponse<FootprintShuiTieEntity>> getFootprintShuiTieList(@Body RequestBody body);

    /**
     * 删除我的足迹水贴
     */
    @POST("/service/footprint/footprint-tip-remove.htm")
    Observable<BaseResponse<Object>> deleteFootprintShuiTie(@Body RequestBody body);

    /**
     * 我的足迹水塘列表
     */
    @POST("/service/footprint/footprint-drop-retrieve.htm")
    Observable<BaseResponse<FootprintShuiTangEntity>> getFootprintShuiTangList(@Body RequestBody body);

    /**
     * 删除我的足迹水塘
     */
    @POST("/service/footprint/footprint-drop-remove.htm")
    Observable<BaseResponse<Object>> deleteFootprintShuiTang(@Body RequestBody body);

    /**
     * 我的水塘
     */
    @POST("/service/drop/create-drop-retrieve.htm")
    Observable<BaseResponse<PoolListEntity>> getCreatePoolList(@Body RequestBody body);

    /**
     * 好友关注的水塘列表
     */
    @POST("/service/drop/friend-attention-drop-retrieve.htm")
    Observable<BaseResponse<PoolListEntity>> getFriendAttentionPoolList(@Body RequestBody body);

    /**
     * 好友创建的水塘列表
     */
    @POST("/service/drop/friend-create-drop-retrieve.htm")
    Observable<BaseResponse<PoolListEntity>> getFriendCreatePoolList(@Body RequestBody body);

    /**
     * 我的水帖
     */
    @POST("/service/drop/tip/my-tip-retrieve.htm")
    Observable<BaseResponse<MyPostEntity>> getCreatePostList(@Body RequestBody body);


    /**
     * 好友收藏的水贴列表
     */
    @POST("/service/user/friend-collect-tip-retrieve.htm")
    Observable<BaseResponse<PostForFriendCollectEntity>> getFriendCollectPostList(@Body RequestBody body);

    /**
     * 好友创建的水贴列表
     */
    @POST("/service/drop/tip/friend-tip-retrieve.htm")
    Observable<BaseResponse<MyPostEntity>> getFriendCreatePostList(@Body RequestBody body);

    /**
     * 删除收藏水帖
     */
    @POST("/service/user/collect-remove.htm")
    Observable<BaseResponse<Object>> deleteCollectPost(@Body RequestBody body);

    /**
     * 取消隐秘收藏
     */
    @POST("/service/user/collect-show.htm")
    Observable<BaseResponse<Object>> cancelPrivate(@Body RequestBody body);

    /**
     * 隐秘收藏
     */
    @POST("/service/user/collect-hide.htm")
    Observable<BaseResponse<Object>> setPrivate(@Body RequestBody body);

    /**
     * 关注的水塘列表
     */
    @POST("/service/drop/attention-drop-retrieve.htm")
    Observable<BaseResponse<PoolListEntity>> getFocusPoolList(@Body RequestBody body);

    /**
     * 取消水塘关注
     */
    @POST("/service/drop/drop-cancel-attention.htm")
    Observable<BaseResponse<Object>> cancelFocusPool(@Body RequestBody body);

    /**
     * 显示关注的水塘
     */
    @POST("/service/drop/drop-attention-show.htm")
    Observable<BaseResponse<Object>> showFocusPool(@Body RequestBody body);

    /**
     * 隐藏关注的水塘
     */
    @POST("/service/drop/drop-attention-hide.htm")
    Observable<BaseResponse<Object>> hideFocusPool(@Body RequestBody body);

    /**
     * 清除足迹
     */
    @POST("/service/footprint/footprint-clean.htm")
    Observable<BaseResponse<Object>> cleanFootprint(@Body RequestBody body);

    /**
     * 我的门票
     */
    @POST("/service/user/my-ticket.htm")
    Observable<BaseResponse<MyTicketEntity>> getMyTickets(@Body RequestBody body);


    /**
     * 水贴搜索热点
     */
    @POST("/service/tip-hot-word-retrieve.htm")
    Observable<BaseResponse<HotSearchEntity>> getHotSearchForPost(@Body RequestBody body);

    /**
     * 水塘搜索热点
     */
    @POST("/service/drop-hot-word-retrieve.htm")
    Observable<BaseResponse<HotSearchEntity>> getHotSearchForPool(@Body RequestBody body);

    /**
     * 水贴关键字搜索
     */
    @POST("/service/search/tip-word-search.htm")
    Observable<BaseResponse<SearchPostEntity>> getSearchForPost(@Body RequestBody body);

    /**
     * 水塘关键字搜索
     */
    @POST("/service/search/drop-word-search.htm")
    Observable<BaseResponse<SearchPoolEntity>> getSearchForPool(@Body RequestBody body);

    /**
     * 关注水塘
     */
    @POST("/service/drop/drop-attention.htm")
    Observable<BaseResponse<Object>> attentionPool(@Body RequestBody body);

    /**
     * 取消关注水塘
     */
    @POST("/service/drop/drop-cancel-attention.htm")
    Observable<BaseResponse<Object>> cancelAttentionPool(@Body RequestBody body);


    /**
     * 获取水塘或者水贴分类列表
     */
    @POST("/service/category-retrieve.htm")
    Observable<BaseResponse<CategoryEntity>> getCategorys(@Body RequestBody body);


    /**
     * 根据分类id获取水贴列表
     */
    @POST("/service/drop/tip/tip-retrieve-category.htm")
    Observable<BaseResponse<PostListEntity>> getPostLists(@Body RequestBody body);

    /**
     * 水宝详情
     */
    @POST("/service/good/good-detail.htm")
    Observable<BaseResponse<GoodsDetailEntity>> getGoodsDetail(@Body RequestBody body);

    /**
     * 获取水宝评分列表
     */
    @POST("/service/order/good-score-retrieve.htm")
    Observable<BaseResponse<GoodsScoreListEntity>> getGoodsScoreList(@Body RequestBody body);

    /**
     * 个人中心接口
     */
    @POST("/service/user/my-info.htm")
    Observable<BaseResponse<UserCenterEntity>> getUserInfo(@Body RequestBody body);

    /**
     * 领取门票
     */
    @POST("/service/ticket/ticket-insert.htm")
    Observable<BaseResponse<Object>> receiveTicket(@Body RequestBody body);

    /**
     * 订单评分
     */
    @POST("/service/order/order-score.htm")
    Observable<BaseResponse<Object>> markScore(@Body RequestBody body);

    /**
     * 生成订单
     */
    @POST("/service/order/order-insert.htm")
    Observable<BaseResponse<OrderDetailEntity>> insertOrder(@Body RequestBody body);

    /**
     * 请求微信支付串
     */
    @POST("/service/pay/weixin/trade-construct.htm")
    Observable<BaseResponse<WechatPayDetail>> getWechatPayStr(@Body RequestBody body);

    /**
     * 获取兴趣列表
     */
    @POST("/service/user-category-retrieve.htm")
    Observable<BaseResponse<InterestEntity>> getInterests(@Body RequestBody body);

    /**
     * 提交选择的兴趣列表
     */
    @POST("/service/user/user-category-like.htm")
    Observable<BaseResponse<Object>> putInterests(@Body RequestBody body);

    /**
     * 物流详情
     */
    @POST("/service/order/order-logistics-retrieve.htm")
    Observable<BaseResponse<LogiticsEntity>> getLogisticsInfo(@Body RequestBody body);

    /**
     * 买过的水贴列表
     */
    @POST("/service/user/collect-buy-tip-retrieve.htm")
    Observable<BaseResponse<CollectionSTEntry>> getBuyPostList(@Body RequestBody body);

    /**
     * 取消订单
     */
    @POST("/service/order/order-cancel.htm")
    Observable<BaseResponse<Object>> cancelOrder(@Body RequestBody body);

    /**
     * 删除我的水帖
     */
    @POST("/service/drop/tip/tip-remove.htm")
    Observable<BaseResponse<Object>> deleteMyPost(@Body RequestBody body);

    /**
     * 确认收货
     */
    @POST("/service/order/order-confirm.htm")
    Observable<BaseResponse<Object>> confirmReceived(@Body RequestBody body);

    /**
     * 请求消息列表
     */
    @POST("/service/user/message-retrieve.htm")
    Observable<BaseResponse<RecentMessageListEntity>> getRecentMessageList(@Body RequestBody body);

    /**
     * 同意加入群申请
     */
    @POST("/service/drop/drop-join-confirm.htm")
    Observable<BaseResponse<Object>> confirmAddGroup(@Body RequestBody body);

    /**
     * 推荐水贴banner
     */
    @POST("/service/drop/tip/slider-tip-retrieve.htm")
    Observable<BaseResponse<TipBannerEntity>> getTipBannerList(@Body RequestBody body);

    /**
     * 订单详情
     */
    @POST("/service/order/order-detail.htm")
    Observable<BaseResponse<OrderDetailEntity2>> getOrderDetail(@Body RequestBody body);

    /**
     * 联系客服
     */
    @POST("/service/user/user-feedback-insert.htm")
    Observable<BaseResponse<Object>> feedBack(@Body RequestBody body);

    /**
     * 根据铁粉群号获取水塘详情
     */
    @POST("/service/drop/drop-retrieve-by-room.htm")
    Observable<BaseResponse<FansGroupEntity>> getRoomInfo(@Body RequestBody body);

    /**
     * 申请加入水塘
     */
    @POST("/service/drop/drop-join.htm")
    Observable<BaseResponse<Object>> applyJoinRoom(@Body RequestBody body);

    /**
     * 用户协议url
     */
    @POST("/service/agreement.htm")
    Observable<BaseResponse<UserAgreementUrlEntity>> getUserAgreementUrl();

    /**
     * 显示买过的水帖
     */
    @POST("/service/user/collect-buy-show.htm")
    Observable<BaseResponse<Object>> buyPostShow(@Body RequestBody body);

    /**
     * 隐藏买过的水帖
     */
    @POST("/service/user/collect-buy-hide.htm")
    Observable<BaseResponse<Object>> buyPostHide(@Body RequestBody body);

    /**
     * 删除买过的水帖
     */
    @POST("/service/user/collect-buy-remove.htm")
    Observable<BaseResponse<Object>> deleteBuyPost(@Body RequestBody body);

    /**
     * OpenId验证
     */
    @POST("/service/user/openid-verify.htm")
    Observable<BaseResponse<VerifyOpenId>> verifyOpenId(@Body RequestBody body);

    /**
     * 手机号码验证
     */
    @POST("/service/user/mobile-verify.htm")
    Observable<BaseResponse<VerifyPhoneEntity>> verifyPhoneNum(@Body RequestBody body);

    /**
     * 第三方手机号绑定
     */
    @POST("/service/user/third-party-bind-mobile.htm")
    Observable<BaseResponse<Object>> bindPhone(@Body RequestBody body);

 /**
     * 发送用户邀请短信
     */
    @POST("/service/sms/invite-msg-push.htm")
    Observable<BaseResponse<Object>> sendInviteSms(@Body RequestBody body);

 /**
     * 订单状态查询
     */
    @POST("/service/order/order-status-retrieve.htm")
    Observable<BaseResponse<OrderStatusEntity>> checkOrderStatus(@Body RequestBody body);

 /**
     * 获取app参数
     */
    @POST("/service/index.htm")
    Observable<BaseResponse<AppInfoEntity>> getAppInfo();

 /**
     * 根据关键词搜索好友
     */
    @POST("/service/relation/friend-search.htm")
    Observable<BaseResponse<SearchFriendEntity>> searchFriendKeyword(@Body RequestBody body);

    /**
     * 邀请用户加入群
     */
    @POST("/service/drop/drop-holding.htm")
    Observable<BaseResponse<Object>> addTeam(@Body RequestBody body);


    /**
     *把人从群里踢出去
     */
    @POST("/service/drop/drop-kickoff.htm")
    Observable<BaseResponse<Object>> kickOut(@Body RequestBody body);

    /**
     *把人从群里踢出去
     */
    @POST("/service/drop/drop-withdraw.htm")
    Observable<BaseResponse<Object>> logoutTeam(@Body RequestBody body);

    /**
     * 查看绑定银行卡状态
     * @param body
     * @return
     */
    @POST("/service/supply/user-card-retrieve.htm")
    Observable<BaseResponse<BankCardListEntity>> getBankCards(@Body RequestBody body);

    /**
     * 添加银行卡
     */
    @POST("/service/supply/user-card-insert.htm")
    Observable<BaseResponse<Object>> addBankCard(@Body RequestBody body);

    /**
     *提现记录
     */
    @POST("/service/supply/user-funds-retrieve.htm")
    Observable<BaseResponse<WithdrawHistoryEntity>> getWithdrawHistoryList(@Body RequestBody body);

    /**
     *账户明细
     */
    @POST("/service/supply/fund-detail-retrieve.htm")
    Observable<BaseResponse<AccountDetaiEntity>> getAccountDetailList(@Body RequestBody body);

    /**
     *绑定银行的状态
     */
    @POST("/service/supply/user-card-retrieve.htm")
    Observable<BaseResponse<SeleBankAccountEntity>> getUserCardList(@Body RequestBody body);

    /**
     *申请提现
     */
    @POST("/service/supply/user-funds-withdraw.htm")
    Observable<BaseResponse<Object>> withdraw(@Body RequestBody body);

    /**
     * 获取银行卡卡号和银行对应关系
     * @return
     */
    @POST("/service/supply/card-rule-retrieve.htm")
    Observable<BaseResponse<BankCardNoListEntity>> getBankCardNO();

    /**
     * 删除绑定银行卡
     * @param body
     * @return
     */
    @POST("/service/supply/user-card-remove.htm")
    Observable<BaseResponse<Object>> delBankCard(@Body RequestBody body);

    /**
     * 查询帖子收藏的好友
     * @param body
     * @return
     */
    @POST("/service/drop/tip/friend-collect-tip.htm")
    Observable<BaseResponse<FriendCollectEntity>> getFriendCollect(@Body RequestBody body);


    /**
     * 根据用户姓名查找用户身份证
     * @param body
     * @return
     */
    @POST("/service/address/user-idcard-retrieve.htm")
    Observable<BaseResponse<IdCardEntity>> retrieveIdCard(@Body RequestBody body);

    /**
     * 	订单中完善用户身份信息
     * @param body
     * @return
     */
    @POST("/service/address/user-idcard-update.htm")
    Observable<BaseResponse<AddressEntity.ResultsBean>> updateUserIdcard(@Body RequestBody body);

    /**
     * 无界商品支付调起
     * @param body
     * @return
     */
    @POST("/service/pay/wujie/trade-construct.htm")
    Observable<BaseResponse<WujieTradeConstructEntity>> getWujieTradeConstruct(@Body RequestBody body);

    /**
     * 查找推荐好友
     * @param body
     * @return
     */
    @POST("/service/relation/friend-recommend.htm")
    Observable<BaseResponse<RecommendFriendEntity>> getRecommendFriend(@Body RequestBody body);

    /**
     * 会员激活
     * @param body
     * @return
     */
    @POST("/service/member/member-active.htm")
    Observable<BaseResponse<MemberActiveEntitiy>> activeMember(@Body RequestBody body);

    /**
     * 查询某个国家的品牌列表
     */
    @POST("/service/good/brand-retrieve-by-country.htm")
    Observable<BaseResponse<BrandTagEntity>> getBrandList(@Body RequestBody body);

    /**
     * 商城首页
     */
    @POST("/service/banner/banner-recommend.htm")
    Observable<BaseResponse<StoreHomePageEntity>> getHomePageInfo(@Body RequestBody build);

    /**
     * 全部品牌页面
     */
    @POST("/service/good/good-recommend.htm")
    Observable<BaseResponse<BrandListEntity>> getAllBrandData(@Body RequestBody build);

    /**
     * 子品牌页面
     */
    @POST("/service/good/good-retrieve-by-brand.htm")
    Observable<BaseResponse<BrandItemEntity>> getItemBrandData(@Body RequestBody build);

    /**
     * 根据兑换码获取礼品信息
     */
    @POST("/service/gift/gift-retrieve.htm")
    Observable<BaseResponse<GiftInfoEntity>> getGiftInfo(@Body RequestBody build);

    /**
     * 兑换礼品
     */
    @POST("/service/gift/gift-exchange.htm")
    Observable<BaseResponse<Object>> giftExchange(@Body RequestBody build);

    /**
     * 兑换礼品记录
     */
    @POST("/service/gift/exchange-retrieve.htm")
    Observable<BaseResponse<CashGiftRecordEntity>> getGiftRecord(@Body RequestBody build);

    /**
     * 兑换的礼物物流信息
     */
    @POST("/service/gift/gift-logistics-retrieve.htm")
    Observable<BaseResponse<LogiticsEntity>> getGiftLogitics(@Body RequestBody build);

    /**
     * 会员商品列表
     */
    @POST("/service/good/member-good-retrieve.htm")
    Observable<BaseResponse<VipAreaEntity>> getVipGoodList(@Body RequestBody build);

    /**
     * 会员申请微信支付
     */
    @POST("/service/pay/member/trade-construct.htm")
    Observable<BaseResponse<WechatPayDetail>> applyPayForMember(@Body RequestBody build);

    /**
     * 会员激活送VR眼镜，输入地址信息
     */
    @POST("/service/member/member-active-address-insert.htm")
    Observable<BaseResponse<Object>> sendAddressForMemberActiveGift(@Body RequestBody body);
}
