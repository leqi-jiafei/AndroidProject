package com.test.util;


/**
 * Class holding constant values used throughout the application
 * 
 * @author Qiu Le Qi
 * @version $Revision:
 */
public class Constants {
	/** Generic TRUE as String value. */
	public static final String TRUE  = Boolean.TRUE.toString();

	/** Generic FALSE as String value. */
	public static final String FALSE = Boolean.FALSE.toString();

	/** Status ACTIVE as int value of 1. */
	public static final int ACTIVE   = 1;

	/** Status INACTIVE as int value of 0. */
	public static final int INACTIVE = 0;

	/** Status ACTIVE as Boolean value of TRUE. */
	public static final Boolean IS_ACTIVE_TRUE  = Boolean.TRUE;

	/** Status INACTIVE as Boolean value of FALSE. */
	public static final Boolean IS_ACTIVE_FALSE = Boolean.FALSE;

	/** Start version number for database records. */
	public static final Long VERSION_NUM = Long.valueOf(1);
	
	/** Protocol name: HTTP. */
	public static final String PROTOCOL_HTTP  = "http";

	/** Protocol name: HTTPS. */
	public static final String PROTOCOL_HTTPS = "https";

	/** Struts action forward: forward. */
	public static final String ACTION_FORWARD = "forward";
	
	/** Struts action forward: cancel. */
	public static final String ACTION_CANCEL  = "cancel";

	/** Struts action forward: success. */
	public static final String SUCCESS = "success";

	/** Struts action forward: failure. */
	public static final String FAILURE = "failure";

	/** Struts action forward: error. */
	public static final String ERROR   = "error";

	/** Struts action forward: confirm. */
	public static final String CONFIRM = "confirmation";

	/** Error message to return when a property is not defined. */
	public static final String PROPERTY_NOT_DEFINED_MSG = "property-not-defined";

	/** Error code used to tag error in action messages. */
	public static final String GENERAL_ERROR_MESSAGE = "errorMessage";

	/** Error code used to tag error in action messages. */
	public static final String CARD_CCV_ERROR_MESSAGE    = "cardVerificationValue";
	public static final String CARD_EXPIRY_ERROR_MESSAGE = "expiryMonth";
	public static final String CARD_NAME_ERROR_MESSAGE   = "holderName";
	public static final String CARD_NUMBER_ERROR_MESSAGE = "cardNumber";
	public static final String CARD_TYPE_ERROR_MESSAGE   = "cardType";
	
	/** Success code used to tag message in action messages. */
	public static final String NOTIFICATION_MESSAGE = "notificationMessage";

	/** Application server variable. */
	public static final String APPLICATION_SERVER = "application.server";

	/** UI value for drop-down's no selection option. TODO: Replace with message resource. */
	public static final String APPLICATION_DEFAULT_SELECT ="Please select one";
   
	/** Email: CLA administrator generic name. (Mantis issue: 0001110) TODO: Repalce with message resource. */
	public static final String CLIENT_APPLICATION_ADMIN = "Client Application Admin";

	/** Email: Application administrator generic name. (Mantis issue: 0001110) TODO: Replace with message resource. */
	public static final String SYS_ADMIN = "IPS Admin";

	/** 
	 * Constants used to generate NEW account name label: "<NEW_ACCOUNT> <payment provider> <ACCOUNT>". 
	 * TODO: Replace with message resource (with one input parameter).
	 **/
	public static final String NEW_ACCOUNT = "New";
	public static final String ACCOUNT = "Account";
	
	/** Global session constants. */
	public static final String SESSION_USER_HEADER = "session_user_header";
	public static final String SESSION_REQUESTED_SERVLET_PATH = "session_requested_servlet_path";
	public static final String SESSION_REQUESTED_QUERY_STRING = "session_requested_query_string";
	public static final String SESSION_USER_VO = "session_user_vo";
	public static final String SESSION_USER_FUNCTION_LIST = "session_user_function_list";
	public static final String SESSION_USER_ROLE_LIST = "session_user_role_list";
	public static final String SESSION_VBMK_GALOGIN_ID = "session_vbmk_galogin_id";

	public static final String ZERO = "0";
		
	
}