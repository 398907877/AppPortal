package com.huake.saas.util;

import java.io.File;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileUtil {
	/** 
     * Return the extension portion of the file's name . 
     * 
     * @see #getExtension 
     */ 
    public static String getExtension(File f) { 
        return (f != null) ? getExtension(f.getName()) : ""; 
    } 

    public static String getExtension(String filename) { 
        return getExtension(filename, ""); 
    } 

    public static String getExtension(String filename, String defExt) { 
        if ((filename != null) && (filename.length() > 0)) { 
            int i = filename.lastIndexOf('.'); 

            if ((i >-1) && (i < (filename.length() - 1))) { 
                return filename.substring(i + 1); 
            } 
        } 
        return defExt; 
    } 

    public static String trimExtension(String filename) { 
        if ((filename != null) && (filename.length() > 0)) { 
            int i = filename.lastIndexOf('.'); 
            if ((i >-1) && (i < (filename.length()))) { 
                return filename.substring(0, i); 
            } 
        } 
        return filename; 
    } 
    
    /**
     * 通用文件请求头解析
     * @param request
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static List processServletRequest(final MultipartHttpServletRequest request) {
    	if (ServletFileUpload.isMultipartContent(request)) {

    	    final FileItemFactory factory = new DiskFileItemFactory();
    	    final ServletFileUpload upload = new ServletFileUpload(factory);
    	    upload.setHeaderEncoding("utf-8");
    	    List<FileItem> items = null;
    	    try {
    	      items = upload.parseRequest(request);
    	      return items;
    	    } catch (final FileUploadException e) {
    	        return null;
    	    }

    	}
    	return null;
    }
}
