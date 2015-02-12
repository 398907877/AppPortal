package com.huake.saas.files;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件服务
 * @author zhangjiayong
 *
 */
public interface FilesUploadService {
	/**
	 * 保存文件
	 * @param fileMap 文件
	 * @param subDir 模块目录名
	 * @return 文件的保存路径
	 */
	public String saveFile(Map<String,MultipartFile> fileMap,String subDir);
}
