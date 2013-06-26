package com.test.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Class contains utilizable methods about number
 *
 * @author Qiu Le Qi
 * @version $Revision: 
 */
public class NumberUtil {
    private NumberUtil() {
    }

    public static String format(double value, int decimalPlace) {
        NumberFormat nft = NumberFormat.getInstance();
        nft.setMinimumFractionDigits(decimalPlace);
        nft.setMaximumFractionDigits(decimalPlace);

        return nft.format(value);
    }

    public static String format(double value, String pattern) {
        NumberFormat nft = NumberFormat.getInstance();
        ((DecimalFormat) nft).applyPattern(pattern);

        return nft.format(value);
    }

    public static byte[] getByte(int value) {
        byte[] buffer = new byte[8];

        for (int i = 6; i >= 0; i -= 2) {
            short val = (short) (value & 255);
            buffer[i + 1] = (byte) (val & 127);
            buffer[i] = (byte) (val >> 7);
            value >>>= 8;
        }

        return buffer;
    }

    public static double getDouble(int integral, int decimal) {
        String str = integral + "." + decimal;

        return Double.parseDouble(str);
    }

    public static float getFloat(int integral, int decimal) {
        String str = integral + "." + decimal;

        return Float.parseFloat(str);
    }

    public static int getInt(byte[] buffer) {
        int value = 0;
        short val;

        for (int i = 0; i < 6; i += 2) {
            val = buffer[i];
            val <<= 7;
            val |= buffer[i + 1];
            value |= val;
            value <<= 8;
        }

        val = buffer[6];
        val <<= 7;
        val |= buffer[7];
        value |= val;

        return value;
    }


    public static boolean isNumber(String value, boolean isFraction) {
        try {
            if (value.trim().toUpperCase().endsWith("D") ||
                    value.trim().toUpperCase().endsWith("F") ||
                    value.trim().toUpperCase().endsWith("L")) {
                return false;
            }

            double d;

            if (isFraction) {
                d = Double.parseDouble(value);
            } else {
                d = Long.parseLong(value);
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static boolean isValidNumber(String value, boolean isFraction,
        String format) {
        int wholeNo = 0;
        int decimalPlace = 0;

        if (!isNumber(value, isFraction)) {
            return false;
        }

        int index = format.indexOf(".");

        if (index != -1) {
            wholeNo = Integer.parseInt(format.substring(0, index));
            decimalPlace = Integer.parseInt(format.substring(index + 1,
                        format.length()));
        } else {
            wholeNo = Integer.parseInt(format);
        }

        index = value.indexOf(".");

        if (index != -1) {
            String part1 = value.substring(0, index);

            if (part1.length() > wholeNo) {
                return false;
            }

            String part2 = value.substring(index + 1, value.length());

            if (part2.length() > decimalPlace) {
                return false;
            }
        } else if (value.length() > wholeNo) {
            return false;
        }

        return true;
    }
    
    /**
     * @author lidong
     * @param val
     * @return
     */
    public static boolean isMinus(String val){
    	if(StringUtil.isEmpty(val)){
    		return false;
    	}
    	String t=val.trim();
    	if(isNumber(t,false)&&t.startsWith("-")){
    		return true;
    	}else{
    		return false;
    	}
    }

}
