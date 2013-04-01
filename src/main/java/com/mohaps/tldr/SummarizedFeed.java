package com.mohaps.tldr;

import java.util.List;

public class SummarizedFeed {
	public SummarizedFeed(String url, List<SummarizedFeedEntry> entries, long millis) {
		this.url = url;
		this.entries = entries;
		this.millis = millis;
	}
	private String url;
	private List<SummarizedFeedEntry> entries;
	private long millis;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<SummarizedFeedEntry> getEntries() {
		return entries;
	}
	public void setEntries(List<SummarizedFeedEntry> entries) {
		this.entries = entries;
	}
	public long getMillis() {
		return millis;
	}
	public void setMillis(long millis) {
		this.millis = millis;
	}
	public int getItemCount() {
		return this.entries.size();
	}
}
