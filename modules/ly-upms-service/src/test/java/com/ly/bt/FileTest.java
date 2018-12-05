package com.ly.bt;

import com.aliyun.oss.OSSClient;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @auther Administrator liyang
 * @create 2018/11/18 17:05
 */
public class FileTest {

	@Test
	public void testAliyunUpload() {

        String key = "LTAIFmokxi4gaorI";
        String secret = "aWjrphAVvzUvnQxCVq34yJwkmRzCZ1";


        //ossPropertiesConfig.getSecretId(), ossPropertiesConfig.getSecretKey()

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient("oss-cn-shanghai.aliyuncs.com", key, secret);

        // 上传网络流。
        try {
            File file = new File("C:\\Users\\Administrator\\Pictures\\358979416064468189.png");

            ossClient.putObject("cryptomeetup-img",createImagePath() , new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 关闭OSSClient。
        ossClient.shutdown();

	}

    public String createImagePath() {

        String key = String.format("%s/%s/%s.jpg",
                "test",
                dateFormat.format(new Date()),
                UUID.randomUUID().toString().replace("-", ""));

        return key;
    }

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

}
