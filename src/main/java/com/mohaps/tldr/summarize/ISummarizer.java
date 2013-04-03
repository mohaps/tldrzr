package com.mohaps.tldr.summarize;

import java.util.Set;

public interface ISummarizer {
	String summarize(final String input, int sentenceCount, int maxFrequentWords, boolean shouldIgnoreSingleOccurences) throws Exception;
	String summarize(String input, int sentenceCount) throws Exception;
	
	Set<String> keywords(final String input, int maxKeyWords) throws Exception;
}
