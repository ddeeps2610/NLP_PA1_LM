/**
 * 
 */
package NGrams;

import java.util.ArrayList;
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
		if(this.nGramMap == null)
			this.nGramMap = new HashMap<String, HashMap<String,NthWord>>();
		
		HashMap<String, NthWord> nthWordMap = new HashMap<String, NthWord>();
		String nMinus1 = "";
		
		for(String sentenceToken : corpus)
		{
			List<String> wordTokens = this.tokenizeSentence(sentenceToken);
			
			for(String word : wordTokens)
			{
				// Add the nth word if it is already not available.
				if(!nthWordMap.containsKey(word))
				{
					NthWord nthWord = new NthWord();
					nthWord.setWord(word);
					nthWord.setCount(1);
					nthWordMap.put(word, nthWord);
				}
				// Increment the count of the nth word if it is already available in the corpus.
				else
				{
					nthWordMap.get(word).setCount(nthWordMap.get(word).getCount()+1);
				}
			}
		}
		this.nGramMap.put(nMinus1, nthWordMap);
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
	public String getNextWord() 
	{
		return null;
	}
	
	@Override
	public double calculateEmailProbability(String email) {
		// TODO Auto-generated method stub
		return 0;
	}
}
