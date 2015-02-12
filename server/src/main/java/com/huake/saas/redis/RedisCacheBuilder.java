package com.huake.saas.redis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import com.huake.saas.redis.repository.AuthCfgRepository;
import com.huake.saas.redis.repository.NewsRepository;

/**
 * 重组Redis Cache.适用于初始化存储
 * @author 
 *
 */
public class RedisCacheBuilder implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(RedisCacheBuilder.class);
	
	private boolean buildIndex = true;
	@Autowired
	private List<AuthCfgRepository> authCfgRepositorys;
	
	@Autowired
	private NewsRepository newsRepository;
	// 索引操作线程延时启动的时间，单位为秒  
    private int lazyTime = 10; 
    
	private CacheManager redisCacheManager;
	
	private Thread cacheBuildThread = new Thread() {  
        @Override  
        public void run() {  
            try {  
                Thread.sleep(lazyTime * 1000);  
                logger.debug("begin redis cache build...");  
                long beginTime = System.currentTimeMillis();  
                
                //这里执行缓存业务逻辑，如排行榜，消息队列等
                /**
                 * 获取所有租户 所有配置 当前的使用情况 并更新到 redis中
                 */
                for(AuthCfgRepository authCfg : authCfgRepositorys){
                	authCfg.putCurrentAuthCfg();
                }
                
                newsRepository.putCurrentNewsTop();
                //结束
                long costTime = System.currentTimeMillis() - beginTime;  
                logger.debug("redis cache build finished.");  
                logger.debug("costed " + costTime + " milliseconds");  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
    };  
    
	@Override
	public void afterPropertiesSet() throws Exception {  
        if (buildIndex) {  
        	cacheBuildThread.setDaemon(true);  
        	cacheBuildThread.setName("Compass Indexer");  
        	cacheBuildThread.start();  
        }  
    }

	public CacheManager getRedisCacheManager() {
		return redisCacheManager;
	}

	public void setRedisCacheManager(CacheManager redisCacheManager) {
		this.redisCacheManager = redisCacheManager;
	}

	public int getLazyTime() {
		return lazyTime;
	}

	public void setLazyTime(int lazyTime) {
		this.lazyTime = lazyTime;
	}
}
