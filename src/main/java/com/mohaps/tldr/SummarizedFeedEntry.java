package com.mohaps.tldr;

import java.util.Set;

public class SummarizedFeedEntry {
	
	public SummarizedFeedEntry(String title, String author, String link,
			String text, String summary, Set<String> keywords) {
		this.title = title;
		this.author = author;
		this.link = link;
		this.text = text;
		this.summary = summary;
		this.keywords = keywords;
	}
	private String title;
	private String author;
	private String link;
	private String text;
	private String summary;
	private Set<String> keywords;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public Set<String> getKeywords(){ return this.keywords; }
	public void setKeywords(){ this.keywords = keywords; }
}
