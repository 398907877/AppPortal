package com.huake.saas.images;

import java.io.File;

import com.huake.saas.base.entity.Compress;

/**
 * 
 * @author laidingqing
 *
 */
public interface SavedImageCallback {
	 /**
     * 压缩图片，传入文件和压缩比例
     * 
     * @param file
     * @param compress
     * @return
     */
    public boolean imageCompressToFile(File file, Compress compress, String destPath);
}
