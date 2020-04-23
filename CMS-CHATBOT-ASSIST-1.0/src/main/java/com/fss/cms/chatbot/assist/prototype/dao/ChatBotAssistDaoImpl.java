package com.fss.cms.chatbot.assist.prototype.dao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.fss.cms.chatbot.assist.prototype.model.AssistResponse;
import com.fss.cms.chatbot.assist.prototype.model.AuthRequest;
import com.fss.cms.chatbot.assist.prototype.model.UserOperationDetails;
import com.fss.cms.chatbot.assist.prototype.model.UserOperations;
import com.fss.cms.chatbot.assist.prototype.model.UserTokenData;

@Repository("chatBotAssistDaoImpl")
public class ChatBotAssistDaoImpl implements ChatBotAssistDao {
	Logger log = LoggerFactory.getLogger(ChatBotAssistDaoImpl.class);
	static HashMap<Integer, UserOperationDetails> userOpsDetailsMap = new HashMap<>();
	static HashMap<String, UserTokenData> tokenMap = new HashMap<>();
	static HashMap<String, AuthRequest> userDataMap = new HashMap<>();
	
	@PostConstruct
	private void setOperationsMap(){
		fetchUserOpsDetails();
		fetchUserData();
		log.debug("TOKEN:"+tokenMap);
	
	}
	@Autowired
	Environment env;
	@Override
	public boolean authenticateUser(AuthRequest req) {
		boolean authStatus = false;
		try {
			if (req.getUserID() != null && userDataMap.containsKey(req.getUserID())) {
				log.debug("user exists" + req.getUserID() + ":: verifying password and type..");
				AuthRequest userData = userDataMap.get(req.getUserID());
				if (req.getUserPassword() != null && req.getUserPassword().equals(userData.getUserPassword())) {
					log.debug("user pwd matched " + req.getUserID() + ":: verifying  type..");
					if (req.getUserType() != null && req.getUserType().equals(userData.getUserType())) {
						authStatus = true;
						log.debug("Auth Success! for user ID :" + req.getUserID());
					}
				}
			}
		} catch (Exception e) {
			log.debug("Exception during user authentication", e);
			log.error("Error :{}", e);
		}
		return authStatus;
	}

	private HashMap<String, AuthRequest> fetchUserData() {
		try {
			userDataMap.put(env.getProperty("UID1"), new AuthRequest(env.getProperty("UID1"), env.getProperty("UPWD1"), env.getProperty("BKU")));
			userDataMap.put(env.getProperty("UID2"), new AuthRequest(env.getProperty("UID2"), env.getProperty("UPWD2"), env.getProperty("CCU")));
			userDataMap.put(env.getProperty("UID3"), new AuthRequest(env.getProperty("UID3"), env.getProperty("UPWD3"), env.getProperty("CPU")));
			log.debug("user,MAP->{}",userDataMap);
		} catch (Exception e) {
			log.debug("Exception :", e);
			log.debug("Error :", e);
		}
		return userDataMap;
	}

	public List<UserOperations> fetchUseropsByUserType(String userType) {
		HashMap<String, List<UserOperations>> useropsMap = null;
		List<UserOperations> userTypOps = null;
		try {
			useropsMap = new HashMap<>();
			useropsMap = fetchUserops();
			userTypOps = useropsMap.get(userType);
		} catch (Exception e) {
			log.debug("Exception while fetchgs usertype ops -", e);
			log.error("Error {}", e);
		}
		return userTypOps;
	}

	private HashMap<String, List<UserOperations>> fetchUserops() {
		HashMap<String, List<UserOperations>> useropsMap = null;
		try {
			List<UserOperations> useropsBankUser = new ArrayList<>();
			List<UserOperations> useropsCallCenterUser = new ArrayList<>();
			List<UserOperations> useropsCustPortalUser = new ArrayList<>();
			useropsMap = new HashMap<>();
			useropsBankUser = new ArrayList<>();
			log.debug("userOpsDetailsMap:"+userOpsDetailsMap);
			useropsBankUser.add(new UserOperations(1, userOpsDetailsMap.get(1).getOperationName()));
			useropsBankUser.add(new UserOperations(2, userOpsDetailsMap.get(2).getOperationName()));
			useropsBankUser.add(new UserOperations(3, userOpsDetailsMap.get(3).getOperationName()));
			useropsCallCenterUser.add(new UserOperations(4, userOpsDetailsMap.get(4).getOperationName()));
			useropsCallCenterUser.add(new UserOperations(5, userOpsDetailsMap.get(5).getOperationName()));
			useropsCallCenterUser.add(new UserOperations(6, userOpsDetailsMap.get(6).getOperationName()));
			useropsCustPortalUser.add(new UserOperations(7, userOpsDetailsMap.get(7).getOperationName()));
			useropsCustPortalUser.add(new UserOperations(8, userOpsDetailsMap.get(8).getOperationName()));
			useropsCustPortalUser.add(new UserOperations(9, userOpsDetailsMap.get(9).getOperationName()));
			useropsMap.put(env.getProperty("BKU"), useropsBankUser);
			useropsMap.put(env.getProperty("CCU"), useropsCallCenterUser);
			useropsMap.put(env.getProperty("CPU"), useropsCustPortalUser);
			log.debug("map user ops ->"+useropsMap);
		} catch (Exception e) {
			log.debug("Exception while fetching userOps by UserType", e);
			log.error("Error {}", e);
		}
		return useropsMap;
	}

	@Override
	public boolean saveToken(String userID, UserTokenData tokenData) {
		boolean saveFlag = true;
		try {
			tokenMap.put(tokenData.getUserToken(), tokenData);
		} catch (Exception e) {
			log.debug("Exception in saving token:{}", e);
			log.error("Error in saving token:{}", e);
			saveFlag = false;
		}
		return saveFlag;
	}

	@Override
	public UserTokenData chkTokenMatch(String userToken) {
		UserTokenData tknDtls = null;
		try {
			tknDtls = tokenMap.get(userToken);
			log.debug("Token {} found it's match in {} " ,userToken,tknDtls);
		} catch (Exception e) {
			log.debug("Exception on token matching check:", e);
			log.error("Exception on token matching check:", e);
		}
		return tknDtls;
	}

	@Override
	public AssistResponse setOperationDtls(String operationID, AssistResponse response) {
		try {
			UserOperationDetails operationDtls = fetchUserOpsDetails(operationID);
			response.setOperationDesc(operationDtls.getOperationDesc());
			response.setOperationName(operationDtls.getOperationName());
		} catch (Exception e) {
			log.debug("Exception on setOperationDtls:", e);
			log.error("Exception on setOperationDtls:", e);
		}
		return response;
	}

	private UserOperationDetails fetchUserOpsDetails(String operationID) {
		log.debug("operationID:"+operationID);
		log.debug("operationID:"+userOpsDetailsMap);
		return userOpsDetailsMap.get(Integer.parseInt(operationID));
	}
	private void fetchUserOpsDetails() {

		UserOperationDetails opDtls1 = new UserOperationDetails(1, env.getProperty("BKU_OPNAME1"),env.getProperty("BKU_OPDESC1"),env.getProperty("BKU"));
		UserOperationDetails opDtls2 = new UserOperationDetails(2, env.getProperty("BKU_OPNAME2"),env.getProperty("BKU_OPDESC2"),env.getProperty("BKU"));
		UserOperationDetails opDtls3 = new UserOperationDetails(3, env.getProperty("BKU_OPNAME3"),env.getProperty("BKU_OPDESC3"),env.getProperty("BKU"));
		UserOperationDetails opDtls4 = new UserOperationDetails(4, env.getProperty("CCU_OPNAME4"),env.getProperty("CCU_OPDESC4"),env.getProperty("CCU"));
		UserOperationDetails opDtls5 = new UserOperationDetails(5, env.getProperty("CCU_OPNAME5"),env.getProperty("CCU_OPDESC5"),env.getProperty("CCU"));
		UserOperationDetails opDtls6 = new UserOperationDetails(6, env.getProperty("CCU_OPNAME6"),env.getProperty("CCU_OPDESC6"),env.getProperty("CCU"));
		UserOperationDetails opDtls7 = new UserOperationDetails(7, env.getProperty("CPU_OPNAME7"),env.getProperty("CPU_OPDESC7"),env.getProperty("CPU"));
		UserOperationDetails opDtls8 = new UserOperationDetails(8, env.getProperty("CPU_OPNAME8"),env.getProperty("CPU_OPDESC8"),env.getProperty("CPU"));
		UserOperationDetails opDtls9 = new UserOperationDetails(9, env.getProperty("CPU_OPNAME9"),env.getProperty("CPU_OPDESC9"),env.getProperty("CPU"));
		userOpsDetailsMap.put(1, opDtls1);
		userOpsDetailsMap.put(2, opDtls2);
		userOpsDetailsMap.put(3, opDtls3);
		userOpsDetailsMap.put(4, opDtls4);
		userOpsDetailsMap.put(5, opDtls5);
		userOpsDetailsMap.put(6, opDtls6);
		userOpsDetailsMap.put(7, opDtls7);
		userOpsDetailsMap.put(8, opDtls8);
		userOpsDetailsMap.put(9, opDtls9);
		for(int i=1;i<=9;i++) {
			log.debug("userOperationsTableData"+userOpsDetailsMap.get(i).toString());
	
		}
			}

}
