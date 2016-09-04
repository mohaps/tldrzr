package com.tldrzr.nlp;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Sentence {
	private String text;
	private int index;
	private int score;
	public Sentence(String text, int index) {
		this(text, index, 0);
	}
	public Sentence(String text, int index, int score) {
		this.text = text;
		this.index = index;
		this.score = score;
	}

	public void incrementScore(int count) {
		score += count;
	}

	public String getText() {
		return text;
	}

	public int getScore() {
		return score;
	}

	public int getIndex() {
		return index;
	}

	public static class IndexComparator implements Comparator<Sentence> {

		@Override
		public int compare(Sentence o1, Sentence o2) {
			return o1.index - o2.index;
		}

	}

	public static class ScoreComparator implements Comparator<Sentence> {

		@Override
		public int compare(Sentence o1, Sentence o2) {
			return o2.score - o1.score;
		}

	}
	
	public static final void main(String[] args) {
		Set<Sentence> byScore = new TreeSet<Sentence>(new ScoreComparator());
		Set<Sentence> byIndex = new TreeSet<Sentence>(new IndexComparator());
		
		{
			Sentence s1 = new Sentence("second sentence", 1, 2);
			byScore.add(s1);
			byIndex.add(s1);
		}
		{
			Sentence s1 = new Sentence("third sentence", 2, 3);
			byScore.add(s1);
			byIndex.add(s1);
		}
		{
			Sentence s1 = new Sentence("first sentence", 0, 1);
			byScore.add(s1);
			byIndex.add(s1);
		}
		
		System.out.println(">> ----- by score ----- <<");
		for (Sentence s : byScore) {
			System.out.println(">> "+s.getText());
		}
		System.out.println(">> ----- by index ----- <<");
		for (Sentence s : byIndex) {
			System.out.println(">> "+s.getText());
		}
		
		
	}
}
