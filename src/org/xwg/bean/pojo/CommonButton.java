package org.xwg.bean.pojo;
/**
 * 普通按钮(子菜单项（不含二级菜单的一级菜单）)
 * @author xuwenguang
 *
 */
public class CommonButton extends Button {
	//按钮类型
	private String type;
	private String key;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	

}
