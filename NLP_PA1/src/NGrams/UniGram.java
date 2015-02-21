/**
 * 
 */
package NGrams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

/**
 * @author Deepak
 *
 */
public class UniGram extends AbstractNGrams
{
	//private HashMap<String, Integer> uniGramCounts;
	//private HashMap<String, Float> uniGramProbabilities;
	/* (non-Javadoc)
	 * @see NGrams.INGram#countNGram(java.util.LinkedList)
	 */
	@Override
	public void countNGram(LinkedList<String> corpus) 
	{
		if(this.nGramCounts == null)
			this.nGramCounts = new HashMap<String, Integer>();
		
		for(String sentenceToken : corpus)
		{
			String[] wordTokens = sentenceToken.split(" ");
			
			for(String word : wordTokens)
			{
				if(!this.nGramCounts.containsKey(word))
					this.nGramCounts.put(word, 1);
				else
					this.nGramCounts.put(word, this.nGramCounts.get(word)+1);
			}
		}
	}

	@Override
	public String generateRandomSentence() 
	{
		System.out.println("Generating Random Senetence for Unigram Model!");
		// Generating a random sentence
		StringBuilder newSentence = new StringBuilder("<s> ");
		
		String nthWord = "";
		
		// fetch next probable word and append to random sentence until end tag is encountered.
		do
		{
			nthWord = this.getNextWord();
			// Ignore the start tag if its encountered in the middle of the sentence.
			if(nthWord.equals("<s>"))
				continue;
			
			newSentence.append(nthWord + " ");
		}while(!nthWord.contains("</s>"));
		System.out.println("New Sentence generated:\n"+newSentence);

		return newSentence.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNextWord() {
		String retVal = "";
		
		if(nextLargestProbabilityIndex == -1 || sortedProbabilityList == null ||sortedProbabilityList.size() == 0) {
			
			// get all the KeyValue pairs and put it into an array-list
			sortedProbabilityList = new ArrayList<Entry<String,Float>>(nGramProbabilities.entrySet());
			
			// the comparator class can be a separate class instead of an anonymous class
			Collections.sort(sortedProbabilityList, new Comparator<Entry<String, Float>>() {
		        
				@Override
		        public int compare(Entry<String, Float> entrySet1, Entry<String, Float> entrySet2) {
		            return entrySet1.getValue().compareTo(entrySet2.getValue()) * -1; // to sort in descending order (confirmation required)
		        }
				
		    });
			nextLargestProbabilityIndex = 0;
		}
		
		//System.out.println(sortedProbabilityList.get(nextLargestProbabilityIndex));
		retVal = sortedProbabilityList.get(nextLargestProbabilityIndex++).getKey();
		System.out.println(retVal);	
		return retVal;
	}
	
	// stores the Map entries in a sorted manner based on "entry" values (if entries are removed/added in the map, sink the list as well (better make the list empty))
	private List<Entry<String, Float>> sortedProbabilityList  = new ArrayList<Entry<String,Float>>();
	// not required if we pass the index can be passed (better to pass as parameter)
	private int nextLargestProbabilityIndex = -1;
}
