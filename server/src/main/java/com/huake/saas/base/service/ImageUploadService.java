package com.huake.saas.base.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.huake.saas.user.entity.TenancyUser;
import com.huake.saas.util.FileUtil;
import com.huake.saas.util.RandomUtils;

//Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class ImageUploadService {

	final static Logger logger = LoggerFactory
			.getLogger(ImageUploadService.class);

	public static final String IMAGE_URL = "url";
	public static final String IMAGE_PRODUCT = "product";
	public static final String IMAGE_COMPANY = "company";
	public final static String IMAGE_MEMBER = "member";
	public static final String IMAGE_NEWS = "news";
	public static final String IMAGE_INVITATION = "invitation";
	public final static String PLAYER_AVATAR = "avatar";
	public final static String ORIGIN = "origin";
	
	public static final int NUM_FIVE = 5;


	@Value("#{envProps.app_images_path}")
	private String filePath;

	@Value("#{envProps.app_files_url_prefix}")
	private String imgPath;
	/*@Value("#{envProps.imgUrl}")
	private String imgUrl;*/

	/**
	 * 产品图片上传
	 * 
	 * @param file
	 * @param inputFileName
	 * @return
	 */
	public Map<String, String> uploadProductImage(CommonsMultipartFile file, String inputFileName) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
			StringBuilder fileName = new StringBuilder(sdf.format(new Date()));
			fileName.append(RandomUtils.get(NUM_FIVE));

			SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
			SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");

			String path = "/"+IMAGE_PRODUCT+"/" + sdfYear.format(new Date()) + "/" + sdfMonth.format(new Date());

			String fullName = fileName.toString()
					+ ".origin."
					+ file.getOriginalFilename().split("\\.")[file
							.getOriginalFilename().split("\\.").length - 1];

			String fullPath = filePath + path + "/" + fullName;

			File saveFile = new File(filePath + path);

			String xsName = fileName.append(".xs.").append(file.getOriginalFilename().split("\\.")[file
							.getOriginalFilename().split("\\.").length - 1])
					.toString();
			String xsPath = filePath + path + "/" + xsName;
			if (!saveFile.exists()) {
				saveFile.mkdirs();
			}
			file.getFileItem().write(new File(fullPath));
			Thumbnails.of(fullPath).size(200, 200).toFile(xsPath);
			String finPath =  path + "/" + xsName;
			Map<String, String> result = new HashMap<String, String>();
			result.put(IMAGE_URL, finPath);
			return result;
		} catch (Exception e) {
			logger.error("upload  planRecord of pic fail.........");
			return null;
		}
	}

	/**
	 * 产品简介，详细图片上传
	 * 
	 * @param file
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	public String upload(CommonsMultipartFile file, String callback)
			throws Exception {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
			StringBuilder fileName = new StringBuilder(sdf.format(new Date()));
			fileName.append(RandomUtils.get(NUM_FIVE));

			SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
			SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");

			String path =  "/detailPic" + "/" + sdfYear.format(new Date()) + "/"
					+ sdfMonth.format(new Date());

			String fullName = fileName.toString()
					+ ".origin."
					+ file.getOriginalFilename().split("\\.")[file
							.getOriginalFilename().split("\\.").length - 1];

			String fullPath = filePath + path + "/" + fullName;

			File saveFile = new File(filePath + path);

			if (!saveFile.exists()) {
				saveFile.mkdirs();
			}

			file.getFileItem().write(new File(fullPath));

			String finPath = imgPath +  path + "/" + fullName;

			return "<script type='text/javascript'>"
					+ "window.parent.CKEDITOR.tools.callFunction(" + callback
					+ ",'" + finPath + "',''" + ");</script>";
		} catch (Exception e) {
			logger.error("file upload exception:", e);
			return "false";
		}
	}

	/**
	 * 新闻资讯图片上传
	 * 
	 * @param file
	 * @param inputFileName
	 * @return
	 */
	public Map<String, String> uploadNewsImage(CommonsMultipartFile file,String category) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
			StringBuilder fileName = new StringBuilder(sdf.format(new Date()));
			fileName.append(RandomUtils.get(NUM_FIVE));

			SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
			SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");

			String path = "/"+IMAGE_NEWS+"/" + sdfYear.format(new Date())
					+ "/" + sdfMonth.format(new Date());

			String fullName = fileName.toString()
					+ ".origin."
					+ file.getOriginalFilename().split("\\.")[file
							.getOriginalFilename().split("\\.").length - 1];

			String fullPath = filePath + path + "/" + fullName;

			File saveFile = new File(filePath + path);

			String xsName = fileName
					.append(".xs.")
					.append(file.getOriginalFilename().split("\\.")[file
							.getOriginalFilename().split("\\.").length - 1])
					.toString();
			String xsPath = filePath + path + "/" + xsName;
			if (!saveFile.exists()) {
				saveFile.mkdirs();
			}
			file.getFileItem().write(new File(fullPath));
			Thumbnails.of(fullPath).size(200, 200).toFile(xsPath);
			String finPath =  path + "/" + xsName;
			Map<String, String> result = new HashMap<String, String>();
			result.put(IMAGE_URL, finPath);
			return result;
		} catch (Exception e) {
			logger.error("upload  planRecord of pic fail.........");
			return null;
		}
	}

	/**
	 * 企业介绍图片上传
	 * 
	 * @param file
	 * @param inputFileName
	 * @return
	 */
	public Map<String, String> uploadCompanyImage(CommonsMultipartFile file,
			String inputFileName) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
			StringBuilder fileName = new StringBuilder(sdf.format(new Date()));
			fileName.append(RandomUtils.get(NUM_FIVE));

			SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
			SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");

			String path = "/"+IMAGE_COMPANY+"/" + sdfYear.format(new Date())
					+ "/" + sdfMonth.format(new Date());

			String fullName = fileName.toString()
					+ ".origin."
					+ file.getOriginalFilename().split("\\.")[file
							.getOriginalFilename().split("\\.").length - 1];

			String fullPath = filePath + path + "/" + fullName;

			File saveFile = new File(filePath + path);

			String xsName = fileName
					.append(".xs.")
					.append(file.getOriginalFilename().split("\\.")[file
							.getOriginalFilename().split("\\.").length - 1])
					.toString();
			String xsPath = filePath + path + "/" + xsName;
			if (!saveFile.exists()) {
				saveFile.mkdirs();
			}
			file.getFileItem().write(new File(fullPath));
			Thumbnails.of(fullPath).size(200, 200).toFile(xsPath);
			String finPath =  path + "/" + xsName;
			Map<String, String> result = new HashMap<String, String>();
			result.put(IMAGE_URL, finPath);
			return result;
		} catch (Exception e) {
			logger.error("upload  planRecord of pic fail.........");
			return null;
		}
	}
	
	/**
	 * 企业介绍图片上传
	 * 
	 * @param file
	 * @param inputFileName
	 * @return
	 */
	public Map<String, String> uploadInvitationImage(InputStream file,
			String inputFileName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		StringBuilder fileName = new StringBuilder(sdf.format(new Date()));
		fileName.append(RandomUtils.get(NUM_FIVE));
		SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");

		String path = "/" + IMAGE_INVITATION + "/" + sdfYear.format(new Date())
				+ "/" + sdfMonth.format(new Date());

		String fullName = fileName.append(".")
				.append(inputFileName.split("\\.")[1]).toString();

		String fullPath = filePath + path + "/" + fullName;

		File saveFile = new File(filePath + path);

		if (!saveFile.exists()) {
			saveFile.mkdirs();
		}

		String finishPath = imgPath+ path + "/" + fullName;

		Map<String, String> result = new HashMap<String, String>();
		result.put(IMAGE_URL, finishPath);
		try {
			FileOutputStream fos = new FileOutputStream(new File(fullPath));
			IOUtils.copy(file, fos);

		} catch (IOException e) {
			logger.error("file upload exception:", e);
		}

		return result;
	}
	
	/**
	 * 上传会员头像
	 * @param file
	 * @param inputFileName
	 * @param category
	 * @param member
	 * @return
	 * @throws AppBizException
	 */
	public Map<String, String> uploadMemberAvatar(InputStream file, String inputFileName, String category, TenancyUser member)  {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		StringBuilder fileName = new StringBuilder(sdf.format(new Date()));
		fileName.append( RandomUtils.get(5));
		
		SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
		

		String path =  "/"+ IMAGE_MEMBER + "/" + category + "/" + sdfYear.format(new Date()) + "/" + sdfMonth.format(new Date());

		String fullName = fileName.append(".").append(FileUtil.getExtension(inputFileName)).toString();
		
		String fullPath = filePath + path + "/" + fullName;
		
		File saveFile = new File(filePath + path);
		
		if(!saveFile.exists()){
			saveFile.mkdirs();
		}
		
		String finishPath = path + "/" + fullName;
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("origin", finishPath);
		try{
			FileOutputStream fos = new FileOutputStream(new File( fullPath));
			IOUtils.copy(file, fos);
			// TODO 需要裁减为相应大小如70*70
			try{
				fos.close();
			}catch(Exception e){
				
			}			
		}catch(IOException e){
			logger.error("file upload exception:", e);
		}
		
		return result;
	}
}
