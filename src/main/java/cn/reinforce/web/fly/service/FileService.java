package cn.reinforce.web.fly.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Fate on 2016/8/26.
 */
public interface FileService {

    /**
     * 表单上传的文件
     * @param uploadFile
     * @param dir
     * @param filename
     * @param type 1.普通 2.阿里云 3.七牛
     * @param request
     * @return
     */
    public String upload(MultipartFile uploadFile, String dir, String filename, int type, HttpServletRequest request);

    public String upload(InputStream is, String dir, String filename, int type);

    /**
     * 以byte数组上传的文件
     * @param file
     * @param dir
     * @param filename
     * @param type 1.普通 2.阿里云 3.七牛
     * @param request
     * @return
     */
    public String upload(byte[] file, String dir, String filename, int type, HttpServletRequest request);

    public String upload(File uploadFile, String dir, String filename, int type, HttpServletRequest request);

    public String uploadOnlineFile(String url, String dir, String filename, int type, HttpServletRequest request);

    String getPreview(MultipartFile uploadFile, String dir, String current, HttpServletRequest request) throws IOException;
}
