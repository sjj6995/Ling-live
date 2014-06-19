package com.android.storemanage.utils;

public class JFConfig {

	public static int curentPage = 0;
	public static final int PAGE_COUNT = 8;
	public static boolean isMove = false;
	public static final String HOST_URL = "http://112.126.72.53:8080/app/";
	public static final String REGISTER = "ajax.sword?tid=UserService_register";// 注册
	public static final String MESSAGE_CENTER = "ajax.sword?tid=MessageService_initMessageList";// 消息中心
	public static final String CLASSIFY_LIST = "ajax.sword?tid=CategoryService_initCategoryList";// 分类
	public static final String MESSAGE_DETAIL = "ajax.sword?tid=MessageService_getMessageDetail";// 消息详情
	public static final String CATEGORY_BY_ID = "ajax.sword?tid=CategoryService_getBrandList";// 获取分类下的品牌列表
	public static final String CATEGORY_BRAND_GET_WELATH = "ajax.sword?tid=CategoryService_getWealthByClickBrand";// 获取财富值
	public static final String MY_INFOR = "ajax.sword?tid=UserService_personalInfo";// 个人信息
	public static final String WECHAT_SHARA_APP_IP = "";
	public static final String HOME_PAGE = "ajax.sword?tid=CommonService_genHomePage";// 首页
	public static final String WEALTH_PRIZE = "ajax.sword?tid=WealthareaService_getPrizeList";// 财富奖区
	public static final String GET_WEALTH_BY_CLICK_BRANCH = "ajax.sword?tid=CategoryService_getWealthByClickBrand";// 给用户增加相应的财富值
	public static final String GET_MY_PRIZE = "ajax.sword?tid=UserService_userPrizeList";// 我的奖品列表
	public static final String GET_PRIZE_DETAIL = "ajax.sword?tid=UserService_userPrizeDetail";// 奖品详情
	public static final String GET_WEALTH_RRIZE_DETAILL = "ajax.sword?tid=WealthareaService_getPrizeDetail";// 财富详情
	public static final String EXCHANGE_PRIZE = "ajax.sword?tid=WealthareaService_exchangePrize";// 兑换奖品
	public static final String USE_PRIZE = "ajax.sword?tid=UserService_userUsePrize";// 使用奖品
	public static final String SCAN_REQUEST = "ajax.sword?tid=QRcodeService_userScanQRcode";//扫描二维码
	public static final String USER_FEEDBACK = "ajax.sword?tid=FeedbackService_userFeedback";//意见反馈
}
