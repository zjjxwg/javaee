package org.xwg.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xwg.bean.message.response.Article;
import org.xwg.bean.message.response.NewsMessage;
import org.xwg.bean.message.response.TextMessage;
import org.xwg.util.MessageUtil;
import org.xwg.util.QQFaceUtil;

public class CoreService {
	
	public static Log log=LogFactory.getLog(CoreService.class);

	public static String processRequest(HttpServletRequest request) {
		//回复的消息
		String respMessage=null;
		 
        //xml请求解析
        try {
        	// 默认返回的文本消息内容  
            String respContent = "请求处理异常，请稍候尝试！";
            
            //接收推送的事件和关注用户发送消息
			Map<String, String> map=MessageUtil.parseXml(request);
			//发送方账号（openID）
			String fromUserName=map.get("FromUserName");
			//（公众号）
			String toUserName=map.get("ToUserName");
			String msgType=map.get("MsgType");
			log.info("消息类型："+msgType);
			// 回复文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
            
         // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                //接收用户发送的文本信息内容;
                String content=map.get("Content");
                log.info("用户发送的文本信息内容："+content);
                //新建一个多图文消息
                NewsMessage newsMessage=new NewsMessage();
                newsMessage.setFromUserName(toUserName);
                newsMessage.setToUserName(fromUserName);
                newsMessage.setCreateTime(new Date().getTime());
                newsMessage.setFuncFlag(0);
                newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                
                List<Article> articleList =new ArrayList<Article>();
                /**
                 * 单图文消息
                 */
                if("1".equals(content)){
                	//新建一个图文
                	Article article=new Article();
                	article.setTitle("微信公众号开发");
                	article.setDescription("微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列教程，也希望借此机会认识更多同行！");
                	//图片地址
                	article.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
                	//链接地址
                	article.setUrl("http://blog.csdn.net/lyq8479");
                	articleList.add(article);
                	//设置图文消息个数
                	newsMessage.setArticleCount(articleList.size());
                	//设置图文消息包含的图文集合
                	newsMessage.setArticles(articleList);
                	//设置图文消息转化为xml数据包
                	respMessage = MessageUtil.newsMessageToXml(newsMessage);
                	
                }// 单图文消息---不含图片  
                else if ("2".equals(content)) {  
                    Article article = new Article();  
                    article.setTitle("微信公众帐号开发教程Java版");  
                    // 图文消息中可以使用QQ表情、符号表情  
                    article.setDescription("柳峰，80后，" + QQFaceUtil.emoji(0x1F6B9)  
                            + "，微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列连载教程，也希望借此机会认识更多同行！\n\n目前已推出教程共12篇，包括接口配置、消息封装、框架搭建、QQ表情发送、符号表情发送等。\n\n后期还计划推出一些实用功能的开发讲解，例如：天气预报、周边搜索、聊天功能等。");  
                    // 将图片置为空  
                    article.setPicUrl("");  
                    article.setUrl("http://blog.csdn.net/lyq8479");  
                    articleList.add(article);  
                    newsMessage.setArticleCount(articleList.size());  
                    newsMessage.setArticles(articleList);  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
                } 
             // 多图文消息  
                else if ("3".equals(content)) {  
                    Article article1 = new Article();  
                    article1.setTitle("微信公众帐号开发教程\n引言");  
                    article1.setDescription("");  
                    article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");  
                    article1.setUrl("http://blog.csdn.net/lyq8479/article/details/8937622");  
  
                    Article article2 = new Article();  
                    article2.setTitle("第2篇\n微信公众帐号的类型");  
                    article2.setDescription("");  
                    article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");  
                    article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8941577");  
  
                    Article article3 = new Article();  
                    article3.setTitle("第3篇\n开发模式启用及接口配置");  
                    article3.setDescription("");  
                    article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");  
                    article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8944988");  
  
                    articleList.add(article1);  
                    articleList.add(article2);  
                    articleList.add(article3);  
                    newsMessage.setArticleCount(articleList.size());  
                    newsMessage.setArticles(articleList);  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
                }  
                // 多图文消息---首条消息不含图片  
                else if ("4".equals(content)) {  
                    Article article1 = new Article();  
                    article1.setTitle("微信公众帐号开发教程Java版");  
                    article1.setDescription("");  
                    // 将图片置为空  
                    article1.setPicUrl("");  
                    article1.setUrl("http://blog.csdn.net/lyq8479");  
  
                    Article article2 = new Article();  
                    article2.setTitle("第4篇\n消息及消息处理工具的封装");  
                    article2.setDescription("");  
                    article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");  
                    article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8949088");  
  
                    Article article3 = new Article();  
                    article3.setTitle("第5篇\n各种消息的接收与响应");  
                    article3.setDescription("");  
                    article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");  
                    article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8952173");  
  
                    Article article4 = new Article();  
                    article4.setTitle("第6篇\n文本消息的内容长度限制揭秘");  
                    article4.setDescription("");  
                    article4.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");  
                    article4.setUrl("http://blog.csdn.net/lyq8479/article/details/8967824");  
  
                    articleList.add(article1);  
                    articleList.add(article2);  
                    articleList.add(article3);  
                    articleList.add(article4);  
                    newsMessage.setArticleCount(articleList.size());  
                    newsMessage.setArticles(articleList);  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
                }  
                // 多图文消息---最后一条消息不含图片  
                else if ("5".equals(content)) {  
                    Article article1 = new Article();  
                    article1.setTitle("第7篇\n文本消息中换行符的使用");  
                    article1.setDescription("");  
                    article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");  
                    article1.setUrl("http://blog.csdn.net/lyq8479/article/details/9141467");  
  
                    Article article2 = new Article();  
                    article2.setTitle("第8篇\n文本消息中使用网页超链接");  
                    article2.setDescription("");  
                    article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");  
                    article2.setUrl("http://blog.csdn.net/lyq8479/article/details/9157455");  
  
                    Article article3 = new Article();  
                    article3.setTitle("如果觉得文章对你有所帮助，请通过博客留言或关注微信公众帐号xiaoqrobot来支持柳峰！");  
                    article3.setDescription("");  
                    // 将图片置为空  
                    article3.setPicUrl("");  
                    article3.setUrl("http://blog.csdn.net/lyq8479");  
  
                    articleList.add(article1);  
                    articleList.add(article2);  
                    articleList.add(article3);  
                    newsMessage.setArticleCount(articleList.size());  
                    newsMessage.setArticles(articleList);  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
                }  
                
            }  
            // 图片消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
                respContent = "您发送的是图片消息！"; 
                textMessage.setContent(respContent);
                respMessage=MessageUtil.textMessageToXml(textMessage);
            }  
            // 地理位置消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
                respContent = "您发送的是地理位置消息！";
                textMessage.setContent(respContent);
                respMessage=MessageUtil.textMessageToXml(textMessage);
            }  
            // 链接消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
                respContent = "您发送的是链接消息！";
                textMessage.setContent(respContent);
                respMessage=MessageUtil.textMessageToXml(textMessage);
            }  
            // 音频消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
                respContent = "您发送的是音频消息！";
                textMessage.setContent(respContent);
                respMessage=MessageUtil.textMessageToXml(textMessage);
            }  // 事件推送  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = map.get("Event");  
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { 
                	
                    respContent = "谢谢您的关注！";  
                }  
                // 取消订阅  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
                }  
                // 自定义菜单点击事件  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                	// 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = map.get("EventKey");
                    // TODO 自定义菜单权没有开放，暂不处理该类消息  
                	 if (eventKey.equals("11")) {  
                         respContent = "天气预报菜单项被点击！";  
                     } else if (eventKey.equals("12")) {  
                         respContent = "公交查询菜单项被点击！";  
                     } else if (eventKey.equals("13")) {  
                         respContent = "周边搜索菜单项被点击！";  
                     } else if (eventKey.equals("14")) {  
                         respContent = "历史上的今天菜单项被点击！";  
                     } else if (eventKey.equals("21")) {  
                         respContent = "歌曲点播菜单项被点击！";  
                     } else if (eventKey.equals("22")) {  
                         respContent = "经典游戏菜单项被点击！";  
                     } else if (eventKey.equals("23")) {  
                         respContent = "美女电台菜单项被点击！";  
                     } else if (eventKey.equals("24")) {  
                         respContent = "人脸识别菜单项被点击！";  
                     } else if (eventKey.equals("25")) {  
                         respContent = "聊天唠嗑菜单项被点击！";  
                     } else if (eventKey.equals("31")) {  
                         respContent = "Q友圈菜单项被点击！";  
                     } else if (eventKey.equals("32")) {  
                         respContent = "电影排行榜菜单项被点击！";  
                     } else if (eventKey.equals("33")) {  
                         respContent = "幽默笑话菜单项被点击！";  
                     }  
                }  
            } 
           
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return respMessage;
	}

}
