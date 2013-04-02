package com.mohaps.tldr.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;


import com.mohaps.tldr.summarize.Defaults;
import com.mohaps.tldr.summarize.IStopWords;
import com.mohaps.tldr.summarize.ITokenizer;

import java.io.*;

public final class Words {
	private static SentenceModel SENTENCE_MODEL;
	static {
		try { 
			InputStream inputFile = Words.class.getClassLoader().getResourceAsStream("en-sent.bin");
			if(inputFile != null) {
				try {
					SENTENCE_MODEL = new SentenceModel(inputFile);
					System.out.println(">> OpenNLP Sentence Model loaded!");
				} finally {
					if(inputFile != null) {
						try { inputFile.close(); } catch (Throwable t){}
					}
				}
			}
		} catch (IOException ex) {
			System.err.println("Failed to load sentence model for OpenNLP. error = "+ex.getLocalizedMessage()+". Will fall back to regex based sentence parsing");
			ex.printStackTrace();
		}
	}
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
		if(SENTENCE_MODEL != null) {
			return parseSentencesNLP(input, tokenizer, minimumWordsInASentence);
		} else {
			return parseSentencesRegEx(input, tokenizer, minimumWordsInASentence);
		}
	}
	public static Set<String> parseSentencesNLP(String input, ITokenizer tokenizer, int minimumWordsInASentence) throws Exception {
		SentenceDetectorME sentenceDetector = new SentenceDetectorME(SENTENCE_MODEL);
		String[] rawSentences = sentenceDetector.sentDetect(input);
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
	public static Set<String> parseSentencesRegEx(String input, ITokenizer tokenizer, int minimumWordsInASentence) throws Exception {
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
	public static final String replaceSmartQuotes(String input) {
	    StringBuilder sb = new StringBuilder();
	    for ( int i = 0; i < input.length(); i++ ) {
	        char c = input.charAt(i);
	        switch (c) {
	            case '\u8220':
	                sb.append("\"");
	                break;
	            case '\u8221':
	                sb.append("\"");
	                break;
	            case '\u8216':
	                sb.append("\'");
	                break;
	            case '\u8217':
	                sb.append("\'");
	                break;
	            default:
	                sb.append(c);
	                break;
	        }
	    }
	    return sb.toString();
		
	}
}
