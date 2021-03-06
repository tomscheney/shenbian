package com.sbh.basedao.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @ClassName: DateUtil
 * @date 2014年4月15日 下午11:28:43
 *
 */
public class DateUtil {

	/**
	 * 网站上线时间
	 */
	public static final String	onlineDate			= "2013-9-2";

	/**
	 * 英文简写（默认）如：2014-12-01
	 */
	public static String		FORMAT_SHORT		= "yyyy-MM-dd";

	/**
	 * 英文全称 如：2014-12-01 23:15:06
	 *
	 */
	public static String		FORMAT_LONG			= "yyyy-MM-dd HH:mm:ss";

	/**
	 * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
	 */
	public static String		FORMAT_FULL			= "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * 中文简写 如：2014年12月01日
	 */
	public static String		FORMAT_SHORT_CN		= "yyyy年MM月dd";

	/**
	 * 中文全称 如：2014年12月01日 23时15分06秒
	 */
	public static String		FORMAT_LONG_CN		= "yyyy年MM月dd日  HH时mm分ss秒";

	/**
	 * 精确到毫秒的完整中文时间
	 */
	public static String		FORMAT_FULL_CN		= "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

	/**
	 * 一天的毫秒数
	 */
	public static final long	millisecond_of_day	= 1000 * 24 * 60 * 60;

	/**
	 * 连连风险参数用
	 */
	public static String		FORMAT_LONG_24		= "yyyyMMddHHmmss";

	public static String		FORMAT_LONG_YMD		= "yyyyMMdd";

	public static String now(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	public static String yesterday(String format, Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String format(Date date, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Calendar setDayOfMonthInCurrentMonth(Calendar calendar, Integer day) {
		Integer oldMonth = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		Integer currentMonth = calendar.get(Calendar.MONTH);
		if (oldMonth < currentMonth) {
			calendar.set(Calendar.MONTH, oldMonth);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return calendar;

	}

	public static Date parse(String dateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date addByCalendar(Date date, int step, int calendarField) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, step);
		return c.getTime();
	}

	public static Date addDays(Date date, int step) {
		// java.util.Calendar c = Calendar.getInstance();
		// c.setTime(date);
		// c.add(Calendar.DATE, step);
		// return c.getTime();

		return addByCalendar(date, step, Calendar.DATE);
	}

	public static Date addMonth(Date date, int step) {
		// java.util.Calendar c = Calendar.getInstance();
		// c.setTime(date);
		// c.add(Calendar.MONTH, step);
		//
		// return c.getTime();
		return addByCalendar(date, step, Calendar.MONTH);
	}

	/*
	 * public static int interval(Date date1, Date date2) { Long l =
	 * date1.getTime() - date2.getTime(); Long day = l / (24 * 60 * 60 * 1000);
	 * if (date1.compareTo(date2) > 0) return day.intValue() + 1; return
	 * Math.abs(day.intValue()); }
	 */

	public static long getNowTimes() {
		Calendar now = Calendar.getInstance();
		return now.getTimeInMillis();
	}

	public static Date getDateAddHours(Date date, int hour) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.HOUR_OF_DAY, hour);
		return ca.getTime();
	}

	public static Date getDateAddMinute(Date date, int minute) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MINUTE, minute);
		return ca.getTime();
	}

	// 计算两个日期之间有多少分钟
	public static int getMinutesBetween(Date startDate, Date endDate) {
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(endDate);
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.MINUTE) - d1.get(Calendar.MINUTE);

		return days;
	}

	// 2个时间的倒计时
	public static Long[] getCountDown(Date startDate, Date endDate) {
		long between = (startDate.getTime() - endDate.getTime()) / 1000;// 除以1000是为了转换成秒
		long day = between / (24 * 3600);
		long hour = between % (24 * 3600) / 3600;
		long minute = between % 3600 / 60;
		long second = between % 60;
		Long[] long_ = new Long[4];
		long_[0] = day;
		long_[1] = hour;
		long_[2] = minute;
		long_[3] = second;
		return long_;
	}

	// 距今倒计时
	public static String getCountDownStr(Date startDate) {
		Calendar now = Calendar.getInstance();
		return getCountDownStr(startDate, now.getTime(), null);
	}

	// 距今倒计时
	public static String getCountDownStr(Date startDate, Integer index) {
		Calendar now = Calendar.getInstance();
		return getCountDownStr(startDate, now.getTime(), index);
	}

	// 2个时间的倒计时
	public static String getCountDownStr(Date startDate, Date endDate, Integer index) {
		Long[] longs = getCountDown(startDate, endDate);
		StringBuffer stringBuffer = new StringBuffer();
		if (longs[0] > 0) {
			stringBuffer.append(longs[0] + "天  ");
		} else {
			stringBuffer.append("0天  ");
		}
		int size = longs.length;
		if (index != null && index <= size) {
			size = index;
		}
		for (int i = 1; i < size; i++) {
			Long num = longs[i];
			if (i != 1) {
				stringBuffer.append(":" + (num == 0 ? "00" : num));
			} else {
				stringBuffer.append(num == 0 ? "00" : num);
			}
		}
		// stringBuffer.append((longs[1] == 0 ? "00" : longs[1]) + ":" +
		// (longs[2] == 0 ? "00" : longs[2]) + ":" + (longs[3] == 0 ? "00" :
		// longs[3]));
		return stringBuffer.toString();
	}

	// 判读是否是今天
	public static boolean isToday(Date startDate) {
		Calendar today = Calendar.getInstance();
		if (format(startDate, "yyyyMMdd").equals(format(today.getTime(), "yyyyMMdd"))) {
			return true;
		}
		return false;
	}

	// 距今倒计时
	public static Long[] getCountDown(Date startDate) {
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		long between = (d1.getTimeInMillis() - getNowTimes()) / 1000;// 除以1000是为了转换成秒
		long day = between / (24 * 3600);
		long hour = between % (24 * 3600) / 3600;
		long minute = between % 3600 / 60;
		long second = between % 60 / 60;
		Long[] long_ = new Long[4];
		long_[0] = day;
		long_[1] = hour;
		long_[2] = minute;
		long_[3] = second;
		return long_;
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param start
	 *            较小的时间
	 * @param end
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 *             author: zhouwei
	 */
	public static int daysBetween(Date start, Date end) {
		int daysBetween = 0;
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		if (start.after(end)) {
			startDate.setTime(end);
			endDate.setTime(start);
		} else {
			startDate.setTime(start);
			endDate.setTime(end);
		}
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);

		endDate.set(Calendar.HOUR_OF_DAY, 0);
		endDate.set(Calendar.MINUTE, 0);
		endDate.set(Calendar.SECOND, 0);
		endDate.set(Calendar.MILLISECOND, 0);

		while (startDate.before(endDate)) {
			startDate.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}

	/*
	 * public static int monthsDiff(Date start,Date end){
	 * 
	 * }
	 */

	/*
	 * Calendar c1 = Calendar.getInstance(); c1.setTime(smdate);
	 * c1.set(Calendar.HOUR_OF_DAY, 0); c1.set(Calendar.MINUTE, 0);
	 * c1.set(Calendar.SECOND, 0); long time1 = c1.getTime().getTime();
	 * 
	 * 
	 * Calendar c2 = Calendar.getInstance(); c2.setTime(bdate);
	 * c2.set(Calendar.HOUR_OF_DAY, 0); c2.set(Calendar.MINUTE, 0);
	 * c2.set(Calendar.SECOND, 0); long time2 = c2.getTime().getTime(); Long
	 * between_days = (Math.abs(time2 - time1) +10 )/ (1000 * 3600 * 24);
	 * System.out.println(c2.get(Calendar.d) - c1.get(Calendar.MINUTE)); return
	 * between_days.intValue(); }
	 */

	/**
	 * 是否有效时间
	 *
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean isValidTime(Date beginTime, Date endTime) {
		Date now = new Date();
		if (beginTime == null && endTime == null) {
			return true;
		} else if (beginTime != null && endTime == null) {
			if (beginTime.compareTo(now) <= 0) {
				return true;
			} else {
				return false;
			}
		} else if (beginTime == null && endTime != null) {
			if (endTime.compareTo(now) >= 0) {
				return true;
			} else {
				return false;
			}
		} else if (beginTime != null && endTime != null) {
			if (beginTime.compareTo(now) <= 0 && endTime.compareTo(now) >= 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 是否还未过期
	 *
	 * @param time
	 * @return
	 */
	public static boolean checkValidTime(Date time) {
		if (time != null) {
			Date now = new Date();
			if (time.compareTo(now) >= 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 *
	 * @Title: getBetweenMinute
	 * @Description: 得到2个时间的分钟数
	 * @param: @param endTime 结束时间
	 * @param: @param now 当前时间
	 * @param: @return
	 * @return: int
	 * @throws
	 */
	public static int getBetweenMinute(Date endTime, Date now) {
		Long time = endTime.getTime() - now.getTime();
		Long day = time / (60 * 1000);
		if (endTime.compareTo(now) > 0)
			return day.intValue() + 1;
		return Math.abs(day.intValue());
	}

	/**
	 * 两个日期间差的天数
	 *
	 * @param beginDay
	 * @param endDay
	 * @return
	 */
	public static long getBetweenDay(Date beginDay, Date endDay) {
		Long difftime = getTodayStart(endDay).getTime() - getTodayStart(beginDay).getTime();
		return Math.abs(difftime / millisecond_of_day);
	}

	/**
	 *
	 * @Title: getHoursAfterTime
	 * @Description: TODO
	 * @param: @param hour
	 * @param: @param date
	 * @param: @return
	 * @return: Date
	 * @throws
	 */
	public static Date getHoursAfterTime(int hour, Date date) {
		String hoursAgoTime = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + hour);
		hoursAgoTime = new SimpleDateFormat(DateUtil.FORMAT_LONG).format(cal.getTime());
		return parse(hoursAgoTime, DateUtil.FORMAT_LONG);
	}

	public static void main(String[] args) throws ParseException {
		Date dd = parse("2017-04-02 14:45:02", FORMAT_LONG);
		Date dd2 = parse("2017-05-21 23:59:59", FORMAT_LONG);

		System.out.println(daysBetween(dd, dd2));
		
		Date d = new Date(729908000L);
		System.out.println(d);
		
		Date startDate = parse("2017-2-28 00:00:00", FORMAT_LONG);
		List<Date> expectedTimeList = DateUtil.getExpectedTimeList(startDate, 3);
		System.out.println(expectedTimeList);
		// System.out.println(format(new Date(), "yyyy-MM-dd"));
		// System.out.println(format(new Date(), "yyyy-MM-dd"));
		// System.out.println(format(new Date(), "yyyy-MM-dd"));
		//
		// System.out.println(format(new Date(), "yyyy年MM月dd日"));

		/*
		 * Date a = new Date(2015 - 1900, 0, 1); Date b = new Date(2015 - 1900,
		 * 1, 1); int diff = daysBetween(a, b); System.out.println(diff);
		 * System.out.println(getDateAddMinute(new Date(), 3));
		 */
		// System.out.println(addDays(new Date(), -7));

		/*
		 * String aa = getCountDownStr(parse("2014-05-16 17:10:11",
		 * "yyyy-MM-dd HH:mm:ss")); System.out.println(aa);
		 */
		/*
		 * SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss"); String
		 * dateString = "20140516140101"; try { Date date =
		 * df.parse(dateString); System.out.println(getBetweenMinute(date, new
		 * Date())); System.out.println(df.format(date)); } catch (Exception ex)
		 * { System.out.println(ex.getMessage()); }
		 */

		// System.err.println(getHoursAgoTime(2));
		/*
		 * System.out.println(getTodayStart(new Date()));
		 * System.out.println(getTodayEnd(new Date()));
		 * 
		 */
		int dc = DateUtil.getBetweenMonth(dd,dd2);
		 System.out.println("d=="+dc);
		  List<Date> list = DateUtil.getFixedRepaymentDates(dd, dd2);
		  for(int i =0;i<list.size();i++){
			  Date d3 = list.get(i);
			  System.out.println(d3.toLocaleString());
		  }

	}

	public static long getSeconds(Date beginTime, Date endTime) {
		if (null == beginTime || endTime == null) {
			return 0;
		}
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		Long beginMi = begin.getTimeInMillis();
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		Long endMi = end.getTimeInMillis();
		return (endMi - beginMi) / 1000;
	}

	/**
	 * 取当天零点零分零秒
	 *
	 * @return Date
	 */
	public static Date getTodayStart(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		// 如果没有这种设定的话回去系统的当期的时间
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 取指定日期23点59分59秒
	 *
	 * @return Date
	 */
	public static Date getTodayEnd(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getTodayEnd58(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 58);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 判断时间是否过期 例: 判断2015-1-6 18:02:02,是否过期,过期时间为1分钟, 如果当前时间为2015-1-6
	 * 18:04:02,则已经过期
	 *
	 * @param  createTime
	 * @return
	 */
	public static boolean isExpired(Date createTime, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(createTime);
		calendar.add(Calendar.MINUTE, i);
		Date now = new Date();
		return now.after(calendar.getTime());
	}

	/**
	 * 算出一共相差多少个月
	 *
	 * @param beginMonth
	 *            开始日期
	 * @param endMonth
	 *            结束日期
	 * @return
	 */
	public static int getBetweenMonth(Date beginMonth, Date endMonth) {
		Calendar bcalendar = new GregorianCalendar();
		Calendar ecalendar = new GregorianCalendar();
		bcalendar.setTime(beginMonth);
		ecalendar.setTime(endMonth);
		int diffYear = ecalendar.get(Calendar.YEAR) - bcalendar.get(Calendar.YEAR);
		int diffMonth = 0;
		if (diffYear == 0) {
			diffMonth = ecalendar.get(Calendar.MONTH) - bcalendar.get(Calendar.MONTH);
		} else if (diffYear == 1) {

			diffMonth = ecalendar.get(Calendar.MONTH) + Calendar.DECEMBER - bcalendar.get(Calendar.MONTH) + 1;
		} else {
			diffMonth = (diffYear - 1) * 12 + ecalendar.get(Calendar.MONTH) + Calendar.DECEMBER - bcalendar.get(Calendar.MONTH) + 1;
		}
		return diffMonth;
	}

	/**
	 * 2日期相差天数
	 *
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	/*
	 * public static int getBetweenDay(Date beginMonth, Date endMonth) {
	 * Calendar bcalendar = new GregorianCalendar(); Calendar ecalendar = new
	 * GregorianCalendar(); bcalendar.setTime(beginMonth);
	 * ecalendar.setTime(endMonth); int diffDays = 0; if
	 * (bcalendar.get(Calendar.YEAR) == ecalendar.get(Calendar.YEAR)) { diffDays
	 * = ecalendar.get(Calendar.DAY_OF_YEAR) -
	 * bcalendar.get(Calendar.DAY_OF_YEAR); } else diffDays =
	 * ecalendar.get(Calendar.DAY_OF_MONTH) + (365 -
	 * bcalendar.get(Calendar.DAY_OF_YEAR)); return diffDays; }
	 */

	/**
	 * 计算一个月有多少天,month (0-11)
	 *
	 * @param
	 */
	public static int getDaysOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		// System.out.println(c.getTime());
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/*
	 * 借款标从满标日计算各期的还款日期
	 */
	public static List<Date> getExpectedTimeList(Date startDate, Integer periods) {
		Calendar ocalendar = new GregorianCalendar();
		ocalendar.setTime(startDate);
		int day = ocalendar.get(Calendar.DAY_OF_MONTH);
		int maxDayOfMonth;
		int dayOfMonth;
		List<Date> expectedTimeList = new ArrayList<Date>();
		for (int i = 0; i < periods; i++) {
			ocalendar.add(Calendar.MONTH, 1);
			if (i != 0) {
				dayOfMonth = ocalendar.get(Calendar.DAY_OF_MONTH);
				if (day != dayOfMonth) {
					// 这个月最大的天数
					maxDayOfMonth = ocalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
					if (maxDayOfMonth <= day) {
						ocalendar.set(Calendar.DAY_OF_MONTH, maxDayOfMonth);

					} else {
						ocalendar.set(Calendar.DAY_OF_MONTH, day);
					}
				}
			}

			ocalendar.set(Calendar.HOUR_OF_DAY, 23);
			ocalendar.set(Calendar.MINUTE, 59);
			ocalendar.set(Calendar.SECOND, 59);

			expectedTimeList.add(ocalendar.getTime());
		}

		return expectedTimeList;

	}

	// TODO unFinished
	public static List<Map<String, Date>> getBeginTimeAndExpectedTimeList(Date startDate, Date endDate, Integer periods) {

		List<Map<String, Date>> list = new ArrayList<Map<String,Date>>();
		Map<String, Date> map;
		Calendar ocalendar = new GregorianCalendar();
		ocalendar.setTime(startDate);
		Calendar ncalendar = new GregorianCalendar();
		ncalendar.setTime(endDate);

		// int month = ocalendar.get(Calendar.MONTH);
		int day = ocalendar.get(Calendar.DAY_OF_MONTH);
		// int maxDayOfMonth;
		// int dayOfMonth;

		for (int i = 0; i < periods; i++) {
			map = new HashMap<String, Date>();
			ocalendar.add(Calendar.MONTH, 1);
			ncalendar.add(Calendar.MONTH, 1);
			if (i != 0) {
				setDayOfMonthInCurrentMonth(ocalendar, day);
				setDayOfMonthInCurrentMonth(ncalendar, day);
			}
			map.put("begin", ocalendar.getTime());
			map.put("expected", ncalendar.getTime());
			list.add(map);

		}

		return list;

	}
	
	/**
	 * 
	 * @param beginDate 开始日期
	 * @param endDate  结束日期 
	 * @return 固定还款日
	 */
	public static List<Date> getFixedRepaymentDates(Date beginDate,Date endDate){
		 List<Date> listDate = new LinkedList<Date>();
		   int result = 0;
		   Calendar c1 = Calendar.getInstance();
	       Calendar c2 = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        
	        if (beginDate.after(endDate)) {
				c1.setTime(endDate);
				c2.setTime(beginDate);
			} else {
				c1.setTime(beginDate);
				c2.setTime(endDate);
			}

	       

	        //c1.setTime(beginDate);
	        //c2.setTime(endDate);
	        //result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
	        if(!DateUtil.isTheDay(endDate)){
	        	
	        	 result= (c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR))*12+(c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH))+1;//濡傛灉褰撳墠鏃ユ湡灏忎簬寮�濮嬫棩鏈熷氨浠庡綋鍓嶆湀鐨勫浐瀹氭棩鏈熷紑濮嬭繕娆�
	        	
	        }else{
	        	
	        	result= (c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR))*12+(c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH));//濡傛灉褰撳墠鏃ユ湡澶т簬鎴栫瓑浜庡綋鍓嶆棩鏈� 浠庝笅涓湀寮�濮嬭繕娆�
	        }
	       
	             
	        //int a = (endDate.getYear()-beginDate.getYear())*12+beginDate.getMonth()-endDate.getMonth();
	      // System.out.println("aaaa==="+a);
	        System.out.println("ddddd==="+result);
	        List<Calendar> cals=new ArrayList<Calendar>();
	         result= result == 0 ? 1 : Math.abs(result);
	         for(int i =0;i<result;i++){
	        	 Calendar c = Calendar.getInstance();
	        	 c.setTime(endDate);
	        	 
	        	 
	        	 c.add(Calendar.MONTH, -i);
	        	 c.set(Calendar.HOUR_OF_DAY, 23);
	 			 c.set(Calendar.MINUTE, 59);
	 			 c.set(Calendar.SECOND, 59);
	        	 
	        	 cals.add(c);
	        	 listDate.add(c.getTime());
	        	 
	         }
	         Collections.sort(listDate);
	         /*for(int i =0;i<cals.size();i++){
	        	 Calendar c = cals.get(i);
	        	 Date d = c.getTime();
	        	
	         }*/
	        
		return listDate;
	}

	/*
	 * 判断两个日期是否为同一天
	 */
	public static boolean isSameDay(Date day1, Date day2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ds1 = sdf.format(day1);
		String ds2 = sdf.format(day2);
		if (ds1.equals(ds2)) {
			return true;
		} else {
			return false;
		}
	}

	// 获取下一个工作日
	public static Date getNextWorkDay() {
		// TimeZone.getTimeZone("GMT+8")
		Calendar gc = Calendar.getInstance(Locale.CHINA);
		// gc.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		int week = gc.get(Calendar.DAY_OF_WEEK);
		if (week == Calendar.SATURDAY) {
			gc.add(Calendar.DAY_OF_YEAR, 2);
		} else if (week == Calendar.FRIDAY) {
			gc.add(Calendar.DAY_OF_YEAR, 3);
		} else {
			gc.add(Calendar.DAY_OF_YEAR, 1);
		}
		gc.set(Calendar.HOUR_OF_DAY, 23);
		gc.set(Calendar.MINUTE, 59);
		gc.set(Calendar.SECOND, 59);
		return gc.getTime();
	}

	/**
	 * 网站上线时间
	 *
	 * @return
	 */
	public static Date onlineDate() {
		return parse(onlineDate, FORMAT_SHORT);
	}
	
	/**
	  * 比较日期在今天之前还是之后
	  * @param dateTime
	  * @return
	  */
	 public static boolean compareDate(Date dateTime){  
		 
		 boolean retResult= false;
        String date = DateUtil.format(new Date(), "yyyy-MM-dd");//今天日期
        String dateStr=DateUtil.format(dateTime, "yyyy-MM-dd"); //开始日期
        Date d = DateUtil.parse(dateStr,"yyyy-MM-dd"); 
        int flag = d.compareTo(DateUtil.parse(date, "yyy-MM-dd"));  //两个日期比较
        if (flag >= 0) {//当天及当天之后，<0就是在日期之前  
       	 
            retResult = true;
            
         }else{
       	  
             retResult= false;
             
         }
        
		return retResult;  

       
   } 
	 /**
	  * 今天是大于还款日还是小于还款日
	  * @param endDate
	  * @return
	  */
	 public static boolean isTheDay(Date endDate){
		 Calendar c = Calendar.getInstance();
		 Calendar c1 = Calendar.getInstance();
		 c.setTime(endDate);//当前日期
		 c1.setTime(new Date());//结束日期
		 int day = c.get(Calendar.DAY_OF_MONTH);//还款的日
		 int day1 = c1.get(Calendar.DAY_OF_MONTH);
		 
		 if(day>day1){
			 return false;
		 }else{
			 
			 return true;
		 }
		 
	 }
	
}
