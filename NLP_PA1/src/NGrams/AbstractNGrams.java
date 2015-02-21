/**
 * 
 */
package NGrams;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Deepak
 *
 */
public abstract class AbstractNGrams implements INGram 
{
	protected HashMap<String, Integer> nGramCounts;
	protected HashMap<String, Float> nGramProbabilities;
	

	/* (non-Javadoc)
	 * @see NGrams.INGram#countNGram(java.util.LinkedList)
	 */
	@Override
	public abstract void countNGram(LinkedList<String> corpus);

	/* (non-Javadoc)
	 * @see NGrams.INGram#computeNGramProbabilities(java.util.LinkedList)
	 */
	@Override
	public final void computeNGramProbabilities(LinkedList<String> corpus) 
	{
		double sumOfProbability = 0;
		int zeroProbCount = 0;
		// Count the occurrences of each token type
		if (this.nGramCounts == null)
			this.countNGram(corpus);
		
		// Calculate the total number of tokens
		int totalTokens = this.getTotalTokens();
		
		if(this.nGramProbabilities == null)
			this.nGramProbabilities = new HashMap<String, Float>();
		
		// Calculate the probability of each token type in the corpus
		for(Map.Entry<String, Integer> entry : this.nGramCounts.entrySet())
		{
			this.nGramProbabilities.put(entry.getKey(), ((float) entry.getValue()/totalTokens));
			sumOfProbability += (float) entry.getValue()/totalTokens;
			if(entry.getValue() == 0)
				zeroProbCount++;
		}
		System.out.println("Sum of all probabilities : " + sumOfProbability);
		System.out.println("Count of elements with zero probability : "+zeroProbCount);
	}
	
	public final void laplaceSmoothing(LinkedList<String> corpus) 
	{
		double sumOfProbability = 0 ;
		
		System.out.println("Performing Laplace smoothing..!!");
		if(this.nGramCounts == null)
			this.countNGram(corpus);
		
		int totalTokens = this.getTotalTokens();
		
		for(Map.Entry<String, Integer> entry : this.nGramCounts.entrySet())
		{
			this.nGramProbabilities.put(entry.getKey(), ((float) (entry.getValue()+1)/(totalTokens+this.nGramCounts.size())));
			sumOfProbability += (float) (entry.getValue()+1)/(totalTokens+this.nGramCounts.size());
		}
		System.out.println("Sum of all probabilities with laplace smoothing : " + sumOfProbability);
	}
	
	public final double calculatePerplexity() 
	{
		System.out.println("\nCalculating perplexity..!!");
		double perplexity = 0;
		for(Map.Entry<String, Float> entry : this.nGramProbabilities.entrySet())
		{
			perplexity +=Math.log(entry.getValue()); 
		}
		//System.out.println("Pre Perplexity: "+ perplexity + ": " + this.nGramProbabilities.size());
		perplexity = -1 * perplexity / this.nGramProbabilities.size();
		//System.out.println("Pre antilog: "+perplexity);
		return Math.pow(Math.E, (perplexity));
	}
	
	private final int getTotalTokens()
	{
		int count = 0;
		
		if(this.nGramCounts != null)
		{
			for(Map.Entry<String, Integer> entry : this.nGramCounts.entrySet())
		    {
		    	count += entry.getValue();
		    }
		}
	    return count;
	}


	/* (non-Javadoc)
	 * @see NGrams.INGram#printNGramProbabilities()
	 */
	@Override
	public final void printNGramProbabilities() 
	{
		if(this.nGramProbabilities != null)
		{
			for(Entry<String, Float> entry : this.nGramProbabilities.entrySet())
			{
				System.out.println(entry.getKey() + " : " + entry.getValue());
			}
		}
	}

}
