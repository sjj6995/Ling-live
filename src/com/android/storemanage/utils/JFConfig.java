package com.android.storemanage.utils;

public class JFConfig {										
		
	public static int curentPage = 0;
	public static final int PAGE_COUNT = 8;
	public static boolean isMove = false;
	public static final String IMA_URL = "http://112.126.72.53:8181/ht/";
	public static final String HOST_URL = "http://112.126.72.53:8181/app/";
	public static final String COMMON_MD5_STR = "lingshenghuomd5";
	public static final String HOST_TEST_URL = "http://192.168.6.107:8181/app/";
	public static final String REGISTER = "ajax.sword?tid=UserService_register";// 注册
	public static final String FORGET = "ajax.sword?tid=UserService_forgetPassword";//忘记密码
	public static final String LOGIN = "ajax.sword?ctrl=UserCtrl_login";//登陆
	public static final String DOWN = "ajax.sword?tid=BrandService_addAppCs";//下载统计
	public static final String SAVEADDERSS = "ajax.sword?ctrl=UserprizeOrderCtrl_saveOrder";
	public static final String CHANGEDEVICE="ajax.sword?ctrl=UserCtrl_changeDevice";
	public static final String LSH_TOKEN = "lshToken";
	public static final String FIXPWD = "ajax.sword?tid=UserService_modifyPassword";
	public static final String MESSAGE_CENTER = "ajax.sword?tid=MessageService_initMessageList";// 消息中心
	public static final String CLASSIFY_LIST = "ajax.sword?tid=CategoryService_initCategoryList";// 分类
	public static final String MESSAGE_DETAIL = "ajax.sword?tid=MessageService_getMessageDetail";// 消息详情
	public static final String CATEGORY_BY_ID = "ajax.sword?tid=CategoryService_getBrandList";// 获取分类下的品牌列表1
	public static final String ZHAOPIN_BY_ID = "ajax.sword?tid=RecruitService_getList";// 获取分类下的品牌列表1
	public static final String ADDONE = "ajax.sword?tid=CommonService_addOneWealth";// 分享加一积分
	public static final String REDUCE = "ajax.sword?tid=CommonService_reduceOneWealth";// 点招聘减分
	public static final String CATEGORY_DETAIL = "ajax.sword?tid=CategoryService_getBrandDetail";//分类详情
	//public static final String CATEGORY_BRAND_GET_WELATH = "ajax.sword?tid=CategoryService_getWealthByClickBrand";// 获取财富值
	public static final String MY_INFOR = "ajax.sword?tid=UserService_personalInfo";// 个人信息
	public static final String WECHAT_SHARA_APP_IP = "wxc11a20943368d63f";
	public static final String HOME_PAGE = "ajax.sword?tid=CommonService_genHomePage";// 首页
	public static final String WEALTH_PRIZE = "ajax.sword?tid=WealthareaService_getPrizeList";// 财富奖区
	public static final String GET_WEALTH_BY_CLICK_BRANCH = "ajax.sword?tid=CategoryService_getWealthByClickBrand";// 给用户增加相应的财富值
	public static final String GET_MY_PRIZE = "ajax.sword?tid=UserService_userPrizeList";// 我的奖品列表
	public static final String GET_PRIZE_DETAIL = "ajax.sword?tid=UserService_userPrizeDetail";// 奖品详情
	public static final String GET_WEALTH_RRIZE_DETAILL = "ajax.sword?tid=WealthareaService_getPrizeDetail";// 财富详情
	public static final String EXCHANGE_PRIZE = "ajax.sword?tid=WealthareaService_exchangePrize";// 兑换奖品
	public static final String USE_PRIZE = "ajax.sword?tid=UserService_userUsePrize";// 使用奖品
	public static final String CONFIRM = "ajax.sword?ctrl=UserprizeOrderCtrl_confirmSh";// 使用奖品
	public static final String SCAN_REQUEST = "ajax.sword?ctrl=QrcodeCtrl_userScanQrcode";//扫描二维码
	public static final String SCAN_VERFY = "ajax.sword?ctrl=QrcodeCtrl_verifyPassword";//扫描二维码
	public static final String USER_FEEDBACK = "ajax.sword?tid=FeedbackService_userFeedback";//意见反馈
	public static final String CHECK_UPDATE = "ajax.sword?tid=AppVersionService_userUpdateApp";//版本更新
	public static final String CHECK_ISORNOT_REGISTERED = "ajax.sword?tid=UserService_checkRegister";//检查是否注册过
	public static final String DELETE_USER_PRIZE = "ajax.sword?tid=UserService_userDeletePrize";//删除奖品
	public static final String GET_WEALTH_AND_USERAMOUNT = "ajax.sword?tid=CommonService_getSyPd";//获取首页会员数和用户财富值
    public static final String BRAND_LIST = "BrandList";//品牌列表
    public static final String CATEGORY_LIST = "CategoryList";//分类列表
    public static final String FRONT_PAGE = "FrontPage";//首页
    public static final String MESSAGE_LIST = "MessageList";//消息列表
    public static final String PRIZE_LIST = "PrizeList";//奖品列表\
    public static final String ZHAOPIN_LIST = "ZhaopinList";//奖品列表\
    public static final String PWD_LIMIT = "t_limit";
    public static final String T_USER = "t_user";
    public static final String USER_SIGN = "ajax.sword?ctrl=UserCtrl_sign";//用户签到接口
    public static final String SIGN_COUNT = "ajax.sword?ctrl=UserCtrl_getSignCount";//签到天数
    public static final String ADD_WEALTH = "ajax.sword?tid=BrandService_addWealth";//增加财富值按钮
    public static final String ADD_NUM = "ajax.sword?ctrl=BrandCtrl_addClicknum";
    public static final String SAVE_ORDER = "ajax.sword?ctrl=UserprizeOrderCtrl_saveOrder";
}
