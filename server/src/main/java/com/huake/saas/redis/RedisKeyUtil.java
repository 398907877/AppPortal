package com.huake.saas.redis;


/**
 * Redis Key Definition
 * @author skylai
 *
 */
public abstract class RedisKeyUtil {

	/**
	 * 跟节点 appportal
	 */
    public static final String CACHE_ROOT_NAME = "appportal";
    /**
     * 分隔符
     */
	public static final String CACHE_KEY_SEPARATOR = ":";
	/**
	 * 租户跟节点 tenancy
	 */
    public static final String TENANCY_ROOT = "tenancy";
	
    public static final String NEWS = "news";
    
    /**
     * 新闻置顶 最大值
     */
    public static final String NEWS_TOP = "newsTop";
	/**
	 * apppotal-租户id-新闻资讯配置
	 */
	public static final String NEWS_AUTH_CFG = "newsAuthCfg";
    
	
    /**
     * apppotal-租户id-新闻资讯配置-每日条数限制余额
     */
	public static final String NEWS_AUTH_CFG_DAYLIMIT = "dayLimit";
	
	/**
     * apppotal-租户id-新闻资讯配置-月视频数限制余额
     */
	public static final String NEWS_AUTH_CFG_MONTHVIDEOLIMIT = "monthVideoLimit";
    
    public static String getKey(String... keys) {	
        StringBuilder sb = new StringBuilder("");
        sb.append(CACHE_ROOT_NAME).append(CACHE_KEY_SEPARATOR);
        int i = 0;
        for (String key : keys) {
            sb.append(key);
            if(i < keys.length - 1 ){
                 sb.append(CACHE_KEY_SEPARATOR);
            }
            i++;
        }
        return  sb.toString();
    }
}
