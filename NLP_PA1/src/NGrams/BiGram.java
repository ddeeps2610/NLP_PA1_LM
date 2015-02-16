/**
 * 
 */
package NGrams;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Deepak
 *
 */
public class BiGram extends AbstractNGrams 
{

	@Override
	public void countNGram(LinkedList<String> corpus) 
	{
		if(this.nGramCounts == null)
			this.nGramCounts = new HashMap<String, Integer>();
		
		StringBuilder previousWord = null;
		StringBuilder biGram = null;
		
		for(String sentenceToken : corpus)
		{
			String[] wordTokens = sentenceToken.split(" ");
			
			for(String word : wordTokens)
			{
				if (previousWord == null)
				{
					previousWord = new StringBuilder();
					previousWord.append(word);
				}					
				else
				{
					if (biGram == null)
						biGram = new StringBuilder();
					
					biGram.append(previousWord + " " + word);
					
					previousWord = null;
					previousWord = new StringBuilder(word);
					
					if(!this.nGramCounts.containsKey(biGram.toString()))
						this.nGramCounts.put(biGram.toString(), 1);
					else
						this.nGramCounts.put(biGram.toString(), this.nGramCounts.get(biGram.toString())+1);
					
					biGram = null;
				}
			}
		}
	}

	@Override
	public String generateRandomSentence() 
	{
		// TODO Auto-generated method stub
		// Instantiate the Sentence String and initialize it with Start tag <s>
		StringBuilder randomSentence = new StringBuilder("<s> ");
		String nMinusOneWord = "<s>";
		
		// Recursively fetch the probable next word until the Sentence end <\s> is reached.
		while(randomSentence.toString().contains("<\\s>"))
		{
			// Approach 1
			// Fetch all words starting with the N-1 word
			
			// Identify the highest probable word
			
			
			// Approach 2
			// Sort the map
			// Fetch first key starting with the given N-1 word
			
			// Common:
			// Append the word
		}
		return randomSentence.toString();
	}
}
