package com.huake.saas.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

	public static String getDateStringWithZone(Calendar cal, String format, TimeZone timeZone, Locale locale) {
		if (cal == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		sdf.setTimeZone(timeZone);
		return sdf.format(cal.getTime());
	}
}
