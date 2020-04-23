package com.fss.cms.chatbot.assist.prototype.model;

public class UserOperationDetails {
	Integer operationID;
	String operationName;
	String operationDesc;
	String userType;

	public UserOperationDetails(int operationID,String operationName, String operationDesc, String userType) {
		
		this.operationID=operationID;
		this.operationName = operationName;
		this.operationDesc = operationDesc;
		this.userType = userType;
	}

		public Integer getOperationID() {
		return operationID;
	}

	public void setOperationID(Integer operationID) {
		this.operationID = operationID;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationDesc() {
		return operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "UserOperationDetails [operationID=" + operationID + ", operationName=" + operationName
				+ ", operationDesc=" + operationDesc + ", userType=" + userType + "]";
	}

}
