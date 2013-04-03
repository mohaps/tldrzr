package com.mohaps.tldr.utils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import java.io.*;

import org.jsoup.Jsoup;

import com.mohaps.tldr.summarize.Defaults;
import com.mohaps.tldr.summarize.Factory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public final class Feeds {
	public static class Item {
		private String title;
		private String author;
		private String link;
		private String text;

		public Item() {
			
		}
		public Item(String title, String author, String link, String text) {
			super();
			this.title = title;
			this.author = author;
			this.link = link;
			this.text = text;
		}

		public String getTitle() {
			return title;
		}

		public String getAuthor() {
			return author;
		}

		public String getLink() {
			return link;
		}

		public String getText() {
			return text;
		}

		@Override
		public String toString() {
			return "Feeds.Item [title=" + title + ", author=" + author
					+ ", link=" + link + "]";
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public void setText(String text) {
			this.text = text;
		}

	}

	public static final String fetchPageText(String pageUrl) throws Exception {
		URL url = new URL(pageUrl);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		int contentLength = conn.getContentLength();
		String contentType = conn.getContentType();
		if (contentType != null
				&& !(contentType.toLowerCase().startsWith("text/") || contentType
						.toLowerCase().endsWith("xml"))) {
			throw new Exception("Content type " + contentType
					+ " detected at page " + pageUrl + " is non-textual");
		}
		String encoding = conn.getContentEncoding();
		Logger.getLogger("Feeds").log(Level.INFO, "!---- Encoding Detected : "+encoding);
		if(encoding == null) {
			encoding = "ISO-8859-1";
		}
		if (contentLength >= 0) {
			System.out.println(">> Reading "+contentLength+" bytes!!!");
			byte[] buf = new byte[contentLength];
			int bread = readUpto(in, contentLength, buf, 0, buf.length);
			return bread > 0 ? new String(buf, 0, bread, encoding)
					: Defaults.BLANK_STRING;
		} else {
			System.out.println(">> No content length specified for " + pageUrl
					+ ". Falling back to reading line by line...");
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			for (String line = reader.readLine(); line != null; line = reader
					.readLine()) {
				sb.append(line).append("\n");
			}
			return Words.replaceSmartQuotes(sb.toString());
		}
	}

	public static int readUpto(InputStream in, int contentLength, byte[] buf,
			int offset, int length) throws Exception {
		int totalRead = 0;
		if (buf == null || offset < 0 || length < 0
				|| (offset + length) < contentLength) {
			return 0;
		} else {
			while (totalRead < contentLength) {
				int bread = in.read(buf, totalRead, contentLength - totalRead);
				if (bread > 0) {
					totalRead += bread;
				} else {
					break;
				}
			}
			return totalRead;
		}
	}

	public static final List<Item> fetchFeedItems(String feedUrl)
			throws Exception {
		URL url = new URL(feedUrl);
		SyndFeedInput input = new SyndFeedInput();
		//XmlReader.setDefaultEncoding("ISO-8859-1");
		XmlReader xmlReader = new XmlReader(url);
		SyndFeed feed = input.build(xmlReader);
		ArrayList<Item> items = new ArrayList<Item>();
		@SuppressWarnings("unchecked")
		Iterator<SyndEntry> entries = feed.getEntries().iterator();
		while (entries.hasNext()) {
			SyndEntry entry = entries.next();
			String title = entry.getTitle();
			if (title == null) {
				title = "unknown";
			}
			title = Words.replaceSmartQuotes(title);
			String link = entry.getLink();
			if (link == null) {
				link = feedUrl;
			}
			String author = entry.getAuthor();
			if (author == null || author.length() == 0) {
				author = "unknown";
			}

			@SuppressWarnings("unchecked")
			List<SyndContent> contents = entry.getContents();
			if (contents.size() == 0) {
				String desc = Jsoup.parse(Words.replaceSmartQuotes(entry.getDescription().getValue())).text();
				items.add(new Item(title,author,link,desc));
			} else {
				// System.out.println(title);
				for (SyndContent content : contents) {
					if (content.getType().equalsIgnoreCase("html")) {
						String html = Jsoup.parse(Words.replaceSmartQuotes(content.getValue())).text();
						items.add(new Item(title,author,link,html));
					} else {
						System.out.println(">> non html content type : "
								+ content.getType());
					}
				}
			}
		}
		return items;
	}

	public static final void main(String[] args) throws Exception {
		String feedUrl = args.length > 1 ? args[1] : "http://feeds.feedburner.com/TechCrunch/";
		System.out.println("Fetching feed : "+feedUrl);
		List<Item> feedItems = fetchFeedItems(feedUrl);
		System.out.println(">> items found : "+feedItems.size());
		long start = System.currentTimeMillis();
		for(Item item : feedItems) {
			System.out.println("\n"+item.title);
			//String summary = Factory.getSummarizer().summarize(item.getText(), Defaults.SUMMARY_LENGTH);
			//System.out.println("SUMMARY: \n"+summary);
		}
		long time1 = System.currentTimeMillis() - start;
		start = System.currentTimeMillis();
		for(Item item : feedItems) {
			//System.out.println("\n"+item.title);
			String summary = Factory.getSummarizer().summarize(item.getText(), Defaults.SUMMARY_LENGTH);
			//System.out.println("SUMMARY: \n"+summary);
		}
		long time2 = System.currentTimeMillis() - start;
		
		System.out.println(">> First cycle : "+time1 +" Second Cycle : "+time2);
	}

}
