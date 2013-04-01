package com.mohaps.tldr;

public class Summary {
	public Summary(){}
	public Summary(String text, String summary, int sentence_count, long millis) {
		this.original = text;
		this.summary = summary;
		this.sentence_count = sentence_count;
		this.millis = millis;
	}
	private String original;
	private String summary;
	private int sentence_count;
	private long millis;
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String text) {
		this.original = text;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	@Override
	public String toString() {
		return "Summary [text=" + original + ", summary=" + summary + "]";
	}
	public int getSentence_count() {
		return sentence_count;
	}
	public void setSentence_count(int sentence_count) {
		this.sentence_count = sentence_count;
	}
	public long getMillis() {
		return millis;
	}
	public void setMillis(long millis) {
		this.millis = millis;
	}
	
}
