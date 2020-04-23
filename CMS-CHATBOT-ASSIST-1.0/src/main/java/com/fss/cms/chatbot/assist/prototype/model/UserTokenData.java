package com.fss.cms.chatbot.assist.prototype.model;

import java.util.List;

public class UserTokenData {
	String userID;
	String userType;
	String userToken;
	List<UserOperations> userOperations;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public List<UserOperations> getUserOperations() {
		return userOperations;
	}

	public void setUserOperations(List<UserOperations> userOperations) {
		this.userOperations = userOperations;
	}

	
	public UserTokenData(String userID, String userType, String userToken, List<UserOperations> userOperations) {
		super();
		this.userID = userID;
		this.userType = userType;
		this.userToken = userToken;
		this.userOperations = userOperations;
	}

	@Override
	public String toString() {
		return "UserTokenData [userID=" + userID + ", userType=" + userType + ", userToken=" + userToken
				+ ", userOperations=" + userOperations + "]";
	}

	
}
