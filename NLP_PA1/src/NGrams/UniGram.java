/**
 * 
 */
package NGrams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import preProcessor.PreProcessor;

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
	public double calculateEmailProbability(String email) 
	{
		double emailProbability = 0.0;
		
		List<String> sentences = PreProcessor.tokenizeEmails(email);

		// Loop over all sentences
		for(String sentence: sentences)
		{
			// Tokenize each sentence
			List<String> wordTokens = this.tokenizeSentence(sentence);
			String history = "";
			
			double prob = 0.0;
			// Calculate the probability for each word
			for(String word : wordTokens)
			{
				// Check if the required history is present.
				// For unigram, there is no history. Its only ""
				if(!this.nGramMap.containsKey(history))
				{
					prob = getUnknownProb();
					System.err.println("Error in calculating the probability in Unigram.");
				}
				// Always enters this
				else
				{
					if(!this.nGramMap.get(history).containsKey(word))
						prob = getUnknownProb();
					else
						prob = this.nGramMap.get(history).get(word).getProbability();
				}
				// Add the log of the probability for easier calculations
				emailProbability += Math.log(prob);
			}
		}
		// Return the antilog of the log of probabilities.
		return Math.pow(Math.E, emailProbability);
	}

}
