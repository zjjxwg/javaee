package org.xwg.bean.message.response;

import java.util.List;

public class NewsMessage extends BaseMessage {
	private int ArticleCount;//图文消息个数，10个以内
	private List<Article> articles;//// 多条图文消息信息，默认第一个item为大图  
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<Article> getArticles() {
		return articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	
	

}
