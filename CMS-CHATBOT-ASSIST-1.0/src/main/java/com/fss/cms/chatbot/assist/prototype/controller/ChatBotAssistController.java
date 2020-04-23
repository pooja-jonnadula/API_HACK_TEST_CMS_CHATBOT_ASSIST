package com.fss.cms.chatbot.assist.prototype.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fss.cms.chatbot.assist.prototype.model.AssistRequest;
import com.fss.cms.chatbot.assist.prototype.model.AssistResponse;
import com.fss.cms.chatbot.assist.prototype.model.AuthRequest;
import com.fss.cms.chatbot.assist.prototype.model.AuthResponse;
import com.fss.cms.chatbot.assist.prototype.service.ChatBotAssistService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Authenticates users (with /authAPI endpoint) and accesses CMS Assist (with /assistAPI endpoint) that helps users of CMS Branch, Call Center and Customer Portal to know how to do their operations")
@RestController("chatBotAssistController")
public class ChatBotAssistController {
	Logger log = LoggerFactory.getLogger(ChatBotAssistController.class);
	@Autowired
	ChatBotAssistService chatBotAssistService;
	@ApiOperation("Auth API Authenticates users to access CMS Assist")
	@PostMapping(value = "${AUTH-API-URL}", produces = "application/json", consumes = "application/json")
	AuthResponse userAuthenticationHandler(@RequestBody AuthRequest authRequest) {

		log.info("User Authentication -Started");
		log.debug("userAuth Request->{}", authRequest.toString());
		AuthResponse authResponse = chatBotAssistService.authenticateUser(authRequest);
		log.debug(" userAuth Response->{}", authRequest.toString());

		return authResponse;
	}
	@ApiOperation("Assist API allows users to access CMS Assist for requested operation details with Token generated from Auth API")
	@PostMapping(value = "${ASSIST-API-URL}", produces = "application/json", consumes = "application/json")
	AssistResponse assistCMSUsersHandler(@RequestBody AssistRequest assistRequest) {

		log.info("-User assist API-");
		log.debug("userAssist Request->{}", assistRequest.toString());
		AssistResponse assistResponse = chatBotAssistService.assistUser(assistRequest);
		log.debug(" userAssist Response->{}", assistRequest.toString());
		
		return assistResponse;
	}
	
}
