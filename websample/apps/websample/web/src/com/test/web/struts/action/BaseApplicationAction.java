package com.test.web.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.test.util.Constants;
import com.test.web.struts.form.TestForm;


public abstract class BaseApplicationAction extends Action {
	/** log4j logger */
	private static final Logger log = Logger.getLogger(BaseApplicationAction.class);

	public final ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	throws Exception
	{
		try {
			// Page form.
			TestForm baseForm = (TestForm) form;
			
				return mapping.findForward(
						doAction(mapping, baseForm, request, response)); 	
			
		} catch (Exception ex) {
			// Handle exception and save the error message.
			this.handleException(ex, request, response, true);
         
			// Forward action to error page.
			return mapping.findForward("global-error");
		}
	}

	public abstract String doAction(ActionMapping mapping, TestForm baseForm,
			HttpServletRequest request, HttpServletResponse response)throws Exception ;

    /**
     * Handle application specific exceptions - 2 args
     *
     * @param e <code>Exception</code> to handle
     * @param request <code>HttpServletRequest</code> generating Exception
     * @param response <code>HttpServletResponse</code> to send smartform
     */
    protected void handleException(Exception e, HttpServletRequest request,
                                   HttpServletResponse response, boolean saveErrors) {
    	long currentTime = System.currentTimeMillis();
    	
    	String errorID = "E_"+currentTime;
    	logErrorInformation(e, request, response, errorID); // log error message and print stack trace
        
        if (saveErrors) {
        	// save error message
        	saveErrorMessages(request, getUserErrorMessage(e), new String[]{errorID});
        }
    }
    
   /**
    * Handle application specific exceptions - 2 args
    *
    * @param e <code>Exception</code> to handle
    * @param request <code>HttpServletRequest</code> generating Exception
    * @param response <code>HttpServletResponse</code> to send smartform
    */
   protected void logErrorInformation(Exception e, HttpServletRequest request,
                                  HttpServletResponse response, String errorID) {
       log.error(errorID + " " + e.getMessage(), e); // log error message       
       
   }


	/**
	 * Saves the error messages in request for the UI to read from using the
	 * default error property.
	 * 
	 * @param request
	 * @param userErrorMessage
	 *            Error code
	 * @param errorArgs
	 *            The arguments to construct the message
	 * 
	 */
	protected void saveErrorMessages(HttpServletRequest request,
			String userErrorMessage, Object[] errorArgs) {
		saveErrorMessages(request, userErrorMessage, errorArgs,
				Constants.GENERAL_ERROR_MESSAGE);
	}

	/**
	 * Saves the error messages in request for the UI to read from.
	 * 
	 * @param request
	 * @param userErrorMessage
	 *            Error code
	 * @param errorArgsThe
	 *            arguments to construct the message
	 * @param errorName
	 *            The name to save the error with.
	 */
	protected void saveErrorMessages(HttpServletRequest request,
			String userErrorMessage, Object[] errorArgs, String errorName) {
		ActionMessages actMessages = new ActionMessages();
		actMessages.add(errorName, new ActionMessage(userErrorMessage,
				errorArgs));
		saveErrors(request, actMessages);
	}

	/**
	 * Saves the messages in session for the UI to read from Accepts another
	 * parameter as ActionMessages, which it would use to append the messages
	 * to.
	 * 
	 * @param request
	 * @param userMessage
	 *            message code
	 * @param errorArgs
	 *            The arguments to construct the message
	 * @param errorName
	 *            The name to save the message with.
	 */
	protected ActionMessages saveVBMKMessages(HttpServletRequest request,
			String userMessage, Object[] errorArgs, String messageName,
			ActionMessages actMessages) {
		actMessages.add(messageName, new ActionMessage(userMessage, errorArgs));
		saveMessages(request.getSession(), actMessages);
	
		return actMessages;
	}

	/**
	 * A convenience method for the one above, creates a new Action Messages
	 * object to add messges to.
	 * 
	 * @param request
	 * @param userMessage
	 * @param errorArgs
	 * @param messageName
	 */
	protected ActionMessages saveVBMKMessages(HttpServletRequest request,
			String userMessage, Object[] errorArgs, String messageName) {
		ActionMessages actMessages = new ActionMessages();
	
		return saveVBMKMessages(request, userMessage, errorArgs, messageName,
				actMessages);
	}

	/**
     * Return a message resource key based on the Exception passed
     *
     * @param e <code>Exception</code> to return a message key for
     *
     * @return message resource key
     */
    public static String getUserErrorMessage(Exception e) {
        String _userErrorMessage = "errors.generic";

        return _userErrorMessage;
    }
}
