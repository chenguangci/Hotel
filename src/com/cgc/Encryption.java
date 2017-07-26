package com.cgc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
	private String password;
	public Encryption(String password){
		this.password = password;
	}
	private String tobyte(byte[] b){
		String temps = "";
		for(int i=0;i<b.length;i++){
			int t = b[i]&0xff;
			String temp = Integer.toHexString(t);
			if(temp.length()>1){
				temps+=temp;
			}else{
				temp = "0"+temp;
				temps+= temp;
			}
		}
		
		return temps;
	}
	private String Md5(String m){
		String temp = "";
		try {
			MessageDigest mess = MessageDigest.getInstance("MD5");
			byte[] byt = mess.digest(m.getBytes());
			temp = tobyte(byt);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return temp;
	}
	public String getstr(){
		return Md5(password);
	}
}
