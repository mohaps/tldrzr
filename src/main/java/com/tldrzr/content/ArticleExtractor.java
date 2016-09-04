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
package com.tldrzr.content;

import com.gravity.goose.Article;
import com.gravity.goose.Configuration;
import com.gravity.goose.Goose;
import com.tldrzr.util.Web;

/**
 * A Goose based article extractor
 * 
 * @author mohaps
 *
 */
public class ArticleExtractor {
	private static String IMAGE_MAGICK_PATH;
	static {
	try {
		IMAGE_MAGICK_PATH = System.getProperty("tldrzr.imagemagick.path");
	} catch (Exception ex) {
	} finally {
		if (IMAGE_MAGICK_PATH == null) {
			IMAGE_MAGICK_PATH = "/usr/local/bin";
		}
	}
	}

	public static final String extractText(String urlString) throws Exception {
		return extractArticleFromURL(urlString, false).getText();
	}

	public static final ArticleData extractArticleFromURL(String url, boolean fetchImages) throws Exception {
		String rawHTML = Web.fetchHTML(url);
		Configuration config = new Configuration();
		config.setEnableImageFetching(fetchImages);
		if (fetchImages) {
			config.setImagemagickConvertPath(IMAGE_MAGICK_PATH + "/convert");
			config.setImagemagickIdentifyPath(IMAGE_MAGICK_PATH + "/identify");
		}
		Goose goose = new Goose(config);
		Article article = goose.extractContent(url, rawHTML);
		return new ArticleData(article.title(), article.topImage().imageSrc(), article.canonicalLink(),
				article.cleanedArticleText());
	}
}
