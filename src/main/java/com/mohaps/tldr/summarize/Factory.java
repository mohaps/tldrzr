package com.mohaps.tldr.summarize;

public final class Factory {
	public static final IStopWords DEFAULT_STOPWORDS = new StopWords();
	public static final ITokenizer DEFAULT_TOKENIZER = new Tokenizer();
	public static final ISummarizer DEFAULT_SUMMARIZER = new Summarizer(DEFAULT_STOPWORDS, DEFAULT_TOKENIZER);
	public static final IStopWords getStopWords() {
		return DEFAULT_STOPWORDS;
	}
	public static final ITokenizer getTokenizer() {
		return DEFAULT_TOKENIZER;
	}
	public static final ISummarizer getSummarizer() {
		return DEFAULT_SUMMARIZER;
	}

}
