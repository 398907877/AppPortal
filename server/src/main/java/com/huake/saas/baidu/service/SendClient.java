package com.huake.saas.baidu.service;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;


/**
 * 
 * @author wujiajun
 * @time 20140604
 * @Description  用于注册发送的client 
 *
 */
public class SendClient {
	

	public static final String    apiKEY="iSIN6O0wyTddZpN9oR5kD7GP";
	public static final String  secretKey="NICAfEB3cOddTBW2mCLVaTQ1xzxZxSt7";
	/*
	 * 获取到  BaiduChannelClient 对象 用于发送请求
	 */
	 public static BaiduChannelClient  getChannelClient(){
		
		
		ChannelKeyPair pair = new ChannelKeyPair(SendClient.apiKEY,SendClient.secretKey);
		
		BaiduChannelClient channelClient = new BaiduChannelClient(pair);
        channelClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });
		
		return channelClient;
	}


	

}
