package com.huake.saas.files;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.huake.saas.base.service.ServiceException;
import com.huake.saas.images.ImagesUploadServiceImpl;

@Component
public class FilesUploadServiceImpl implements FilesUploadService{
	private Logger logger = LoggerFactory.getLogger(ImagesUploadServiceImpl.class);
	/**
	 * 注入文件存储路径
	 */
	@Value("#{envProps.app_files_path}")
	private String filePath;
	
	@Override
	public String saveFile(Map<String, MultipartFile> fileMap, String subDir) {
		StringBuffer sb = new StringBuffer();
    	StringBuffer extPath = new StringBuffer();
    	extPath.append("/").append(subDir).append("/").append(generateDistPathEnd());
    	sb.append(filePath).append(extPath);
    	File dir = new File(sb.toString());//存储目录
        if (!dir.exists()) {
        	dir.mkdirs();
        }
        try {
        	String filesPath = "";
        	if(fileMap != null){
        		for(Map.Entry<String, MultipartFile> entity : fileMap.entrySet()){
        			MultipartFile mf = entity.getValue();   
        			StringBuffer extention = new StringBuffer(getOriginFileExtention(mf));
        			StringBuffer fullPath = new StringBuffer(extPath.toString()).append("/").append(generateDistName()).append("_"+getOriginFileName(mf)).append(".").append(extention);
                    StringBuffer fullPathString = new StringBuffer();
                    fullPathString.append(filePath).append(fullPath.toString());
                    
                   // File uploadFile = new File(fullPathString.toString()); 
                    FileOutputStream fos = new FileOutputStream(new File( fullPathString.toString()));
                    
                    IOUtils.copy(mf.getInputStream(), fos);
                    try{
                        fos.close();
                        filesPath += fullPath.toString() + ",";
                    }catch(Exception e){
                    	logger.error("上传文件异常：", e);
                        throw new ServiceException("upload Exception:", e);
                    }
                  /*  try {  
                        FileCopyUtils.copy(mf.getBytes(), uploadFile);  
                        filesPath += "上传成功" + ",";  
                    } catch (IOException e) {  
                    	logger.error("上传文件异常：", e);
                        throw new ServiceException("upload Exception:", e);
                    } */
        		}
        	}
        	return "".equals(filesPath) ? "" : filesPath.substring(0,filesPath.length()-1);
        	
		} catch (Exception e) {
			logger.error("上传文件异常：", e);
            throw new ServiceException("upload Exception:", e);
		}
        
	}
	/**
	 * 生成文件名及目录
	 * @return
	 */
	private  String generateDistPathEnd(){
		Calendar cal = Calendar.getInstance();
        Integer year = cal.get(Calendar.YEAR);
        Integer month = cal.get(Calendar.MONTH) + 1;
        Integer day = cal.get(Calendar.DAY_OF_MONTH);
        String distPathEnd = year + "/" + month + "/" + day;
        return distPathEnd;
	}
	
	/**
	 * 生成文件名
	 * @return
	 */
	private  String generateDistName(){
		Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        Integer year = cal.get(Calendar.YEAR);
        Integer month = cal.get(Calendar.MONTH) + 1;
        Integer day = cal.get(Calendar.DAY_OF_MONTH);
		String name = year + "_" + month + "_" + day + "_" + date.getTime();
		return name;
	}
	
	/**
	 * 获取原始文件的扩展名
	 * @param file
	 * @return
	 */
	private String getOriginFileExtention(MultipartFile file){
		return file.getOriginalFilename().split("\\.")[file.getOriginalFilename().split("\\.").length - 1];
	}
	/**
	 * 获得原始文件的文件名
	 * @param file
	 * @return
	 */
	private String getOriginFileName(MultipartFile file){
		return file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
	}
}
