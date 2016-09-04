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
package com.tldrzr.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tldrzr.content.ArticleData;
import com.tldrzr.content.ArticleExtractor;
import com.tldrzr.summarizer.DefaultSummarizer;
import com.tldrzr.summarizer.Request;
import com.tldrzr.summarizer.Response;
import com.tldrzr.summarizer.Summarizer;
import com.tldrzr.util.Strings;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Main {
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	private static OptionParser OPT = new OptionParser();
	static {
		OPT.accepts("url").withRequiredArg().describedAs("url to fetch content from")
			.defaultsTo("http://www.thehindu.com/todays-paper/the-pigeon-paradox-feeding-them-could-be-bad-for-your-lungs/article9070973.ece");
			//.defaultsTo("http://www.thehindu.com/news/international/india-china-diagnose-insensitivity-to-core-interests-as-chief-obstacle-in/article9072372.ece");	
			//.defaultsTo("http://www.cnn.com/2016/09/03/asia/australia-doughnut-reef-discovered/");
		OPT.accepts("language").withRequiredArg().defaultsTo("en").describedAs("language hint");
		OPT.accepts("help", "show help").forHelp();
	}

	public static void main(String[] args) throws Exception {
		OptionSet options = OPT.parse(args);
		if (options.has("help")) {
			OPT.printHelpOn(System.out);
			return;
		}
		String url = options.valueOf("url").toString();
		String language = options.valueOf("language").toString();
		LOG.info(">> fetching article from : " + url);
		long startTime = System.currentTimeMillis();
		ArticleData aData = ArticleExtractor.extractArticleFromURL(url, true);
		String text = aData.getText();
		LOG.info("TITLE: "+aData.getTitle());
		LOG.info("LINK: "+aData.getUrl());
		LOG.info("IMG : "+aData.getImage());
		LOG.info(">>> Article Text : " + text.length() + " bytes in " + (System.currentTimeMillis() - startTime)
				+ " ms!");
		Summarizer summarizer = new DefaultSummarizer();
		Request.Builder builder = new Request.Builder();
		builder.setIgnoreSingleOccurences(false).setMaxLines(5).setLanguage(language);
		Request req = builder.build(text);

		Response resp = summarizer.summarize(req);
		LOG.info(">> Summary (upto " + req.getMaxLines() + " lines in " + resp.getTimeTakenMillis() + " ms)");
		LOG.info("\n -> " + Strings.join("\n -> ", resp.getLines()));
	}

}
