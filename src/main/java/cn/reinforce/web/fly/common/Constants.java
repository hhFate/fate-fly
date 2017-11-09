package cn.reinforce.web.fly.common;


public class Constants {

	
	private Constants() {
		super();
	}


	/**
	 * 手机验证码超时时间
	 */
	public static final int MOBILE_TIMEOUT=10;
	
	/**
	 * 邮箱验证超时时间
	 */
	public static final int EMAIL_TIMEOUT=12*60;
	

    public static final String ALIYUN_ACCESS_KEY_ID = "aliyun_access_key_id";
    
    public static final String ALIYUN_ACCESS_KEY_SECRET = "aliyun_access_key_secret";
    
    public static final String OSS_REGION = "oss_region";

    public static final String OSS_NET = "oss_net";

    public static final String OSS_BUCKET = "oss_bucket";

    /**
     * 是否使用图片加速
     */
    public static final String OSS_IMG_ON = "oss_img_on";

    public static final String OSS_URL = "oss_url";

    /**
     * 图片处理的域名
     */
    public static final String IMG_URL = "img_url";

    /**
     * 图片处理的服务名,缩略图
     */
    public static final String IMG_SERVICE_THUMBNAIL = "img_service_thumbnail";

    /**
     * 图片处理的服务名，普通
     */
    public static final String IMG_SERVICE_NORMAL = "img_service_normal";

    public static final String OPENSEARCH_ENDPOINT = "opensearch_endpoint";
    
    public static final String OPENSEARCH_APPNAME = "opensearch_appname";

    public static final String QINIU_ACCESS_KEY = "qiniu_access_key";

    public static final String QINIU_SECRET_KEY = "qiniu_secret_key";

    public static final String QINIU_URL = "qiniu_url";

    public static final String QINIU_BUCKET = "qiniu_bucket";
    
    public static final String SITE_MODE = "site_mode";
    
    public static final String SITE_NAME = "site_name";
    
    public static final String APP_NAME = "app_name";
    
    public static final String APP_URL = "app_url";

    public static final String APP_COPYRIGHT = "app_copyright";

    public static final String MESSAGE_STORAGE_URL = "message_storage_url";

    /**
     * 是否在创建不忙的时候，直接创建群组
     */
    public static final String DEPT_GROUP_AUTO_CREATE = "dept_group_auto_create";

    public static final String APP_EN_NAME = "app_en_name";
    
    public static final String ADMIN_EMAIL = "admin_email";
    
    public static final String ADMIN_MOBILE = "admin_mobile";

    public static final String PROTOCOL = "protocol";

    public static final String ICP = "icp";
    
    public static final String STATISTICS = "statistics";
    
    public static final String STATISTICSHEAD = "statistics_head";
    
    public static final String ALIYUN_USED = "aliyun_used";
    
    public static final String REG_ALLOW = "reg_allow";
    
    public static final String NEED_EMAIL_VERIFY = "need_email_verify";
    
    public static final String QQ = "qq";
    
    public static final String WEIBO = "weibo";
    
    public static final String SMTP_SERVER = "smtp_server";
    
    public static final String SMTP_FROM = "smtp_from";
    
    public static final String SMTP_USERNAME = "smtp_username";
    
    public static final String SMTP_PASSWORD = "smtp_password";
    
    public static final String SMTP_TIMEOUT = "smtp_timeout";
    
    public static final String GEETEST_ID = "geetest_id";
    
    public static final String GEETEST_KEY = "geetest_key";
    
    public static final String DUOSHUO_KEY = "duoshuo_key";
    
    public static final String DUOSHUO_SECRET = "duoshuo_secret";
    
    public static final String TX_APP_KEY = "tx_app_key";
    
    public static final String LOG_ID = "log_id";
    
    public static final String REDIS_OPEN = "redis_open";

    public static final String FILE_STORAGE_TYPE = "file_storage_type";

    public static final String FILE_ENDPOINT = "file_endpoint";

    public static final String SMS_KEY = "sms_key";

    public static final String SMS_MODEL_ID = "sms_model_id";

    public static final String COMPANY_MODEL_ID = "company_model_id";

    public static final String INVITE_MODEL_ID = "invite_model_id";

    /**
     * 融云appkey和appSecret
     */
    public static final String RONG_APP_KEY = "rong_app_key";
    public static final String RONG_APP_SECRET = "rong_app_secret";


    /**
     * 单点登录相关
     */
    public static final String SSO_ENDPOINT = "sso_endpoint";
    public static final String SSO_APP_KEY = "sso_app_key";
    public static final String SSO_APP_SECRET = "sso_app_secret";

    /*--------------------群组相关------------------------*/

    /**
     * 每个公司限制的群组数量
     */
    public static final String GROUP_NUMBER = "group_number";

    /**
     * 每个群组默认的人数
     */
    public static final String GROUP_USER_NUMBER = "group_user_number";


    public static final String SUCCESS = "success";
    
    public static final String ERROR_CODE = "error_code";
    
    public static final String REQUEST_ID = "request_id";
    
    public static final String MSG = "msg";
    
    /**
     * 微信相关
     */
    public static final String WEIXIN_APP_ID = "weixin_app_id";
    
    public static final String WEIXIN_APP_SECRET = "weixin_app_secret";
    
    public static final String WEIXIN_ACCESS_TOKEN = "weixin_access_token";

    /**
     * 安卓版本号
     */
    public static final String ANDRIOD_VERSION = "andriod_version";

    /**
     * 安卓版本号
     */
    public static final String ANDRIOD_VERSION_CODE = "andriod_version_code";

    /**
     * 安卓安装包路径
     */
    public static final String ANDRIOD_URL = "andriod_url";

    public static final String UPDATE_SET_FAIL = "设置保存失败";

    public static final String UPDATE_SET_SUCCESS = "设置保存成功";

    /**
     * 第三方登录
     */
    public static final String QQ_ACCESS_KEY = "qq_access_key";

    public static final String QQ_ACCESS_SECRET = "qq_access_secret";

    public static final String XINLANG_ACCESS_KEY = "xinlang_access_key";

    public static final String XINLANG_ACCESS_SECRET = "xinlang_access_secret";
}
