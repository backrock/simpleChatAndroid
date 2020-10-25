package com.example.think.simplechatsystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by 273503 on 2015/1/16.
 */
public class DateUtil {

    /**
     * 男性
     */
    public static int GENDER_MALE = 1;
    /**
     * 女性
     */
    public static int GENDER_FEMALE = 2;

    //日期格式
    public static String formatstr="yyyy-MM-dd";
   //今天昨天日期格式化
    public static String todayformatstr="yyyy-MM-dd";

    public static String fullDateStr="yyyy-MM-dd HH:mm:ss";

    public static String mmdd="MM.dd";

    public static String dd = "dd";

    public static String yyyy = "yyyy";

    public static String mm = "MM";

    /**
     * 日期格式为字符
     * @param date
     * @return
     */
    public static String dateFrom(Date date){
        String dstr=null;
        if(date != null){
            SimpleDateFormat format = new SimpleDateFormat(formatstr);
            dstr = format.format(date);
        }
        return dstr;
    }

    /**
     * 日期字符转为 日期
     * @param date
     * @return
     */
    public static Date dateFrom(String date){
        Date dstr=null;
        if(date != null){
            SimpleDateFormat format = new SimpleDateFormat(formatstr);
            try {
                dstr = format.parse(date);
            } catch (ParseException e) {
                // e.printStackTrace();
            }
        }
        return dstr;
    }

    /**
     * 日期转换成带有今天的字符
     * @param date
     * @return
     */
    public static String dateFromToday(Date date){
        String dstr=null;
        if(date != null){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.add(calendar.DATE,-1);
            Date yd = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat(todayformatstr);
            dstr = format.format(date); //日期
            String today = format.format(new Date());//今天
            String yesterday =format.format(yd);//昨天

            if(dstr.equals(today)){
                return"今天";
            }else if(dstr.equals(yesterday)){
                return"昨天";
            }
        }
        return dstr;

    }
public static String timespecToMsgDate(long spec){
    String dstr = "";
    Date date = new Date(spec);
    String today = dateFromToday(date);
    if (today!=null && today.equals("今天")){
        //上午，下午
        if (date.getHours()<12)dstr += "上午 ";
        else dstr += "下午 ";
        dstr += dateToString(date,DateString_HH_MM);
    }else return dateToString(date,DateString_YYYY_MM_DD_HH_MM);
    return dstr;
}
    /**
     * 根据格式转换为指定的日期
     * @param date
     * @param formatstr
     * @return
     */
    public static String dateFrom(Date date, String formatstr){
        String dstr="";
        if(date != null){
            SimpleDateFormat format = new SimpleDateFormat(formatstr);
            dstr = format.format(date);
        }
        return dstr;
    }

    /**
     * 根据格式转换为指定的日期
     * @param str
     * @param formatstr
     * @return
     */
    public static Date strFrom(String str, String formatstr){
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatstr);
            return format.parse(str);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 根据格式转换为指定的日期
     * @param l
     * @return
     */
    public static Date longFrom(long l){
        try {
            Date d = new Date(l);
            return d;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new Date();
    }


    public final static int DateString_YYYY_MM_DD = 1;
    public final static int DateString_YYYY_MM_DD_HH_MM = 2;
    public final static int DateString_YYYY_MM_DD_HH_MM_SS = 3;
    public final static int DateString_HH_MM = 4;
    public final static int DateString_MM_DD_HH_MM = 5;
    public final static int DateString_YYYY_MM_DD_CN = 6;
    public final static int DateString_MM_DD_CN = 7;

    public static String dateToString(Date d,int flag)
    {
        if (d==null)return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        if(flag==DateString_YYYY_MM_DD)
            return String.format("%d-%02d-%02d",
                    cal.get(Calendar.YEAR),cal.get(Calendar.MONTH) + 1,cal.get(Calendar.DATE));
        else if(flag==DateString_YYYY_MM_DD_HH_MM)
            return String.format("%d-%02d-%02d %02d:%02d",
                    cal.get(Calendar.YEAR),cal.get(Calendar.MONTH) + 1,cal.get(Calendar.DATE),
                    cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));
        else if (flag==DateString_YYYY_MM_DD_HH_MM_SS)
            return String.format("%d-%02d-%02d %02d:%02d:%02d",
                    cal.get(Calendar.YEAR),cal.get(Calendar.MONTH) + 1,cal.get(Calendar.DATE),
                    cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND));
        else if(flag==DateString_HH_MM)
            return String.format("%02d:%02d",
                    cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));
        else if(flag == DateString_MM_DD_HH_MM)
        {
            return String.format("%02d月%02d日 %02d:%02d",
                    cal.get(Calendar.MONTH) + 1,cal.get(Calendar.DATE),
                    cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));
        }
        else if(flag==DateString_YYYY_MM_DD_CN)
        {
            return String.format("%d年%d月%d日",
                    cal.get(Calendar.YEAR),cal.get(Calendar.MONTH) + 1,cal.get(Calendar.DATE));
        }
        else if(flag==DateString_MM_DD_CN)
        {
            return String.format("%d月%d日",
                    cal.get(Calendar.MONTH) + 1,cal.get(Calendar.DATE));
        }
        else
            return "";
    }
}
