package com.huake.saas.baidu.web.main;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushTagMessageRequest;
import com.baidu.yun.channel.model.PushTagMessageResponse;
import com.baidu.yun.channel.model.SetTagRequest;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;

public class SetTag {

    public static void main(String[] args) {

        /*
         * @ 推送tag
         */

        // 1. 设置developer平台的ApiKey/SecretKey
        String apiKey = "iSIN6O0wyTddZpN9oR5kD7GP";
        String secretKey = "NICAfEB3cOddTBW2mCLVaTQ1xzxZxSt7";
        ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);

        // 2. 创建BaiduChannelClient对象实例
        BaiduChannelClient channelClient = new BaiduChannelClient(pair);

        // 3. 若要了解交互细节，请注册YunLogHandler类
        channelClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

 
        SetTagRequest request = new SetTagRequest();
        request.setUserId("677895883004651845");
        request.setTag("junzero3");
        try {
			channelClient.setTag(request);
		} catch (ChannelClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ChannelServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("设置成功");

    }

}
