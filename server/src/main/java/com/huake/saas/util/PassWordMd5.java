package com.huake.saas.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PassWordMd5 {

	public static String getPassWord(String passWord) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(passWord.getBytes());
			passWord = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return passWord;
	}

	
	public static void main(String[] args) {
		System.out.println(PassWordMd5.getPassWord("aaa"));
	}
}
