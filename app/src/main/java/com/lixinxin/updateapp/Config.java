package com.lixinxin.updateapp;

public class Config {

    //3.0的线下
    //public static final String BASE_URL2 = "http://gateway.inner.evente.cn:8000/";
    //线下
    //public static final String BASE_URL = "http://open.inner.evente.cn:30380/1.0/";
    // 线下 lab
    // public static final String MD5_SECRET = "3mOx8Kzr0Vn2bYphJ7yEepn1NEwPk4eo";
    //3.0 lab
    //public static final String BASE_URL2 = "http://entry.lab.evente.cn/";
    //lab
    //public static final String BASE_URL = "http://open.lab.evente.cn/1.0/";

    //3.0
    public static final String REQUEST_APPID_VALUE_2 = "100001";
    //3.0
    public static final String MD5_SECRET2 = "2xwqDE4monNJkDEtjMX7EEnXzpbOY76a";

    //3.0线上
    public static final String BASE_URL2 = "https://gateway.evente.cn/";
    // 线上
    public static final String BASE_URL = "https://open.evente.cn/1.0/";
    // 线上
    public static final String MD5_SECRET = "RO8plD5d0Vve5d9IDRwK7MvPQmjEwA6Y";


    //上传图片
    public static final String uploadUrl = Config.BASE_URL2 + "public/upload";
    // 请求失败code
    public static final String HTTP_REQUEST_FAILURE = "-1";
    // 请求成功code
    public static final String HTTP_REQUEST_SUCCESS = "0";
    // 请求数据为空
    public static final String HTTP_REQUEST_EMPTY = "1";
    // json解析失败
    public static final String HTTP_REQUEST_JSON_ERROR = "-2";
    // 请求成功code
    public static final String HTTP_SUCCESS = "10000";
    //TOKEN 过期
    public static final String HTTP_REQUEST_TOKEN_OVERDUE = "90016";
    // 申请的应用ID
    public static final String REQUEST_APPID_LAB = "app_id";
    // 当前时间：date('Y-m-d H:i:s')
    public static final String REQUEST_TIMESTAMP_LAB = "timestamp";
    // 相应信息格式：json
    public static final String REQUEST_FORMAT_LAB = "format";
    // 加密方式：md5
    public static final String REQUEST_SIGN_METHOD_LAB = "sign_method";
    // sing
    public static final String REQUEST_SIGN_LAB = "sign";

    public static final String REQUEST_FORMAT_VALUE = "json";

    public static final String REQUEST_SIGN_METHOD_VALUE = "md5";

    public static final String REQUEST_APPID_VALUE = "10106";

    public static final String ACCESS_TOKEN = "access_token";

    public static final String REQUEST_TOKEN_LAB = "token";

    public static final String EVENT_RUNNING_ACTION = "event.running.action";

    public static final int EVENT_RUNNING_TIME = 1000 * 60 * 2;  //1000 * 60 * 5

    public static final String LOCAL_VERIFICATION_STATE = "android";

    //服务器版本号
    public static final String REQUEST_SERVICE_VERSION = "app_version";
    //服务器版本值
    public static final String REQUEST_SERVICE_VERSION_VALUE = "6.0.2";
    //用户版本号
    public static final String VERSION_CODE = "3.2.7";

    public static final String WELCOME = "welcome";

    public static final String ACTION_UPDATEUI = "action.updateUI";
    // 微信应用的 appId
    public static String WX_APP_ID = "wx63bc4bff0e0494ae";
    // 微信应用的 appSecret
    public static String WX_SECRET = "482c9ab9bd1398ff669792cf7522ef92";
    //分页拉票的数量
    public static int size = 2000;

    public static final String WEIBO_APP_ID = "4040835298";

    public static final String WEIBO_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    public static final String WEIBO_SCOPE = "f1565d8fd2be08d8be26e00ce00a3c7e";

    public static final String JPUSH_MSG_ACTION = "com.eventmosh.evente.push_msg";

    public static final String JPUSH_MSG_HOLD_ACTION = "com.eventmosh.evente.push_hold_msg";

    public static final String LOGIN_ACTION = "com.eventmosh.evente.login";

    public static final String LOGOUT_ACTION = "com.eventmosh.evente.logout";

    public static final String APPLICATION_ID = "com.eventmosh.evente";

    //表单预览 线上
    public static final String FORM_PREVIEW_URL = "https://form.evente.cn/wap/app";
    //线下
    //public static final String FORM_PREVIEW_URL = "http://form.inner.evente.cn:8000/wap/app";




}
