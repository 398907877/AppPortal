package com.huake.saas.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ThumbnailUtil {
	private static final Logger logger = LoggerFactory.getLogger(ThumbnailUtil.class);

    /************************
     * 支持输出的图片流类型
     * 
     * @author Administrator
     */
    public static enum ImageType {
            BMP("bmp"), JPG("jpg"), WBMP("wbmp"), PNG("png"), JPEG("jpeg"), GIF("gif");
            public String extention;

            private ImageType(String extention) {
                    this.extention = extention;
            }
    }

    /*****************
     * 图片裁剪时的图片选取相对位置
     * 
     * @author Administrator
     * 
     */
    public static enum ImageCutPosition {
            TOP_LEFT(10), TOP_CENTER(13), TOP_RIGHT(15), CENTER_LEFT(20), CENTER(22), CENTER_RIGHT(24), BOTTOM_LEFT(30), BOTTOM_CENTER(33), BOTTOM_RIGHT(
                            35);

            public final Positions position;

            private ImageCutPosition(int v) {
                    Positions pos = null;
                    switch (v) {
                    case 10:
                            pos = Positions.TOP_LEFT;
                            break;
                    case 13:
                            pos = Positions.TOP_CENTER;
                            break;
                    case 15:
                            pos = Positions.TOP_RIGHT;
                            break;

                    case 20:
                            pos = Positions.CENTER_LEFT;
                            break;
                    case 22:
                            pos = Positions.CENTER;
                            break;
                    case 24:
                            pos = Positions.CENTER_RIGHT;
                            break;

                    case 30:
                            pos = Positions.BOTTOM_LEFT;
                            break;
                    case 33:
                            pos = Positions.BOTTOM_CENTER;
                            break;
                    default:
                            pos = Positions.BOTTOM_RIGHT;
                    }

                    position = pos;
            }
    }

    /************
     * <P>
     * 判断缩略图长宽是否正常
     * </P>
     * 
     * @param thumbMaxWidth
     * @param thumbMaxHeight
     * @return
     */
    private static final boolean isThumbSizeValid(final int thumbMaxWidth, final int thumbMaxHeight) {
            return thumbMaxWidth >= 2 && thumbMaxHeight >= 2;
    }

    /*********************
     * <P>
     * 参数是否存在为null
     * </P>
     * 
     * @param objs
     * @return
     */
    private static final boolean isParamObjectsNotNull(final Object... objs) {
            for (Object obj : objs) {
                    if (obj == null) {
                            return false;
                    }
            }
            return true;
    }

    /*************************
     * <P>
     * 对参数originFile指定的图片进行等比压缩，压缩后的图片会被保存到destFile, 注意: 1. destFile如果已经存在，
     * 就会直接覆盖，否则就会新建; 2. thumbMaxWidth,thumbMaxHeight是缩略图的最大高度和宽度，由于图片是等比压缩，
     * 生成的缩略图在等比压缩时，会保证宽度和高度在这个范围内。
     * 例如800*600的原图，thumbMaxWidth=120和thumbMaxHeight=120时，生成的缩略图为120×90 3.
     * 缩略图类型和原图一致，如果扩展名类型不一致，那么系统会自动在文件名后面加上指定类型扩展名，
     * 例如origin.jpg生成缩略图dest.bmp时，系统会输入到"dest.bmp.JPEG"文件
     * </P>
     * 
     * @param originFile
     *            ：图片必须可读
     * @param destFile
     *            ：图片必须可写，如果已经存在就会直接覆盖
     * @param thumbMaxWidth
     *            :缩略图最大宽度
     * @param thumbMaxHeight
     *            :缩略图最大高度
     * @return true-successfully generate thumb nail; false-failed to generate
     *         thumb nail
     */
    public static final boolean generateThumb(final File originFile, final File destFile, final int thumbMaxWidth, final int thumbMaxHeight,
                    final double quality) {
            if (!isParamObjectsNotNull(originFile, destFile) || !isThumbSizeValid(thumbMaxWidth, thumbMaxHeight)) {
                    logger.error("Fail to generateThumb: error params!");
                    return false;
            }
            try {
                    Thumbnails.of(originFile).size(thumbMaxWidth, thumbMaxHeight).outputQuality(quality).toFile(destFile);
                    return true;
            } catch (Exception ex) {
                    logger.error("Fail to generateThumb: ex=" + ex.getMessage(), ex);
                    return false;
            }
    }

    /********************
     * <P>
     * 和上述接口类似， 只是指定了目标图片文件的类型，需要保证目标文件的扩展名和指定destFileType参数是同一图片类型
     * </P>
     * 
     * @param originFile
     * @param destFile
     * @param destFileType
     * @param thumbMaxWidth
     * @param thumbMaxHeight
     * @return
     */
    public static final boolean generateThumb(final File originFile, final File destFile, final ImageType destFileType, final int thumbMaxWidth,
                    final int thumbMaxHeight) {
            if (!isParamObjectsNotNull(originFile, destFile, destFileType) || !isThumbSizeValid(thumbMaxWidth, thumbMaxHeight)) {
                    logger.error("Fail to generateThumb: error params!");
                    return false;
            }
            try {
                    Thumbnails.of(originFile).size(thumbMaxWidth, thumbMaxHeight).outputFormat(destFileType.extention).toFile(destFile);
                    return true;
            } catch (Exception ex) {
                    logger.error("Fail to generateThumb: ex=" + ex.getMessage(), ex);
                    return false;
            }
    }

    /*************************
     * <P>
     * 对参数originImageInputStream指定的图片进行等比压缩，压缩后的图片会被保存到destImageOutputStream,
     * 注意: 1. thumbMaxWidth,thumbMaxHeight是缩略图的最大高度和宽度，由于图片是等比压缩，
     * 生成的缩略图在等比压缩时，会保证宽度和高度在这个范围内。
     * 例如800*600的原图，thumbMaxWidth=120和thumbMaxHeight=120时，生成的缩略图为120×90 2.
     * 缩略图类型和原图一致 3. 实例: FileInputStream in = new FileInputStream(new
     * File("D:/TDDOWNLOAD/origin.jpg")); FileOutputStream out = new
     * FileOutputStream(new File("D:/TDDOWNLOAD/1.jpg"));
     * ThumbnailUtil.generateThumb(in, out, 100, 100);
     * </P>
     * 
     * @param originImageInputStream
     *            ：图片流必须可读
     * @param destImageOutputStream
     *            ：图片流必须可写，如果是FileOutputStream并且文件已经存在就会直接覆盖，不存在则新建文件
     * @param thumbMaxWidth
     *            :缩略图最大宽度
     * @param thumbMaxHeight
     *            :缩略图最大高度
     * @return true-successfully generate thumb nail; false-failed to generate
     *         thumb nail
     */
    public static final boolean generateThumb(InputStream originImageInputStream, OutputStream destImageOutputStream, final int thumbMaxWidth,
                    final int thumbMaxHeight) {
            if (!isParamObjectsNotNull(originImageInputStream, destImageOutputStream) || !isThumbSizeValid(thumbMaxWidth, thumbMaxHeight)) {
                    logger.error("Fail to generateThumb: error params!");
                    return false;
            }
            try {
                    Thumbnails.of(originImageInputStream).size(thumbMaxWidth, thumbMaxHeight).toOutputStream(destImageOutputStream);
                    return true;
            } catch (Exception ex) {
                    logger.error("Fail to generateThumb: ex=" + ex.getMessage(), ex);
                    return false;
            }
    }

    /**********************
     * <P>
     * 和上述接口类似， 只是指定了目标图片文件的类型，需要保证目标文件的扩展名和指定destFileType参数是同一图片类型
     * </P>
     * 
     * @param originImageInputStream
     * @param destImageOutputStream
     * @param destFileType
     * @param thumbMaxWidth
     * @param thumbMaxHeight
     * @return
     */
    public static final boolean generateThumb(InputStream originImageInputStream, OutputStream destImageOutputStream, final ImageType destFileType,
                    final int thumbMaxWidth, final int thumbMaxHeight) {
            if (!isParamObjectsNotNull(originImageInputStream, destImageOutputStream, destFileType) || !isThumbSizeValid(thumbMaxWidth, thumbMaxHeight)) {
                    logger.error("Fail to generateThumb: error params!");
                    return false;
            }
            try {
                    Thumbnails.of(originImageInputStream).size(thumbMaxWidth, thumbMaxHeight).outputFormat(destFileType.extention).toOutputStream(
                                    destImageOutputStream);
                    return true;
            } catch (Exception ex) {
                    logger.error("Fail to generateThumb: ex=" + ex.getMessage(), ex);
                    return false;
            }
    }

    /*************************
     * <P>
     * 对参数originBufferedImage指定的图片进行等比压缩，返回压缩后的图片, 注意: 1.
     * thumbMaxWidth,thumbMaxHeight是缩略图的最大高度和宽度，由于图片是等比压缩，
     * 生成的缩略图在等比压缩时，会保证宽度和高度在这个范围内。
     * 例如800*600的原图，thumbMaxWidth=120和thumbMaxHeight=120时，生成的缩略图为120×90 2.
     * 缩略图类型和原图一致 3. 实例: BufferedImage originalImage = ImageIO.read(new
     * File("D:/TDDOWNLOAD/origin.jpg")); BufferedImage buf =
     * ThumbnailUtil.generateThumb(originalImage, ImageType.BMP, 20, 20);
     * ImageIO.write(buf, "bmp", new FileOutputStream(new
     * File("D:/TDDOWNLOAD/1.bmp")));
     * </P>
     * 
     * @param originBufferedImage
     *            ：图片流必须可读
     * @param thumbMaxWidth
     *            :缩略图最大宽度
     * @param thumbMaxHeight
     *            :缩略图最大高度
     * @return BufferedImage-successfully generate thumb nail; null-failed to
     *         generate thumb nail
     */
    public static final BufferedImage generateThumb(BufferedImage originBufferedImage, final int thumbMaxWidth, final int thumbMaxHeight) {
            if (!isParamObjectsNotNull(originBufferedImage) || !isThumbSizeValid(thumbMaxWidth, thumbMaxHeight)) {
                    logger.error("Fail to generateThumb: error params!");
                    return null;
            }
            try {
                    return Thumbnails.of(originBufferedImage).size(thumbMaxWidth, thumbMaxHeight).asBufferedImage();
            } catch (Exception ex) {
                    logger.error("Fail to generateThumb: ex=" + ex.getMessage(), ex);
                    return null;
            }
    }

    /**********************
     * <P>
     * 和上述接口类似， 只是指定了目标图片文件的类型，需要保证目标文件的扩展名和指定destFileType参数是同一图片类型
     * </P>
     * 
     * @param originBufferedImage
     * @param destFileType
     * @param thumbMaxWidth
     * @param thumbMaxHeight
     * @return
     */
    public static final BufferedImage generateThumb(BufferedImage originBufferedImage, final ImageType destFileType, final int thumbMaxWidth,
                    final int thumbMaxHeight) {
            if (!isParamObjectsNotNull(originBufferedImage, destFileType) || !isThumbSizeValid(thumbMaxWidth, thumbMaxHeight)) {
                    logger.error("Fail to generateThumb: error params!");
                    return null;
            }
            try {
                    return Thumbnails.of(originBufferedImage).size(thumbMaxWidth, thumbMaxHeight).outputFormat(destFileType.extention).asBufferedImage();
            } catch (Exception ex) {
                    logger.error("Fail to generateThumb: ex=" + ex.getMessage(), ex);
                    return null;
            }
    }

    /*****************************
     * <P>
     * 对参数originFile指定的图片进行裁剪，裁剪后的图片会被保存到destFile, 注意: 1.
     * width,height是裁剪的宽度和高度，也是最终图片的宽度和高度，高度或宽度超过超过原图的话，就采取原图的高度或宽度。 2. 实例: File
     * in = new File("D:/TDDOWNLOAD/origin.jpg"); File out = new
     * File("D:/TDDOWNLOAD/1.jpg"); ThumbnailUtil.cutImage(in, out,
     * ImageCutPosition.TOP_LEFT, 200, 200);
     * </P>
     * 
     * @param originFile
     *            ：图片流必须可读
     * @param destFile
     *            ：图片流必须可写，如果是destFile并且文件已经存在就会直接覆盖，不存在则新建文件
     * @param position
     *            ：: 裁剪出来的图片相对于原图的位置
     * @param width
     *            : 裁剪出来的图片宽度
     * @param height
     *            : 裁剪出来的图片高度
     * @return true-successfully cut image; false-failed to cut image;
     */
    public static final boolean cutImage(InputStream originImageInputStream, OutputStream destImageOutputStream,
                    final ImageCutPosition imageCutPosition, final int width, final int height) {
            if (!isParamObjectsNotNull(originImageInputStream, destImageOutputStream, imageCutPosition) || !isThumbSizeValid(width, height)) {
                    logger.error("Fail to cutImage: error params!");
                    return false;
            }
            try {
                    Thumbnails.of(originImageInputStream).sourceRegion(imageCutPosition.position, width, height).size(width, height).toOutputStream(
                                    destImageOutputStream);
                    return true;
            } catch (Exception ex) {
                    logger.error("Fail to cutImage: ex=" + ex.getMessage(), ex);
                    return false;
            }
    }

    /*****************************
     * <P>
     * 对参数originBufferedImage指定的图片进行裁剪，裁剪后的图片会被保存到返回结果中, 注意: 1.
     * width,height是裁剪的宽度和高度，也是最终图片的宽度和高度，高度或宽度超过超过原图的话，就采取原图的高度或宽度。 2. 实例:
     * BufferedImage originalImage = ImageIO.read(new
     * File("D:/TDDOWNLOAD/origin.jpg")); BufferedImage buf =
     * ThumbnailUtil.cutImage(originalImage, ImageCutPosition.TOP_LEFT, 200,
     * 200); ImageIO.write(buf, "jpg", new FileOutputStream(new
     * File("D:/TDDOWNLOAD/1.jpg")));
     * </P>
     * 
     * @param originBufferedImage
     *            ：图片流必须可读
     * @param position
     *            ：: 裁剪出来的图片相对于原图的位置
     * @param width
     *            : 裁剪出来的图片宽度
     * @param height
     *            : 裁剪出来的图片高度
     * @return BufferedImage-successfully cut image; null-failed to cut image
     */
    public static final boolean cutImage(final File originFile, final File destFile, final ImageCutPosition imageCutPosition, final int width,
                    final int height) {
            if (!isParamObjectsNotNull(originFile, destFile, imageCutPosition) || !isThumbSizeValid(width, height)) {
                    logger.error("Fail to cutImage: error params!");
                    return false;
            }
            try {
                    Thumbnails.of(originFile).sourceRegion(imageCutPosition.position, width, height).size(width, height).toFile(destFile);
                    return true;
            } catch (Exception ex) {
                    logger.error("Fail to cutImage: ex=" + ex.getMessage(), ex);
                    return false;
            }
    }

    /*****************************
     * <P>
     * 对参数originBufferedImage指定的图片进行裁剪，裁剪后的图片会被保存到返回结果中, 注意: 1.
     * width,height是裁剪的宽度和高度，也是最终图片的宽度和高度，高度或宽度超过超过原图的话，就采取原图的高度或宽度。 2. 实例:
     * BufferedImage originalImage = ImageIO.read(new
     * File("D:/TDDOWNLOAD/origin.jpg")); BufferedImage buf =
     * ThumbnailUtil.cutImage(originalImage, ImageCutPosition.TOP_LEFT, 200,
     * 200); ImageIO.write(buf, "jpg", new FileOutputStream(new
     * File("D:/TDDOWNLOAD/1.jpg")));
     * </P>
     * 
     * @param originBufferedImage
     *            ：图片流必须可读
     * @param position
     *            ：: 裁剪出来的图片相对于原图的位置
     * @param width
     *            : 裁剪出来的图片宽度
     * @param height
     *            : 裁剪出来的图片高度
     * @return BufferedImage-successfully cut image; null-failed to cut image
     */
    public static final BufferedImage cutImage(BufferedImage originBufferedImage, final ImageCutPosition imageCutPosition, final int width,
                    final int height) {
            if (!isParamObjectsNotNull(originBufferedImage, imageCutPosition) || !isThumbSizeValid(width, height)) {
                    logger.error("Fail to cutImage: error params!");
                    return null;
            }
            try {
                    return Thumbnails.of(originBufferedImage).sourceRegion(imageCutPosition.position, width, height).size(width, height).asBufferedImage();
            } catch (Exception ex) {
                    logger.error("Fail to cutImage: ex=" + ex.getMessage(), ex);
                    return null;
            }
    }

    public static final void main(String[] args) throws Exception {
            // FileInputStream in 

            File in = new File("D:/TDDOWNLOAD/origin.jpg");
            File out = new File("D:/TDDOWNLOAD/1.jpg");
            ThumbnailUtil.cutImage(in, out, ImageCutPosition.TOP_LEFT, 2020, 200);
    }
}
