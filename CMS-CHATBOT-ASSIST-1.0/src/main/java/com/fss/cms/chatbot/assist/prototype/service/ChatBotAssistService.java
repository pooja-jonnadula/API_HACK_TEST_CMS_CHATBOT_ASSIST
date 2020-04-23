package com.fss.cms.chatbot.assist.prototype.service;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fss.cms.chatbot.assist.prototype.controller.AssistResponse;
import com.fss.cms.chatbot.assist.prototype.dao.ChatBotAssistDao;
import com.fss.cms.chatbot.assist.prototype.model.AssistRequest;
import com.fss.cms.chatbot.assist.prototype.model.AuthRequest;
import com.fss.cms.chatbot.assist.prototype.model.AuthResponse;
import com.fss.cms.chatbot.assist.prototype.model.UserOperations;
import com.fss.cms.chatbot.assist.prototype.model.UserTokenData;

import ch.qos.logback.core.subst.Token;

@Service("chatBotAssistService")
public class ChatBotAssistService {
	@Autowired
	ChatBotAssistDao chatBotAssistDao;

	Logger log = LoggerFactory.getLogger(ChatBotAssistService.class);

	public AuthResponse authenticateUser(AuthRequest req) {
		AuthResponse resp = null;
		try {
			boolean isUserAuthenticated = chatBotAssistDao.authenticateUser(req);
			log.debug("Auth Status :{} for userID :{}", isUserAuthenticated, req.getUserID());
			if (isUserAuthenticated) {
				resp = new AuthResponse();
				resp.setUserOperations(chatBotAssistDao.fetchUseropsByUserType(req.getUserType()));
				resp.setToken(generateAndSaveToken(req,resp.getUserOperations()));
			} else {
				resp = new AuthResponse();
				resp.setErrMsg("Invalid User");
			}
		} catch (Exception e) {
			log.debug("Exception during authprocessing:", e);
			log.error("Error ", e);
		}
		return resp;
	}

	private String generateAndSaveToken(AuthRequest req, List<UserOperations> list) {
		String tokn = null;
		try {
			int tkn = new Random().nextInt();
			tokn = String.valueOf(tkn);
			UserTokenData tokenData = new UserTokenData(req.getUserID(), req.getUserType(), tokn,list);
			log.debug("token ::" + tokn + ", for user ID :" + req.getUserID());
			chatBotAssistDao.saveToken(req.getUserID(), tokenData);
		} catch (Exception e) {
			log.debug("Exception while generating token", e);
			log.error("Exception while generating token", e);
		}
		return tokn;
	}

	public AssistResponse assistUser(AssistRequest assistRequest) {
	AssistResponse response=null;
		try {
		response=new AssistResponse();
		String errMsg=validateAssistReq(assistRequest);
		if(errMsg!=null) {
			response.setErrMsg(errMsg);
		}
		else {
			response=chatBotAssistDao.setOperationDtls(assistRequest.getOperationID(),response);
		}
		}catch(Exception e) {
		log.debug("Exception :",e);
		log.error("Exception :",e);
	}
		return response;
	}

	private String validateAssistReq(AssistRequest assistRequest) {
		String errMsg=null;
		try {
			if(assistRequest.getUserToken()!=null) {
				UserTokenData userDlts=chatBotAssistDao.chkTokenMatch(assistRequest.getUserToken());
				if(userDlts==null) {
					errMsg="Sorry! Please check the token!";
				}
				else {
					//check operation 
					List<UserOperations> userOps=userDlts.getUserOperations();
					log.debug("tkn user ops:"+userOps);
					
					boolean operationMatchCount=userOps.stream().anyMatch(n-> assistRequest.getOperationID().equals(String.valueOf(n.getOperationID())));
					log.debug("op match"+operationMatchCount);
					if(operationMatchCount) {
						log.debug("operation {} found in access -for token-{}-processing assist ..count:{}",assistRequest.getOperationID(),assistRequest.getUserToken(),operationMatchCount);
					}
					else {
						errMsg="Operation is not allowed!";
					}
				}
			}
			else {
				errMsg="Sorry! User Token is Madatory!";
			}
		}catch(Exception e) {
			log.debug("Exception :",e);
			log.error("Exception :",e);
			}
		return errMsg;
	}

}
