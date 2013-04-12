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

import java.util.*;
/**
 * A summary cache (psuedo-LRU, doesn't refresh keys)
 * In a real world service, this would be in memcached/redis type store.
 * @author mohaps
 *
 */
public final class SummaryCache {
	public static final int MAX_CACHE_SIZE = 40;
	private static final SummaryCache sInstance = new SummaryCache();
	public static final SummaryCache instance() { return sInstance; }
	static final class Key {
		private byte[] key;
		Key(byte[] key) { this.key = key; }
		public boolean equals(Object o) {
			if(o != null && o instanceof Key) {
				return Arrays.equals(((Key)o).key, key);
			}
			return false;
		}
		public int hashCode() { return Arrays.hashCode(key); }
	}
	private Map<Key, String> cache = new LinkedHashMap<Key, String>();
	public void put(byte[] textHash, String summary) {
		cache.put(new Key(textHash), summary);
		if(cache.size() > MAX_CACHE_SIZE + 10) {
			while(cache.size() > MAX_CACHE_SIZE){
				cache.remove(cache.keySet().iterator().next());
			}
		}
	}
	public String get(byte[] inputHash) {
		
		return cache.get(new Key(inputHash));
	}
}
