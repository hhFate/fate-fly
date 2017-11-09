package cn.reinforce.web.fly.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @author Fate
 * @create 2017/7/13
 */
public class FfmpegUtil {

    public static void getFirstImage(String src, String target) throws IOException {
        Runtime rn = Runtime.getRuntime();
        ClassPathResource classPathResource = new ClassPathResource("log4j.properties");
        String filePath = classPathResource.getFile().getAbsolutePath();
        filePath = filePath.substring(0, filePath.lastIndexOf("\\")+1);

        if(System.getProperty("os.name").startsWith("Win")||System.getProperty("os.name").startsWith("win")){
            System.out.println("\""+filePath+"ffmpeg/bin/ffmpeg.exe\""+" -i "+src+" -y -f image2 -ss 0 -t 0.001 "+target);
            rn.exec("cmd /c \""+filePath+"ffmpeg/bin/ffmpeg.exe\""+" -i "+src+" -y -f image2 -ss 0.5 -t 0.001 "+target);
        } else {
            rn.exec("ffmpeg -i "+src+" -y -f image2 -ss 0.5 -t 0.001 "+target);
        }
    }
}
