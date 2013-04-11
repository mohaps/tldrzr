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

import java.security.MessageDigest;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.mohaps.tldr.utils.Words;

public class Summarizer implements ISummarizer {
	private IStopWords stopWords;
	private ITokenizer tokenizer;

	public Summarizer(IStopWords stopWords, ITokenizer tokenizer) {
		this.stopWords = stopWords;
		this.tokenizer = tokenizer;
	}

	public String summarize(final String inputRaw, int sentenceCount,
			int maxFrequentWords, boolean shouldIgnoreSingleOccurences)
			throws Exception {

		// for short bursts just return the input itself
		if (inputRaw.length() < sentenceCount * Defaults.AVG_WORDS_PER_SENTENCE) {
			return inputRaw;
		} else {

			// check summary cache for input hit
			byte[] inputHash = sha1(inputRaw, ":sentences=", Integer.toString(sentenceCount));
			String cached = SummaryCache.instance().get(inputHash);
			if (cached != null) {
				return cached;
			} else {
				// change U.S. to US etc.
				final String input = Words.dotCorrection(inputRaw);
				// get top 100 most frequent words that are not stop words
				Set<String> frequentWords = Words.getMostFrequent(input,
						tokenizer, stopWords, maxFrequentWords,
						shouldIgnoreSingleOccurences?2:1);
				// now let's get the unique sentences
				Set<String> sentences = Words.parseSentences(input, tokenizer,
						Defaults.MIN_WORDS_PER_SENTENCE);

				// hashmap to cache sentence indices
				final HashMap<String, Integer> sentenceIndex = new HashMap<String, Integer>();
				// we'll sort the sentences based on their appearance in the
				// input
				// text
				Set<String> outputSentences = new TreeSet<String>(
						new Comparator<String>() {
							public int compare(String sentence1,
									String sentence2) {
								int index1 = -1;
								int index2 = -1;
								// check cache for sentence 1
								Integer index1Obj = sentenceIndex
										.get(sentence1);
								if (index1Obj == null) {
									index1 = input.indexOf(sentence1);
									sentenceIndex.put(sentence1, new Integer(
											index1));
								} else {
									index1 = index1Obj.intValue();
								}
								// check cache for sentence 2
								Integer index2Obj = sentenceIndex
										.get(sentence2);
								if (index2Obj == null) {
									index2 = input.indexOf(sentence2);
									sentenceIndex.put(sentence2, new Integer(
											index2));
								} else {
									index2 = index2Obj.intValue();
								}

								return index1 - index2;
							}
						});

				// now look through the sentences and build summary ( not
				// exceeding
				// sentenceCount sentences )
				Iterator<String> iter = sentences.iterator();
				while (iter.hasNext()
						&& outputSentences.size() < sentenceCount) {
					String actualSentence = iter.next();
					String workingSentence = actualSentence.toLowerCase();
					Iterator<String> words = frequentWords.iterator();
					while (words.hasNext()) {
						String word = words.next();
						if (workingSentence.indexOf(word) >= 0) {
							outputSentences.add(actualSentence);
						}
						if (outputSentences.size() >= sentenceCount) {
							break;
						}
					}

				}
				// clear the sentence index cache
				sentenceIndex.clear();
				// build the paragraph
				StringBuilder sb = new StringBuilder();
				Iterator<String> summarySentences = outputSentences.iterator();
				while (summarySentences.hasNext()) {
					sb.append(summarySentences.next()).append(".");
					if (summarySentences.hasNext()) {
						sb.append(" ");
					}
				}
				// bob's your uncle :)
				String summary = sb.toString();
				// update summary cache
				SummaryCache.instance().put(inputHash, summary);
				return summary;
			}
		}
	}

	public static final byte[] sha1(String ...inputs) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		for(String input :  inputs) {
			md.update(input.getBytes());
		}
		return md.digest();
	}

	public String summarize(final String input, int sentenceCount)
			throws Exception {
		return summarize(input, sentenceCount,
				Defaults.MAX_MOST_FREQUENT_WORDS,
				Defaults.SHOULD_IGNORE_SINGLE_OCCURENCES);

	}

	public Set<String> keywords(String input, int maxKeyWords) throws Exception {

		return Words.getMostFrequent(input,
				tokenizer, stopWords, maxKeyWords,
				4);
	}

}
