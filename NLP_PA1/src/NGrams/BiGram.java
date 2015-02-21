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
public class BiGram extends AbstractNGrams 
{

	@Override
	public void countNGram(LinkedList<String> corpus) 
	{
		if(this.nGramMap == null)
			this.nGramMap = new HashMap<String, HashMap<String,NthWord>>();
		
		HashMap<String, NthWord> nthWordMap = new HashMap<String, NthWord>();
		
		StringBuilder nMinus1 = null;
		
		for(String sentenceToken : corpus)
		{
			nMinus1 = null;
			nMinus1.append("");
			
			List<String> wordTokens = this.tokenizeSentence(sentenceToken);
			
			for(String word : wordTokens)
			{
				if(this.nGramMap.containsKey(nMinus1))
				{
					if(this.nGramMap.get(nMinus1).containsKey(word))
					{
						this.nGramMap.get(nMinus1).get(word).setCount(this.nGramMap.get(nMinus1).get(word).getCount()+1);
					}
					else
					{
						NthWord nthWord = new NthWord();
						nthWord.setWord(word);
						nthWord.setCount(1);
						this.nGramMap.get(nMinus1).put(word, nthWord);
					}
					nMinus1 = null;
					nMinus1.append(word);
				}
				else
				{
					NthWord nthWord = new NthWord();
					nthWord.setWord(word);
					nthWord.setCount(1);
					this.nGramMap.get(nMinus1).put(word, nthWord);
					this.nGramMap.put(nMinus1.toString(), null);
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

	@Override
	public double calculateEmailProbability(String email) {
		// TODO Auto-generated method stub
		return 0;
	}
}
