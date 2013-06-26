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
public class GetOTPAction extends BaseApplicationAction {
	public String doAction(ActionMapping mapping, TestForm form,
			HttpServletRequest request, HttpServletResponse response)		
	throws Exception 
	{
		String phone = form.getPhone();
		if (StringUtil.isEmpty(phone)){
			System.out.println("empty phone");
			return Constants.ERROR;
		}
		Map resultMap = new HashMap();
		Random random = new Random();
		int r = random.nextInt(1000);
		String otp = String.valueOf(r);
		while(otp.length()<3){
			otp = "0" + otp;
		}
		Map map = AppStorage.getInstance();
		Map valuemap = new HashMap();
		valuemap.put("otp", otp);
		map.put(phone, valuemap);
		
		System.out.println("phone: " +phone + "; otp: "+otp);
		resultMap.put("phone", phone);
		resultMap.put("otp", otp);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		request.setAttribute("jsonresult", jsonObject.toString());
		return Constants.SUCCESS;
	}
}