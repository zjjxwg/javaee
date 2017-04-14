package org.xwg.bean.pojo;
/**
 * ‘通用接口凭证
 * @author xuwenguang
 *
 */
public class AccessToken {
	//凭证
	private String token;
	//凭证有效时间
	private String expiresIn;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	

}
