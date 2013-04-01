package com.mohaps.tldr.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import com.mohaps.tldr.summarize.Defaults;
import com.mohaps.tldr.summarize.IStopWords;
import com.mohaps.tldr.summarize.ITokenizer;


public final class Words {
	private static class Word {
		private String word;
		private int frequency;
		public Word(String word) {
			this.word = word.toLowerCase();
			this.frequency = 1;
		}
		public String getWord(){ return word; }
		public int getFrequency(){ return frequency; }
		public int increment(){ return ++frequency; }
		public int hashCode() {
			return word.hashCode();
		}
		public String toString() {
			return new StringBuilder(word).append("(").append(frequency).append(")").toString();
		}
	}
	public static final Set<String> getMostFrequent(String input, ITokenizer tokenizer, IStopWords stopWords, int maxCount, boolean ignoreSingleOccurences) throws Exception {

		HashMap<String, Word> words = new HashMap<String, Word>();
		ArrayList<Word> wordList = new ArrayList<Word>();
		String[] wordTokens = tokenizer.tokenize(input);
		for(int i = 0; i < wordTokens.length; i++) {
			String wordToken = wordTokens[i];
			if(!stopWords.isStopWord(wordToken)) {
				Word w = words.get(wordToken);
				if(w != null) { w.increment(); }
				else { w = new Word(wordToken); words.put(wordToken, w); wordList.add(w); }
			}
		}
		Collections.sort(wordList, new Comparator<Word>()
		{

			public int compare(Word w1, Word w2) {
				if(w1.getFrequency() > w2.getFrequency()){ return -1; }
				else if(w1.getFrequency() < w2.getFrequency()){ return 1; }
				else {
					String s1 = w1.getWord();
					String s2 = w2.getWord();
					
					for(int i = 0; i < s1.length() && i < s2.length(); i++) {
						if(s1.charAt(i) > s2.charAt(i) ){ return -1; }
						else if(s1.charAt(i) > s2.charAt(i)){ return 1; }
					}
					
					if(s1.length() > s2.length()){ return -1; }
					else if(s1.length() < s2.length()){ return 1; }
					else { return 0; }
				}
				
			}
			
		});
		HashSet<String> ret = new HashSet<String>();
		Iterator<Word> iter = wordList.iterator();
		while(iter.hasNext() && ret.size() <= maxCount) {
			Word w = iter.next();
			ret.add(w.getWord());
		}
		return ret;
	}
	public static final boolean isWord(String word) {
		return (word != null && word.trim().length() > 0);
	}
	
	public static Set<String> parseSentences(String input, ITokenizer tokenizer, int minimumWordsInASentence) throws Exception {
		String[] rawSentences = input.split(Defaults.REGEX_SENTENCES);
		HashSet<String> sentences = new HashSet<String>();
		for(int i = 0; i < rawSentences.length; i++) {
			String rawSentence = rawSentences[i];
			String[] words = tokenizer.tokenize(rawSentence);
			if(words.length >= minimumWordsInASentence) {
				sentences.add(rawSentence);
			}
		}
		return sentences;
	}

}
