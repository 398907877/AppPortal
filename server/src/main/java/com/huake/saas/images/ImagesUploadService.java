package com.huake.saas.images;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.huake.saas.base.entity.Compress;

/**
 * 新的图片处理服务，保存、裁减等
 * @author laidingqing
 *
 */
public interface ImagesUploadService {
	
    /**
     * 保存图片，根据compress不定参进行裁减
     * @param file 文件
     * @param subDir 模块目录名
     * @param compress
     * @return
     */
    public List<Photo> saveImage(CommonsMultipartFile file, String subDir, Compress...compress);
    
    
    /**
     * 保存多图片,根据compress不定参进行裁减
     * @param files 文件集合
     * @param subDir 模块目录名
     * @param compress
     * @return
     */
    public String saveImage(List<CommonsMultipartFile> files, String subDir, Compress...compress);
}
