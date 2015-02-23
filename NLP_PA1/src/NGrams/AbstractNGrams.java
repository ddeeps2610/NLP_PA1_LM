/**
 * 
 */
package NGrams;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

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
				double count = 0;
				// Count the total occurences of the given (n-1) words
				for(Map.Entry<String, NthWord> nThWordEntry : nGramEntry.getValue().entrySet())
				{
					count += nThWordEntry.getValue().getCount();
				}
				
				// Calculate and assign probabilities
				for(Map.Entry<String, NthWord> nThWordEntry : nGramEntry.getValue().entrySet())
				{
					double occurrence = nThWordEntry.getValue().getCount();
					nThWordEntry.getValue().setProbability(occurrence/count);
				}
			}
		}	
	}
	
	
	@SuppressWarnings("rawtypes")
	protected List<String> tokenizeSentence (String sentence1)
	{
		List<String> retVal = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		PTBTokenizer ptbt = new PTBTokenizer(new StringReader(sentence1), new CoreLabelTokenFactory(), "");
	      for (CoreLabel label; ptbt.hasNext(); ) {
	        label = (CoreLabel) ptbt.next();
	        retVal.add(label.value());
	      }
		return retVal;
	}
	protected double getUnknownProb(String history) 
	{
		
		double N1 = 0 ; // = this.calculateUnknownCount(history);
		double count = 0;
		
		
		// Count the total occurrences of the given (n-1) words
		if(!this.nGramMap.containsKey(history))
			return 0.0;
	//for(Map.Entry<String, HashMap<String, NthWord>> nGramEntry : this.nGramMap.entrySet())
		for(Map.Entry<String, NthWord> nThWordEntry : this.nGramMap.get(history).entrySet())
		{
			count += nThWordEntry.getValue().getCount();
			if(nThWordEntry.getValue().getCount() == 1) 
				N1++;
		}
		return (N1/count);
	}
	
	public final void laplaceSmoothing(LinkedList<String> corpus) 
	{
		System.out.println("\nPerforming Laplace smoothing..!!");
		if(this.nGramMap == null)
			this.countNGram(corpus);
		
		// Calculate the probability of each token type in the corpus
		for(Map.Entry<String, HashMap<String, NthWord>> nGramEntry : this.nGramMap.entrySet())
		{
			if((nGramEntry.getValue() != null) && (!nGramEntry.getValue().isEmpty()))
			{
				double count = 0;
				// Count the total occurrences of the given (n-1) words
				for(Map.Entry<String, NthWord> nThWordEntry : nGramEntry.getValue().entrySet())
				{
					count += nThWordEntry.getValue().getCount();
				}
				
				// Calculate and assign probabilities
				for(Map.Entry<String, NthWord> nThWordEntry : nGramEntry.getValue().entrySet())
				{
					double occurrence = nThWordEntry.getValue().getCount();
					nThWordEntry.getValue().setProbability((occurrence+1)/(count+nGramEntry.getValue().size()));
				}
			}
		}		
	}
	
	public final void deepakSmoothing(LinkedList<String> corpus) 
	{
		System.out.println("\nPerforming Deepak smoothing..!!");
		if(this.nGramMap == null)
			this.countNGram(corpus);
		
		
		// Calculate the probability of each token type in the corpus
		for(Map.Entry<String, HashMap<String, NthWord>> nGramEntry : this.nGramMap.entrySet())
		{
			
			if((nGramEntry.getValue() != null) && (!nGramEntry.getValue().isEmpty()))
			{
				double unknownCount = this.calculateUnknownCount(nGramEntry.getKey());
				
				double count = 0;
				// Count the total occurrences of the given (n-1) words
				for(Map.Entry<String, NthWord> nThWordEntry : nGramEntry.getValue().entrySet())
				{
					count += nThWordEntry.getValue().getCount();
				}
				
				// Calculate and assign probabilities
				for(Map.Entry<String, NthWord> nThWordEntry : nGramEntry.getValue().entrySet())
				{
					double occurrence = nThWordEntry.getValue().getCount()* (count - unknownCount)/count;
					nThWordEntry.getValue().setCount(occurrence);
					nThWordEntry.getValue().setProbability(occurrence/count);
				}
			}
		}		
	}
	
	private double calculateUnknownCount(String key)
	{		
		System.out.println("Get Unknown Count");
		double N1 = 0;
		if (this.nGramMap.containsKey(key))
		{
					
			for(Map.Entry<String,NthWord> Ngram : this.nGramMap.get(key).entrySet())
			{
				System.out.println(Ngram.getValue().getWord() + ": "+ Ngram.getValue().getCount());
				
				if (Ngram.getValue().getCount() == 1)
					N1 += 1;					
				
			}
		}
		
		return N1;
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
		System.out.println("Token types: " + count);
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
		double totalProb = 0.0;
		if(this.nGramMap != null)
		{
			for(Map.Entry<String, HashMap<String, NthWord>> entry : this.nGramMap.entrySet())
		    {
		    	for(Map.Entry<String, NthWord> nthWordENtry : entry.getValue().entrySet())
		    	{
		    		totalProb += nthWordENtry.getValue().getProbability();
		    		System.out.println(entry.getKey()+" " +nthWordENtry.getKey() + " : " + nthWordENtry.getValue().getProbability());
		    		
		    	}
		    }			
		}
		System.out.println("Total Probability: " + totalProb);
	}

}
