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

Join the Hacker News Discussion About TL;DRzr : https://news.ycombinator.com/item?id=5523538

Hacker News Post about the source code release : https://news.ycombinator.com/item?id=5535827

## Version History
* **v0.0.1** - the first/original version of TLDR;ZR  `git clone git@github.com:mohaps/tldrzr.git -b v0.0.1`
* **v0.2.0** - the WIP version for v0.2.0 which leverages Java 8 and a modular design with improved extraction, summarization etc. `git clone git@github.com:mohaps/tldrzr.git -b v0.2.0`

## Prerequisites (for Build)

JDK 1.8+
Apache Maven 2.x+

## How it works

`TL;DRzr` uses an algorithm derived from `Classifier4J`. I used the basic algo from Classifier4j, optimized it and added some refinements.

The basic algorithm for summarization is like this. It first tokenizes the text into words and then calculates the top N 
most frequent words (discarding stop words and single occurence words). It then scans the sentences and gets the first N 
sentences which feature any or all of the most frequent words. The sentences are sorted based on first occurence in original 
text and concatenated to create the summary. The user has control over how long the generated summary should be in 
terms of sentence count.

For implementation details a good starting point is the 
[Summarizer](https://github.com/mohaps/tldrzr/blob/master/src/main/java/com/mohaps/tldr/summarize/Summarizer.java) class.

`TL;DRzr` is written in Java and uses `Jsoup` for html text scraping, `ROME` for RSS Feed parsing (which depends on JDOM). 
The parsing of sentences and word tokenization uses `OpenNLP`. 

It uses the `Porter2` stemmer algorithm from here to process the tokens emitted by the tokenizer. 

The `v0.0.1` used `BoilerPipe` for article extraction. `v0.2.0` uses the imporved article extraction from [XTractor](https://github.com/mohaps/xtractor)

## Running the application locally

First build with:

    `$mvn clean install`

Then run it with:

    `$java -cp target/classes:target/dependency/* com.mohaps.tldr.Main`


## Credits

Original version of TL;DRzr was a weekend project/quick hack demo created by [Saurav Mohapatra](https://github.com/mohaps). It was written as a fun weekend 
hack after reading about the Summly acquisition by Yahoo!. 

The `v0.2.0` is a redesign and reimplementation of the original version (available in the branch `v0.0.1`)

