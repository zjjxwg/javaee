package org.xwg.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jasper.tagplugins.jstl.core.Url;
import org.xwg.bean.pojo.AccessToken;
import org.xwg.bean.pojo.Menu;

/**
 * 通用接口工具类
 * @author xuwenguang
 *
 */
public class WeixinUtil {
	private static Log logger=LogFactory.getLog(WeixinUtil.class);
	
	// 获取access_token的接口地址（GET） 限200（次/天）  
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 菜单创建（POST） 限100（次/天）  
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	/**
	 * 
	 * @param reqeustUrl 请求的url
	 * @param reqeustMethod 请求的方式（get，post）
	 * @param outputStr  提交的数据
	 * @return
	 */
	public static JSONObject httpRequest(String reqeustUrl,String reqeustMethod ,String outputStr){
		JSONObject jsonObject=null;
		StringBuffer buffer=new StringBuffer();
		TrustManager tm [] ={new MyX509TrustManager()};
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化  
			SSLContext sslContext=SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			//从sslContext，获取SSLSocketFactory
			SSLSocketFactory ssf=sslContext.getSocketFactory();
			
			
			URL url=new URL(reqeustUrl);
			HttpsURLConnection httpUrlConn =(HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);//使用缓存
			
			//设置请求方式
			httpUrlConn.setRequestMethod(reqeustMethod);
			if("GET".equalsIgnoreCase(reqeustMethod)){//get请求
				httpUrlConn.connect();
			}
			//有数据需要提交
			if(null!=outputStr){
				//相当于请求的时候往后面写参数
				OutputStream os=httpUrlConn.getOutputStream();
				os.write(outputStr.getBytes("UTF-8"));
				os.close();
			}
			
			//将返回的流转换为字符串
			InputStream is=httpUrlConn.getInputStream();
			InputStreamReader read=new InputStreamReader(is,"UTF-8");
			BufferedReader bufferReader=new BufferedReader(read);
			String str=null;
			while((str=bufferReader.readLine())!=null){
				buffer.append(str);
			}
			//释放资源
			bufferReader.close();  
            read.close();  
            // 释放资源  
            is.close();  
            is = null;  
            httpUrlConn.disconnect();//断开连接
            jsonObject=JSONObject.fromObject(buffer.toString());//字符串转换为json对象
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static AccessToken getAccessToken(String APPID,String appsecret){
		AccessToken accessToken=null;
		String reqeustUtl=access_token_url.replace("APPID", APPID).replace("APPSECRET", appsecret);
		JSONObject jsonObject=httpRequest(reqeustUtl,"GET",null);
		if(jsonObject!=null){
			try{
				accessToken=new AccessToken();
				accessToken.setToken(jsonObject.get("access_token").toString());
				accessToken.setExpiresIn(jsonObject.get("expires_in").toString());
			}catch(JSONException e){
				accessToken=null;
				 //logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg")); 
			}
			
			
		}
		return accessToken;
	}
	
	public static int createMenu(Menu menu, String accessToken){
		int result=0;
		String url=menu_create_url.replace("ACCESS_TOKEN", accessToken);
		//将菜单对象转换为jsonMenu,先将javabean转换为json对象，再转换为String类型
		String jsonMenu=JSONObject.fromObject(menu).toString();
		
		JSONObject jsonObject =httpRequest(url, "POST", jsonMenu);
		
		if (null != jsonObject) {  
	        if (0 != jsonObject.getInt("errcode")) {  
	            result = jsonObject.getInt("errcode");  
	            //logger.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    } 
		return result;
		
	}
	

}
