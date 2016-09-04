package com.tldrzr.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * miscellaneous utilities for handling web content
 * @author mohaps
 *
 */
public final class Web {
	/**
	 * Get the HTML content for url (parse and reconvert it to HTML via jsoup)
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static final String fetchHTML(String url) throws Exception {
		Document doc = Jsoup.connect(url).get();
		return doc.html();
	}
}
