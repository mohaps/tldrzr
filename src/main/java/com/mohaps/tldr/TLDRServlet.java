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
package com.mohaps.tldr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;

import com.mohaps.tldr.summarize.Defaults;
import com.mohaps.tldr.summarize.Factory;
import com.mohaps.tldr.utils.Feeds;
import com.mohaps.tldr.utils.Words;
import com.mohaps.tldr.utils.Feeds.Item;

import com.google.gson.stream.JsonWriter;

/**
 * The web endpoint for TL;DRzr service/API
 * @author mohaps
 *
 */
public class TLDRServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.length() == 0 || pathInfo.equals("/")) {
			String feedUrl = req.getParameter("feed_url");
			if (feedUrl != null && feedUrl.length() > 0) {
				summarizeFeedUrl(feedUrl, req, resp);
			} else {
				showHomePage(req, resp);
			}
		} else {
			resp.sendError(404, "Path " + pathInfo + " not found");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo != null && pathInfo.startsWith("/text")) {
			handleSummarizeTextCall(req, resp);
		} else if (pathInfo != null && pathInfo.startsWith("/feed")) {
			handleSummarizeFeedCall(req, resp);
		} else if (pathInfo != null && pathInfo.startsWith("/api")) {
			handleAPICall(req, resp);
		} else {
			resp.sendError(404, "could not locate POST endpoint " + pathInfo);
		}
	}

	// show the home page
	protected void showHomePage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.sendRedirect("/");
	}

	// handle an API call (currently it's just a post to /tldr/api/summarize that takes input_text and sentence_count (default: 5)
	protected void handleAPICall(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		if(req.getContentLength() > Defaults.MAX_API_INPUT_LENGTH) {
			resp.sendError(400, "Request is over API limit of "+Defaults.MAX_API_INPUT_LENGTH+" bytes");
			return;
		}
		String pathInfo = req.getPathInfo();
		if (pathInfo.startsWith("/api/summarize")) {
			String inputText = req.getParameter("input_text");
			int sentenceCount = Integer.parseInt(req
					.getParameter("sentence_count"));
			if (sentenceCount <= 0) {
				sentenceCount = Defaults.MAX_SENTENCES;
			}
			String summaryText = null;
			
			long start = System.currentTimeMillis();
			long millis = 0;
			try {
				if(inputText == null || inputText.length() == 0){ summaryText = ""; }
				else {
				summaryText = Factory.getSummarizer().summarize(inputText,
						sentenceCount);
				}
			} catch (Exception ex) {
				throw new IOException("Failed to summarize", ex);
			} finally {
				millis = System.currentTimeMillis() - start;
			}
			resp.setContentType("application/json");
			// serialize out as json
			JsonWriter writer = new JsonWriter(resp.getWriter());
			writer.beginObject();
			writer.name("summary_text").value(summaryText);
			writer.name("time_taken_millis").value(millis);
			writer.endObject();
			writer.flush();
			writer.close();
			resp.getWriter().flush();
			resp.getWriter().close();

		} else {
			resp.sendError(404, "API endpoint " + pathInfo + " not found!");
		}
	}

	// summarize a feed from a url (if the url is non feed, then try to extract article text)
	protected void handleSummarizeFeedCall(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		String feedUrl = req.getParameter("feed_url");
		summarizeFeedUrl(feedUrl, req, resp);
	}


	// summarize a feed from a url (if the url is non feed, then try to extract article text)
	protected void summarizeFeedUrl(String feedUrl, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		String contentType = Feeds.getContentType(feedUrl);
		// System.out.println(" >> Content Type ("+feedUrl+") -> "+contentType);
		if (contentType != null
				&& (contentType.startsWith("text/html") || contentType
						.startsWith("text/plain"))) {
			summarizePageText(feedUrl, req, resp);
		} else {
			String scStr = req.getParameter("sentence_count");
			int sentenceCount = scStr == null?Defaults.MAX_SENTENCES:Integer.parseInt(scStr);
			if(sentenceCount <= 0){ sentenceCount = Defaults.MAX_SENTENCES; }
			List<SummarizedFeedEntry> entries = new ArrayList<SummarizedFeedEntry>();
			long start = System.currentTimeMillis();
			long millis = 0;
			try {
				List<Feeds.Item> feedItems = Feeds.fetchFeedItems(feedUrl);
				for (Item item : feedItems) {
					String summary = Factory.getSummarizer().summarize(
							item.getText(), sentenceCount);
					// TODO: (mohaps) hook up keywords after stem word fix (currently working on this)
					// might have to wait till I get topic modelling integrated till I turn this back on

					//Set<String>  keywords = Factory.getSummarizer().keywords(item.getText(), 10);
					Set<String> keywords = null; 
					SummarizedFeedEntry entry = new SummarizedFeedEntry(
							item.getTitle(), item.getAuthor(), item.getLink(),
							item.getText(), summary, keywords);
					entries.add(entry);
				}
			} catch (Exception ex) {
				throw new IOException("Failed to summarize feed url : "
						+ feedUrl, ex);
			} finally {
				millis = System.currentTimeMillis() - start;
			}
			SummarizedFeed summarizedFeed = new SummarizedFeed(feedUrl,
					entries, millis);
			req.setAttribute("summarized_feed", summarizedFeed);
			req.getRequestDispatcher("/feed_summary.jsp").forward(req, resp);
		}
	}


	// summarize a page text from a url 
	protected void summarizePageText(String pageUrl, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		try {
			String inputText = Feeds.extractPageBodyText(pageUrl);
			String scStr = req.getParameter("sentence_count");
			int sentenceCount = scStr == null?5:Integer.parseInt(scStr);
			if(sentenceCount == 0){ sentenceCount = 5; }
			summarizeText(inputText, sentenceCount, req, resp);
		} catch (Exception ex) {
			resp.sendError(500, "Failed to get text from : " + pageUrl
					+ " error=" + ex.getLocalizedMessage());
		}
	}

	// summarize supplied text
	protected void summarizeText(String inputText, int sentenceCount,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String summaryText = null;
		long start = System.currentTimeMillis();
		long millis = 0;
		try {
			summaryText = Factory.getSummarizer().summarize(inputText,
					sentenceCount);
		} catch (Exception ex) {
			throw new IOException("Failed to summarize", ex);
		} finally {
			millis = System.currentTimeMillis() - start;
		}
		Summary summary = new Summary(inputText, summaryText,
				sentenceCount, millis);
		req.setAttribute("summary", summary);
		req.getRequestDispatcher("/text_summary.jsp").forward(req, resp);
	}

	// handle a summarize text call from the web
	protected void handleSummarizeTextCall(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		String inputText = req.getParameter("input_text");
		int sentenceCount = Integer
				.parseInt(req.getParameter("sentence_count"));
		summarizeText(inputText, sentenceCount, req, resp);
	}

}
