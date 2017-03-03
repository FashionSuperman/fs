package com.fashionSuperman.fs.core.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @description AES加密解密工具
 * @author FashionSuperman
 * @date 2017年2月22日 下午5:28:15
 * @version 1.0
 */
public class AesCryptography {
	
	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

	/**
	 * 获取32位密钥
	 * @return
	 */
	public static String generateKey(){
		// 创建 GUID 对象
		UUID uuid = UUID.randomUUID();
	    // 得到对象产生的ID
	    return uuid.toString().replaceAll("-", "");
	}
	
	/**
	 * 数据加密
	 * @param str
	 * @param key
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String encodeData(String str, String key)	throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,	IllegalBlockSizeException, BadPaddingException {
		
		byte[] textBytes = str.getBytes("UTF-8");
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		     SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		     Cipher cipher = null;
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
		
		return Base64.encodeBase64String(cipher.doFinal(textBytes));
	}

	/**
	 * 数据解密
	 * @param str
	 * @param key
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String decodeData(String str, String key)	throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		byte[] textBytes = Base64.decodeBase64(str);
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
		
		return new String(cipher.doFinal(textBytes), "UTF-8");
	}
}