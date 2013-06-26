package com.test.web.struts.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionMapping;

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
 *          Make sure place all of the ejb reference here.
 * 
 * 
 */
public class VerifyAction extends BaseApplicationAction {
	public String doAction(ActionMapping mapping, TestForm form,
			HttpServletRequest request, HttpServletResponse response)		
	throws Exception 
	{
		String phone = form.getPhone();
		if (StringUtil.isEmpty(phone)){
			return Constants.ERROR;
		}
		String otp = form.getOtp();
		if (StringUtil.isEmpty(otp)){
			return Constants.ERROR;
		}
		Map resultMap = new HashMap();
		Map map = AppStorage.getInstance();
		Map valueMap = (Map) map.get(phone);
		if (valueMap!=null){
			String existOTP = (String) valueMap.get("otp");
			if (existOTP.equalsIgnoreCase(otp)){
				System.out.println("Match otp.");
				resultMap.put("status", "true");
			}else{
				System.out.println("invalid otp");
				resultMap.put("status", "false");
			}
		}else{
			System.out.println("invalid otp");
			resultMap.put("status", "false");
		}
		
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		request.setAttribute("jsonresult", jsonObject.toString());
		return Constants.SUCCESS;
	}
}