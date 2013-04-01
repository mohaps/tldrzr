package com.mohaps.tldr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mohaps.tldr.summarize.Defaults;
import com.mohaps.tldr.summarize.Factory;
import com.mohaps.tldr.utils.Feeds;
import com.mohaps.tldr.utils.Feeds.Item;

import com.google.gson.stream.JsonWriter;

public class TLDRServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		
		String pathInfo = req.getPathInfo();
		if(pathInfo == null || pathInfo.length() == 0) {
			showHomePage(req, resp);
		} else {
			resp.sendError(404, "Path "+pathInfo+" not found");
		}	
    }
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if(pathInfo != null && pathInfo.startsWith("/text")) {
			handleSummarizeTextCall(req, resp);
		} else if(pathInfo != null && pathInfo.startsWith("/feed")) {
			handleSummarizeFeedCall(req, resp);
		} else if(pathInfo != null && pathInfo.startsWith("/api")) {
			handleAPICall(req, resp);
		} else {
			resp.sendError(404, "could not locate POST endpoint "+pathInfo);
		}
	}
	protected void showHomePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("/");
	}
	protected void handleAPICall(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if(pathInfo.startsWith("/api/summarize")) {
			String inputText = req.getParameter("input_text");
			int sentenceCount = Integer.parseInt(req.getParameter("sentence_count"));
			if(sentenceCount <= 0) { sentenceCount = Defaults.MAX_SENTENCES; }
			String summaryText = null;
			long start = System.currentTimeMillis();
			long millis = 0;
			try {
				summaryText = Factory.getSummarizer().summarize(inputText, sentenceCount);
			} catch (Exception ex) {
				throw new IOException("Failed to summarize", ex);
			}finally {
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
			resp.sendError(404,"API endpoint "+pathInfo+" not found!");
		}
	}
	protected void handleSummarizeFeedCall(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String feedUrl = req.getParameter("feed_url");
		List<SummarizedFeedEntry> entries = new ArrayList<SummarizedFeedEntry>();
		long start = System.currentTimeMillis();
		long millis = 0;
		try {
			List<Feeds.Item> feedItems = Feeds.fetchFeedItems(feedUrl);
			for(Item item : feedItems) {
				String summary = Factory.getSummarizer().summarize(item.getText(), Defaults.SUMMARY_LENGTH);
				SummarizedFeedEntry entry = new SummarizedFeedEntry(item.getTitle(), item.getAuthor(), item.getLink(), item.getText(), summary);
				entries.add(entry);
			}
		} catch(Exception ex) {
			throw new IOException("Failed to summarize feed url : "+feedUrl, ex);
		} finally {
			millis = System.currentTimeMillis() - start;
		}
		SummarizedFeed summarizedFeed = new SummarizedFeed(feedUrl, entries, millis);
		req.setAttribute("summarized_feed", summarizedFeed);
		req.getRequestDispatcher("/feed_summary.jsp").forward(req, resp);
	}
	protected void handleSummarizeTextCall(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String inputText = req.getParameter("input_text");
		int sentenceCount = Integer.parseInt(req.getParameter("sentence_count"));
		String summaryText = null;
		long start = System.currentTimeMillis();
		long millis = 0;
		try {
			summaryText = Factory.getSummarizer().summarize(inputText, sentenceCount);
		} catch (Exception ex) {
			throw new IOException("Failed to summarize", ex);
		}finally {
			millis = System.currentTimeMillis() - start;
		}
		Summary summary = new Summary(inputText, summaryText, sentenceCount, millis);
		req.setAttribute("summary", summary);
		req.getRequestDispatcher("/text_summary.jsp").forward(req, resp);
	}
	protected void writeHello(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ServletOutputStream out = resp.getOutputStream();
        out.write("Hello Heroku".getBytes());
        out.flush();
        out.close();
	}
    
}
