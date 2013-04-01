package com.mohaps.tldr.summarize;

public interface ISummarizer {
	String summarize(final String input, int sentenceCount, int maxFrequentWords, boolean shouldIgnoreSingleOccurences) throws Exception;
	String summarize(String input, int sentenceCount) throws Exception;
}
