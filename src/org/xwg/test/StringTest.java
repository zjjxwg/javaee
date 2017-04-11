package org.xwg.test;

import java.io.UnsupportedEncodingException;

public class StringTest {
	public static void main(String[] args) throws Exception {
		// 运行结果：2  
	    System.out.println("柳峰".getBytes("ISO8859-1").length);  
	    // 运行结果：4  
	    System.out.println("柳峰".getBytes("GB2312").length);  
	    // 运行结果：4  
	    System.out.println("柳峰".getBytes("GBK").length);  
	    // 运行结果：6  
	    System.out.println("柳峰".getBytes("UTF-8").length);  
	}

}
