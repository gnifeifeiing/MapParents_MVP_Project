package com.zele.maipuxiaoyuan.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hyl on 2017/4/19.
 */

public class DateUtils {
    private static SimpleDateFormat hm =new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat ymd =new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat ymdhm =new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat mdhm =new SimpleDateFormat("MM-dd HH:mm");
    public static String getListFormat(Date d){
        if(isToday(d)){
            return hm.format(d);
        }else if(isYesterday(d)){
            return "昨天";
        }else{
            return ymd.format(d);
        }
    }
    public static String getDetialFormat(Date d){
        if(isToday(d)){
            return hm.format(d);
        }else if(isYesterday(d)){
            return "昨天"+hm.format(d);
        }else if(IsSameYear(d)){
            return mdhm.format(d);
        }else{
            return ymdhm.format(d);
        }
    }




    /**
     * 判断是否为今天(效率比较高)
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     */
    public static boolean isToday(Date day)  {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为昨天(效率比较高)
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     */
    public static boolean isYesterday(Date day)  {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }
    public static boolean IsSameYear(Date day)  {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            return true;
        }
        return false;
    }
}
