package com.mohaps.tldr.summarize;

import java.util.*;

public final class SummaryCache {
	public static final int MAX_CACHE_SIZE = 20;
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
