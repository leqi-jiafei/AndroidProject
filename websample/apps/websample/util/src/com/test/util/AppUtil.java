package com.test.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.test.web.struts.form.TestForm;


/**
 * This class provides util method.
 * 
 * @author Qiu Le Qi
 * @version $Revision: 
*/
public class AppUtil {
    public static final String DATEFORMAT = "yyyyMMddHHmmssSSS";
    public static final String DATEFORMAT2 = "yyyyMMdd";
    public static final String UTCDATEFORMAT = "yyyyMMddTHHmmssZ";
    public static final String DATEDISPLAYFORMAT = "yyyy:MM:dd HH:mm:ss";
    public static final String TIMEDISPLAYFORMAT = "h:mm a";
    public static final String TIMEDISPLAYFORMAT2 = "hh:mm aaa";
    public static final String DATEDISPLAYFORMAT1 = "dd/MM/yyyy";
    public static final String DATEDISPLAYFORMAT2 = "dd/MM/yyyy hh:mm aaa";
    public static final String DATEDISPLAYFORMAT3 = "d/MM/yyyy";
    public static final String DATEDISPLAYFORMAT4 = "d MMMMM yyyy"; // sample "12 March 2007"
    public static final String DATEDISPLAYFORMAT5 = "d MMMMM yyyy hh:mm aaa"; // sample "12 March 2007 03:12 AM"
    public static final String DATEDISPLAYFORMAT6 = "d MMM yyyy hh:mm aaa"; // sample "12 Mar 2007 03:12 AM"
    public static final String DATEDISPLAYFORMAT7 = "d MMM yyyy"; // sample "12 Mar 2007"
    public static final String DATEDISPLAYFORMAT8 = "dd-MM-yyyy hh:mm"; // sample "14-11-2008 10:09"
    public static final String DATEDISPLAYFORMAT9 = "dd-MM-yyyy HH:mm"; // sample "14-11-2008 10:09"

	private static final Logger log = Logger.getLogger(AppUtil.class);
	
    /**
     * add by lidong on Apr 22,2009 for ajax check
     * @param response
     * @param result
     * @throws IOException
     */
    public static void writeResponseTextForCheck(HttpServletResponse response, String result) throws IOException{
		response.setContentType("text/xml;charset=UTF-8");
		StringBuffer sb = new StringBuffer();
		sb.append("<result>");
		if(StringUtil.notEmpty(result)){
			sb.append(result);
		}else{
			sb.append("noResult");
		}
		sb.append("</result>");

		PrintWriter out = response.getWriter();
		out.write(sb.toString().toCharArray());
		out.flush();
		out.close();
	}
	
    /**
     * @author leqi
     *
     * @param date
     * @return
     */
    public static String formatDateToString(Date date) {
        return formatDateToString(date, DATEFORMAT);
    }

    /**
     * @author leqi
     *
     * @param date
     * @return
     */
    public static String formatDateToDisplayString(Date date) {
        return formatDateToString(date, DATEDISPLAYFORMAT);
    }

    /**
     * @author leqi
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDateToString(Date date, String format) {
        String rv = null;

        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            rv = sdf.format(date);
        }

        return rv;
    }

    /**
     * @author leqi
     *
     * @param str
     * @param format
     * @return
     */
    public static String formatStringToGivenFormat(String str,String format){
    	Date date = formatStringToDate(str);
		return formatDateToString(date,format);
    }
    /**
     * @author leqi
     *
     * @param args
     */
    public static void main(String[] args) {
        prepareDateList();

        //		deleteFileTree("c:/EMS-TMP");
    }

    /**
     * @author leqi
     *
     * @param Filepath
     */
    public static void deleteFileTree(String Filepath) {
        File file = new File(Filepath);
        String[] fileTree = file.list();
        String filePath;
        File tempFile;
        File fold;
        if (fileTree != null) {
            for (int i = 0; i < fileTree.length; i++) {
                filePath = fileTree[i];
                tempFile = new File(Filepath + "\\" + filePath);

                if (tempFile.exists() && tempFile.isFile()) {
                    tempFile.delete();
                } else {
                    deleteFileTree(Filepath + "\\" + filePath);

                    fold = new File(Filepath + "\\" + filePath);
                    fold.delete();
                }
            }
        }
    }

    /**
     * @author leqi
     *
     * @param yearmonthday
     * @param hourminutesecondmili
     * @return
     */
    public static Date formatInputDateStringToDate(String yearmonthday,
        String hourminutesecondmili) {
        Date rv = null;

        try {
            if ((yearmonthday != null) && (yearmonthday.trim().length() > 0) &&
                    (yearmonthday.trim().length() == 10)) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR,
                    Integer.parseInt(yearmonthday.substring(6)));
                cal.set(Calendar.MONTH,
                    Integer.parseInt(yearmonthday.substring(3, 5)) - 1);
                cal.set(Calendar.DAY_OF_MONTH,
                    Integer.parseInt(yearmonthday.substring(0, 2)));

                if (StringUtil.notEmpty(hourminutesecondmili) &&
                        (hourminutesecondmili.trim().length() == 9)) {
                    cal.set(Calendar.HOUR_OF_DAY,
                        Integer.parseInt(hourminutesecondmili.substring(0, 2)));
                    cal.set(Calendar.MINUTE,
                        Integer.parseInt(hourminutesecondmili.substring(2, 4)));
                    cal.set(Calendar.SECOND,
                        Integer.parseInt(hourminutesecondmili.substring(4, 6)));
                    cal.set(Calendar.MILLISECOND,
                        Integer.parseInt(hourminutesecondmili.substring(6)));
                } else {
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                }

                rv = cal.getTime();

                // throw new Exception();
            }
        } catch (Exception e) {
            log.debug("Exception occured in the formatInputDateStringToDate method of " + AppUtil.class);
            log.debug(e.getMessage());
        }

        return rv;
    }

    /**
     * @author leqi
     *
     * @param datestr
     * @return
     */
    public static Date formatStringToDate(String datestr) {
        Date rv = null;

        try {
            if ((datestr != null) && (datestr.trim().length() > 0) &&
                    (datestr.trim().length() == 17)) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, Integer.parseInt(datestr.substring(0, 4)));
                cal.set(Calendar.MONTH,
                    Integer.parseInt(datestr.substring(4, 6)) - 1);
                cal.set(Calendar.DAY_OF_MONTH,
                    Integer.parseInt(datestr.substring(6, 8)));
                cal.set(Calendar.HOUR_OF_DAY,
                    Integer.parseInt(datestr.substring(8, 10)));
                cal.set(Calendar.MINUTE,
                    Integer.parseInt(datestr.substring(10, 12)));
                cal.set(Calendar.SECOND,
                    Integer.parseInt(datestr.substring(12, 14)));
                cal.set(Calendar.MILLISECOND,
                    Integer.parseInt(datestr.substring(14)));
                rv = cal.getTime();

                // throw new Exception();
            }
        } catch (Exception e) {
        	log.debug("Exception occured in the formatStringToDate of " + AppUtil.class);
            log.debug(e.getMessage());
        }

        return rv;
    }

    /**
     * trim date input from 'dd/MM/yyyy' to 'yyyyMMdd' simple trim , no
     * validation:
     *
     * @param inputDate
     * @return
     */
    public static String trimDateFromInput(String inputDate) {
        if (StringUtil.isEmpty(inputDate) || (inputDate.trim().length() != 10)) {
            return null;
        } else {
            return inputDate.substring(6) + inputDate.substring(3, 5) +
            inputDate.substring(0, 2);
        }
    }
    /**
     * check the input String array, if has dummy values, remove them
     *
     * @author leqi
     *
     * @param source
     * @return
     */
    public static String[] checkDummyStringArray(String[] source) {
        if (source == null) {
            return null;
        }

        if ((source.length == 1) && StringUtil.isEmpty(source[0])) {
            return null;
        }

        ArrayList list = new ArrayList();

        for (int i = 0; i < source.length; i++) {
            if (!StringUtil.isEmpty(source[i])) {
                list.add(source[i]);
            }
        }

        if (list.size() > 0) {
            String[] rv = new String[list.size()];
            int i = 0;

            for (int j = 0; j < list.size(); j++) {
                String value = (String) list.get(j);
                rv[i++] = value;
            }

            return rv;
        } else {
            return null;
        }
    }

    /**
     * @author leqi
     *
     * @param clob
     * @return
     */
    public static String readClob(Clob clob) {
        StringBuffer returnvalue = new StringBuffer();
        
        try {
            if (clob != null) {
                Reader is;
                is = clob.getCharacterStream();

                BufferedReader br = new BufferedReader(is);
                String s = br.readLine();
                boolean first = true;

                while (s != null) {
                    if (!first) {
                        first = false;
                        returnvalue.append("\n");
                    }

                    returnvalue.append(s);
                    s = br.readLine();
                }
            }
        } catch (SQLException e) {
        	log.debug("Exception occured in " + AppUtil.class);
            log.debug(e.getErrorCode()+e.getMessage());
        } catch (IOException e) {
        	log.debug("Exception occured in " + AppUtil.class);
            log.debug(e.getMessage());
        }

        return returnvalue.toString();
    }

    /*
     * format String number from '0034.00' to '34.00'
     *
     */
    /**
     * @author leqi
     *
     * @param originalValue
     * @return
     */
    public static String formatNumberToDisplay(String originalValue) {
        String rv = "";

        try {
            if (originalValue == null) {
                rv = null;
            } else{
//            	if (originalValue.trim().length() == 0) {
//            } else 
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMinimumFractionDigits(2);
                nf.setMaximumFractionDigits(2);
                nf.setGroupingUsed(false);
                rv = nf.format(Double.parseDouble(originalValue));
            }
        } catch (Exception e) {
        	log.debug("Exception catught in formatNumberToDisplay method of " + AppUtil.class);
            log.debug(e.getMessage());
        }

        return rv;
    }

    /*
     * format String number from '34' to '0034.00'
     *
     */
    /**
     * @author leqi
     *
     * @param originalValue
     * @return
     */
    public static String formatNumberToStorage(String originalValue) {
        String rv = "";

        try {
            if (originalValue == null) {
                rv = null;
            } else{
//            	if (originalValue.trim().length() == 0) {
//            } else {
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMinimumFractionDigits(2);
                nf.setMaximumFractionDigits(2);
                nf.setMaximumIntegerDigits(4);
                nf.setMinimumIntegerDigits(4);
                nf.setGroupingUsed(false);
                rv = nf.format(Double.parseDouble(originalValue));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rv;
    }

    /**
     * @author leqi
     *
     * @param date
     * @return
     */
    public static String getDayOfWeekDescription(Calendar date) {
        String rv = "";

        if (date != null) {
            switch (date.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                rv = "Sunday";

                break;

            case 2:
                rv = "Monday";

                break;

            case 3:
                rv = "Tuesday";

                break;

            case 4:
                rv = "Wednesday";

                break;

            case 5:
                rv = "Thursday";

                break;

            case 6:
                rv = "Friday";

                break;

            case 7:
                rv = "Saturday";

                break;
            }
        }

        return rv;
    }

    /**
     * @author leqi
     *
     * @param date
     * @return
     */
    public static String getMonthDescription(Calendar date) {
        String rv = "";

        if (date != null) {
            switch (date.get(Calendar.MONTH)) {
            case 0:
                rv = "January";

                break;

            case 1:
                rv = "February";

                break;

            case 2:
                rv = "March";

                break;

            case 3:
                rv = "April";

                break;

            case 4:
                rv = "May";

                break;

            case 5:
                rv = "June";

                break;

            case 6:
                rv = "July";

                break;

            case 7:
                rv = "August";

                break;

            case 8:
                rv = "September";

                break;

            case 9:
                rv = "October";

                break;

            case 10:
                rv = "November";

                break;

            case 11:
                rv = "December";

                break;
            }
        }

        return rv;
    }

    /**
     * @author leqi
     *
     * @param date
     * @return
     */
    public static String formatDateToUTCString(Date date) {
        String rv = null;

        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            rv = sdf.format(date);
            rv += "T";
            sdf = new SimpleDateFormat("HHmmss");
            rv += sdf.format(date);
            rv += "Z";
        }

        return rv;
    }

    /**
     * @author leqi
     *
     * @param percent
     * @return
     */
    public static double formatPercentToDecimal(String percent) {
        double re = 0.00;

        if (percent != null) {
            percent = percent.replaceAll("%", "");
            re = Double.parseDouble(percent);
            re = re / 100;
        }

        return re;
    }

    /**
     * @author leqi
     *
     * @param input
     * @return
     */
    public static String filterCSV(String input) {
        String rv = "";

        if (StringUtil.notEmpty(input)) {
            rv = input.replaceAll("\"", "\"\"");
        }

        rv = "\"" + rv + "\"";

        return rv;
    }

    /**
     * @author leqi
     *
     * @param doc
     * @param name
     * @return
     */
    public static String getValueFromXML(Document doc, String name) {
        String[] names = name.split("/");
        Element element = doc.getRootElement();

        if ((names == null) || (names.length < 2)) {
            return null;
        }

        for (int i = 2; i < names.length; i++) {
            element = searchName(element, names[i]);

            if (element != null) {
                continue;
            } else {
                break;
            }
        }

        if (element != null) {
            return element.getText();
        } else {
            return null;
        }
    }

    /**
     * @author leqi
     *
     * @param element
     * @param name
     * @return
     */
    public static Element searchName(Element element, String name) {
        for (Iterator i = element.elementIterator(); i.hasNext();) {
            element = (Element) i.next();

            if (name.equals(element.getName())) {
                return element;
            }
        }

        return null;
    }

    /**
     * @author leqi
     *
     * @param svc_ABN
     * @return
     */
    public static String formatABN(String svc_ABN) {
        if (!StringUtil.isEmpty(svc_ABN)) {
            String abn = "";

            while (svc_ABN.length() > 3) {
                String temp = svc_ABN.substring(svc_ABN.length() - 3,
                        svc_ABN.length());
                svc_ABN = svc_ABN.substring(0, svc_ABN.length() - 3);

                if (svc_ABN.length() > 0) {
                    abn = " " + abn;
                }

                abn = temp + abn;
            }

            if (svc_ABN.length() > 0) {
                abn = svc_ABN + " " + abn;
            }

            svc_ABN = abn;
        } else {
            svc_ABN = "";
        }

        return svc_ABN;
    }

    /**
     * @author leqi
     *
     * @param oldStr
     * @return
     */
    public static String getFirstCharToUpper(String oldStr) {
        if (!StringUtil.isEmpty(oldStr)) {
            String newStr = oldStr.toLowerCase();
            String returnStr = newStr.substring(0, 1) + oldStr.substring(1);

            return returnStr;
        } else {
            return "";
        }
    }

    /**
     * @author leqi
     *
     * @param dd
     * @param mm
     * @param yy
     * @return
     */
    public static String mergeInputDate(String dd, String mm, String yy) {
        String rv = null;

        if (StringUtil.isEmpty(dd) || StringUtil.isEmpty(mm) ||
                StringUtil.isEmpty(yy)) {
        } else if ((dd.length() > 2) || (mm.length() > 2) || (yy.length() > 4)) {
        } else {
            dd = dd.trim();
            mm = mm.trim();
            yy = yy.trim();

            if (dd.length() < 2) {
                dd = Constants.ZERO + dd;
            }

            if (mm.length() < 2) {
                mm = Constants.ZERO + mm;
            }

            switch (yy.length()) {
            case 1:
                yy = "200" + yy;

                break;

            case 2:
                yy = "20" + yy;

                break;

            case 3:
                yy = "2" + yy;

                break;

            default:
            }

            if (DateUtil.isValidDate(yy + mm + dd, "yyyyMMdd")) {
                rv = dd + "/" + mm + "/" + yy;
            }
        }

        return rv;
    }

    /**
     * @author leqi
     *
     * @return
     */
    public static Map prepareDateList() {
        Map rv = new LinkedHashMap();
        Calendar cal = Calendar.getInstance();
        Calendar endcal = Calendar.getInstance();
        endcal.add(Calendar.MONTH, 1);

        //		HashMap day;
        while (cal.getTime().getTime() <= endcal.getTime().getTime()) {
            //			System.out.println(cal.getTime().getTime()+"----"+endcal.getTime().getTime());
            rv.put(AppUtil.formatDateToString(cal.getTime(),
                    AppUtil.DATEFORMAT2),
                AppUtil.formatDateToString(cal.getTime(),
                    AppUtil.DATEDISPLAYFORMAT7));
            //			day=new HashMap()
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        //		System.out.println(rv.size());
        //		System.out.println(cal.getTime());
        //		System.out.println(endcal.getTime());
        return rv;
    }
	
	/**
	 * @author leqi
	 *
	 * @param keyword
	 * @param tablefield
	 * @param where
	 * @param sc
	 * @return
	 */
	public static String orgnizeSQL(String keyword,String tablefield,StringBuffer where,SimpleContainer sc){
		String[] keywords=new String[]{keyword};
		StringBuffer keywordvalue;
		int x=sc.getCount();
		List list = sc.getList();
		for (int i=0;i<keywords.length;i++){
			if (StringUtil.notEmpty(keywords[i])){
//				System.out.println(keywords[i]);
				keywords[i]=keywords[i].replaceAll("\\*","%");
//				System.out.println(keywords[i]);
				if (StringUtil.notEmpty(where.toString())){
					where.append(" or ");
				}
				where.append(" ").append(tablefield).append(" like ");
				keywordvalue=new StringBuffer();
				if (!keywords[i].startsWith("%")){
					keywordvalue.append("%");
				}
				keywordvalue.append(keywords[i]);
				if (!keywords[i].endsWith("%")){
					keywordvalue.append("%");
				}
				list.add(keywordvalue.toString());
//				System.out.println("para"+x+"  "+keywordvalue.toString());
				where.append("?");
//				where.append("'");
				where.append(" or ").append(tablefield).append(" like ");
				keywordvalue=new StringBuffer();
				keywordvalue.append(keywords[i]);
				if (!keywords[i].endsWith("%")){
					keywordvalue.append("%");
				}
				list.add(keywordvalue.toString());
//				System.out.println("para"+x+"  "+keywordvalue.toString());
				where.append("?");
//				where.append("'");
				where.append(" or ").append(tablefield).append(" like ");
				keywordvalue=new StringBuffer();
				if (!keywords[i].startsWith("%")){
					keywordvalue.append("%");
				}
				keywordvalue.append(keywords[i]);
				list.add(keywordvalue.toString());
//				System.out.println("para"+x+"  "+keywordvalue.toString());
				where.append("?");
//				where.append("'");
				//added at Nov.8th 2007 ,for the missed case table field = keyword
				where.append(" or ").append(tablefield).append(" like ");
				keywordvalue=new StringBuffer();
				keywordvalue.append(keywords[i]);
				list.add(keywordvalue.toString());
//				System.out.println("para"+x+"  "+keywordvalue.toString());
				where.append("?");
//				where.append("'");
				
			}
		}
		sc.setCount(x);
		sc.setList(list);
		
		return where.toString();
	}
	
	/**
	 * @author leqi
	 * get checks[] from form , then set string check to form
	 * 
	 * @param form
	 * @param checksProperty   property name
	 * @param otherPageCheckProperty property name
	 */
	public static void getPageCheckFromChecks(TestForm form, String checksProperty, String otherPageCheckProperty) {
		//        	deal with the user's selection of multiboxes..
		{
//    		System.out.println("a. " + form.getOtherPageChecks());
		String[] currPageChecks = null;
		String checksStr = null;
		try {
			currPageChecks = (String[])PropertyUtils.getProperty(form, checksProperty);
			checksStr = (String)PropertyUtils.getProperty(form, otherPageCheckProperty);
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer selectchecks = new StringBuffer(StringUtil.deNull(checksStr));
		if (currPageChecks != null) {
			for (int i = 0; i < currPageChecks.length; i++) {
				if (!StringUtil.isEmpty(currPageChecks[i])) {
					if (StringUtil.isEmpty(selectchecks.toString())) {
						selectchecks.append("|").append(currPageChecks[i])
								.append("|");
					} else {
						selectchecks.append(currPageChecks[i]).append("|");
					}
				}
			}
		}
		try {
			PropertyUtils.setProperty(form, otherPageCheckProperty, selectchecks.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//    		System.out.println("b. " + form.getOtherPageChecks());
		}
	}


	/**
	 * @author leqi
	 *
	 * checked previous checked checkbox when loadding pages
	 *
	 * @param form
	 * @param currPageList
	 * @param checksProperty
	 * @param otherPageCheckProperty
	 * @param clazz  class in list
	 * @param pkProperty pk's property name
	 */	
	public static void checkedMutilChecks(TestForm form, List currPageList, String checksProperty, String otherPageCheckProperty, Class clazz, String pkProperty,String type) {
		//deal with the user's selection of multiboxes
//    		System.out.println("c. "+form.getOtherPageChecks());
		String selectchecks = null;
		try {
			selectchecks = (String)PropertyUtils.getProperty(form, otherPageCheckProperty);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (!StringUtil.isEmpty(selectchecks) && currPageList.size() > 0) {
//    			ArrayList nowPageIDs = new ArrayList();
			Hashtable prevChecksMap = new Hashtable();
			//add by zhangli to ensure there is no "" in the result array
			if("|".equalsIgnoreCase(selectchecks.substring(0,1))){
				selectchecks = selectchecks.substring(1,selectchecks.length());
			}
			String[] prevChecksArray = selectchecks.split("\\|");
			try {
				PropertyUtils.setProperty(form, checksProperty, prevChecksArray);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			form.setChecks(prevChecksArray);
			if (prevChecksArray != null) {
				for (int i = 0; i < prevChecksArray.length; i++) {
					if (!StringUtil.isEmpty(prevChecksArray[i])) {
						prevChecksMap.put(prevChecksArray[i],
								prevChecksArray[i]);
					}
				} 
				Object obj;
				for (int i = 0; i < currPageList.size(); i++) {
					obj = currPageList.get(i);
					Object pk = null;
					Object oType = null;
					try {
//						Filed fd = clazz.getDeclaredField(pkProperty);
						pk = (Object)PropertyUtils.getProperty(obj,pkProperty);
						if(type!=null){
							oType = PropertyUtils.getProperty(obj,type);
						}
						//versionNum = (Object)PropertyUtils.getProperty(obj, "versionNum");
					} catch (Exception e) {
						e.printStackTrace();
					} 
					if(oType==null){
						prevChecksMap.remove(pk.toString());
					}else{
						//add by zhangli
						prevChecksMap.remove(oType.toString()+","+pk.toString());
						prevChecksMap.remove(oType.toString()+"+"+pk.toString());
					}
					
				}
				if (prevChecksMap.isEmpty()) {
					try {
						PropertyUtils.setProperty(form, otherPageCheckProperty, "");
					} catch (Exception e) {
						e.printStackTrace();
					}
//					form.setOtherPageChecks("");
				} else {
					ArrayList remainIDs = new ArrayList(prevChecksMap.values());
					StringBuffer sb = new StringBuffer("|");
					for (int i = 0; i < remainIDs.size(); i++) {
						sb.append((String) remainIDs.get(i)).append("|");
					}
					try {
						PropertyUtils.setProperty(form, otherPageCheckProperty, sb.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
//					form.setOtherPageChecks(sb.toString());
				}
			}
		}
//    		System.out.println("d. "+form.getOtherPageChecks());
	}
	/**
	 * @author jiafei
	 *
	 * read a line data from csv file and return string array.
	 *
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String[] splitCSV(String src) throws Exception{
		  if (src==null || src.equals("")) return new String[0];
		  StringBuffer st=new StringBuffer();
		  Vector result=new Vector();
		  boolean beginWithQuote = false;
		  for (int i=0;i<src.length();i++){
		   char ch = src.charAt(i);
		   if (ch=='\"'){
		    if (beginWithQuote){
		     i++;
		     if (i>=src.length()){
		      result.addElement(st.toString());
		      st=new StringBuffer();
		      beginWithQuote=false;
		     }else{
		      ch=src.charAt(i);
		      if (ch == '\"'){
		       st.append(ch);
		      }else if (ch == ','){
		       result.addElement(st.toString());
		       st=new StringBuffer();
		       beginWithQuote = false;
		      }else{
		       throw new Exception("Single double-quote char mustn't exist in filed "+(result.size()+1)+" while it is begined with quote\nchar at:"+i);
		      }
		     }
		    }else if (st.length()==0){
		     beginWithQuote = true;
		    }else{
		     throw new Exception("Quote cannot exist in a filed which doesn't begin with quote!\nfield:"+(result.size()+1));
		    }
		   }else if (ch==','){
		    if (beginWithQuote){
		     st.append(ch);
		    }else{
		     result.addElement(st.toString());
		     st=new StringBuffer();
		     beginWithQuote = false;
		    }
		   }else{
		    st.append(ch);
		   }
		  }
		  if (st.length()!=0){
		   if (beginWithQuote){
		    throw new Exception("last field is begin with but not end with double quote");
		   }else{
		    result.addElement(st.toString());
		   }
		  }
		  String rs[] = new String[result.size()];
		  for (int i=0;i<rs.length;i++){
		   rs[i]=(String)result.elementAt(i);
		  }
		 return rs;
		 }

	 
	 
	/**
	 * @author leqi
	 *
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEqual(String str1,String str2){
		if(StringUtil.notEmpty(str1)&&StringUtil.notEmpty(str2)){
			if(str1.equalsIgnoreCase(str2)){
				return true;
			}
		}else if(StringUtil.isEmpty(str1)&&StringUtil.isEmpty(str2)){
			return true;
		}
		return false;
	}

	 /*
	 ***@author jiafei
	 *
	 * @param message
	 * @return
	 */
	public static String stripHTMLTags(String message) {
	        StringBuffer returnMessage = new StringBuffer(message);
	        int startPosition = message.indexOf("<");  // encountered the first
	                                                   // opening brace

	        int endPosition = message.indexOf(">");  // encountered the first closing
	                                                 // braces

	        while (startPosition != -1) {
	            if (endPosition == -1) {
	                returnMessage.deleteCharAt(startPosition);
	            } else if (startPosition < endPosition) {
	                returnMessage.delete(startPosition, endPosition + 1);  // remove
	                                                                       // the
	                                                                       // tag
	            } else {
	                returnMessage.deleteCharAt(endPosition);
	            }

	            startPosition = (returnMessage.toString()).indexOf("<");  // look
	                                                                      // for
	                                                                      // the
	                                                                      // next
	                                                                      // opening
	                                                                      // brace

	            endPosition = (returnMessage.toString()).indexOf(">");  // look for
	                                                                    // the next
	                                                                    // closing
	                                                                    // brace
	        }

	        return returnMessage.toString();
	    }
	
	 /**Method to check a string whether begin with "0"
	  * @author jiafei*/
	public static String checkZeroBegin(String seqNum){
		
		if(seqNum.trim().length()==1){
			return seqNum.trim();
		}
		
		if(StringUtil.notEmpty(seqNum)){
			char tmp;
			int zeroLength=0;
			for(int i=0;i<seqNum.length();i++){
				tmp=seqNum.charAt(i);
				if(!Constants.ZERO.equalsIgnoreCase((String.valueOf(tmp)))){
					zeroLength=i;
					break;
				}
			}
			seqNum=seqNum.substring(zeroLength,seqNum.length());
		}
		return seqNum.trim();
	}
	
	
	 /**
     * TODO Auto-generated method
     *
     * @param strParam TODO description here
     * @param strDefault TODO description here
     *
     * @return TODO description here
     */
    public static String getDefault(String strParam, String strDefault) {
        if ((strParam == null) || ("null".equalsIgnoreCase(strParam))) {
            return strDefault;
        } else {
            return strParam;
        }
    }
    
    /**
     * validate whether the paramter passed in is a numbric or not.
     * if the paramter was made up of number return true.
     * else return false.
     */
    public static boolean validateNumber(String number){
    	Pattern p = Pattern.compile("\\d*");
    	Matcher m = p.matcher(number);
    	return m.matches();
    }
    

    /**
     * read content from file and convert to char array
     * @author leqi
     *
     * @param file
     * @return
     */
    public static char[] readChars(File file){ 

         CharArrayWriter caw = new CharArrayWriter(); 

         try 

         { 

             Reader fr = new FileReader(file); 

            Reader in = new BufferedReader(fr); 

             int count = 0; 

             char[] buf = new char[16384]; 

            while ((count=in.read(buf)) != -1) { 

                 if (count > 0) caw.write(buf, 0, count); 

             } 

             in.close(); 

         } 

         catch (Exception e) { 
        	 log.debug("invoke readChars(File file) error");
        	 log.debug(e, e); 
         } 

         return caw.toCharArray(); 

    } 


    /**
     * read from string and convert to char array
     * 
     * @author leqi
     *
     * @param string
     * @return
     */
    public static char[] readChars(String string){ 

         CharArrayWriter caw = new CharArrayWriter(); 
         Reader sr = null;
         Reader in = null;
         try 

         { 

             sr = new StringReader(string.trim()); 

             in = new BufferedReader(sr); 

             int count = 0; 

             char[] buf = new char[16384]; 

            while ((count=in.read(buf)) != -1) { 

                 if (count > 0) caw.write(buf, 0, count); 

             } 

             in.close(); 

         } 

         catch (Exception e) { 
        	 log.debug("invoke readChars(String string) error");
        	 log.debug(e, e); 
         } finally{
        	 if(sr!=null){
        		try {
					sr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	 }
        	 if(in!=null){
        		try {
 					in.close();
 				} catch (IOException e) {
 					e.printStackTrace();
 				}
        	 }
         }
         return caw.toCharArray(); 

    }
     


    /**
     * read from file and conver to byte array
     * @author leqi
     *
     * @param file
     * @return
     */
    public static byte[] readBytes(File file){ 

         ByteArrayOutputStream baos = new ByteArrayOutputStream(); 

         try 

         { 

             InputStream fis = new FileInputStream(file); 

             InputStream is = new BufferedInputStream(fis); 

            int count = 0; 

             byte[] buf = new byte[16384]; 

             while ((count=is.read(buf)) != -1) { 

                 if (count > 0) baos.write(buf, 0, count); 

             } 

             is.close(); 

        } 
         catch (Exception e) { 
        	 log.debug("invoke readBytes(File file) error");
        	 log.debug(e, e); 
         } 

         return baos.toByteArray(); 

    } 



    /**
     * write byte array to a file
     * @author leqi
     *
     * @param file
     * @param data
     */
    public static void writeBytes(File file, byte[] data) { 

         try { 

             OutputStream fos = new FileOutputStream(file); 

             OutputStream os = new BufferedOutputStream(fos); 

             os.write(data); 

             os.close(); 

         } 

         catch (Exception e) { 
        	 log.debug("invoke writeBytes(File file, byte[] data) error");
        	 log.debug(e, e); 
         } 

    } 


    /**
     * write char array to a file
     * @author leqi
     *
     * @param file
     * @param data
     */
    public static void writeChars(File file, char[] data) { 
         try { 

             Writer fos = new FileWriter(file); 

             Writer os = new BufferedWriter(fos); 

            os.write(data); 

             os.close(); 

         } 

         catch (Exception e) { 
        	 log.debug("invoke writeChars(File file, char[] data) error");
        	 log.debug(e, e); 
         } 

    } 
    
    /**
     * @author chenjia
     * @param datestr
     * @return
     */
    public static Date formatStringFormat2ToDate(String datestr){
    	  Date rv = null;
          if (datestr != null) {
              SimpleDateFormat sdf = new SimpleDateFormat(DATEDISPLAYFORMAT2,java.util.Locale.US);
              try {
				rv = sdf.parse(datestr);
			} catch (ParseException e) {
				log.debug("Exception occured in the formatStringToDate of " + AppUtil.class);
	            log.debug(e.getMessage());
			}
          }
          return rv;
    }
    
    /**
     * @author zhouchao
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password){
    	if(StringUtil.isEmpty(password) || password.length() < 8
    			|| !password.matches("^.*(?=.{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$")){
    		return false;
    	}
    	return true;
    }
    
    /**
     * @author chenjia
     * @param is
     * @return
     */
    public static String inputStreamToString(InputStream is){
    	if(is == null){
    		return "";
    	}
    	BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
			while ((line = in.readLine()) != null){
			  buffer.append(line);
			}
		} catch (IOException e) {
			log.debug("IOException occured in inputStreamToString of " + AppUtil.class);
			log.debug(e, e); 
		}finally{
			try {
				if(in != null){
					in.close();
				}
			} catch (IOException e) {
				log.debug("IOException occured in inputStreamToString of " + AppUtil.class);
				log.debug(e, e); 
			}
		}
        return buffer.toString();
    }
}
