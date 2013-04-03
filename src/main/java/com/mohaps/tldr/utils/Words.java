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

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

public final class Words {
	private static SentenceModel SENTENCE_MODEL;
	static {
		try {
			InputStream inputFile = Words.class.getClassLoader()
					.getResourceAsStream("en-sent.bin");
			if (inputFile != null) {
				try {
					SENTENCE_MODEL = new SentenceModel(inputFile);
					System.out.println(">> OpenNLP Sentence Model loaded!");
				} finally {
					if (inputFile != null) {
						try {
							inputFile.close();
						} catch (Throwable t) {
						}
					}
				}
			}
		} catch (IOException ex) {
			System.err
					.println("Failed to load sentence model for OpenNLP. error = "
							+ ex.getLocalizedMessage()
							+ ". Will fall back to regex based sentence parsing");
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

		public String getWord() {
			return word;
		}

		public int getFrequency() {
			return frequency;
		}

		public int increment() {
			return ++frequency;
		}

		public int hashCode() {
			return word.hashCode();
		}

		public String toString() {
			return new StringBuilder(word).append("(").append(frequency)
					.append(")").toString();
		}
	}
	public static final Set<String> getMostFrequent(String input,
			ITokenizer tokenizer, IStopWords stopWords, int maxCount,
			int minimumOccurences) throws Exception {

		HashMap<String, Word> words = new HashMap<String, Word>();
		ArrayList<Word> wordList = new ArrayList<Word>();
		String[] wordTokens = tokenizer.tokenize(input);
		SnowballStemmer stemmer = new englishStemmer();
		for (int i = 0; i < wordTokens.length; i++) {
			if(isWord(wordTokens[i]) && wordTokens[i].length() > 4) {
				stemmer.setCurrent(wordTokens[i]);
				stemmer.stem();
				String wordToken = stemmer.getCurrent();
				if (isWord(wordToken) && !stopWords.isStopWord(wordToken) && wordToken.length() > 4) {
					Word w = words.get(wordToken);
					if (w != null) {
						w.increment();
					} else {
						w = new Word(wordToken);
						words.put(wordToken, w);
						wordList.add(w);
					}
				}
			}
		}
		Collections.sort(wordList, new Comparator<Word>() {

			public int compare(Word w1, Word w2) {
				if (w1.getFrequency() > w2.getFrequency()) {
					return -1;
				} else if (w1.getFrequency() < w2.getFrequency()) {
					return 1;
				} else {
					String s1 = w1.getWord();
					String s2 = w2.getWord();

					for (int i = 0; i < s1.length() && i < s2.length(); i++) {
						if (s1.charAt(i) > s2.charAt(i)) {
							return -1;
						} else if (s1.charAt(i) > s2.charAt(i)) {
							return 1;
						}
					}

					if (s1.length() > s2.length()) {
						return -1;
					} else if (s1.length() < s2.length()) {
						return 1;
					} else {
						return 0;
					}
				}

			}

		});
		HashSet<String> ret = new HashSet<String>();
		Iterator<Word> iter = wordList.iterator();
		while (iter.hasNext() && ret.size() <= maxCount) {
			Word w = iter.next();
			if(w.getFrequency() >= minimumOccurences) {
				ret.add(w.getWord());
			}
		}
		return ret;
	}

	public static final boolean isWord(String word) {
		return (word != null && word.trim().length() > 0);
	}

	public static Set<String> parseSentences(String input,
			ITokenizer tokenizer, int minimumWordsInASentence) throws Exception {
		if (SENTENCE_MODEL != null) {
			return parseSentencesNLP(input, tokenizer, minimumWordsInASentence);
		} else {
			return parseSentencesRegEx(input, tokenizer,
					minimumWordsInASentence);
		}
	}

	public static Set<String> parseSentencesNLP(String input,
			ITokenizer tokenizer, int minimumWordsInASentence) throws Exception {
		SentenceDetectorME sentenceDetector = new SentenceDetectorME(
				SENTENCE_MODEL);
		String[] rawSentences = sentenceDetector.sentDetect(input);
		HashSet<String> sentences = new HashSet<String>();
		for (int i = 0; i < rawSentences.length; i++) {
			String rawSentence = rawSentences[i];
			String[] words = tokenizer.tokenize(rawSentence);
			if (words.length >= minimumWordsInASentence) {
				sentences.add(rawSentence);
			}
		}
		return sentences;
	}

	public static Set<String> parseSentencesRegEx(String input,
			ITokenizer tokenizer, int minimumWordsInASentence) throws Exception {
		String[] rawSentences = input.split(Defaults.REGEX_SENTENCES);
		HashSet<String> sentences = new HashSet<String>();
		for (int i = 0; i < rawSentences.length; i++) {
			String rawSentence = rawSentences[i];
			String[] words = tokenizer.tokenize(rawSentence);
			if (words.length >= minimumWordsInASentence) {
				sentences.add(rawSentence);
			}
		}
		return sentences;
	}

	public static final String replaceSmartQuotes(String s) {
		return s.replace('\u201b', '\'').replace('\u2018', '\'').replace('\u2019', '\'').replace('\u2026', '-').replace('\u2014', '-').replaceAll("&#8220;", "\"")
				.replaceAll("&#8221;", "\"")
				.replaceAll("&#8216;", "\'")
				.replaceAll("&#8217;", "\'")
				.replaceAll("&#8219;", "\'")
				.replaceAll("&#039;", "\'")
				.replaceAll("&#8230;", "...")				
				.replaceAll("&#8212;", "-");
	}
	
	public static void main(String[] args) {
		String s = "Yeah, yeah — it’s just a quick CSS hack applied to any detected instance of Gmail, but imagine all the jealous looks… sympathy… hipster cred*";
		for(int i = 0; i < s.length(); i++){
			System.out.println(">> Char Code "+(short)s.charAt(i)+" (0x"+Integer.toHexString((short)s.charAt(i))+") - {"+s.charAt(i)+"}");
		}
	}
}
