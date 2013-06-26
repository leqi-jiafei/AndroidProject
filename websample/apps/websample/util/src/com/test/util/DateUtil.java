package com.test.util; 

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Class contains utilizable methods about date
 *
 * @author Qiu Le Qi
 * @version $Revision: 
 */
public class DateUtil {
    private static long milliSecPerDay = 24 * 60 * 60 * 1000;
    private static long milliSecPerHour = 60 * 60 * 1000;
    private static long milliSecPerMinute =60 * 1000;

    public static String DATEFORMAT = "dd/MM/yyyy";
    public static String TIMESTAMPFORMAT = "dd/MM/yyyy HH:mm:ss.SSS";
    private static String timezone = "Australia/Sydney";

    private DateUtil () {
    }
    
    /**
     * This method is to check if the second parameter is equals or later than first parameter.
     * @param dte1  earlier date
     * @param dte2  later date
     * @return true if dte2 >= dte1
     */
    public static boolean equalsOrLaterThan(String dte1, String dte2) {
        try {                 
            Date earlierDte = parse(dte1,DATEFORMAT);
            Date laterDte = parse(dte2,DATEFORMAT);
            
            if(laterDte.before(earlierDte)) 
                return false;
            
            return true;
        }
        catch(java.text.ParseException pe) {            
        }
            
        return false;
    }
    
    /**
     * This method is to check if the first parameter is equals or earlier than second parameter
     * @param dte1  earlier date
     * @param dte2  later date
     * @return true if dte1 <= dte2
     */
    public static boolean equalsOrEarlierThan(String dte1, String dte2) {
        try {                 
            Date earlierDte = parse(dte1,DATEFORMAT);
            Date laterDte = parse(dte2,DATEFORMAT);
            
            if(earlierDte.after(laterDte)) 
                return false;
            
            return true;
        }
        catch(java.text.ParseException pe) {            
        }
            
        return false;
    }

    /**
     * Compute the age returning an array of integers, for year, month, and day respectively.
     * <p>
     * @param  birthdate The start date to start the age computation.
     * @param  asOf      The end date for the age computation
     * @return The age in years, months, days in the 0, 1, 2 indices respectively.
     */
    public static int[] age(Date birthdate, Date asOf) {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.setTime(birthdate);
        to.setTime(asOf);

        int birthYYYY = from.get(Calendar.YEAR);
        int birthMM = from.get(Calendar.MONTH);
        int birthDD = from.get(Calendar.DAY_OF_MONTH);

        int asofYYYY = to.get(Calendar.YEAR);
        int asofMM = to.get(Calendar.MONTH);
        int asofDD = to.get(Calendar.DAY_OF_MONTH);

        int ageInYears = asofYYYY - birthYYYY;
        int ageInMonths = asofMM - birthMM;
        int ageInDays = asofDD - birthDD;

        if ( ageInDays < 0 ) {
            // Guaranteed after this single treatment, ageInDays will be >= 0.
            // i.e. ageInDays = asofDD - birthDD + daysInBirthMM.
            ageInDays += from.getActualMaximum(Calendar.DAY_OF_MONTH);
            ageInMonths--;
        }

        if (ageInDays == to.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            ageInDays = 0;
            ageInMonths++;
        }

        if (ageInMonths < 0) {
            ageInMonths += 12;
            ageInYears--;
        }
        if (birthYYYY < 0 && asofYYYY > 0) ageInYears--;

        if (ageInYears < 0) {
            ageInYears = 0;
            ageInMonths = 0;
            ageInDays = 0;
        }

        int[] result = new int[3];
        result[0] = ageInYears;
        result[1] = ageInMonths;
        result[2] = ageInDays;
        return result;
    }

    /**
     * Calculate the duration in number of days. If the start date is later than the
     * end date, a negative value will be returned.
     * <p>
     * @param  startDate The start date of the duration.
     * @param  endDate   The end date of the duration.
     * @return The difference in number of days between the start date and end date.
     */
    public static long computeDuration(Date startDate, Date endDate) {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.setTime( startDate );
        to.setTime( endDate );

        // Set both from and to to the same time so that the number of
        // days calcualted will be accurate.
        from.set(Calendar.HOUR_OF_DAY, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);
        from.set(Calendar.MILLISECOND, 0);

        to.set(Calendar.HOUR_OF_DAY, 0);
        to.set(Calendar.MINUTE, 0);
        to.set(Calendar.SECOND, 0);
        to.set(Calendar.MILLISECOND, 0);

        long diff = to.getTime().getTime() - from.getTime().getTime();
        long days = diff / milliSecPerDay;

        return days;
    }
    
    /**
     * Calculate the duration in number of days. If the start date is later than the
     * end date, a negative value will be returned.
     * <p>
     * @param  startDate The start date of the duration.
     * @param  endDate   The end date of the duration.
     * @return The difference in number of minutes between the start date and end date.
     */
    public static long computeDurationMinute(Date startDate, Date endDate) {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.setTime( startDate );
        to.setTime( endDate );

        // Set both from and to to the same time so that the number of
        // days calcualted will be accurate.
//        from.set(Calendar.HOUR_OF_DAY, 0);
//        from.set(Calendar.MINUTE, 0);
//        from.set(Calendar.SECOND, 0);
//        from.set(Calendar.MILLISECOND, 0);
//
//        to.set(Calendar.HOUR_OF_DAY, 0);
//        to.set(Calendar.MINUTE, 0);
//        to.set(Calendar.SECOND, 0);
//        to.set(Calendar.MILLISECOND, 0);

        long diff = to.getTime().getTime() - from.getTime().getTime();
        long days = diff / milliSecPerMinute;

        return days;
    }

    /**
     * Calculate the duration in the format "d day(s) hh:mm:ss". If the start date is later than the
     * end date, a negative value will be returned.
     * <p>
     * @author zhigang
     * @param  startDate The start date of the duration.
     * @param  endDate   The end date of the duration.
     * @return The difference in number of minutes between the start date and end date.
     */
    public static String computeDurationToFormat(Date startDate, Date endDate) {
        /* the millisecond between the two dates */
    	long diff = endDate.getTime() - startDate.getTime();
        
    	/* get the days */
        long days = diff / milliSecPerDay;
        
        long diffRemoveDays = diff % milliSecPerDay;
        /* get the hours */
        long hours = diffRemoveDays / milliSecPerHour;
        
        long diffRemoveDaysAndHours = diffRemoveDays % milliSecPerHour;
        /* get the minutes */
        long minutes = diffRemoveDaysAndHours / milliSecPerMinute;
        /* get the seconds */
        long seconds = (diffRemoveDaysAndHours % milliSecPerMinute) / 1000;
        
        StringBuffer result = new StringBuffer();
        if(days != 0){
        	result.append(days).append(" day(s) ");
        }
        if(hours < 10){
        	result.append("0");
        }
        result.append(hours).append(":");
        if(minutes < 10){
        	result.append("0");
        }
        result.append(minutes).append(":");
        if(seconds < 10){
        	result.append("0");
        }
        result.append(seconds);
        
        return result.toString().trim();
    }

    
    
   
    /**
     * Get the formatted string of the given date instance based on the
     * date format provided.
     * <p>
     * See {@link java.text.SimpleDateFormat SimpleDateFormat}
     * for examples of the format string.
     * <p>
     * @param  date   The date that needs to be formatted.
     * @param  format The format to be applied to the date.
     * @return The formatted Date String.
     */
    public static String format(Date date, String format) {
        if (date == null || format == null) return null;

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        
        return sdf.format(date);
    }
        

    /**
     * Retrieves the value of the field in the Date.
     * Some common fields that is likely to be used include :
     * <p>
     * <li>Calendar.YEAR - retrieves the year value
     * <li>Calendar.MONTH - retrieves the month value ( 1 - 12 )
     * <li>Calendar.DAY_OF_MONTH - retrieve the day value ( 1 - 31 )
     * <li>Calendar.HOUR - retrieves the hours value in 12 hour format ( 1 - 12 )
     * <li>Calendar.HOUR_OF_DAY - retrieves the hours value in 24 hour format ( 0 - 23 )
     * <li>Calendar.MINUTE - retrieves the minutes value ( 0 - 59 )
     * <li>Calendar.AM_PM - retrieves the am/pm value ( 0 = am; 1 = pm )
     * <p>
     * @param  date  The Date object to extract value from.
     * @param  field A Calendar constant to retrieve the field value from the Date
     * object.
     * @return The value of the field that is requested.
     * @throws ArrayIndexOutOfBoundsException - if specified field is out of
     * range (<code>field</code> &lt; 0 || <code>field</code> &gt;= <code>Calendar.FIELD_COUNT</code>).
     * @see java.util.Calendar
     */
    public static int get(Date date, int field) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        int value = cal.get(field);

        // Add 1 if the field is Calendar.MONTH since Calendar returns
        // the month value starting from 0.
        if (Calendar.MONTH == field)
            value += 1;

        // If it is 12 am/pm, the value will be 0. Need to change it to 12 for ease of display.
        if (Calendar.HOUR == field && value == 0)
            value = 12;

        return value;
    }

    /**
     * Returns the date instance based on the current system date
     * <p>
     * @return Current System date.
     */
    public static Date getDate() {
        TimeZone tz = TimeZone.getTimeZone( timezone );
        return Calendar.getInstance(tz).getTime();
    }

    /**
     * Return the Date instance with the provided year,
     * month ( 1 - 12 ), and day ( 1 - 31 ) values.
     * <p>
     * The date value will roll over when given a value that is greater
     * than the max possible. Eg. when getDate( 2002, 10, 32 )
     * is provided, the Date instance will be 1st Nov 2002.
     * <p>
     * @param  year  Year
     * @param  month Month ( 1 - 12 )
     * @param  day   Day( 1 - 31 )
     * @return The Date instance created.
     */
    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();

        // Clear all fields first
        cal.clear();

        cal.set(year, month - 1, day);

        return cal.getTime();
    }

    /**
     * Tests if the input year is a leap year. Overriding method
     * that accepts either an integer representing the year, or a Date object.
     * <p>
     * @param  year The year to check if it is a leap year.
     * @return True if the year is a leap year; False otherwise.
     */
    public static boolean isLeap(int year) {
        GregorianCalendar cal = new GregorianCalendar();

        return cal.isLeapYear( year );
    }

    /**
     * Tests if the input year is a leap year. Overriding method
     * that accepts either an integer representing the year, or a Date object.
     * <p>
     * @param  date The Date instance to check if it falls on a leap year.
     * @return True if the date falls on a leap year; False otherwise.
     */
    public static boolean isLeap(Date date) {

        if (date == null) return false;

        int year = get(date, Calendar.YEAR);

        return isLeap(year);
    }

    
    /**
     * Tests the input date to check if the date falls on a Saturday.
     * <p>
     * @param  date The date instance.
     * @return True if the date falls on a Saturday; False otherwise
     */
    public static boolean isSaturday(Date date) {

        if (date == null) return false;

        if (get( date, Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            return true;
        else
            return false;
    }

    /**
     * Tests the input date to check if the date falls on a Sunday.
     * <p>
     * @param  date The date instance.
     * @return True if the date falls on a Sunday; False otherwise
     */
    public static boolean isSunday(Date date) {

        if (date == null) return false;

        if (get(date, Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            return true;
        else
            return false;
    }

    /**
     * Tests the input value to ensure that a valid Date instance can be created from it.
     * Roll over dates are not allowed here and will return a false value.
     * Eg. isValidDate(2002, 10, 32) will return false.
     * <p>
     * @param  year  The year value.
     * @param  month The month value. ( 1 - 12 )
     * @param  day   The day value. ( 1 - 31 )
     * @return True if all values can be used to create a single valid Date instance.
     */
    public static boolean isValidDate(int year, int month, int day) {

        if (day <= 0 || month <= 0 || year <= 0) return false;
        if (month > 12 || day > 31) return false;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);

        // Find the maximum field value possible for the day with the year and month.
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        if ( day > maxDay ) return false;

        return true;
    }

    /**
     * Tests the input string to ensure that a valid Date instance can be created from it
     * according to the format provided.
     * <p>
     * @param  date   A date string.
     * @param  format Date format to conform to.
     * @return True if it conforms to the specified date format; False otherwise.
     */
    public static boolean isValidDate(String date, String format) {

        if (date == null) return false;

        try {						
			Date temp = parse( date, format );

			if(!validateYear(date, format, temp))
				return false;

			return true;
            
        } catch (java.text.ParseException e) {
            return false;
        }     
    }

    /**
     * Tests the input string to ensure that a valid Date instance can be created from it
     * according to "dd/MM/yyyy" format.
     * <p>
     * @param  date   A date string.
     * @return True if it conforms to "dd/MM/yyyy" format; False otherwise.
     */
    public static boolean isValidDate(String date) {
        return isValidDate(date, DateUtil.DATEFORMAT);        
    }
    /**
     * Tests if the inputs are valid time. When the ampm parameter is true,
     * the input hour will be tested for 12-hour format ( 1 - 12 ).
     * When it is false, the input hour will be tested for 24-hour format ( 0 - 23 ).
     * <p>
     * @param  hour   The Hour value. ( 0 - 23 or 1 - 12 )
     * @param  minute The Minute value. ( 0 - 59 )
     * @param  ampm   If true, the time is in 12 hour format.
     * Otherwise, it is in 24 hour format.
     *
     * @return True if the time inputs can be used to create a valid time instance.
     */
    public static boolean isValidTime(int hour, int minute, boolean ampm) {

        if (minute < 0 || minute > 59)
            return false;

        if (ampm && ( hour < 1 || hour > 12))
            return false;

        else if (hour < 0 || hour > 23)
            return false;

        else
            return true;
    }

   

    /**
     * Tests the input string to ensure that a valid time can be created
     * according to the time format provided.
     * <p>
     * @param  time   The time string.
     * @param  format Time format to conform to.
     * @return True if it conforms to the specified time format; False otherwise.
     */
    public static boolean isValidTime(String time, String format) {

        if (time == null || format == null) return false;

        try {
            Date temp = parse(time, format);
        } catch (java.text.ParseException e) {
            return false;
        }

        return true;
    }

    /**
     * Tests the input date to check if the date falls on a weekend.
     * Weekend falls on a Saturday or a Sunday.
     * <p>
     * @param date The date instance.
     * @return True if the date falls on a weekend; False otherwise.
     */
    public static boolean isWeekend(Date date) {

        if (isSaturday(date) || isSunday(date))
            return true;
        else
            return false;
    }

   

    /**
     * Returns a Date object instance from the input string.
     * The input date string is parsed to return a date object
     * based on the format provided.
     * <p>
     * Eg., to parse the date string "01/01/2003 9:2 PM", use the
     * format "dd/MM/yyyy h:m a". The resultant data object will have
     * the value "Mar 11 2003 09:02", as displayed in 24 hr format.
     * <p>
     * @param  date   The date string.
     * @param  format The date format that the date string conforms to.
     * @return The date instance created.
     * @throws java.text.ParseException  - if the beginning of the specified string cannot be parsed.
     */
    public static Date parse(String date, String format) throws java.text.ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        TimeZone tz = TimeZone.getTimeZone( timezone );
        sdf.setTimeZone(tz);
        return sdf.parse( date );
    }

    /**
     * Returns a Date object instance from the input string.
     * This method is specifically for date string of the form "EEE MMM dd HH:mm:ss 'GMT+08:00' yyyy"
     * <p>
     * For eg, "Thu Jun 30 15:00:54 GMT+08:00 2003"
     *
     * @param date  The date string of the format "EEE MMM dd HH:mm:ss 'GMT+08:00' yyyy"
     * @return The date instance created.
     * @throws java.text.ParseException - if the date string is not of the given format.
     */
    public static Date parseLocale(String date) throws java.text.ParseException {

        String localeFormat = "EEE MMM dd HH:mm:ss 'GMT+08:00' yyyy";

        return parse(date, localeFormat);
    }

    

    /**
     * Date Arithmetic function. Adds the specified (signed) amount of time to
     * the given time field, based on the calendar's rules.
     * <p>
     * For example, to subtract 5 days from a specific date, it can be achieved
     * by calling: <p>
     * DateUtil.add(date, Calendar.DATE, -5).
     * <p>
     * @param date   The date to perform the arithmetic function on
     * @param field  A Calendar constant to retrieve the field value from the Date
     * object. Same as for {@link #get get()}.
     * @param amount the amount of date or time to be added to the field
     * @return The date as a result of the execution of the arithmetic function.
     */
    public static Date add(Date date, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);

        return cal.getTime();
    }

    /**
     * Set the time component as the last seconds of the day.
     * <p>
     * The Time Component of the date returned will be set to
     * 23:59:59.
     * <p>
     * @param date The Date to get the last seconds
     * @return The date with the time component set to the last seconds
     * of the day.
     */
    public static Date getEndOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // Clear the time component
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum( Calendar.HOUR_OF_DAY ));
        cal.set(Calendar.MINUTE, cal.getActualMaximum( Calendar.MINUTE ));
        cal.set(Calendar.SECOND, cal.getActualMaximum( Calendar.SECOND ));
        cal.set(Calendar.MILLISECOND, cal.getActualMaximum( Calendar.MILLISECOND ));

        // System.out.println( "cal.toString() = " + cal.toString() );

        return cal.getTime();
    }

    /**
     * Set the time component as the start of the day.
     * <p>
     * The Time Component of the date returned will be set to
     * 00:00:00.
     * <p>
     * @param date The Date to get the start of the day
     * @return The date with the time component reset to 00:00:00.
     */
    public static Date getStartOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // Clear the time component
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        Date start = getDate(year, month, day);
        return start;
    }
    
    /**
     * This method is used to validate the year of the specified date.
     * 
     * @param dteStr date as String object
     * @param format the format of the date
     * @param dte date as Date object
     * @return true if the year is valid.
     */
    private static boolean validateYear(String dteStr, String format, Date dte) {
		int index = format.indexOf("yyyy");
		if(index != -1) {
			if(dteStr.length() < index+4)
				return false;
			
			String year = dteStr.substring(index, index+4);
			if(!NumberUtil.isNumber(year,false))
				return false;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(dte);					
		if(String.valueOf(c.get(Calendar.YEAR)).length() > 4) 
			return false;	
		
		return true;
		
	}
    
    /**
     * @author zhangli
     */
    public static String formatDate(String date){
    	if(StringUtil.isEmpty(date)){
    		return "";
    	}
    	if(date.length()<8){
    		return date;
    	}
    	String day = date.substring(6,8);
    	String month = date.substring(4,6);
    	String year = date.substring(0,4);
    	
    	return day+"/"+month+"/"+year;
    }
    
    /**
     * @author chenjia
     */
    public static Date getStartOfMonthAndDay(Date date){
    	Calendar from=Calendar.getInstance();
    	from.setTime(date);
		from.set(Calendar.DAY_OF_MONTH,from.getActualMinimum(Calendar.DAY_OF_MONTH));
		return DateUtil.getStartOfDay(from.getTime());
    }
    
    /**
     * @author chenjia
     */
    public static Date getEndOfMonthAndDay(Date date){
    	Calendar to=Calendar.getInstance();
    	to.setTime(date);
		to.set(Calendar.DAY_OF_MONTH,to.getActualMaximum(Calendar.DAY_OF_MONTH));
		return DateUtil.getEndOfDay(to.getTime());
    }
    
    
	
	
	
    
   
}