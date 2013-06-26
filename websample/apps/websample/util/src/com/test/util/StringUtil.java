package com.test.util;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


/**
 * This class is the utility for formatting and validating String.
 * @author Qiu Le Qi
 * @version $Revision: 1.3 $
 */
public class StringUtil {
    private StringUtil() {
    }

    /**
     * This method is to check the pass-in String object is not empty.
     *
     * @param str a String object
     * @return true if the str object is not null or empty.
     */
    public static boolean notEmpty(String str) {
        
        return !isEmpty(str);
    }
    
    /**
     * This method is to check the pass-in String object is empty.
     *
     * @param str a String object
     * @return true if the str object is null or empty.
     */
    public static boolean isEmpty(String str) {
        if ((str == null) || (str.trim().length() == 0)) {
            return true;
        }

        return false;
    }

    /**
     * This method is to denull the pass-in String object.
     *
     * @param str a String object
     * @return return empty string if str object is null.
     */
    public static String deNull(String str) {
        if (str == null) {
            return "";
        }

        return str;
    }

    /**
     * This method is to denull the pass-in String object.
     *
     * @param str a String object
     * @return return empty string if str object is null.
     */
    public static String deNullToQuote(String str) {
        if (str == null) {
            return "''";
        }

        return str.trim();
    }
    
    public static ArrayList strArrayToAl(String[] str) {
        ArrayList al = new ArrayList();

        if (str == null) {
            return al;
        }

        for (int i = 0; i < str.length; i++) {
            if ((str[i] != null) && !str[i].trim().equals("")) {
                al.add(str[i]);
            }
        }

        al.trimToSize();

        return al;
    }

    public static String[] alToStrArray(ArrayList al) {
        String[] str = null;

        if (al == null) {
            return str;
        }

        String tmpstr = null;

        for (int i = 0; i < al.size(); i++) {
            if (tmpstr == null) {
                tmpstr = (String) al.get(i) + "-";
            } else {
                tmpstr = tmpstr + (String) al.get(i) + "-";
            }
        }

        str = tmpstr.split("-");

        return str;
    }

    public static String organiseStr(String str) {
        String str_mod = "";
        String[] al = str.split(" ");

        for (int i = 0; i < al.length; i++) {
            if (!al[i].equalsIgnoreCase("")) {
                str_mod = str_mod + " " + al[i].trim();
            }
        }

        return str_mod;
    }

    public static ArrayList strToAl(String str, String delimPattern) {
        ArrayList al = new ArrayList();

        if (str == null) {
            return al;
        }

        String[] strArray = str.split(delimPattern);

        for (int i = 0; i < strArray.length; i++) {
            if ((strArray[i] != null) && !strArray[i].trim().equals("")) {
                al.add(strArray[i]);
            }
        }

        al.trimToSize();

        return al;
    }

    public static String alToStr(ArrayList al, String delim) {
        int size = al.size();

        if (size == 0) {
            return "";
        }

        String str = (String) al.get(0);

        for (int i = 1; i < size; i++) {
            str = str + delim + al.get(i);
        }

        return str;
    }

    public static String stringControl(String longstring, int maxlength) {
        String[] controller = longstring.split(" ");
        String shortstring = "";

        for (int i = 0; i < controller.length; i++) {
            if (controller[i].length() > maxlength) {
                int index = maxlength;
                String temp = controller[i].substring(0, index);

                for (int j = 1; j < (controller[i].length() / maxlength);
                        j++) {
                    temp += ("<br />" +
                    controller[i].substring(index, (index + maxlength)));
                    index += maxlength;
                }

                if (index != (controller[i].length() - 1)) {
                    temp += ("<br />" +
                    controller[i].substring(index, controller[i].length()));
                }

                controller[i] = temp;
            }
        }

        for (int k = 0; k < controller.length; k++) {
            if (!controller[k].equals("")) {
                shortstring += (controller[k] + " ");
            }
        }

        return shortstring;
    }

    public static String lengthControl(String text, int maxlength) {
        if (text != null && text.length() > maxlength) {
            text = text.substring(0, (maxlength - 1)) + " ...";
        }

        return text;
    }

    /**
     * Function: Replace multiple whitespace to be one whitespace
     *
     * @param inputStr
     * @return String
     */
    public static String removeDuplicateSpace(String inputStr) {
        // check either the string content more than 1 whitespace
        // if it does, then repalce it with a single whitespace.
        String patternStr = "\\s+";
        String replaceStr = " ";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.replaceAll(replaceStr);
    }

    public static String space(String value) {
        if ((value == null) || value.equals("") ||
                value.equalsIgnoreCase("null") || (value.length() == 0)) {
            return " ";
        }

        return value;
    }

    /**
     * Function: Get any datas/strings inside a BODY tag. It also ignore <p> tag
     * @param value
     * @return
     */
    public static String getHTMLBody(String value) {
        StringTokenizer st = new StringTokenizer(value, "\n");
        String temp = "";
        String result = "";
        int foundBody = 0;

        while (st.hasMoreTokens()) {
            temp = st.nextToken();

            if (temp.indexOf("<body>") >= 0) {
                foundBody = 1;
            } else if (temp.indexOf("</body>") >= 0) {
                foundBody = 0;

                break;
            }

            //             if (foundBody == 2)
            //             {
            //            	 if ((!temp.contains("</p>"))&&(!temp.contains("<p style")))
            //            		 result+=temp+"<br>";
            //           }else if (foundBody == 1)
            //           {
            //          	 foundBody = 2;
            //           }
            if (foundBody == 2) {
                result += temp;
            } else if (foundBody == 1) {
                foundBody = 2;
            }
        }

        return result;
    }

    /**
     * Function:n Repalace a specific value(oldValue parameter) in a String and repace it with newValue parameter
     *
     * @param inputStr
     * @param oldValue
     * @param newValue
     * @return
     */
    public static String replaceNewLineBR(String inputStr, String oldValue,
        String newValue) {
        String patternStr = oldValue;
        String replaceStr = newValue;
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.replaceAll(replaceStr);
    }

    /**
     * Function: If a String start with whitespace(s), this function will remove it.
     * @param inputStr
     * @return
     */
    public static String replaceWhitespace(String inputStr) {
        String temp = "";

        for (int i = 0; i < inputStr.length(); i++) {
            if (inputStr.charAt(i) == ' ') {
                try {
                    //remove the white space
                    temp = inputStr.substring(i + 1);
                } catch (Exception e) {
                    temp = inputStr.substring(i);
                }
            } else {
                break;
            }
        }

        return temp;
    }

    /**
     * Function: Replace 'Null' to be empty string. if string not 'Null' Trim it.
     * @param inputStr
     * @return
     */
    public static String replaceNullToEmptyString(String inputStr) {
        if ((inputStr == null) || inputStr.equalsIgnoreCase("null")) {
            return "";
        } else {
            return inputStr.trim();
        }
    }

    public static String formatMindServerResult(String inputStr) {
        if ((inputStr == null) || inputStr.equalsIgnoreCase("null")) {
            return "";
        } else {
            inputStr = StringUtils.replace(inputStr,
                    (new Character((char) 146)).toString(), "&#146;");
            inputStr = StringUtils.replace(inputStr,
                    (new Character((char) 8217)).toString(), "&#8217;");
            inputStr = StringUtils.replace(inputStr,
                    (new Character((char) 8216)).toString(), "&#8216;");
            inputStr = StringUtils.replace(inputStr,
                    (new Character((char) 8220)).toString(), "&#8220;");
            inputStr = StringUtils.replace(inputStr,
                    (new Character((char) 8221)).toString(), "&#8221;");

            return inputStr;
        }
    }

    /**
     * Business method to tuncate java.lang.String to a max length. If the inputStr string is null, it will return null
     * @param inputStr Text will be truncated
     * @param maxLength maximum length for the input string
     * @return tructacated java.lang.String.
     */
    public static String truncateString(String inputStr, int maxLength) {
        if (inputStr == null) {
            return null;
        } else {
            String tmp = (StringUtils.trimToEmpty(inputStr).length() > maxLength)
                ? StringUtils.trimToEmpty(inputStr).substring(0, maxLength)
                : StringUtils.trimToEmpty(inputStr);

            return tmp;
        }
    }
    
    public static String trimZeroForMoney(String inputStr) {
    	if(!StringUtil.isEmpty(inputStr)){
	    	int index=inputStr.indexOf(".");
	    	if(index==-1){
	    		return inputStr;
	    	}else{
				char[] charGroup=inputStr.toCharArray();
				StringBuffer test1=new StringBuffer("");
				for(int i=0;i<index-1;i++){
					if(!Constants.ZERO.equals(String.valueOf(charGroup[i]))){
						test1.append(charGroup[i]);
						index=i;
						break;
					}
				}
				String endTest=inputStr.substring(index);
				return endTest;
	    	}
			
			
    	}else{
    		return null;
    	}
    }
    
    public static String trim(String s){
    	if(s == null)
    		return "";
    	else
    		return s.trim();
    }
    
    public static long[] toLong(String[] s){
    	if(s == null || s.length == 0){
    		return null;
    	}
    	long[] l = new long[s.length];
		for(int i = 0; i < s.length; i ++)
			l[i] = Long.parseLong(s[i]);
		return l;
    }
    
    public static String[] toString(long[] l){
    	if(l == null || l.length == 0){
    		return null;
    	}
    	String[] s = new String[l.length];
		for(int i = 0; i < l.length; i ++)
			s[i] = Long.toString(l[i]);
		return s;
    }

	public static int[] toInt(String[] s) {
		if(s == null || s.length == 0){
    		return null;
    	}
		int[] intValue = new int[s.length];
		for(int i = 0; i < s.length; i ++)
			intValue[i] = Integer.parseInt(s[i]);
		return intValue;
	}
	
	/**
	 * @param s 
	 * 		String input
	 * @param length 
	 * 		match length expected
	 */
//	public static boolean isNumber(String s, int length){
//		if(s == null || s.length() != length)
//			return false;
//		return isNumber(s);
//	}
	
//	public static boolean isNumber(String s){
//		if(s == null)
//			return false;
//		return Pattern.compile("\\d+").matcher(s).matches();
//	}
	
	/**
	 * @author zhangli
	 */
	public static boolean isNullForPageChecks(String pageChecksID){
		if(StringUtil.isEmpty(pageChecksID)){
			return true;
		}else{
			String checks[] = pageChecksID.split("\\|");
			if(checks==null){
				return true;
			}else if(checks.length==1&&"".equalsIgnoreCase(checks[0])){
				return true;
			}
		}
		return false;	
	}
	
	/**
	 * @author zhangli
	 */
	public static boolean contains(String []types,String value){
		if(types==null){
			return false;
		}else{
			for(int i = 0;i < types.length;i++){
				if(value.equalsIgnoreCase(types[i])){
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * @author zhangli
	 */
	public static boolean contains(String []types,long value){
		if(types==null){
			return false;
		}else{
			for(int i = 0;i < types.length;i++){
				if(String.valueOf(value).equalsIgnoreCase(types[i])){
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * @author zhangli
	 */
    public static boolean isNumeric(String str){
    	if(StringUtil.isEmpty(str)){
    		return false;
    	}else{
    		for(int i=0;i<str.length();i++){
    			if(str.charAt(i)>'9'||str.charAt(i)<'0'){
    				return false;
    			}
    		}
    	}
    	return true;
    }
    /**
	 * @author zhangli
	 */
    public static boolean isNumericAndSpace(String str){
    	if(StringUtil.isEmpty(str)){
    		return false;
    	}else{
    		for(int i=0;i<str.length();i++){
    			if(str.charAt(i)>'9'||str.charAt(i)<'0'){
    				if(str.charAt(i)==' '){
    					continue;
    				}
    				return false;
    			}
    		}
    	}
    	return true;
    }
    /**
     * @author zhigang
     */
    public static String encodeXML(String value){
    	if (StringUtil.isEmpty(value)) {
    		return value;
		}

		StringBuffer result = null;
		String filtered = null;

		for (int i = 0; i < value.length(); i++) {
			filtered = null;
	
			switch (value.charAt(i)) {
				case '<':
					filtered = "&lt;";
					break;
		
				case '>':
					filtered = "&gt;";
					break;
		
				case '&':
					filtered = "&amp;";
					break;
		
				case '"':
					filtered = "&quot;";
					break;
		
				case '\'':
					filtered = "&#39;";
					break;
			}
	
			if (result == null) {
				if (filtered != null) {
					result = new StringBuffer(value.length() + 50);
		
					if (i > 0) {
						result.append(value.substring(0, i));
					}
		
					result.append(filtered);
				}
			} else {
				if (filtered == null) {
					result.append(value.charAt(i));
				} else {
					result.append(filtered);
				}
			}
		}

		return (result == null) ? value : result.toString();
    }
    

    /**
    * Escapes non-ascii and HTML characters
    * @param content
    * @return
    */
    public static String escapeNonAscii(String content) {
	    StringBuffer buffer = new StringBuffer();
	    if (content != null) {
		    for (int i = 0; i < content.length(); i++) {
			    char c = content.charAt(i);
			    switch (c) {
				    case '&':
					    buffer.append("&amp;");
					    break;
				
				    case '<':
					    buffer.append("&lt;");
					    break;
				
				    case '>':
					    buffer.append("&gt;");
					    break;
				
				    default:
					    if (((c >= 0x20) && (c <= 0x7e)) || (c == /* line feed */10) || (c == /* carriage return */13)){
					    	buffer.append(c);
					    }else {
						    buffer.append("&#");
						    buffer.append((int) c);
						    buffer.append(';');
					    }
					    break;
			    } /* switch (c) */
		    } /* for i */
	    }
	    return buffer.toString();
    }

    /**
     * Escapes non-ascii and HTML characters
     * @param content
     * @return
     */
     public static String escapeNonAsciiWithoutConvertCharacter(String content) {
 	    StringBuffer buffer = new StringBuffer();
 	    if (content != null) {
 		    for (int i = 0; i < content.length(); i++) {
 			    char c = content.charAt(i);
 			    switch (c) {
 				    case '&':
 					    buffer.append("&");
 					    break;
 				
 				    case '<':
 					    buffer.append("<");
 					    break;
 				
 				    case '>':
 					    buffer.append(">");
 					    break;
 				
 				    default:
 					    if (((c >= 0x20) && (c <= 0x7e)) || (c == /* line feed */10) || (c == /* carriage return */13)){
 					    	buffer.append(c);
 					    }else {
 						    buffer.append("&#");
 						    buffer.append((int) c);
 						    buffer.append(';');
 					    }
 					    break;
 			    } /* switch (c) */
 		    } /* for i */
 	    }
 	    return buffer.toString();
     }
     
    /**
     * replace '|' char to encoder char
     * @author chenjia
     */
    public static String encodeChar(String value){
    	if (StringUtil.isEmpty(value)) {
    		return value;
		}

		StringBuffer result = null;
		String filtered = null;

		for (int i = 0; i < value.length(); i++) {
			filtered = null;
	
			switch (value.charAt(i)) {
				case '|':
					filtered = "%7C";
					break;
				case '%':
					filtered = "%25";
					break;	
				case '"':
					filtered="%22";
					break;
				case '\'':
					filtered="%27";
					break;
				case '#':
					filtered="%23";
					break;
			}
	
			if (result == null) {
				if (filtered != null) {
					result = new StringBuffer(value.length() + 50);
		
					if (i > 0) {
						result.append(value.substring(0, i));
					}
		
					result.append(filtered);
				}
			} else {
				if (filtered == null) {
					result.append(value.charAt(i));
				} else {
					result.append(filtered);
				}
			}
		}

		return (result == null) ? value : result.toString();
    }
    
    /**
     * @author chenjia
     */
    public static String decodeChar(String value){
    	if (StringUtil.isEmpty(value)) {
    		return value;
		}

		StringBuffer result = null;
		String filtered = null;
		String cellString=null;
		for (int i = 0; i < value.length()-2; i++) {
			filtered = null;
			cellString=value.substring(i,i+3);
			if(cellString.equals("%7C")){
				filtered="|";
			}else if(cellString.equals("%25")){
				filtered="%";
			}else if(cellString.equals("%22")){
				filtered="\"";
			}else if(cellString.equals("%27")){
				filtered="'";
			}else if(cellString.equals("%23")){
				filtered="#";
			}
	
			if (result == null) {
				if (filtered != null) {
					result = new StringBuffer(value.length() + 50);
		
					if (i > 0) {
						result.append(value.substring(0, i));
					}
		
					result.append(filtered);
					i=i+2;
				}
			} else {
				if (filtered == null) {
					result.append(value.charAt(i));
				} else {
					result.append(filtered);
					i=i+2;
				}
			}
		}
		
		if(result == null){
			return value;
		}else{
			result.append(value.substring(value.length()-2));
		}

		return result.toString();
    }
    
    /**
     * @author chenjia
     */
    public static boolean isBodyBlank(String value){
    	boolean isBlank=true;
    	String[] cells=value.split(">");
    	for(int i=0;i<cells.length;i++){
    		if(cells[i].startsWith("<")){
    			
    		}else{
    			if(isEmpty(cells[i])||cells[i].trim().matches("^(&nbsp;)*\\s*</.*$")){
        			
        		}else{
        			isBlank=false;
        		}
    		}
    	}
    	return isBlank;
    }
    
    /**
     * @author zhigang
     */
    public static String deQuote(String value){
    	if(isEmpty(value)){
    		return "";
    	}

		StringBuffer result = null;
		String filtered = null;

		for (int i = 0; i < value.length(); i++) {
			filtered = null;
	
			switch (value.charAt(i)) {
				case '\\':
					filtered = "\\\\";
					break;
				case '\'':
					filtered = "\\'";
					break;	
			}
	
			if (result == null) {
				if (filtered != null) {
					result = new StringBuffer(value.length() + 50);
		
					if (i > 0) {
						result.append(value.substring(0, i));
					}
		
					result.append(filtered);
				}
			} else {
				if (filtered == null) {
					result.append(value.charAt(i));
				} else {
					result.append(filtered);
				}
			}
		}

		return (result == null) ? value : result.toString();
    }
    
    /**
     * @author zhangli
     */
    public static boolean equals(String str1,String str2){
    	if(StringUtil.notEmpty(str1)){
    		str1 = str1.trim();
    	}
    	if(StringUtil.notEmpty(str2)){
    		str2 = str2.trim();
    	}
    	if(StringUtils.isEmpty(str1)&&StringUtils.isEmpty(str2)){
    		return true;
    	}else{
    		if(StringUtils.equals(str1,str2)){
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * @author zhigang
     */
    public static String[] deleteEmpty(String[] arg){
    	if(arg == null || arg.length == 0){
    		return arg;
    	}
    	int len = arg.length;
    	int index = 0;
    	while(index < len){
    		if(isEmpty(arg[index])){
    			len --;
    			arg[index] = arg[len];
    		} else {
    			index ++;
    		}
    	}
    	String[] result = new String[len];
    	System.arraycopy(arg,0,result,0,len);
    	return result;
    }
}
