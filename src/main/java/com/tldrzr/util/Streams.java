package com.tldrzr.util;

import java.io.IOException;
import java.io.InputStream;

public final class Streams {
	public static final void close(InputStream in) {
		try {
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {

		}
	}
}
