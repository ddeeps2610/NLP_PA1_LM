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
public class TriGram extends AbstractNGrams {

	/* (non-Javadoc)
	 * @see NGrams.AbstractNGrams#countNGram(java.util.LinkedList)
	 */
	@Override
	public void countNGram(LinkedList<String> corpus) 
	{
		if(this.nGramCounts == null)
			this.nGramCounts = new HashMap<String, Integer>();
		
		StringBuilder nMinus2Word = null;
		StringBuilder nMinus1Word = null;
		StringBuilder triGram = null;
		
		for(String sentenceToken : corpus)
		{
			String[] wordTokens = sentenceToken.split(" ");
			
			for(String word : wordTokens)
			{
				if (nMinus2Word == null)
				{
					nMinus2Word = new StringBuilder();
					nMinus2Word.append(word);
				}
				else if(nMinus1Word == null)
				{
					nMinus1Word = new StringBuilder();
					nMinus1Word.append(word);
				}
				else
				{
					if (triGram == null)
						triGram = new StringBuilder();
					
					triGram.append(nMinus2Word + " " +nMinus1Word + " " + word);
					
					nMinus2Word = null;
					nMinus2Word = new StringBuilder(nMinus1Word);
					
					nMinus1Word = null;
					nMinus1Word = new StringBuilder(word);
					
					if(!this.nGramCounts.containsKey(triGram.toString()))
						this.nGramCounts.put(triGram.toString(), 1);
					else
						this.nGramCounts.put(triGram.toString(), this.nGramCounts.get(triGram.toString())+1);
					
					triGram = null;
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
		String nMinusTwoWord = "";
		StringBuilder newSentence = new StringBuilder("<s> ");
		String nthWord;
		
		do
		{
			nthWord = this.getNextWord(nMinusTwoWord + " " +nMinusOneWord);
			if(nthWord.equals("<s>"))
				continue;
			
			// Assign the nth word to (n-1)th word
			nMinusTwoWord = nMinusOneWord;
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
