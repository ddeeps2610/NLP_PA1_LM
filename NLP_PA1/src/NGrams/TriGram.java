/**
 * 
 */
package NGrams;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Deepak
 *
 */
public class TriGram extends AbstractNGrams 
{

	/* (non-Javadoc)
	 * @see NGrams.AbstractNGrams#countNGram(java.util.LinkedList)
	 */
	@Override
	public void countNGram(LinkedList<String> corpus) 
	{
		if(this.nGramMap == null)
			this.nGramMap = new HashMap<String, HashMap<String,NthWord>>();
		
		String nMinus1;
		String nMinus2;
		String history = null;
		
		for(String sentenceToken : corpus)
		{
			List<String> wordTokens = this.tokenizeSentence(sentenceToken);
			nMinus1 = "";
			nMinus2 = "";
			history = nMinus2 + nMinus1;
			
			for(String word : wordTokens)
			{
				// Add the new history in case the given history is not available.
				if(!this.nGramMap.containsKey(history))
				{
					NthWord nthWord = new NthWord();
					nthWord.setWord(word);
					nthWord.setCount(1);
					HashMap<String, NthWord> nthWords = new HashMap<String, NthWord>();
					nthWords.put(word, nthWord);
					this.nGramMap.put(history, nthWords);
					
				}
				else
				{
					// Add the new Nth Word in case the given nth Word is not available
					if(!this.nGramMap.get(history).containsKey(word))
					{
						NthWord nthWord = new NthWord();
						nthWord.setWord(word);
						nthWord.setCount(1);
						this.nGramMap.get(history).put(word, nthWord);
						
					}
					// Update the count of the nth word in case it is already present.
					else
					{
						this.nGramMap.get(history).get(word).setCount(this.nGramMap.get(nMinus1).get(word).getCount()+1);
					}
				}
				nMinus2 = nMinus1;
				nMinus1 = word;
				
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

	@Override
	public double calculateEmailProbability(String email) 
	{
		String[] words = email.split(" ");
		for(String nGram : words)
		{
			
		}
		// TODO Auto-generated method stub
		return 0;
	}

}
