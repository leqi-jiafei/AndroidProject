package com.test.web.struts.action;


import com.quickblox.core.QBCallbackImpl;
import com.quickblox.core.result.Result;
import com.quickblox.module.messages.QBMessages;
import com.quickblox.module.messages.model.QBEnvironment;

public class BussinessComponent {

	public static void subscribeQBPushNotificationsTask(String registrationID, String deviceId){
		QBMessages.subscribeToPushNotificationsTask(registrationID, deviceId, QBEnvironment.DEVELOPMENT, new QBCallbackImpl() {
            @Override
            public void onComplete(Result result) {
                if (result.isSuccess()) {
                    System.out.println("Subscribe succssfully.");
                }else{
                    System.out.println("Failed to subscribe.");
                }
            }
        });
	}
}
