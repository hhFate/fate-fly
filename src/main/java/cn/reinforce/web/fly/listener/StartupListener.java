package cn.reinforce.web.fly.listener;

import cn.reinforce.plugin.util.aliyun.Aliyun;
import cn.reinforce.web.fly.common.Constants;
import cn.reinforce.web.fly.model.Param;
import cn.reinforce.web.fly.service.ParamService;
import cn.reinforce.web.fly.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * spring加载完成时执行的方法，用于初始化一些参数，启动线程等等
 *
 * @author 幻幻
 */
@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = Logger.getLogger(StartupListener.class);

    /**
     * 轮询的间隔时长
     */
    private static final int PERIOD = 3000;

//    @Autowired
//    private ForumService forumService;

//    @Autowired
//    private ThemeService themeService;
    //
//    @Autowired
//    private ThemeTagService themeTagService;

    @Autowired
    private UserService userService;

    @Autowired
    private ParamService paramService;

//    @Autowired
//    private SecurityVerificationService securityVerificationService;

//    @Autowired
//    private AnnouncementService announcementService;

    //    @Autowired
//    private AdvertisementService advertisementService;
//
//    @Autowired
//    private ThirdPartyAccessService thirdPartyAccessService;
    //
//    @Autowired
//    private DuoShuoService duoShuoService;
//

//    @Autowired
//    private NaviService naviService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {
        initOthers();
//        initFileEndpoint();
//        friendLinkCheck();
        Param aliyunUsed = paramService.findByKeyNotNull(Constants.ALIYUN_USED);
        if (aliyunUsed != null && aliyunUsed.getIntValue() == 1) {
            initOSS();
        }
    }

    /**
     * 初始化获取根目录
     */
//    public void initFileEndpoint() {
//        LOG.info("---------------------获取程序所在根目录-----------------");
//        GlobalSetting globalSetting = GlobalSetting.INSTANCE;
//        String endpoint = System.getProperty("sso.root");
//        endpoint = endpoint.substring(0, endpoint.indexOf(File.separator) + 1) + "download" + File.separator;
//        globalSetting.setFileEndpoint(endpoint);
//    }
    public void initOthers() {
//        Param logId = paramService.findByKey(Constants.LOG_ID);
//        if (logId == null) {
//            logId = new Param();
//            logId.setType(Param.TYPE_TEXT);
//            logId.setKey(Constants.LOG_ID);
//            logId.setTextValue("0");
//            logId = paramService.update(logId);
//        }
    }

    /**
     * 初始化阿里云的oss
     */
    public void initOSS() {
        Param aliyunUsed = paramService.findByKeyNotNull(Constants.ALIYUN_USED);
        if (aliyunUsed.getIntValue() == 1) {
            Param accessKeyId = paramService.findByKeyNotNull(Constants.ALIYUN_ACCESS_KEY_ID);
            Param accessKeySecret = paramService.findByKeyNotNull(Constants.ALIYUN_ACCESS_KEY_SECRET);
            Aliyun aliyun = Aliyun.INSTANCE;

            aliyun.init(accessKeyId.getTextValue(), accessKeySecret.getTextValue());

            Param ossRegion = paramService.findByKeyNotNull(Constants.OSS_REGION);
            Param ossBucket = paramService.findByKeyNotNull(Constants.OSS_BUCKET);
            Param ossUrl = paramService.findByKeyNotNull(Constants.OSS_URL);
            Param ossNet = paramService.findByKeyNotNull(Constants.OSS_NET);
            if (ossRegion != null && !StringUtils.isEmpty(ossRegion.getTextValue())) {
                aliyun.initOSS(ossRegion.getTextValue(), ossNet.getIntValue());
            }

            Param openSearchEndpoint = paramService.findByKeyNotNull(Constants.OPENSEARCH_ENDPOINT);
            Param openSearchAppName = paramService.findByKeyNotNull(Constants.OPENSEARCH_APPNAME);
            if (!StringUtils.isEmpty(openSearchEndpoint.getTextValue())) {
                aliyun.initOpenSearch(openSearchEndpoint.getTextValue(), openSearchAppName.getTextValue());
            }

            aliyun.initSms();
        }
    }

//    /**
//     * 刷新人员在线情况
//     */
//    @Scheduled(cron = "0 */1 * * * ?")
//    public void refreshOnline() {
//        initGlobalSetting();
//    }

    /**
     * 清理无效的验证码
     */
//    @Scheduled(cron = "0 */30 * * * ?")
//    public void cleanSecurity() {
//        LOG.info("-----------开始清理无效的验证码-----------");
//        securityVerificationService.deleteOutOfTime();
//    }


    /**
     * 更新微信的AccessToken
     */
//    @Scheduled(cron = "0 */60 * * * ?")
//    private void refreshWeixinAccessToken() {
//        LOG.info("---------------------开始更新微信的AccessToken-----------------");
//        GlobalSetting setting = GlobalSetting.INSTANCE;
//        WxMpService service = setting.getWxMpService();
//        try {
//            paramService.updateParam(Constants.WEIXIN_ACCESS_TOKEN, service.getAccessToken(), Param.TYPE_TEXT);
//        } catch (WxErrorException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

}
