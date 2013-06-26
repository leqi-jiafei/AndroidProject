package com.test.web.struts.action;

import java.util.HashMap;
import java.util.Map;

public class AppStorage {

	private static Map map;
	public static Map getInstance(){
		if (map==null){
			map = new HashMap();
		}
		return map;
	}
	
	
}
