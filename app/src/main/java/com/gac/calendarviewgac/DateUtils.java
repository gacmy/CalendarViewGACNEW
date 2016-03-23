package com.gac.calendarviewgac;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {
	private static SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");
	public static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	
	//��ȡ����������ַ�
	public static String getTodayDate(){
		return DATE.format(new Date());
	}
	
	//��ȡָ�����ڸ�ʽ������
	public static String getDateStr(String datePattern,Date date){
		return new SimpleDateFormat(datePattern).format(date);
	}
	
	//���ַ��������������
	public static Date parse(String strDate, String pattern)  
            throws ParseException  {  
        return StrUtils.isBlank(strDate) ? null : new SimpleDateFormat(  
                pattern).parse(strDate);  
    }  
	
	//ʹ��Ĭ�ϸ�ʽ���������ַ�Ϊ��������
	public static Date parse(String strDate)  
            throws ParseException  {  
        return StrUtils.isBlank(strDate) ? null : DATE.parse(strDate);  
    }  
	
	//��ȡ�����ַ�
	public static String getDateStr(Date date){
		return DATE.format(date);
	}
	
	
	  /** 
     * ������������������� 
     */  
    public static Date addMonth(Date date, int n)  
    {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        cal.add(Calendar.MONTH, n);  
        return cal.getTime();  
    }  
    
    
    public static String getLastDayOfMonth(String year, String month)  
    {  
        Calendar cal = Calendar.getInstance();  
        // ��  
        cal.set(Calendar.YEAR, Integer.parseInt(year));  
        // �£���ΪCalendar������Ǵ�0��ʼ������Ҫ-1  
        // cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);  
        // �գ���Ϊһ��  
        cal.set(Calendar.DATE, 1);  
        // �·ݼ�һ���õ��¸��µ�һ��  
        cal.add(Calendar.MONTH, 1);  
        // ��һ���¼�һΪ�������һ��  
        cal.add(Calendar.DATE, -1);  
        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));// �����ĩ�Ǽ���  
    }  
  
    public static Date getDate(String year, String month, String day)  
            throws ParseException{  
        String result = year + "- "  
                + (month.length() == 1 ? ("0 " + month) : month) + "- "  
                + (day.length() == 1 ? ("0 " + day) : day);  
        return parse(result);  
    }  
    
    public static Date getDate(int year,int month,int day) 
    		throws ParseException{
    	String tempYear = year+"";
    	String tempMonth = month+"";
    	String tempDay = day+"";
    	String result = tempYear + "- "  
                 + (tempMonth.length() == 1 ? ("0 " + month) : month) + "- "  
                 + (tempDay.length() == 1 ? ("0 " + day) : day);  
         return parse(result);
    }

    
	public static void main(String[] args){
		print(getTodayDate());
		Date date = null;
		try {
			date = parse("2013-01-12");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		print(getDateStr(date));
		
	}
	
	public static void print(String str){
		System.out.println(str);
	}
	
	//�ַ�����
	private static class StrUtils{
		
		static boolean isBlank(String str){
			if(str == null || str.equals("")){
				return true;
			}
			return false;
		}
	}
}
