package NGrams;
import java.util.LinkedList;

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
	public String generateRandomSentence();
	public void laplaceSmoothing(LinkedList<String> corpus);
	public double calculatePerplexity();
	public double calculateEmailProbability(Email email);
	public void deepakSmoothing(LinkedList<String> corpus);
}
