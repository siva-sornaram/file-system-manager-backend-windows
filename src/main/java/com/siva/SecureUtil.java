package com.siva;

import java.util.Base64;
import java.io.UnsupportedEncodingException;

public class SecureUtil {
    public static String encode(String value) throws UnsupportedEncodingException {
		Base64.Encoder e = Base64.getEncoder();
		return e.encodeToString(value.getBytes("utf-8"));
	}
	
	public static String decode(String value) throws UnsupportedEncodingException {
		byte[] byteValues = Base64.getDecoder().decode(value);
		return new String(byteValues, "utf-8");
		
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(encode("Mugesh"));
		System.out.println(decode("MTIzNDU2"));
	}
}
