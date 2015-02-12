package com.huake.saas.tqh.service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huake.saas.tqh.model.Place;
import com.huake.saas.util.DateUtil;
import com.huake.saas.weixin.service.WeixinUserService;
import com.huake.util.MessageDigestUtil;

/**
 * 台球会业务实现。// TODO
 * @author laidingqing
 *
 */
@Component
public class PoolService {
	
	private final static String FETCH_NEAR_URL = "http://www.yqkan.cn/api/tqh/v2/place/list?lat={0}&lng={1}&first={2}&max={3}&type=1&city={4}&apiKey={5}&signature={6}"; 

	private static final String ZONE_DATE_FORMAT = "EEE, yyyy-MM-dd HH:mm:ss zzz";
	private static final String API_KEY = "bc543dc89b57df68062c9a25314fbf7f";
	private final static String SECURE_KEY = "32df2bc3520d53a10ed6eb0807a5a6ab";
	
	private final static Logger log = LoggerFactory.getLogger(PoolService.class);
	
	private WeixinUserService weixinUserService;
	
	private HttpHeaders requestHeaders = new HttpHeaders();
	private List<MediaType> acceptableMediaTypes;
	private HttpEntity<?> requestEntity;
	private RestTemplate restTemplate;
	
	public List<Place> findNearPlaces(BigDecimal lat, BigDecimal lng) {
		String date = DateUtil.getDateStringWithZone(Calendar.getInstance(), ZONE_DATE_FORMAT, TimeZone.getTimeZone("UTC"), Locale.US);
		
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		acceptableMediaTypes.add(new MediaType("application", "JSON", Charset.forName("UTF-8")));
		requestHeaders.setAccept(acceptableMediaTypes);
		requestHeaders.set("Date", date);
		
		
		requestEntity = new HttpEntity<Object>(requestHeaders);
		if (restTemplate == null) {
			restTemplate = new RestTemplate();
			ObjectMapper newObjectMapper = new ObjectMapper();
			MappingJackson2HttpMessageConverter m2mc = new MappingJackson2HttpMessageConverter();
			newObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			m2mc.setObjectMapper(newObjectMapper);
			restTemplate.getMessageConverters().add(m2mc);
		}
		
		String signature = MessageDigestUtil.calculateSignature("GET", date, "/api/tqh/v2/place/list", SECURE_KEY);
		try{
			Place[] places = restTemplate.exchange(FETCH_NEAR_URL, HttpMethod.GET, requestEntity, Place[].class, lat, lng, 0, 8, "0591", URLEncoder.encode(API_KEY, "UTF-8"), URLEncoder.encode(signature, "UTF-8")).getBody();
			log.debug("response: " + places);
			return Arrays.asList(places);
		}catch(Exception e){
			log.error("向台球会请求数据错误", e);
		}
		return null;
	}

	
	public static void main(String[] args) throws Exception {
		PoolService ps = new PoolService();
		List<Place> places = ps.findNearPlaces(new BigDecimal(113), new BigDecimal(26));
		for(Place place : places){
			System.out.println(place.getName());
		}
	}
}
