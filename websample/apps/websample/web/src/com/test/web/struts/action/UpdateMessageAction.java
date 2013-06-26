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
public class UpdateMessageAction extends BaseApplicationAction {
	public String doAction(ActionMapping mapping, TestForm form,
			HttpServletRequest request, HttpServletResponse response)		
	throws Exception 
	{
		String message = form.getMessageId();
		if (StringUtil.isEmpty(message)){
			return Constants.ERROR;
		}
		String status = form.getStatus();
		if (StringUtil.isEmpty(status)){
			return Constants.ERROR;
		}
		System.out.println("messageId: " + message + "; status: " + status);
		Map resultMap = new HashMap();
		resultMap.put("status", "true");
		
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		request.setAttribute("jsonresult", jsonObject.toString());
		return Constants.SUCCESS;
	}
}