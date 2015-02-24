package NGrams;
import java.util.LinkedList;
import java.util.List;

import preProcessor.Email;

/**
 * 
 */

/**
 * @author Deepak
 *
 */
public interface INGram 
{
	public void countNGram(LinkedList<String> corpus);
	public void computeNGramProbabilities(LinkedList<String> corpus);
	public void printNGramProbabilities();
	public String generateRandomSentence(List<String> corpus);
	public void laplaceSmoothing(LinkedList<String> corpus);
	public double calculatePerplexity(LinkedList<String> corpus);
	public double calculateEmailProbability(Email email);
	public void deepakSmoothing(LinkedList<String> corpus);
}
