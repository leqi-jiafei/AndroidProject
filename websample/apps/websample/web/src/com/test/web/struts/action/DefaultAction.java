package com.test.web.struts.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionMapping;

import com.test.util.Constants;
import com.test.web.struts.action.BaseApplicationAction;
import com.test.web.struts.form.TestForm;


/**
 * TODO description here
 * 
 * @author Qiu Le Qi
 * @version $Revision: 1.2 $
 * 
 * 
 */
public class DefaultAction extends BaseApplicationAction {
	public String doAction(ActionMapping mapping, TestForm form,
			HttpServletRequest request, HttpServletResponse response)		
	throws Exception 
	{
		Map resultMap = new HashMap();
		resultMap.put("status", "true");
		resultMap.put("otp", "123");
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		request.setAttribute("jsonresult", jsonObject.toString());
		return Constants.SUCCESS;
	}
}