package com.tldrzr.content;

public class ArticleData {
	private String title;
	private String image;
	private String url;
	private String text;

	public ArticleData(String title, String image, String url, String text) {
		this.title = title;
		this.image = image;
		this.url = url;
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public String getImage() {
		return image;
	}

	public String getUrl() {
		return url;
	}

	public String getText() {
		return text;
	}
}
