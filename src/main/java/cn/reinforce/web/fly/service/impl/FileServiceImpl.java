package cn.reinforce.web.fly.service.impl;

import cn.reinforce.plugin.util.aliyun.Aliyun;
import cn.reinforce.plugin.util.aliyun.OSSUtil;
import cn.reinforce.web.fly.common.Constants;
import cn.reinforce.web.fly.service.FileService;
import cn.reinforce.web.fly.service.ParamService;
import cn.reinforce.web.fly.util.FfmpegUtil;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fate on 2016/8/26.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    // 设置每块为 500K
    private static final long PART_SIZE = 1024 * 500;

    @Autowired
    private ParamService paramService;

    @Override
    public String upload(MultipartFile uploadFile, String dir, String filename, int type, HttpServletRequest request) {
        String url = null;
        switch (type) {
            case 1:
                // 上传到本地
                String relpath = paramService.findByKeyNotNull(Constants.FILE_ENDPOINT).getTextValue();
                dir = dir.substring(0, dir.indexOf("/"));
                File dirs = new File(relpath + dir);
                // 如果目录不存在，则创建目录
                if (!dirs.exists()) {
                    dirs.mkdirs();
                }
                String uppath = dir + File.separator + filename;
                File newFile = new File(relpath + uppath);
                try {
                    uploadFile.transferTo(newFile);
                    url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/op/file/" + dir + "/" + filename;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                // 上传到OSS
                String ossBucket = paramService.findByKey(Constants.OSS_BUCKET).getTextValue();
                String ossUrl = paramService.findByKey(Constants.OSS_URL).getTextValue();
                String ossRegion = paramService.findByKey(Constants.OSS_REGION).getTextValue();
                if (uploadFile.getSize() > PART_SIZE) {
                    Map<String, Object> map = new HashMap<>();
                    try {
                        OSSUtil.multipartUpload(ossBucket, uploadFile, dir, filename);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    PutObjectResult result = null;
                    try {
                        result = OSSUtil.simpleUpload(ossBucket, uploadFile, dir, filename);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (result == null) {
                        return null;
                    }
                }
                url = OSSUtil.getUrl(ossRegion, null, ossBucket, false) + dir + filename;
                break;
        }
        return url;
    }

    @Override
    public String upload(InputStream is, String dir, String filename, int type) {
        String url = null;
        switch (type) {
            case 1:
                // 上传到本地
                String relpath = paramService.findByKeyNotNull(Constants.FILE_ENDPOINT).getTextValue();
                File dirs = new File(relpath + dir);
                // 如果目录不存在，则创建目录
                if (!dirs.exists()) {
                    dirs.mkdirs();
                }
                String uppath = dir + File.separator + filename;
                File newFile = new File(relpath + uppath);
//                try {
//                    FileWriter fw = new FileWriter(newFile);
//                    fw.
//                    url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/op/file/" + dir + "/" + filename;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                break;
            case 2:
                // 上传到OSS
                String ossBucket = paramService.findByKey(Constants.OSS_BUCKET).getTextValue();
                String ossUrl = paramService.findByKey(Constants.OSS_URL).getTextValue();
                String ossRegion = paramService.findByKey(Constants.OSS_REGION).getTextValue();
                PutObjectResult result = OSSUtil.simpleUpload(ossBucket, is, dir, filename);
                if (result == null) {
                    return null;
                }
                url = OSSUtil.getUrl(ossRegion, null, ossBucket, false) + dir + filename;
                break;
        }
        return url;
    }

    @Override
    public String upload(File uploadFile, String dir, String filename, int type, HttpServletRequest request) {
        String url = null;
        switch (type) {
            case 1:
                // 上传到本地
                String relpath = paramService.findByKeyNotNull(Constants.FILE_ENDPOINT).getTextValue();
                File dirs = new File(relpath + dir);
                // 如果目录不存在，则创建目录
                if (!dirs.exists()) {
                    dirs.mkdirs();
                }
                String uppath = dir + File.separator + filename;
                File newFile = new File(relpath + uppath);
                uploadFile.renameTo(newFile);
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/op/file/" + dir + "/" + filename;
                break;
            case 2:
                // 上传到OSS
                String ossBucket = paramService.findByKey(Constants.OSS_BUCKET).getTextValue();
                String ossUrl = paramService.findByKey(Constants.OSS_URL).getTextValue();
                String ossRegion = paramService.findByKey(Constants.OSS_REGION).getTextValue();
                PutObjectResult result = OSSUtil.simpleUpload(ossBucket, uploadFile, dir, filename);
                if (result == null) {
                    return null;
                }
                url = OSSUtil.getUrl(ossRegion, null, ossBucket, false) + dir + filename;
                break;
        }
        return url;
    }

    @Override
    public String upload(byte[] file, String dir, String filename, int type, HttpServletRequest request) {
        String url = null;
        switch (type) {
            case 1:
                // 上传到本地
                String relpath = paramService.findByKeyNotNull(Constants.FILE_ENDPOINT).getTextValue();
                File dirs = new File(relpath + dir);
                // 如果目录不存在，则创建目录
                if (!dirs.exists()) {
                    dirs.mkdirs();
                }
                String uppath = dir + File.separator + filename;
                File newFile = new File(relpath + uppath);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(newFile);
                    fos.write(file);
                    url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/op/file/" + dir + "/" + filename;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case 2:
                // 上传到OSS
                String ossBucket = paramService.findByKey(Constants.OSS_BUCKET).getTextValue();
                String ossUrl = paramService.findByKey(Constants.OSS_URL).getTextValue();
                String ossRegion = paramService.findByKey(Constants.OSS_REGION).getTextValue();
                System.out.println(file == null);
                OSSUtil.byteUpload(ossBucket, file, dir, filename);
                url = OSSUtil.getUrl(ossRegion, null, ossBucket, false) + dir + filename;
                break;
        }
        return url;
    }

    @Override
    public String uploadOnlineFile(String url, String dir, String filename, int type, HttpServletRequest request) {
        filename = dir + filename;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        get.addHeader("Content-Type", "text/html;charset=UTF-8");
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity responseEntity = response.getEntity();

        switch (type) {
            case 1:
                // 上传到本地
//                GlobalSetting setting = GlobalSetting.INSTANCE;
//                String relpath = setting.getFileEndpoint();
//                File dirs = new File(relpath + dir);
//                // 如果目录不存在，则创建目录
//                if (!dirs.exists()) {
//                    dirs.mkdirs();
//                }
//                String uppath = dir + File.separator + filename;
//                File newFile = new File(relpath + uppath);
//                uploadFile.renameTo(newFile);
//                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/op/file/" + dir + "/" + filename;
                break;
            case 2:
                InputStream content = null;
                PutObjectResult result = null;
                try {
                    String ossBucket = paramService.findByKey(Constants.OSS_BUCKET).getTextValue();
                    String ossUrl = paramService.findByKey(Constants.OSS_URL).getTextValue();
                    String ossRegion = paramService.findByKey(Constants.OSS_REGION).getTextValue();
                    content = responseEntity.getContent();
                    ObjectMetadata meta = new ObjectMetadata();
                    meta.setContentLength(responseEntity.getContentLength());
                    result = Aliyun.INSTANCE.getOSSClient().putObject(ossBucket, filename, content, meta);
                    url = OSSUtil.getUrl(ossRegion, null, ossBucket, false) + dir + filename;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return url;
    }

    @Override
    public String getPreview(MultipartFile uploadFile, String dir, String current, HttpServletRequest request) throws IOException {
        String filename = uploadFile.getOriginalFilename();// 获取文件名
        String filetype = filename.substring(filename.lastIndexOf("."));


        //如果是视频，则需要截取他的第一帧
        String relpath = paramService.findByKeyNotNull(Constants.FILE_ENDPOINT).getTextValue();
        String videoPath = relpath + "ffmpeg" + File.separator + current + filetype;
        String imagePath = relpath + "ffmpeg" + File.separator + current + ".png";


        File folder = new File(relpath + File.separator + "ffmpeg");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(videoPath);
        uploadFile.transferTo(file);
        FfmpegUtil.getFirstImage(videoPath, imagePath);
        File image = new File(imagePath);
        while (!image.exists()) {//等待图片生成
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return upload(image, dir, current + ".png", paramService.findByKeyNotNull(Constants.FILE_STORAGE_TYPE).getIntValue(), request);
    }
}
