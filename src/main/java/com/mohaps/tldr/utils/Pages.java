package com.mohaps.tldr.utils;

import org.jsoup.Jsoup;

public final class Pages {
	
	public static final String getBodyTextFromHTML(String html) throws Exception {
		return Jsoup.parse(html).body().text();
	}

}
