package cn.reinforce.web.fly.common;

/**
 * 101xx：文件相关异常
 * 102xx：登录&用户相关异常
 * 103xx：短信相关异常
 * 200xx：阿里云相关异常
 * 3xxxx：系统内应用相关异常
 * @author Fate
 * @create 2017/3/29
 */
public class ErrorCodes {

    /**
     * 授权失败/账号权限验证未通过
     */
    public static final int AUTH_FAIL = 10001;

    /**
     * 未知错误/新bug
     */
    public static final int UNKNOW_ERROR = 10002;

    /**
     * id无效
     */
    public static final int ID_ERROR = 10003;

    /**
     * 不属于自己的对象,对象所属人错误
     */
    public static final int BELONG_ERROR = 10004;

    /**
     * 时间格式化错误
     */
    public static final int DATE_FORMAT_ERROR = 10005;

    /**
     * 筛选时至少需要一个负责人
     */
    public static final int AT_LEAST_ONE_USRE = 1006;

    /**
     * xxx不存在
     */
    public static final int SOMETHING_NOT_EXIST = 10007;

    /**
     * 友链错误
     */
    public static final int FRIENDLINK_ERROR = 10008;

    /**
     * JSON解析异常
     */
    public static final int JSON_ERROR = 10009;

    /**
     * 参数错误
     */
    public static final int PARAM_ERROR = 10010;

    /**
     * 不能包含emoji
     */
    public static final int CONTAIN_EMOJI_ERROR = 10011;


    /**
     * 文件格式错误
     */
    public static final int FILE_TYPE_ERROR = 10101;

    /**
     * 文件IO异常
     */
    public static final int FILE_IO_ERROR = 10102;

    /**
     * 文件或文件夹不存在
     */
    public static final int FILE_NOT_EXIST = 10103;

    /**
     * 文件名编码异常
     */
    public static final int FILE_NAME_ENCODE_ERROR = 10104;

    /**
     * 文件夹已存在
     */
    public static final int FOLDER_ALREADY_EXIST = 10105;

    /**
     * 文件夹创建失败
     */
    public static final int FOLDER_CREATE_ERROR = 10106;

    /**
     * 空指针
     */
    public static final int NULL_ERROR = 10107;

    /**
     * 正则表达式验证失败
     */
    public static final int REGULAR_EXPRESSION_ERROR = 10108;

    /**
     * 文本内容为空
     */
    public static final int TEXT_EMPTY = 10109;

    /**
     * 非法操作
     */
    public static final int ILLEAGE_OPT = 10110;


    /**
     * 用户不存在
     */
    public static final int USER_NOT_EXIST = 10201;

    /**
     * 用户名不存在
     */
    public static final int LOGINNAME_NOT_EXIST = 10202;

    /**
     * 不允许修改密码
     */
    public static final int PASSWORD_CHANGE_NOT_ALLOW = 10203;

    /**
     * 验证码错误
     */
    public static final int VERIFICATION_CODE_ERROR = 10204;

    /**
     * 密码错误
     */
    public static final int PASSWORD_ERROR = 10205;

    /**
     * 快速登录Token错误
     */
    public static final int ACCESS_TOKEN_ERROR = 10206;

    /**
     * 登出失败
     */
    public static final int LOGOUT_ERROR = 10207;

    /**
     * 用户名已存在
     */
    public static final int USERNAME_ALREADY_EXIST = 10208;

    /**
     * 权限不足
     */
    public static final int PERMISSION_ERROR = 10209;

    public static final int USER_VERIFY_FAIL = 10210;


    /**
     * 短信验证码错误
     */
    public static final int SMS_CODE_ERROR = 10301;

    /**
     * 短信验证码超时
     */
    public static final int SMS_CODE_TIME_OUT = 10302;

    /**
     * 短信验证码不存在
     */
    public static final int SMS_CODE_NOT_EXIST = 10303;

    /**
     * 手机号为空
     */
    public static final int MOBILE_IS_NULL = 10304;

    /**
     * 手机号已存在
     */
    public static final int MOBILE_ALREADY_EXIST = 10305;








    /**
     * OSS解析异常
     */
    public static final int OSS_ERROR = 20001;

    /**
     * OSS连接异常
     */
    public static final int OSS_CONNECT_ERROR = 20002;


    /**
     * 群组人员已满
     */
    public static final int GROUP_FULL = 30001;

    /**
     * 群组不存在
     */
    public static final int GROUP_NOT_EXIST = 30002;

    /**
     * 版块不存在
     */
    public static final int FORUM_NOT_EXIST = 30101;

    /**
     * 版块已存在
     */
    public static final int FORUM_ALREADY_EXIST = 30102;

    /**
     * 模块不存在
     */
    public static final int MODULE_NOT_EXIST = 30201;

    /**
     * 地区找不到
     */
    public static final int LOCATION_NOT_FOUND = 30301;

    /**
     * 好友请求已存在
     */
    public static final int FRIEND_REQUEST_EXIST = 30401;

    public static final int FRIEND_REQUEST_NOT_EXIST = 30402;

    /**
     * 重复点赞
     */
    public static final int LIKE_TWICE = 30501;

    /**
     * 工作圈不存在
     */
    public static final int MOMENT_NOTEXIST = 30502;

    /**
     * 公司请求已存在
     */
    public static final int COMPANY_NOT_EXIST = 30601;

    public static final int COMPANY_REQUEST_EXIST = 30602;

    public static final int COMPANY_REQUEST_NOT_EXIST = 30603;

    public static final int COMPANY_EXIST = 30604;

    /**
     * 应用相关
     */
    public static final int APP_SECRET_ERROR = 30701;

    /**
     * 邮箱尚未设置
     */
    public static final int MAIL_NOT_SET = 30801;

    /**
     * 邮件格式错误
     */
    public static final int MAIL_FORMAT_ERROR = 30802;

    public static final int MAIL_NOT_EXIST = 30803;

    public static final int MAIL_CONNECT_ERROR = 30804;

    /**
     * 邮箱正在同步中
     */
    public static final int MAIL_SYNING = 30805;

    public static final int EXPRESS_NOT_EXIST= 30901;

    public static final int EXPRESS_STATUS_ERROR = 30902;

    /**
     * 字典表已存在
     */
    public static final int DIC_ALREADY_EXIST = 31001;

    public static final int DIC_NOT_EXIST = 31002;

    public static final int ADVICE_NOT_EXIST = 31101;

    /**
     * 订单不存在
     */
    public static final int ORDER_NOT_EXIST = 31201;

    public static final int MONEY_NOT_ENOUGH = 31301;

    public static final int RED_NOT_ENOUGH = 31302;

    public static final int RED_ALREADY_SNATCH = 31303;
}
