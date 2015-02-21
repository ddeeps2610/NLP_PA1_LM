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
		// Instantiate the Sentence String and initialize it with Start tag <s>
		StringBuilder randomSentence = new StringBuilder("<s> ");
		String nMinusOneWord = "<s>";
		StringBuilder newSentence = new StringBuilder("<s> ");
		String nthWord;
		
		do
		{
			nthWord = this.getNextWord(nMinusOneWord);
			if(nthWord.equals("<s>"))
				continue;
			
			// Assign the nth word to (n-1)th word
			nMinusOneWord = nthWord;
			newSentence.append(nthWord + " ");
		}while(!nthWord.contains("</s>"));
		
		
		return randomSentence.toString();
	}

	private String getNextWord(String nGramMinusOneWord) {
		// TODO Auto-generated method stub
		return null;
	}
}
