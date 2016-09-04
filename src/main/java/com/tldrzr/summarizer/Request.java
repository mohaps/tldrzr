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
package com.tldrzr.summarizer;

import com.tldrzr.nlp.Language;
import com.tldrzr.nlp.Languages;

public class Request {
	private String text;
	private Language language;
	private int maxLines;
	private boolean ignoreSingleOccurences;

	public Request(String text, Language language, int maxLines, boolean ignoreSingleOccurences) {
		this.language = language;
		this.maxLines = maxLines;
		this.ignoreSingleOccurences = ignoreSingleOccurences;
		this.text = text;
	}
	public String getText() {
		return text;
	}
	
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public int getMaxLines() {
		return maxLines;
	}

	public void setMaxLines(int maxLines) {
		this.maxLines = maxLines;
	}

	public boolean isIgnoreSingleOccurences() {
		return ignoreSingleOccurences;
	}

	public void setIgnoreSingleOccurences(boolean ignoreSingleOccurences) {
		this.ignoreSingleOccurences = ignoreSingleOccurences;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Request [language=").append(language).append(", maxLines=").append(maxLines)
				.append(", ignoreSingleOccurences=")
				.append(ignoreSingleOccurences).append("]");
		return builder.toString();
	}

	public static final class Builder {
		private Language language;
		private int maxLines;
		private boolean ignoreSingleOccurences;

		public Builder() {
			this.language = Languages.getDefault();
			this.maxLines = 8;
			this.ignoreSingleOccurences = true;
		}

		public Request build(String text) {
			return new Request(text, language, maxLines, ignoreSingleOccurences);
		}

		public void setLanguage(Language language) {
			this.language = language;
		}

		public void setLanguage(String language) {
			this.language = Languages.get(language);
		}

		public void setMaxLines(int maxLines) {
			this.maxLines = maxLines;
		}

		public void setIgnoreSingleOccurences(boolean ignoreSingleOccurences) {
			this.ignoreSingleOccurences = ignoreSingleOccurences;
		}
	}
}
