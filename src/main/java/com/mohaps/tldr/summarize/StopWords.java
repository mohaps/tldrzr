/*
 *  
 *  TL;DRzr - A simple algorithmic summarizer
 *  Website: http://tldrzr.com
 *  Author: Saurav Mohapatra (mohaps@gmail.com)
 *  
 *  Copyright (c) 2013, Saurav Mohapatra
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
