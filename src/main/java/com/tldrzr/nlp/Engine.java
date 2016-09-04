/*
 *  
 *  TL;DRzr - A simple algorithmic summarizer
 *  Website: http://tldrzr.com
 *  Author: Saurav Mohapatra (mohaps@gmail.com)
 *  
 *  Copyright (c) 2016-2026, Saurav Mohapatra
 *  All rights reserved.
 *  
 *  
 *  
 *  Redistribution and use in source and binary forms, with or without modification, are permitted 
 *  provided that the following conditions are met:
 *  
 *  	a)  Redistributions of source code must retain the above copyright notice, 
 *  		this list of conditions and the following disclaimer.
 *  
 *  	b)  Redistributions in binary form must reproduce the above copyright notice, 
 *  		this list of conditions and the following disclaimer in the documentation 
 *  		and/or other materials provided with the distribution.
 *  	
 *  	c)  Neither the name of TL;DRzr nor the names of its contributors may be used 
 *  		to endorse or promote products derived from this software without specific 
 *  		prior written permission.
 *  
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, 
 *  BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
 *  SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 *  DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 *  HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.tldrzr.nlp;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.tldrzr.util.Strings;

import opennlp.tools.stemmer.Stemmer;
import opennlp.tools.stemmer.snowball.SnowballStemmer;

public final class Engine {
	private Language language;
	private Sentences sentenceTokenizer;
	private Words wordTokenizer;
	private StopWords stopWords;

	public static final SnowballStemmer.ALGORITHM getStemmerAlgoritm(String language) {
		if (!Strings.isValidString(language)) {
			return getStemmerAlgoritm(Language.Names.getDefault());
		}

		if (language.equalsIgnoreCase(Language.Names.ENGLISH)) {
			return SnowballStemmer.ALGORITHM.ENGLISH;
		}

		// TODO: add bindings for other languages
		return SnowballStemmer.ALGORITHM.ENGLISH;

	}

	public Engine() throws Exception {
		this(Languages.getDefault());
	}

	public Engine(Language language) throws Exception {
		this.language = language;
		this.sentenceTokenizer = new Sentences(language.getName());
		this.wordTokenizer = new Words(language.getName());
		this.stopWords = StopWords.get(language.getName());
	}

	public Stemmer newStemmer() {
		return new SnowballStemmer(getStemmerAlgoritm(language.getName()));
	}

	public Language getLanguage() {
		return this.language;
	}

	public Sentences getSentenceTokenizer() {
		return this.sentenceTokenizer;
	}

	public Words getWordTokenizer() {
		return this.wordTokenizer;
	}

	public StopWords getStopWords() {
		return this.stopWords;
	}
	
	public String[] summarize(String input, int sentenceCount, boolean ignoreSingleOccurence) throws Exception {

		String[] sentences = sentenceTokenizer.tokenize(input);
		if (sentences.length <= sentenceCount) {
			return sentences;
		}
		// first pass
		WordFrequency frequentWords = new WordFrequency();
		Stemmer stemmer = newStemmer();
		for (String s : sentences) {
			String[] words = wordTokenizer.tokenize(s);
			for (String w : words) {
				String normalized = Strings.normalizeWord(w);
				CharSequence cs = stemmer.stem(normalized);
				if (cs == null) {
					continue;
				}
				String stemmed = cs.toString();
				if (stemmed.length() <= 2) {
					continue;
				}
				if (stopWords.isStopWord(stemmed)) {
					continue;
				}
				frequentWords.add(stemmed);
			}
		}
		
		Set<Sentence> significantSentences = new TreeSet<Sentence>(new Sentence.ScoreComparator());
		int sIndex = 0;
		for (String s : sentences) {
			String[] words = wordTokenizer.tokenize(s);
			Sentence curSentence = new Sentence(s, sIndex++);
			for (String w : words) {
				String normalized = Strings.normalizeWord(w);
				CharSequence cs = stemmer.stem(normalized);
				if (cs == null) {
					continue;
				}
				String stemmed = cs.toString();
				if (stemmed.length() <= 2) {
					continue;
				}
				if (stopWords.isStopWord(stemmed)) {
					continue;
				}
				
				WordFrequency.Entry entry = frequentWords.get(stemmed);
				if (entry == null) {
					continue;
				}
				if (ignoreSingleOccurence && entry.getCount() == 1) {
					continue;
				}
				curSentence.incrementScore(1);
			}
			if (curSentence.getScore() > 0) {
				significantSentences.add(curSentence);
			}
		}
		
		// third pass
		int limit = Math.min(sentenceCount, significantSentences.size());
		Set<Sentence> summary = new TreeSet<Sentence>(new Sentence.IndexComparator());
		Iterator<Sentence> iter = significantSentences.iterator();
		int count = 0;
		while (count < limit && iter.hasNext()) {
			summary.add(iter.next());
			count++;
		}
		
		// fourth pass
		String[] ret = new String[summary.size()];
		iter = summary.iterator();
		count = 0;
		while (iter.hasNext()) {
			ret[count++] = iter.next().getText();
		}
		return ret;
		
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("NLP Engine for " + language);
		return sb.toString();
	}
}
