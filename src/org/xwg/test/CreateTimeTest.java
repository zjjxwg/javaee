package org.xwg.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.lang.model.util.SimpleAnnotationValueVisitor6;
/**
 * 获取两种long类型时间的方式
 * @author xuwenguang
 *
 */
public class CreateTimeTest {
	public static void main(String[] args) {
		long Time1=System.currentTimeMillis();
		System.out.println(Time1);  
		System.out.println(formatDate(Time1));  
		long Time2=new Date().getTime();
		System.out.println(Time2); 
	}
	public static String formatDate(long time){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(time));
	}
	
	

}
