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
package com.tldrzr.nlp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tldrzr.util.Strings;
import com.tldrzr.util.Streams;

public final class Languages {
	private static final Logger LOG = LoggerFactory.getLogger(Languages.class);
	private static final Map<String, Language> languages = new HashMap<String, Language>();
	static {
		try {
			load();
		} catch (Exception ex) {
			LOG.warn(">>> failed to load default language list. error = " + ex.getLocalizedMessage());
			languages.put(Language.Names.getDefault(), new Language(Language.Names.getDefault()));
		}
	}

	public static void load() throws Exception {
		load("languages.properties");
	}

	public static void load(String fileName) throws Exception {
		InputStream fin = null;
		try {
			fin = Languages.class.getClassLoader().getResourceAsStream(fileName);
			if (fin == null) {
				throw new IOException("couldn't get " + fileName + " as stream");
			}
			load(fin);
		} finally {
			Streams.close(fin);
		}

	}

	public static void load(InputStream input) throws Exception {
		languages.clear();
		Properties props = new Properties();
		props.load(input);
		for (Object o : props.keySet()) {
			String name = (String) o;
			String description = (String) props.getProperty(name, name);
			languages.put(name, new Language(name, description));
		}

		if (languages.containsKey(Language.Names.getDefault())) {
			languages.put(Language.Names.getDefault(), new Language(Language.Names.getDefault(), Language.Names.getDefaultDescription()));
		}
	}

	public static Language getDefault() {
		return languages.get(Language.Names.getDefault());
	}

	public static Language get(String name) throws Exception {
		if (!Strings.isValidString(name)) {
			return get(Language.Names.getDefault());
		}
		return languages.get(name);
	}

	public static Collection<String> getAvailableLanguageNames() {
		return languages.keySet();
	}

	public static Collection<Language> getAvailableLanguages() {
		return languages.values();
	}

	public static void main(String[] args) {
		Collection<String> names = Languages.getAvailableLanguageNames();
		LOG.info(">> got " + names.size() + " language names!");
		for (String s : names) {
			LOG.info(" >>> " + s);
		}
		
		Collection<Language> languages = Languages.getAvailableLanguages();
		LOG.info(">> got " + names.size() + " languages!");
		for (Language language : languages) {
			LOG.info(" >>> " + language);
		}
	}
}
