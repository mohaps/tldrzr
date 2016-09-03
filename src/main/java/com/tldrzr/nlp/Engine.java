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

public final class Engine {
	private static final Logger LOG = LoggerFactory.getLogger(Engine.class);
	private Language language;
	private Sentences sentenceTokenizer;
	private Words wordTokenizer;
	private StopWords stopWords;
	public Engine() throws Exception {
		this(Languages.getDefault());
	}
	public Engine(Language language) throws Exception {
		this.language = language;
		this.sentenceTokenizer = new Sentences(language.getName());
		this.wordTokenizer = new Words(language.getName());
		this.stopWords = StopWords.get(language.getName());
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
		sb.append("NLP Engine for "+language);
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		Engine engine = new Engine();
		LOG.info("created "+engine);
	}
}
