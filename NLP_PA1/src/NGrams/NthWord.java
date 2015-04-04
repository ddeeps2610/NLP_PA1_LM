package NGrams;

import java.util.Comparator;

/**
 * @author Deepak
 * DEfines the class for each nth word and stores the word along with count
 * and probabilitity of the word for the given history
 *
 */
public class NthWord 
{
	/************************* State ********************************/
	private String word;
	private double count;
	private double probability;
	
	/************************* Getters and Setters *******************/
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


/************************* Business Logic **************************/
/**
 * @author Ravi
 *
 */
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