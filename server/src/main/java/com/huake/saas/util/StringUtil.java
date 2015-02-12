package com.huake.saas.util;

/**
 * Created with IntelliJ IDEA.
 * User: laidingqing
 * Date: 14-4-23
 * Time: 下午4:20
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {
    /**
     * 字符串截取
     * @param str
     * @param length
     * @return
     */
    public static String truncate(String str, int length){
        if( str.length() > length){
            StringBuilder sb = new StringBuilder("");
            sb.append(str.substring(0,length)).append("...");
            return sb.toString();
        }else return str;
    }
    

}
