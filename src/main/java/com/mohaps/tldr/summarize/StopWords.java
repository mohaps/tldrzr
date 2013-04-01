package com.mohaps.tldr.summarize;

import java.util.HashSet;
import java.util.Set;

public class StopWords implements IStopWords {
	public static final String[] STOPWORDS = new String[] { "a", "able",
			"about", "across", "after", "all", "almost", "also", "am", "among",
			"an", "and", "any", "are", "as", "at", "be", "because", "been",
			"but", "by", "can", "cannot", "can\'t", "could", "dear", "did",
			"do", "does", "either", "else", "ever", "every", "for", "from",
			"get", "got", "had", "has", "have", "he", "her", "hers", "him",
			"his", "how", "however", "i", "if", "in", "into", "is", "it",
			"its", "just", "least", "let", "like", "likely", "may", "me",
			"might", "most", "must", "my", "neither", "no", "nor", "not", "of",
			"off", "often", "on", "only", "or", "other", "our", "own",
			"rather", "said", "say", "says", "she", "should", "since", "so",
			"some", "than", "that", "the", "their", "them", "then", "there",
			"these", "they", "this", "tis", "to", "too", "twas", "us", "wants",
			"was", "we", "were", "what", "when", "where", "which", "while",
			"who", "whom", "why", "will", "with", "would", "won\'t", "yet",
			"you", "your" };
	
	private Set<String> stopWords = new HashSet<String>();
	
	public StopWords() {
		for(int i = 0; i < STOPWORDS.length; i++) {
			stopWords.add(STOPWORDS[i]);
		}
	}

	public boolean isStopWord(String word) {
		return word == null || word.length() < 2 || stopWords.contains(word);
	}

}
