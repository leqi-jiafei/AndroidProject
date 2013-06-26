package com.test.web.struts.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

import org.apache.struts.action.ActionMapping;

import com.google.gson.JsonArray;
import com.test.util.Constants;
import com.test.util.StringUtil;
import com.test.web.struts.action.BaseApplicationAction;
import com.test.web.struts.form.TestForm;


/**
 * TODO description here
 * 
 * @author Qiu Le Qi
 * @version $Revision: 1.2 $
 * 
 * 
 * 
 * 
 */
public class RegisterAction extends BaseApplicationAction {
	public String doAction(ActionMapping mapping, TestForm form,
			HttpServletRequest request, HttpServletResponse response)		
	throws Exception 
	{
		String testJson = "{\"phone\":\"123456\",\"gcmRegId\":\"111\", \"deviceId\": \"222\", \"appId\":\"333\", \"os\": \"Android\"}";
		String json = form.getJson();
//		json= testJson;
		String phone;
		String gcmRegId;
		String deviceId;
		String appId;
		String os;
		if (StringUtil.isEmpty(json)){
			phone = form.getPhone();
			if (StringUtil.isEmpty(phone)){
				return Constants.ERROR;
			}
			gcmRegId = form.getGcmRegId();
			if (StringUtil.isEmpty(gcmRegId)){
				return Constants.ERROR;
			}
			deviceId = form.getDeviceId();
			if (StringUtil.isEmpty(deviceId)){
				return Constants.ERROR;
			}
			appId = form.getAppId();
			if (StringUtil.isEmpty(appId)){
				return Constants.ERROR;
			}
			os = form.getOs();
			if (StringUtil.isEmpty(os)){
				return Constants.ERROR;
			}
		}else{
			JSONObject jsonObj = JSONObject.fromObject(json);
			phone = jsonObj.optString("phone");
			gcmRegId = jsonObj.optString("gcmRegId");
			deviceId = jsonObj.optString("deviceId");
			appId = jsonObj.optString("appId");
			os = jsonObj.optString("os");
		}
		
		
		Map resultMap = new HashMap();
		Map map = AppStorage.getInstance();
		if (map.get(phone)==null){
			resultMap.put("status", "false");
		}else{
			resultMap.put("status", "true");
			Map valueMap = (Map) map.get(phone);
			valueMap.put("gcmRegId",gcmRegId);
			valueMap.put("deviceId", deviceId);
			valueMap.put("appId", appId);
			valueMap.put("OS", os);
			System.out.println("Register successfully!"+ valueMap);
		}
		
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		request.setAttribute("jsonresult", jsonObject.toString());
		return Constants.SUCCESS;
	}
}