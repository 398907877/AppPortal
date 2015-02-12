package com.huake.saas.service.images;

import com.huake.saas.BaseTransactionalTestCase;
import com.huake.saas.images.ImagesUploadService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * 上传图片测试.
 * User: laidingqing
 * Date: 14-4-23
 * Time: 下午3:22
 */
public class ImageUploadTest extends BaseTransactionalTestCase{

    @Autowired
    private ImagesUploadService imagesUploadService;

    @Test
    public void testUpload() throws Exception{
        StringBuffer sb = new StringBuffer();
        sb.append("/Users/laidingqing/www").append("/").append("events").append("/").append("2014/04/23");
        File saveFile = new File(sb.toString());
        if (!saveFile.exists()) {
            Boolean successed = saveFile.mkdirs();
            System.out.println(successed);
        }

    }
}
