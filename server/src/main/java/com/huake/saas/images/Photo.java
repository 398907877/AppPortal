package com.huake.saas.images;


/**
 * 系统图片存储表
 * @author laidingqing
 *
 */
public class Photo {

	private String filePath;
	
	/**
	 * 图像类型，如origin:原始图, thumbnail:缩略图, small:小图，等
	 */
	private String imgType;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	
}
