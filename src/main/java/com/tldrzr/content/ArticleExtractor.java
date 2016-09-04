package com.tldrzr.content;

import com.gravity.goose.Article;
import com.gravity.goose.Configuration;
import com.gravity.goose.Goose;
import com.tldrzr.util.Streams;

public class ArticleExtractor {
	public static final String extractText(String urlString) throws Exception {
		String html = Streams.fetchURL(urlString);
		Configuration config = new Configuration();
		config.setEnableImageFetching(false);
		Goose goose = new Goose(config);
		Article article = goose.extractContent(urlString, html);
		return article.cleanedArticleText();
	}
}
