package org.xwg.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/** 
 * 将微信消息中的CreateTime转换成标准格式的时间（yyyy-MM-dd HH:mm:ss） 
 *  
 * @param createTime 消息创建时间 
 * @return 
 */  
public class DateUtil {
	public static String formatcreateTimeToDate(String createTime){
		long msgCreateTime =Long.parseLong(createTime)*1000L;
		return formatDate(msgCreateTime);
		
	}
	public static String formatDate(long time){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(time));
	}
}
