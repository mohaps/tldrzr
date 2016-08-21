# TL;DRzr - A simple algorithmic summarizer
Website: https://tldrzr.herokuapp.com
Author: Saurav Mohapatra (mohaps@gmail.com)

Copyright (c) 2013, Saurav Mohapatra
All rights reserved.

## License

Redistribution and use in source and binary forms, with or without modification, are permitted 
provided that the following conditions are met:

	a)  Redistributions of source code must retain the above copyright notice, 
		this list of conditions and the following disclaimer.

	b)  Redistributions in binary form must reproduce the above copyright notice, 
		this list of conditions and the following disclaimer in the documentation 
		and/or other materials provided with the distribution.
	
	c)  Neither the name of TL;DRzr nor the names of its contributors may be used 
		to endorse or promote products derived from this software without specific 
		prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, 
BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

## Introduction

TL;DRzr (Pronounced as _tee-el-dee-rai-zer_) is a simple algorithmic summary generator written in java. It's deployed as a heroku app accessible at https://tldrzr.herokuapp.com

I wrote this as a weekend hack / fun project. Feel free to use the code as you please. Would love a mention or a line back at mohaps AT gmail DOT com if you find this useful. My twitter is [@mohaps](https://twitter.com/mohaps) and my blog is at http://mohaps.com

I'm currently working on the v2 of this project with better extraction, modularization and updates to use Java 8 features.

Join the Hacker News Discussion About TL;DRzr : https://news.ycombinator.com/item?id=5523538

Hacker News Post about the source code release : https://news.ycombinator.com/item?id=5535827


## Prerequisites (for Build)

JDK 1.6+
Apache Maven 2.x+

## Running the application locally

First build with:

    $mvn clean install

Then run it with:

    $java -cp target/classes:target/dependency/* com.mohaps.tldr.Main


## How does it work?

TL;DRzr uses an algorithm derived from Classifier4J. I used the basic algo from Classifier4j, optimized it and added some refinements.

The basic algorithm for summarization is like this. It first tokenizes the text into words and then calculates the top N most frequent words (discarding stop words and single occurence words). It then scans the sentences and gets the first N sentences which feature any or all of the most frequent words. The sentences are sorted based on first occurence in original text and concatenated to create the summary. The user has control over how long the generated summary should be in terms of sentence count.

For implementation details a good starting point is the [Summarizer](https://github.com/mohaps/tldrzr/blob/master/src/main/java/com/mohaps/tldr/summarize/Summarizer.java) class.

TL;DRzr is written in Java and uses Jsoup for html text scraping, ROME for RSS Feed parsing (which depends on JDOM). The parsing of sentences and word tokenization uses OpenNLP. It uses the Porter2 stemmer algorithm from here to process the tokens emitted by the tokenizer. The new summarize any url feature uses BoilerPipe

## Credits

TL;DRzr is a weekend project/quick hack demo created by Saurav Mohapatra. I wrote this as a fun weekend hack after reading about the Summly acquisition by Yahoo!. I had drunk too many Red Bulls and sleep was not too forthcoming. :) I always wished to try out Heroku and after a couple of hours of googling + coding, I put this together.

The algorithm is a keyword density based one. As this is my current hobby project, I shall work on improving the algorithm. I plan on opensourcing this codebase on github..
