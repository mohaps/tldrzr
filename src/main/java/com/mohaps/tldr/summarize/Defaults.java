package com.mohaps.tldr.summarize;

public final class Defaults {
	public static final int MAX_SENTENCES = 4;
	public static final int MAX_MOST_FREQUENT_WORDS = 20;
	public static final int MIN_WORDS_PER_SENTENCE = 5;
	public static final int AVG_WORDS_PER_SENTENCE = 20;
	
	public static final String REGEX_WHITESPACE = "\\W";
	public static final String REGEX_WORDS = "\\s";
	public static final String REGEX_SENTENCES = "(\\.|!|\\?)+(\\s|\\z)+";
	public static final String BLANK_STRING = "";
	public static final byte[] BLANK_BYTES = new byte[0];
	public static final boolean SHOULD_IGNORE_SINGLE_OCCURENCES = true;
	public static final String[] BLANK_STRING_ARRAY = new String[0];
	public static final int SUMMARY_LENGTH = 4;
}
