package com.fss.cms.chatbot.assist.prototype.model;

import io.swagger.annotations.ApiModelProperty;

public class AssistRequest {
	@ApiModelProperty(notes="userToken generated for the user from Auth API ")
	String userToken;
	@ApiModelProperty(notes="users are given privilege to access certain operation ID's and fetch details of operation against ID. Given any one operation ID that user got response from authAPI to use it along with token" )
	String operationID;
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public String getOperationID() {
		return operationID;
	}
	public void setOperationID(String operationID) {
		this.operationID = operationID;
	}
	@Override
	public String toString() {
		return "AssistRequest [userToken=" + userToken + ", operationID=" + operationID + "]";
	}
	public AssistRequest(String userToken, String operationID) {
		super();
		this.userToken = userToken;
		this.operationID = operationID;
	}
	
}
