package com.yychat.model;

import java.io.Serializable;

public class User implements Serializable {
	private String userName;
	private String passWord;
	private String userMessageType;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getuserMessageType() {
		return userMessageType;
	}
	public void setuserMessageType(String userMessageType) {
		this.userMessageType = userMessageType;
	}
	

}
