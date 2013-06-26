package com.test.web.struts.form;

import org.apache.struts.action.ActionForm;

/**
 * 
 * 
 * @author Qiu Le Qi
 * @version $Revision: 1.31 $
 * 
 * @struts.form name="testForm"
 */

public class TestForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -733677540528528108L;
	
	/** added by zhigang for transaction started */
	private String phone;

	private String otp;
	
	private String gcmRegId;
	
	private String deviceId;
	private String appId;
	private String os;
	private String json;

	private String messageId;
	private String status;
	
	
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getGcmRegId() {
		return gcmRegId;
	}

	public void setGcmRegId(String gcmRegId) {
		this.gcmRegId = gcmRegId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
