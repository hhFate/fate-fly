package cn.reinforce.web.fly.service.impl;

import cn.reinforce.plugin.util.ImageUtil;
import cn.reinforce.plugin.util.TimeUtil;
import cn.reinforce.plugin.util.Tools;
import cn.reinforce.plugin.util.aliyun.OSSUtil;
import cn.reinforce.web.fly.common.Constants;
import cn.reinforce.web.fly.dao.ThemeMapper;
import cn.reinforce.web.fly.dao.ThemeRedisDao;
import cn.reinforce.web.fly.dao.ThemeTagMapper;
import cn.reinforce.web.fly.model.Param;
import cn.reinforce.web.fly.model.Theme;
import cn.reinforce.web.fly.model.ThemeTag;
import cn.reinforce.web.fly.model.User;
import cn.reinforce.web.fly.service.FileService;
import cn.reinforce.web.fly.service.ParamService;
import cn.reinforce.web.fly.service.ThemeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class ThemeServiceImpl implements ThemeService {

    @Autowired
    private ThemeMapper themeMapper;

    @Autowired
    private ThemeRedisDao themeRedisDao;

    @Autowired
    private ThemeTagMapper themeTagMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private ParamService paramService;


    @Override
    public Theme find(int id, boolean redisOpen) {
        if (redisOpen) {
            Theme theme = themeRedisDao.redisThemeFetch(id);
            if (theme == null) {
                Theme t = themeMapper.find(id);
                themeRedisDao.redisThemeUpdate(t);
                theme = t;
            }
            return theme;
        } else {
            return themeMapper.find(id);
        }

    }

    @Override
    public void save(Theme theme) {
        themeMapper.save(theme);
    }

    @Override
    public void crush(int themeId, boolean redisOpen) {
        if (redisOpen) {
            themeRedisDao.redisThemeDelete(themeId);
        }
        themeMapper.delete(themeId);
    }

    @Override
    public List<Theme> findAll(boolean isDelete) {
        return themeMapper.findAll(isDelete);
    }


    @Override
    public Theme update(Theme theme, boolean redisOpen) {
        themeMapper.update(theme);
        Theme t = themeMapper.find(theme.getId());
        if (redisOpen) {
            themeRedisDao.redisThemeUpdate(t);
        }
        return t;
    }

    @Override
    public List<Theme> pageByFid(int fid, int state, boolean timeOrder, boolean priority, boolean isDelete, int pageNum, int pageSize) {
        return themeMapper.pageByFid(fid, state, timeOrder, priority, isDelete, (pageNum - 1) * pageSize, pageSize);
    }

    @Override
    public long countByFid(int fid, int state, boolean isDelete) {
        return themeMapper.countByFid(fid, state, isDelete);
    }


    @Override
    public Theme getLastestTheme(int fid) {
        List<Theme> list = themeMapper.pageByFid(fid, Theme.STATE_PUBLISH, true, false, false, 1, 1);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public long statistics(int fid, int datetype, String day) {
        return themeMapper.statistics(fid, datetype, day);
    }

    @Override
    public Theme findByDateAndTitle(String date, String title, boolean redisOpen) {
        String key = date + "-" + title;
        if (redisOpen) {
            Theme theme = themeRedisDao.redisThemeFetch(themeRedisDao.redisGuidFetch(key));
            if (theme == null) {
                theme = themeMapper.findByDateAndTitle(date, title);
                if (theme != null) {
                    themeRedisDao.redisGuidUpdate(key, theme.getId());
                }
            }
            return theme;
        } else {
            return themeMapper.findByDateAndTitle(date, title);
        }

    }

    @Override
    public List<Theme> pageHot(int pageNum, int pageSize) {
        return themeMapper.pageHot((pageNum - 1) * pageSize, pageSize);
    }

    @Override
    public long countViews(int fid, int state) {
        return themeMapper.countViews(fid, state);
    }

    @Override
    public List<Theme> pageSearchHot(int pageNum, int pageSize) {
        return themeMapper.pageSearchHot((pageNum - 1) * pageSize, pageSize);
    }

    @Override
    public List<Theme> pageByUid(String uid, boolean order, int pageNum, int pageSize) {
        return themeMapper.pageByUid(uid, order, (pageNum - 1) * pageSize, pageSize);
    }

    @Override
    public long countByUid(String uid) {
        return themeMapper.countByUid(uid);
    }

    @Override
    public List<Theme> pageByTag(String tag, int pageNum, int pageSize) {
        return themeMapper.pageByTag(tag, (pageNum - 1) * pageSize, pageSize);
    }

    @Override
    public long countByTag(String tag) {
        return themeMapper.countByTag(tag);
    }

    @Override
    public void multiDelete(List<Integer> ids) {
        themeMapper.multiDelete(ids);
    }

    @Override
    public Theme findLastTheme(int fid) {
        return themeMapper.findLastTheme(fid);
    }


    /**
     * 获取文章内容中的图片地址
     *
     * @param content
     * @return
     */
    @Override
    public String getImg(String content) {
        StringBuilder imgs = new StringBuilder();
        Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");//<img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
        Matcher m = p.matcher(content);
        Param ossUrl = paramService.findByKey(Constants.OSS_URL);
        Param on = paramService.findByKey(Constants.OSS_IMG_ON);
        Param imgUrl = paramService.findByKey(Constants.IMG_URL);
        Param thumbnail = paramService.findByKey(Constants.IMG_SERVICE_THUMBNAIL);
        if (on != null && on.getIntValue() == 1) {
            while (m.find()) {
                //转换成压缩后的图片域名
                String url = m.group(1);
//                if (url.lastIndexOf("-normal") > 0) {
//                    url = url.substring(0, url.lastIndexOf("-normal"));
//                }
                imgs.append(url.replace(ossUrl.getTextValue(), imgUrl.getTextValue()) + thumbnail.getTextValue()).append(",");
            }
        } else {
            while (m.find()) {
                imgs.append(m.group(1)).append(",");
            }
        }

        return imgs.toString();
    }

    /**
     * 替换文章内容中的图片地址为加速地址
     *
     * @param content
     * @return
     */
    @Override
    public String replaceImg(String content) {
        Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");//<img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
        Matcher m = p.matcher(content);
        Param fileStorageType = paramService.findByKey(Constants.FILE_STORAGE_TYPE);
        String tag = "-normal";
        while (m.find()) {
            //转换成压缩后的图片域名
            if (m.group(1).endsWith(tag)) {
                continue;
            }
            String url = m.group(1) + tag;
            content = content.replace(m.group(1), url);
        }

        return content;
    }

    @Override
    public String dealTags(String src, User user) {
        String result = "";
        /* 把文章的标签存入数据库以便下次直接点击 */
        if (!StringUtils.isEmpty(src) && !"".equals(src.trim())) {
            String[] tags = src.split(",");
            Set<String> set = new HashSet<>();
            set.addAll(Tools.asList(tags));
                /* 遍历标签，如果已存在则更新时间；不存在则创建 */
            for (String tag : tags) {
                if (themeTagMapper.findByNameAndUser(tag, user.getUid()) == null) {
                    ThemeTag themeTag = new ThemeTag();
                    themeTag.setTagName(tag.trim());
                    themeTag.setLastUsed(new Date());
                    themeTag.setUid(user.getUid());
                    themeTagMapper.save(themeTag);
                } else {
                    ThemeTag themeTag = themeTagMapper.findByNameAndUser(tag, user.getUid());
                    themeTag.setLastUsed(new Date());
                    themeTagMapper.update(themeTag);
                }
                result += tag + ",";
            }
            result = result.substring(0, result.lastIndexOf(","));
            return result;
        }
        return null;
    }

    /**
     * 检查内容中是否含有非本地图片
     * 有则下载下来，替换成本地链接
     *
     * @param content
     */
    @Override
    public String checkImgs(String content, HttpServletRequest request) {
        Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");//<img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
        Matcher m = pattern.matcher(content);
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        String time = TimeUtil.formatDate(now, "yyyyMM/dd");


        while (m.find()) {
            String url = m.group(1);

            //判断图片是否属于OSS
            Param ossBucket = paramService.findByKey(Constants.OSS_BUCKET);
            Param ossUrl = paramService.findByKey(Constants.OSS_URL);
            Param ossRegion = paramService.findByKey(Constants.OSS_REGION);
            Param fileStorageType = paramService.findByKey(Constants.FILE_STORAGE_TYPE);

            String endpoint = OSSUtil.getUrl(ossRegion.getTextValue(), ossUrl.getTextValue(), ossBucket.getTextValue(), false);
            if (url.contains("://") && !url.contains(endpoint)) {
                String originUrl = url;
                if (url.lastIndexOf("?") >= 0) {
                    url = url.substring(0, url.lastIndexOf("?"));
                }
                String filetype = url.substring(url.lastIndexOf(".") + 1);
                if (StringUtils.isEmpty(filetype) || !ImageUtil.isImage(filetype)) {
                    filetype = "jpg";
                }

                String filename = url.substring(url.lastIndexOf("/") + 1);
                if (!filename.contains(".")) {
                    filename += "." + filetype;
                }
                String dir = "file";
                if (ImageUtil.isImage(filetype)) {
                    dir = "images";
                }

                dir = "forum/" + time + "/" + dir + "/";
                String newUrl = fileService.uploadOnlineFile(url, dir, filename, fileStorageType.getIntValue(), request);
                content = content.replace(originUrl, newUrl);
            }

        }
        return content;
    }
}
