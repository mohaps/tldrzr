package com.mohaps.tldr.summarize;

public class RegExTokenizer implements ITokenizer {
	private String tokenRegEx;
	public RegExTokenizer() {
		this(Defaults.REGEX_WORDS);
	}
	public RegExTokenizer(String tokenRegEx) {
		this.tokenRegEx = tokenRegEx;
		if(this.tokenRegEx == null) {
			this.tokenRegEx = Defaults.REGEX_WORDS;
		}
	}
	public String[] tokenize(String input) throws Exception{
		if(input == null || input.length() == 0){
			return Defaults.BLANK_STRING_ARRAY;
		} else {
			return input.split(tokenRegEx);
		}
	}
	public String toString() {
		return new StringBuilder("RegExTokenizer(regex=\"").append(tokenRegEx).append("\"").toString();
	}

}
