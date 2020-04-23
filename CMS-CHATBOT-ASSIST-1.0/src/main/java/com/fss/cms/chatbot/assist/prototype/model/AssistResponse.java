package com.fss.cms.chatbot.assist.prototype.model;

import io.swagger.annotations.ApiModelProperty;

public class AssistResponse {
	@ApiModelProperty(notes="Name of the Operation specific to user type")
	String operationName;
	@ApiModelProperty(notes="Description of the Operation, can be tuned by intelligence to reply as chatbot")
	String operationDesc;
	@ApiModelProperty(notes="Error message will be shown if there is any error in processing Assist API")
	String errMsg;
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
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
	
	@Override
	public String toString() {
		return "AssistResponse [operationName=" + operationName + ", operationDesc=" + operationDesc + ", errMsg="
				+ errMsg + "]";
	}
	public AssistResponse(String operationName, String operationDesc) {
		super();
		this.operationName = operationName;
		this.operationDesc = operationDesc;
	}
	public AssistResponse() {}
}
