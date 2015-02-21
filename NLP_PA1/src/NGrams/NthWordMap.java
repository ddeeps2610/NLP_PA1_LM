package NGrams;

import java.util.HashMap;

public class NthWordMap 
{
	private int totalTokens;
	private HashMap<String, NthWord> nthWords;
	
	public int getTotalTokens() {
		return totalTokens;
	}
	public void setTotalTokens(int totalTokens) {
		this.totalTokens = totalTokens;
	}
	
	public HashMap<String, NthWord> getNthWords() {
		return nthWords;
	}
	public void setNthWords(HashMap<String, NthWord> nthWords) {
		this.nthWords = nthWords;
	}
	public void put(String word, NthWord nthWord)
	{
		this.nthWords.put(word, nthWord);
		this.totalTokens += nthWord.getCount();
	}
	
}
