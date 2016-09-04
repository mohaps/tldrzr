package com.tldrzr.summarizer;

import com.tldrzr.nlp.Engine;

public class DefaultSummarizer implements Summarizer {

	@Override
	public Response summarize(Request request) throws Exception {
		Response.Builder builder = new Response.Builder();
		long startTimeMillis = System.currentTimeMillis();
		try {
			Engine engine = new Engine(request.getLanguage());
			String[] summary = engine.summarize(request.getText(),request.getMaxLines(), request.isIgnoreSingleOccurences());
			builder.addLines(summary);
		} finally {
			builder.setTimeTakenMillis(System.currentTimeMillis() - startTimeMillis);
		}
		return builder.build();
	}

}
