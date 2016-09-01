package com.tldrzr.util;

public final class Strings {
	public static final String BLANK = "";

	public static final String join(String delimiter, String... strings) {
		if (strings == null || strings.length == 0) {
			return BLANK;
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		boolean useDelimiter = isValidString(delimiter);
		for (String s : strings) {
			if (first) {
				first = false;
			} else if (useDelimiter) {
				sb.append(delimiter);
			}
			sb.append(s);
		}
		return sb.toString();
	}

	public static final boolean isValidString(String s) {
		return !(s == null || s.isEmpty());
	}
}
