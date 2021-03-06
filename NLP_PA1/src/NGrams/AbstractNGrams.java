/**
 * 
 */
package NGrams;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

/**
 * @author Deepak
 * Defines the abstract class for the NGram Model that implements INGram interface.
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
	protected List<String> tokenizeSentence (String sentence)
	{
		List<String> retVal = new ArrayList<String>();
		String[] removeCharacters = {",", ".", "?", "'", "\"", ";", ":"};
		
		
		@SuppressWarnings("unchecked")
		PTBTokenizer ptbt = new PTBTokenizer(new StringReader(sentence), new CoreLabelTokenFactory(), "");
	      for (CoreLabel label; ptbt.hasNext(); ) {
	        label = (CoreLabel) ptbt.next();
	        boolean result = false;
	        for(String str : removeCharacters) {
	        	if(str.equalsIgnoreCase(label.value().trim())) {
	        		result = true;
	        		break;
	        	}
	        }
	        if(result) continue;
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
	
	/* (non-Javadoc)
	 * @see NGrams.INGram#laplaceSmoothing(java.util.LinkedList)
	 * @author Deepak
	 * Performs Add 1 or Laplace smoothing on the given corpus.
	 */
	public final void laplaceSmoothing(LinkedList<String> corpus) 
	{
		//System.out.println("\nPerforming Laplace smoothing..!!");
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
	
	/* (non-Javadoc)
	 * @see NGrams.INGram#deepakSmoothing(java.util.LinkedList)
	 * @author Deepak
	 * Performs smoothing on the given corpus to handle for the unknown words.
	 * For the unknown words, the probability is identified as the N1 count as per 
	 * Good-Turing algorithm. Then the probability mass is relatively distributed
	 * across other entries.
	 */
	public final void deepakSmoothing(LinkedList<String> corpus) 
	{
		//System.out.println("\nPerforming Deepak smoothing..!!");
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
					if(occurrence != 0)
					{
						nThWordEntry.getValue().setCount(occurrence);
						nThWordEntry.getValue().setProbability(occurrence/count);
					}
					
				}
			}
		}		
	}
	
	/**
	 * @param key This is the nGram history for the given ngram model
	 * @return N1 count - Number of nGrams with frequency 1 as per 
	 * Good-Turing algorithm
	 */
	private double calculateUnknownCount(String key)
	{		
		//System.out.println("Get Unknown Count");
		double N1 = 0;
		if (this.nGramMap.containsKey(key))
		{
					
			for(Map.Entry<String,NthWord> Ngram : this.nGramMap.get(key).entrySet())
			{
				//System.out.println(Ngram.getValue().getWord() + ": "+ Ngram.getValue().getCount());
				
				if (Ngram.getValue().getCount() == 1)
					N1 += 1;					
				
			}
		}
		
		return N1;
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
	 * @author Deepak
	 * Prints all NGram probabilities in the corpus
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

	
	public List<String> getFrequentNGrams(int count) {
		List<String> retVal = new ArrayList<String>();

		List<NthWord> tempList = new ArrayList<NthWord>();

		for(String key : this.nGramMap.keySet()) {
			for(String innerKey : this.nGramMap.get(key).keySet()) {
				NthWord word = new NthWord();
				word.setProbability(this.nGramMap.get(key).get(innerKey).getProbability()); 
				word.setCount(this.nGramMap.get(key).get(innerKey).getCount()); 
				word.setWord((key + " " + this.nGramMap.get(key).get(innerKey).getWord()).trim()); 
				if(word.getWord().contains("<s>") || word.getWord().contains("</s>")) continue;
				tempList.add(word);
			}
		}
		
		Collections.sort(tempList, new NthWordComparator());

		for(int index = 0; (index < count && index < tempList.size()); ++index) {
		retVal.add(tempList.get(index).getWord());
		}

		return retVal;
	}
}
