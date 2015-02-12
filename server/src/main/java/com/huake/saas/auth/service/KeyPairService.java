package com.huake.saas.auth.service;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import com.huake.saas.base.service.ServiceException;
import com.huake.util.RSAUtil;



@Component
public class KeyPairService {


	private KeyPair keyPair;
	
	/**
	 * 构造函数，创建一个keyPair
	 * @throws ServiceException
	 */
	public KeyPairService() throws ServiceException{
		try{
			this.keyPair = RSAUtil.generateKeyPair();
		}catch(Exception e){
			throw new ServiceException("", e);
		}
	}
	
	
	public String generatePublicKey(){
		String publicKey = RSAUtil.getKeyAsString(keyPair.getPublic());
		String publicString = null;
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		    md.update(publicKey.getBytes());   
		    publicString = new BigInteger(1, md.digest()).toString(16);   
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}   
		return publicString;
	}
	
	public String generatePrivateKey(){
		String privateKey = RSAUtil.getKeyAsString(keyPair.getPrivate());
	    String privateString = null;
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		    md.update(privateKey.getBytes());   
		    privateString = new BigInteger(1, md.digest()).toString(16);   
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}   
		return privateString;
	}


	
}
