/**
 * 
 */
package NGrams;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Deepak
 *
 */
public abstract class AbstractNGrams implements INGram 
{
	protected HashMap<String, HashMap<String, NthWord>> nGramMap;
	

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
		// If the corpus not created already, create it.
		if (this.nGramMap == null)
			this.countNGram(corpus);
		
		// Initialize the nGramMap
		if(this.nGramMap == null)
			this.nGramMap = new HashMap<String, HashMap<String,NthWord>>();
		
		// Calculate the probability of each token type in the corpus
		for(Map.Entry<String, HashMap<String, NthWord>> nGramEntry : this.nGramMap.entrySet())
		{
			if((nGramEntry.getValue() != null) && (!nGramEntry.getValue().isEmpty()))
			{
				int count = 0;
				// Count the total occurences of the given (n-1) words
				for(Map.Entry<String, NthWord> nThWordEntry : nGramEntry.getValue().entrySet())
				{
					count += nThWordEntry.getValue().getCount();
				}
				
				// Calculate and assign probabilities
				for(Map.Entry<String, NthWord> nThWordEntry : nGramEntry.getValue().entrySet())
				{
					int occurrence = nThWordEntry.getValue().getCount();
					nThWordEntry.getValue().setProbability(occurrence/count);
				}
			}
		}	
	}
	
	protected List<String> tokenizeSentence (String sentence)
	{
		return null;
	}
	
	public final void laplaceSmoothing(LinkedList<String> corpus) 
	{
		System.out.println("Performing Laplace smoothing..!!");
		if(this.nGramMap == null)
			this.countNGram(corpus);
		
		// Calculate the probability of each token type in the corpus
		for(Map.Entry<String, HashMap<String, NthWord>> nGramEntry : this.nGramMap.entrySet())
		{
			if((nGramEntry.getValue() != null) && (!nGramEntry.getValue().isEmpty()))
			{
				int count = 0;
				// Count the total occurences of the given (n-1) words
				for(Map.Entry<String, NthWord> nThWordEntry : nGramEntry.getValue().entrySet())
				{
					count += nThWordEntry.getValue().getCount();
				}
				
				// Calculate and assign probabilities
				for(Map.Entry<String, NthWord> nThWordEntry : nGramEntry.getValue().entrySet())
				{
					int occurrence = nThWordEntry.getValue().getCount();
					nThWordEntry.getValue().setProbability((occurrence+1)/(count+nGramEntry.getValue().size()));
				}
			}
		}		
	}
	
	public final double calculatePerplexity() 
	{
		System.out.println("\nCalculating perplexity..!!");
		double perplexity = 0;
		int count=0;
		
		for(Map.Entry<String, HashMap<String, NthWord>> nGramEntry : this.nGramMap.entrySet())
		{
			
			for(Map.Entry<String, NthWord> nThWordEntry : nGramEntry.getValue().entrySet())
			{
				perplexity += Math.log(nThWordEntry.getValue().getProbability());
				count++;
			}
		}
		
		//System.out.println("Pre Perplexity: "+ perplexity + ": " + this.nGramProbabilities.size());
		perplexity = -1 * perplexity / count;
		
		//System.out.println("Pre antilog: "+perplexity);
		return Math.pow(Math.E, (perplexity));
	}
	
	private final int getTotalTokens()
	{
		int count = 0;
		
		if(this.nGramMap != null)
		{
			for(Map.Entry<String, HashMap<String, NthWord>> entry : this.nGramMap.entrySet())
		    {
		    	for(Map.Entry<String, NthWord> nthWordENtry : entry.getValue().entrySet())
		    	{
		    		count += nthWordENtry.getValue().getCount();
		    	}
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
		if(this.nGramMap != null)
		{
			for(Map.Entry<String, HashMap<String, NthWord>> entry : this.nGramMap.entrySet())
		    {
		    	for(Map.Entry<String, NthWord> nthWordENtry : entry.getValue().entrySet())
		    	{
		    		System.out.println(nthWordENtry.getKey() + " : " + nthWordENtry.getValue().getProbability());
		    	}
		    }			
		}
	}

}
