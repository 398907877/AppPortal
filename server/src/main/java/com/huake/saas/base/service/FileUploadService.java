package com.huake.saas.base.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import com.huake.saas.util.RandomUtils;

//Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class FileUploadService {

	private static final  Logger logger = LoggerFactory
			.getLogger(FileUploadService.class);

	public static final String FILE_URL = "url";
	public static final String IMAGE_NEWS = "news";

	/**
	 * html 文件上传路径
	 */
	@Value("#{envProps.htmlpath}")
	private String filePath;
	/**
	 * html 文件访问 代理路径前缀
	 */
	@Value("#{envProps.newsUrl}")
	private String htmlurl;

	public Map<String, String> uploadNewsHtml(File file, Long id)
			throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		StringBuilder fileName = new StringBuilder(sdf.format(new Date()));
		fileName.append(RandomUtils.get(ImageUploadService.NUM_FIVE));
		SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
		SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
		String path =  sdfYear.format(new Date()) + "/"
				+ sdfMonth.format(new Date())+"/"+ sdfDay.format(new Date())+"/"+id;
		String uploadDir = filePath +"/" + IMAGE_NEWS + "/"+ path;
		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		// 确认系统的文件路径分隔符（dos系统是“\” unix系统是“/”）
		String sep = System.getProperty("file.separator");
		if (logger.isDebugEnabled()) {
			logger.debug("uploading to: " + uploadDir + sep + file.getName());
		}
		File uploadedFile = new File(uploadDir + sep +file.getName());// 这里从file中取出文件名
		FileCopyUtils.copy(file, uploadedFile); // 拷贝指定的文件流
		System.out.println(uploadedFile.getAbsolutePath());
		String finishPath = htmlurl + path + "/" + "news.html";
		Map<String, String> result = new HashMap<String, String>();
		result.put(FILE_URL, finishPath);
		return result;                       
	}
	
	
	/** 
	 *  根据路径删除指定的目录或文件，无论存在与否 
	 *@param sPath  要删除的目录或文件 
	 *@return 删除成功返回 true，否则返回 false。 
	 */  
	public boolean DeleteFolder(String sPath) {  
	    boolean flag = false;  
	    File file = new File(sPath);  
	    // 判断目录或文件是否存在  
	    if (!file.exists()) {  // 不存在返回 false  
	        return flag;  
	    } else {  
	        // 判断是否为文件  
	        if (file.isFile()) {  // 为文件时调用删除文件方法  
	            return deleteFile(sPath);  
	        } else {  // 为目录时调用删除目录方法  
	            return deleteDirectory(sPath);  
	        }  
	    }  
	}  
	
	/** 
	 * 删除目录（文件夹）以及目录下的文件 
	 * @param   sPath 被删除目录的文件路径 
	 * @return  目录删除成功返回true，否则返回false 
	 */  
	public boolean deleteDirectory(String sPath) { 
		boolean flag = false;
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	    //如果dir对应的文件不存在，或者不是一个目录，则退出  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    flag = true;  
	    //删除文件夹下的所有文件(包括子目录)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //删除子文件  
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //删除子目录  
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //删除当前目录  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}  
	
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  

}
