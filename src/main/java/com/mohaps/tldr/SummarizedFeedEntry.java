package com.mohaps.tldr;

public class SummarizedFeedEntry {
	public SummarizedFeedEntry(String title, String author, String link,
			String text, String summary) {
		this.title = title;
		this.author = author;
		this.link = link;
		this.text = text;
		this.summary = summary;
	}
	private String title;
	private String author;
	private String link;
	private String text;
	private String summary;
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
}
