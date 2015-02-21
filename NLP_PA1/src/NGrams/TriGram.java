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
		
		HashMap<String, NthWord> nthWordMap = new HashMap<String, NthWord>();
		
		StringBuilder previousWord = null;
		
		for(String sentenceToken : corpus)
		{
			String[] wordTokens = sentenceToken.split(" ");
			previousWord = null;
			previousWord.append("");
			
			for(String word : wordTokens)
			{
				if(this.nGramMap.containsKey(previousWord))
				{
					if(this.nGramMap.get(previousWord).containsKey(word))
					{
						this.nGramMap.get(previousWord).get(word).setCount(this.nGramMap.get(previousWord).get(word).getCount()+1);
					}
					else
					{
						NthWord nthWord = new NthWord();
						nthWord.setWord(word);
						nthWord.setCount(1);
						this.nGramMap.get(previousWord).put(word, nthWord);
					}
					previousWord = null;
					previousWord.append(word);
				}
				else
				{
					NthWord nthWord = new NthWord();
					nthWord.setWord(word);
					nthWord.setCount(1);
					this.nGramMap.get(previousWord).put(word, nthWord);
					this.nGramMap.put(previousWord.toString(), null);
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
