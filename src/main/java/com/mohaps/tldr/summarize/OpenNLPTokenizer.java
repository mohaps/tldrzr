package com.mohaps.tldr.summarize;

import java.io.IOException;
import java.io.InputStream;

import com.mohaps.tldr.utils.Words;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class OpenNLPTokenizer implements ITokenizer {

	private static TokenizerModel TOKENIZER_MODEL;
	private static RegExTokenizer FALLBACK;
	static {
		try { 
			InputStream inputFile = Words.class.getClassLoader().getResourceAsStream("en-token.bin");
			if(inputFile != null) {
				try {
					TOKENIZER_MODEL = new TokenizerModel(inputFile);
					System.out.println(">> OpenNLP Tokenizer Model loaded!");
				} finally {
					if(inputFile != null) {
						try { inputFile.close(); } catch (Throwable t){}
					}
				}
			}
		} catch (IOException ex) {
			System.err.println("Failed to load token model for OpenNLP. error = "+ex.getLocalizedMessage()+". Will fall back to regex based token parsing");
			ex.printStackTrace();
			FALLBACK = new RegExTokenizer();
		}
	}

	public String[] tokenize(String input) throws Exception {
		if(TOKENIZER_MODEL != null) {
			Tokenizer tokenizer = new TokenizerME(TOKENIZER_MODEL);
			return tokenizer.tokenize(input);
		} else {
			return FALLBACK.tokenize(input);
		}
	}

}
