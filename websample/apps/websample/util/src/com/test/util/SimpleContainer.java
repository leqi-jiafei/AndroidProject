package com.test.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a simple container.
 * 
 * @author Qiu Le Qi
 * @version $Revision: 
*/
public class SimpleContainer {

	private int count=0;
	
	private List list=new ArrayList();

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}

}
