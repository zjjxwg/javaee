package org.xwg.bean.message.request;
//接收消息的基类
public class BaseMessage {
	//开发者微信公众号
	private String ToUserName;
	//发送方账号（一个OpenID）
	private String FromUserName;
	private long CreateTime;
	private String MsgType;//消息类型（text/image/location/link）
	private long MsgId;//消息ID
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public long getMsgId() {
		return MsgId;
	}
	public void setMsgId(long msgId) {
		MsgId = msgId;
	}
	
	

}
