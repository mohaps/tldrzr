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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tldrzr.util.Strings;

import opennlp.tools.stemmer.Stemmer;
import opennlp.tools.stemmer.snowball.SnowballStemmer;

public final class Engine {
	private static final Logger LOG = LoggerFactory.getLogger(Engine.class);
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("NLP Engine for " + language);
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		Engine engine = new Engine();
		LOG.info("created " + engine);
		String inputText = "John F. Kennedy (better known as JFK) was president of the U.S.A., who lost 59lbs by jogging on Main st. and Side blvd. He is known for his book \"Profiles in Courage\", which detailed his time commanding a P.T. Boat during WWII";
		String normalizedText = Strings.normalizeInput(inputText);
		LOG.info(normalizedText);
		String[] sentences = engine.getSentenceTokenizer().tokenize(normalizedText);
		LOG.info(" -> pass 1 : found "+sentences.length+" sentences");
		int sentenceIndex = 0;
		Stemmer stemmer = engine.newStemmer();
		for (String sentence : sentences) {
			LOG.info(" Sentence #"+ (++sentenceIndex)+" => "+sentence);
			String[] words = engine.getWordTokenizer().tokenize(sentence);
			LOG.info("    Found "+words.length+" words!");
			int wordIndex = 0;
			for (String word : words) {
				String normalizedWord = Strings.normalizeWord(word);
				String stemmedWord = stemmer.stem(normalizedWord).toString();
				LOG.info("    Word #"+(++wordIndex)+" => "+ word +" -> "+normalizedWord +" -> "+stemmedWord);
			}
		}
	}
}
