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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tldrzr.util.Strings;

public class Response {
	private static final Logger LOG = LoggerFactory.getLogger(Response.class);
	private String[] lines;
	private String[] keywords;
	private long timeTakenMillis;

	public Response(String[] lines, String[] keywords, long timeTakenMillis) {
		this.lines = lines;
		this.keywords = keywords;
		this.timeTakenMillis = timeTakenMillis;
	}

	public String[] getLines() {
		return lines;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public long getTimeTakenMillis() {
		return timeTakenMillis;
	}

	public String getSummary() {
		return Strings.join(" ", lines);
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Response [\n lines=").append(Strings.join(" ", lines)).append(",\n keywords=")
				.append(Strings.join(", ", keywords)).append(",\n timeTakenMillis=").append(timeTakenMillis)
				.append("\n]");
		return builder2.toString();
	}

	public static final class Builder {
		private List<String> lines = new ArrayList<String>();
		private Set<String> keywords = new HashSet<String>();
		private long timeTakenMillis = 0;

		public Builder() {
		}

		public Response build() {
			String[] lines = new String[this.lines.size()];
			String[] words = new String[this.keywords.size()];
			return new Response(this.lines.toArray(lines), this.keywords.toArray(words), timeTakenMillis);
		}

		public Builder addLine(String s) {
			if (Strings.isValidString(s)) {
				lines.add(s);
			}
			return this;
		}

		public Builder addKeyword(String w) {
			if (Strings.isValidString(w)) {
				keywords.add(w);
			}
			return this;
		}

		public Builder setTimeTakenMillis(long l) {
			timeTakenMillis = Math.max(0, l);
			return this;
		}
	}

	public static void main(String[] args) {
		Response.Builder builder = new Response.Builder();
		builder.addLine("First line.").addLine("Second line.");
		builder.addKeyword("foo").addKeyword("bar").addKeyword("boom");
		builder.setTimeTakenMillis(100);
		LOG.info("Built : " + builder.build());
	}
}
