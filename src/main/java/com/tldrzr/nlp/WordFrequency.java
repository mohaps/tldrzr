package com.tldrzr.nlp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.tldrzr.util.Strings;

public class WordFrequency {
	public static final class Entry implements Comparable<Entry> {
		private String word;
		private int count;

		public Entry(String word) {
			this(word, 1);
		}

		public Entry(String word, int count) {
			this.word = word;
			this.count = count;
		}

		void increment(int count) {
			if (count > 0) {
				this.count += count;
			}
		}

		public String getWord() {
			return word;
		}

		public int getCount() {
			return count;
		}

		@Override
		public int compareTo(Entry e) {
			int c = count - e.count;
			if (c != 0) {
				return c;
			}
			return word.compareTo(e.word);
		}

		@Override
		public boolean equals(Object o) {
			if (o == null) {
				return false;
			}
			if (o instanceof Entry) {
				return word.equals(((Entry) o).word);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return word.hashCode();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(word).append("(").append(count).append(")");
			return sb.toString();
		}
	}

	private Map<String, Entry> table;

	public WordFrequency() {
		this.table = new HashMap<String, Entry>();
	}

	public void add(String word) {
		if (!Strings.isValidString(word)) {
			return;
		}
		Entry e = table.get(word);
		if (e == null) {
			table.put(word, new Entry(word));
		} else {
			e.increment(1);
		}
	}
	public Entry get(String word) {
		if (!Strings.isValidString(word)) { return null; }
		return table.get(word);
	}
	public int getCount(String word) {
		if (!Strings.isValidString(word)) {
			return 0;
		}
		Entry e = table.get(word);
		return e == null ? 0 : e.count;
	}

	public int size() {
		return table.size();
	}

	public void clear() {
		table.clear();
	}

	public boolean contains(String word) {
		return contains(word, false);
	}

	public boolean contains(String word, boolean ignoreSingleOccurence) {
		if (!Strings.isValidString(word)) {
			return false;
		}
		if (!ignoreSingleOccurence) {
			return table.containsKey(word);
		}
		Entry e = table.get(word);
		if (e == null) {
			return false;
		}
		return e.getCount() > 1;
	}

	public List<String> getTopN(int count, boolean ignoreSingleOccurence) {
		Set<Entry> ret = new TreeSet<Entry>(new Comparator<Entry>() {
			public int compare(Entry e1, Entry e2) {
				return e2.compareTo(e1);
			}
		});
		ret.addAll(table.values());
		int limit = Math.min(count, ret.size());
		if (limit == 0) {
			return Collections.unmodifiableList(new ArrayList<String>(0));
		}
		List<String> top = new ArrayList<String>(limit);
		Iterator<Entry> iter = ret.iterator();
		int retCount = 0;
		while (retCount < limit && iter.hasNext()) {
			Entry e = iter.next();
			if (ignoreSingleOccurence && e.getCount() <= 1) {
				continue;
			}
			top.add(e.getWord());
			++retCount;
		}
		return Collections.unmodifiableList(top);

	}

	public static void main(String[] args) throws Exception {
		WordFrequency freq = new WordFrequency();
		freq.add("one");
		freq.add("two");
		freq.add("two");
		freq.add("three");
		freq.add("three");
		freq.add("three");

		List<String> top = freq.getTopN(3, true);
		for (String s : top) {
			System.out.println(">>> " + s);
		}

		System.out.println("Contains(one, ignoreSingleOccurence = true) => " + freq.contains("one", true));
		System.out.println("Contains(one, ignoreSingleOccurence = false) => " + freq.contains("one", false));
	}

}
