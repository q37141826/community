package com.qixiu.intelligentcommunity.constants;

/**
 * Created by HuiHeZe on 2017/6/19.
 */

public class ConstantUrl {

//2019年6月28日21:09:43将以下地址
//    public static String hosturl = "http://sq.whtkl.cn/";
//更换为
//    public static String hosturl = "http://community.qixiuu.com/";

//    public static String hosturl = "http://192.168.168.39/community/index.php";

    //图片URL
    public static String IMAGEURL = "http://community.qixiuu.com/";

    //分享跳转页面
    public static String SHARE_CLICK_GO_URL = "www.baidu.com";
    //主页  匿名的是115地址，外部网络可以访问  147的是内部网络专用
    public static String hosturl = "http://app.mochouwuye.com/";
    public static String hostImageurl = "http://app.mochouwuye.com/";

    //    public static String nativehostImageurl = "http://192.168.168.39/community";
    public static String nativehostImageurl = hostImageurl;
    //   public static String WEBURL_PREFIX = "http://192.168.168.39/community/smartCommunity/#";
    public static String WEBURL_PREFIX = hosturl + "smartCommunity/#";


    //登录接口
    public static String loginUrl = hosturl + "/api/Users/login";
    public static final String touristUrl = hosturl + "/api/Certification/tourist";
    //第三方登录
    public static String appLoginUrl = hosturl + "/api/Users/appLogin";
    //退出登录
    public static String logoutUrl = hosturl + "/api/Users/logout";
    //发送验证码
    public static String sendCodeUrl = hosturl + "/api/Users/sendPhoneCode";
    //
    public static String registerUrl = hosturl + "/api/Users/register";
    //首页
    public static String homeUrl = hosturl + "/api/Certification/banner";
    //新闻公告状态
    public static String newsStateUrl = hosturl + "/api/Personal/type";
    //新闻公告阅读
    public static String readUrl = hosturl + "/api/Personal/read";

    //业主圈发布
    public static String ownerCircleReleaseUrl = hosturl + "/api/Master/masterAdd";
    //业主圈->全部列表
    public static String ownerCircleWholeListUrl = hosturl + "/api/Master/masterList";
    //业主圈->我的列表
    public static String ownerCircleMyListUrl = hosturl + "/api/Master/masterMy";
    //业主圈->删除动态
    public static String ownerCircleDeleteUrl = hosturl + "/api/Master/masterDel";
    //问答区发布
    public static String questionsUrl = hosturl + "/api/Questions/questionsAdd";

    //认证小区
    public static String estateUrl = hosturl + "/api/Certification/estate";
    //认证小区的期数
    public static String periodUrl = hosturl + "/api/Certification/period";
    //认证小区的东别
    public static String buildingUrl = hosturl + "/api/Certification/building";
    //认证小区的单元号
    public static String unitUrl = hosturl + "/api/Certification/unit";
    //小区提交认证
    public static String identifyUrl = hosturl + "/api/Certification/userInfo";

    //个人中心
    public static String mineUrl = hosturl + "/api/Personal/num";
    //修改该密码
    public static String changePswdUrl = hosturl + "/api/Users/editPwd";
    //忘记密码
    public static String lostPswdUrl = hosturl + "/api/Users/lostPwd";
    //修改头像
    public static String changeHeaddUrl = hosturl + "/api/Personal/vitaHaed";
    //修改昵称
    public static String changeNickUrl = hosturl + "/api/Personal/vitaName";
    //代收快递的记录
    public static String getGoodsRecordUrl = hosturl + "/api/Personal/record";


    //一键呼叫
    public static String onekeyCallUrl = hosturl + "/api/Personal/policeList";
    //意见反馈
    public static String suggestionUrl = hosturl + "/api/Personal/feedBack";

    //缴费记录
    public static String payRecordUrl = hosturl + "/api/Cost/costLog";
    //获取物业费指定金额
    public static String payWuyeUrl = hosturl + "/api/Cost/getAmount";
    //去缴费
    public static String gotoPayUrl = hosturl + "/api/Cost/goPay";
    //消息列表
    public static String messageListUrl = hosturl + "/api/Certification/message";
    //阅读消息
    public static String readMessageUrl = hosturl + "/api/Certification/read";
    //获取服务列表
    public static String serviceUrl = hosturl + "/api/Activity/serve";

    //授权详情
    public static String requireUrl = hosturl + "/api/Certification/certInfo";
    //同意授权或者拒绝
    public static String agreeOrRefuseUrl = hosturl + "/api/Certification/certSaves";
    //支付
    public static String payUrl = hosturl + "/api/Cost/pay";
    //打赏的支付
    public static String giveTipUrl = hosturl + "/api/Express/pay";


    //业主圈
    //删除所有评论和回复
    public static final String deleteCommentsUrl = hosturl + "/api/Steward/del";
    //业主圈列表
    public static final String owenAllUrl = hosturl + "/api/Master/masterList";
    //业主圈帖子回复
    public static final String leaveUrl = hosturl + "/api/Master/masterOn";
    //业主圈回复留言
    public static final String leaveBackUrl = hosturl + "/api/Master/masterply";
    //点赞
    public static final String zanOwenUrl = hosturl + "/api/Master/zan";
    public static final String cancleZanOwenUrl = hosturl + "/api/Master/qzan";
    //删除帖子
    public static final String deleteblogUrl = hosturl + "/api/Master/masterDel";
    //业主圈帖子刷新
    public static final String refreshOwenItemUrl = hosturl + "/api/Master/masterOne";
    //我的业主圈
    public static final String owenOwenMineUrl = hosturl + "/api/Master/masterMy";
    //业主圈详情
    public static final String OwenDetailUrl = hosturl + "/api/Master/masterInfo";
    //问答区
    public static final String answerListAllUrl = hosturl + "/api/Questions/questionsList";
    public static final String answerListMineUrl = hosturl + "/api/Questions/questionsMy";
    //问答详情
    public static final String answerDetailsUrl = hosturl + "/api/Questions/questionsInfo";
    //详情评价
    public static final String answerCommentsUrl = hosturl + "/api/Questions/questionsOn";
    //删除详情
    public static final String deleteAnswerDetailUrl = hosturl + "/api/Questions/questionsDel";
    //回复详情
    public static final String answerReplyUrl = hosturl + "/api/Questions/questionsReply";

    public static final String BLUETOOTH_MATCH_URL = hosturl + "/api/Steward/bluetooth";
    /**
     * 获取活动列表
     */
    public static final String activityListUrl = hosturl + "/api/Activity/activityList";
    public static final String activityApplyUrl = hosturl + "/api/Activity/apply";
    public static final String activityInfoUrl = hosturl + "/api/Activity/activityInfo";
    public static final String activitySendComent = hosturl + "/api/Activity/activityOn";
    public static final String activityReplyUrl = hosturl + "/api/Activity/activityRply";
    public static final String activityDelUrl = hosturl + "/api/Steward/del";
    public static final String personalHouseSaveUrl = hosturl + "/api/Personal/houseSave";
    public static final String secondHandSaveUrl = hosturl + "/api/SecondHand/secondSave";
    /*
     * 代收快递
     * */
    //免责声明
    public static final String getGoodsRuleUrl = hosturl + "/smartCommunity/clause.html";
    //打赏提交代收申请
    public static final String getGoodsApply = hosturl + "/api/Express/collection";
    //代收快递详情
    public static final String getGoodsDetailsUrl = hosturl + "/api/Personal/recordInfo";
    //确认收货
    public static final String confirmGetGoodsUrl = hosturl + "/api/Personal/affirm";


    /**
     * 加载WebPage的URL
     */

    public static String loadNeighbourUrl = ConstantUrl.WEBURL_PREFIX + "/neighbour/all" + StringConstants.WEB_PARAMETER_UIDPREFIX;
    public static String loadNeighbourDetailsUrl = ConstantUrl.WEBURL_PREFIX + "/neidetail?detailId=";
    public static String loadHelpUrl = ConstantUrl.WEBURL_PREFIX + "/help/all" + StringConstants.WEB_PARAMETER_UIDPREFIX;
    public static String loadHelpDetailsUrl = ConstantUrl.WEBURL_PREFIX + "/helpdetail?detailId=";
    public static String loadSecondhandMarketUrl = ConstantUrl.WEBURL_PREFIX + "/secondidle" + StringConstants.WEB_PARAMETER_UIDPREFIX;
    public static String loadNoticeUrl = ConstantUrl.WEBURL_PREFIX + "/announcement" + StringConstants.WEB_PARAMETER_UIDPREFIX;
    public static String loadNewsUrl = ConstantUrl.WEBURL_PREFIX + "/newslist" + StringConstants.WEB_PARAMETER_UIDPREFIX;
    public static String loadHousePropertyUrl = ConstantUrl.WEBURL_PREFIX + "/house/rent" + StringConstants.WEB_PARAMETER_UIDPREFIX;
    public static String loadMyRelease = ConstantUrl.WEBURL_PREFIX + "/mysend/house" + StringConstants.WEB_PARAMETER_UIDPREFIX;
    public static String loadCommunityManager = ConstantUrl.WEBURL_PREFIX + "/manager" + StringConstants.WEB_PARAMETER_UIDPREFIX;
    public static String loadGame = hosturl + "/smartCommunity/game.html" + StringConstants.WEB_PARAMETER_UIDPREFIX;


    public static String loadMyCardUrl = ConstantUrl.WEBURL_PREFIX + "/mycard" + StringConstants.WEB_PARAMETER_UIDPREFIX;
    public static String loadAuthorizationUrl = ConstantUrl.WEBURL_PREFIX + "/user" + StringConstants.WEB_PARAMETER_UIDPREFIX;
    public static String loadReleaseGoodsUrl = ConstantUrl.WEBURL_PREFIX + "/mysend/Second" + StringConstants.WEB_PARAMETER_UIDPREFIX;
    public static String loadReleaseSaleUrl = ConstantUrl.WEBURL_PREFIX + "/sale/board" + StringConstants.WEB_PARAMETER_UIDPREFIX;
    public static String loadRepairUrl = ConstantUrl.WEBURL_PREFIX + "/repair" + StringConstants.WEB_PARAMETER_UIDPREFIX;
    public static String shareIconUrl = "https://mmbiz.qlogo.cn/mmbiz_png/3OecsvkRT0ZCg6GBJoiadTfDMlN5IZia4V2XSjian89u2UryrmDvTHYhhgDDLpyCrEUxl1CN8kBicxFamcqL1r0n5g/0?wx_fmt=png";

    //拨打电话
    public static String callUrl = hosturl + "api/Personal/clause";


    //获取商城首页信息
    public static String STORE_SHOP_TAG = "/api/Shop/index";
    public static String STORE_SHOP = hosturl + STORE_SHOP_TAG;

    //获取商城广告
    public static String STORE_ADV_TAG = "/api/Shop/getIndexAdList";
    public static String STORE_ADV = hosturl + STORE_ADV_TAG;

    //获取商城分类条目
    public static String STORE_CLASSIFY_TAG = "/api/Shop/getIndexCateList";
    public static String STORE_CLASSIFY = hosturl + STORE_CLASSIFY_TAG;

    //获取推荐等商品列表
    public static String STORE_CATEGORY_TAG = "/api/Shop/getIndexGoodsList";
    public static String STORE_CATEGORY = hosturl + STORE_CATEGORY_TAG;

    //获取分类列表
    public static String STORE_CLASSIFYLIST_TAG = "/api/Shop/getGoodsListByCateId";
    public static String STORE_CLASSIFYLIST = hosturl + STORE_CLASSIFYLIST_TAG;
    //获取搜索商品列表

    public static String STORE_SEARCHLIST_TAG = "/api/Shop/getGoodsListBykeywords";
    public static String STORE_SEARCHLIST = hosturl + STORE_SEARCHLIST_TAG;

    //获取商品详情列表
    public static String STORE_GOODDETAIL_TAG = "/api/Goods/goodsInfo";
    public static String STORE_GOODDETAIL = hosturl + STORE_GOODDETAIL_TAG;


    //获取热门搜索关键字
    public static String STORE_SEARCHHOT_KEYWORDS_TAG = "/api/Shop/getSearchkeywords";
    public static String STORE_SEARCHHOT_KEYWORDS = hosturl + STORE_SEARCHHOT_KEYWORDS_TAG;

    //添加商品到购物车
    public static String STORE_ADDGOODS_TOSHOPCAR_TAG = "/api/Shop/addGoodsToCart";
    public static String STORE_ADDGOODS_TOSHOPCAR = hosturl + STORE_ADDGOODS_TOSHOPCAR_TAG;
    //获取购物车商品列表
    public static String STORE_GET_SHOPCAR_GOODS_TAG = "/api/Shop/getGoodsInCart";
    public static String STORE_GET_SHOPCAR_GOODS = hosturl + STORE_GET_SHOPCAR_GOODS_TAG;

    //编辑购物车数量
    public static String STORE_SHOPCAR_EDIT_COUNT_TAG = "/api/Shop/editGoodsNumFromCart";
    public static String STORE_SHOPCAR_EDIT_COUNT = hosturl + STORE_SHOPCAR_EDIT_COUNT_TAG;

    //删除购物车
    public static String STORE_SHOPCAR_DELECTED_TAG = "/api/Shop/delGoodsFromCart";
    public static String STORE_SHOPCAR_DELECTED = hosturl + STORE_SHOPCAR_DELECTED_TAG;

    //获取规格
    public static String STORE_GET_SPEC_TAG = "/api/Goods/getSpec";
    public static String STORE_GET_SPEC = hosturl + STORE_GET_SPEC_TAG;


    //查看物流
    public static String CheckWhereTag = "Goods/getFast";
    public static String CheckWhereUrl = hosturl + "/api/" + CheckWhereTag;
    //购物车结算
    public static String SubGoodsFromCartTag = "Shop/subGoodsFromCart";
    public static String SubGoodsFromCartUrl = hosturl + "/api/" + SubGoodsFromCartTag;
    //立刻结算
    public static String addOrderNowTag = "Shop/addOrderNow";
    public static String addOrderNowUrl = hosturl + "/api/" + addOrderNowTag;
    //获取地址列表
    public static String AddressListTag = "Goods/getAddress";
    public static String AddressListUrl = hosturl + "/api/" + AddressListTag;
    //添加地址
    public static String AddAddressTag = "Goods/addAddress";
    public static String AddAddressUrl = hosturl + "/api/" + AddAddressTag;
    //编辑地址
    public static String EditAddressTag = "Goods/editAddress";
    public static String EditressUrl = hosturl + "/api/" + EditAddressTag;
    //删除地址
    public static String DelAddressTag = "Goods/delAddress";
    public static String DelAddressUrl = hosturl + "/api/" + DelAddressTag;
    //设为默认地址
    public static String SetDefaultAddressTag = "Goods/setDefault";
    public static String SetDefaultAddressUrl = hosturl + "/api/" + SetDefaultAddressTag;

    //订单列表
    public static String OrederListTag = "Goods/orderList";
    public static String OrederListUrl = hosturl + "/api/" + OrederListTag;

    //删除订单
    public static String DeleteOrederTag = "Goods/delBill";
    public static String DeleteOrederUrl = hosturl + "/api/" + DeleteOrederTag;

    //订单详情
    public static String OrederDetailTag = "Goods/orderDetail";
    public static String OrederDetailUrl = hosturl + "/api/" + OrederDetailTag;

    //提醒发货
    public static String NoticeSendTag = "Goods/getDeliver";
    public static String NoticeSendUrl = hosturl + "/api/" + NoticeSendTag;
    //确认收货
    public static String ConformTakeGoodsTag = "Goods/takeGoods";
    public static String ConformTakeGoodsUrl = hosturl + "/api/" + ConformTakeGoodsTag;
    //详情页直接支付
//    public static String OrderPayTag = "Shop/orderPay";
//    public static String OrderPayUrl = hosturlPrick + OrderPayTag;
    public static String OrderPayTag = "Shop/getOrderPay";
    public static String OrderPayUrl = hosturl + "/api/" + OrderPayTag;

    //通知服务器我付钱成功了
    public static String NoticePaySuccessTag = "Goods/payGoods";
    public static String NoticePaySuccessUrl = hosturl + "/api/" + NoticePaySuccessTag;

    //评价
    public static String GOODS_EVALUATE_TAG = "Goods/getEva";
    public static String GOODS_EVALUATE = hosturl + "/api/" + GOODS_EVALUATE_TAG;

    //积分列表
    public static String myPointsListUrl = hosturl + "/api/Personal/detail";
    //我的积分
    public static String myPointsUrl = hosturl + "/api/Personal/inter";
    //首页商城
    public static String homeHotShopUrl = hosturl + "api/Shop/indexd";
    public static String versionTag;
    public static String versionUrl = hosturl + "api/Personal/vivt";

    //首页通知
    public static String homeNoticeUrl = hosturl + "api/Certification/noticesIndex";


    //物业新界面获取个人缴费详情
    public static String newWuyePayDetailsUrl = hosturl + "api/cost/getUserWuye";
    //物业新界面生成订单
    public static String newWuyePayCreatOrderUrl = hosturl + "api/cost/wuyePay";

}
