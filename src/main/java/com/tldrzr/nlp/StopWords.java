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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tldrzr.util.Strings;

public final class StopWords {
	private static Map<String, StopWords> table = new HashMap<String, StopWords>();
	private String language;
	private Set<String> words;

	private StopWords(String language) {
		this.language = language;
	}

	public static final StopWords get() throws Exception {
		return get(Language.Names.getDefault());
	}

	public static final StopWords get(String language) throws Exception {
		if (!Strings.isValidString(language)) {
			return get(Language.Names.getDefault());
		}
		StopWords sw = table.get(language);
		synchronized (table) {
			sw = table.get(language);
			if (sw != null) {
				return sw;
			}
			sw = new StopWords(language);
			sw.words = Paths.getStopwordsSet(language);
			table.put(language, sw);
		}
		return sw;
	}

	public boolean isStopWord(String word) {
		if (!Strings.isValidString(word)) {
			return true;
		}
		return words.contains(word.toLowerCase());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(" + language + ") " + words.size() + " stop words");
		return sb.toString();
	}

	public static final void main(String[] args) throws Exception {
		StopWords sw = StopWords.get();
		System.out.println(sw);
	}

}
