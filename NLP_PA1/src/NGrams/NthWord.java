package NGrams;

import java.util.Comparator;

public class NthWord 
{
	private String word;
	private double count;
	private double probability;
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}	
}

class NthWordComparator implements Comparator<NthWord> {
	@Override
	public int compare(NthWord word1, NthWord word2) {
		if (word1.getCount() < word2.getCount()) {
			return 1;
		} else if (word1.getCount() > word2.getCount()) {
			return -1;
		}
		return 0;
	}
}