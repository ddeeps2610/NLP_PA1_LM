/**
 * 
 */
package NGrams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import preProcessor.Email;
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
	
	public double calculatePerplexity(LinkedList<String> corpus) 
	{
		//System.out.println("\nCalculating perplexity..!!");
		double  prob = 0.0;
		double perplexity = 0;
		int count=0;
		
		//String nMinus1;
		//String nMinus2;
		String history = "";
		
		for(String sentenceToken : corpus)
		{
			List<String> wordTokens = this.tokenizeSentence(sentenceToken);
			//nMinus1 = "";
			//nMinus2 = "";
			
			
			for(String word : wordTokens)
			{
				//history = nMinus2 +" " + nMinus1;
				
				// Add the new history in case the given history is not available.
				if(!this.nGramMap.containsKey(history))
				{
					prob = 0.0;
					
				}
				else
				{
					// Add the new Nth Word in case the given nth Word is not available
					if(!this.nGramMap.get(history).containsKey(word))
					{
						prob = 0;
					}
					// Update the count of the nth word in case it is already present.
					else
					{
						prob = this.nGramMap.get(history).get(word).getProbability();
					}
				}
				//nMinus2 = nMinus1;
				//nMinus1 = word;
				
				if(prob != 0)
				{
					perplexity += Math.log(prob);
					count++;
				}
				
				
			}
		}
		
		
		//System.out.println("Pre Perplexity: "+ perplexity + ": " + this.nGramProbabilities.size());
		if(count != 0)
		{
			perplexity = -1 * perplexity / count;
			perplexity = Math.pow(Math.E, (perplexity));
		}
		else
		{
			perplexity = 10000;
		}
		
		//System.out.println("Token types: " + count);
		//System.out.println("Pre antilog: "+perplexity);
		return perplexity;
	}

	@Override
	public String generateRandomSentence(List<String> corpus) {
		StringBuilder retVal = new StringBuilder();
		List<String> tempCorpus = new ArrayList<String>();
		for(String line : corpus) {
			tempCorpus.addAll(this.tokenizeSentence(line));
		}		
		retVal.append("<s> ");
		String nextWord = "";
		Random rand = new Random();
		int index = 0;

		while(true) {
			index = rand.nextInt(tempCorpus.size());	
			nextWord = tempCorpus.get(index);
			if(nextWord.equalsIgnoreCase("<s>")) continue;
			if(nextWord.equalsIgnoreCase("</s>")) break;
			retVal.append(nextWord + " ");
		}
		retVal.append("</s>");
		System.out.println("\nRandom Sentence for Unigram Model: \n" + retVal);
		
		return retVal.toString().trim();
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
	public double calculateEmailProbability(Email email) 
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
					prob = getUnknownProb(history);
					System.err.println("Error in calculating the probability in Unigram.");
				}
				// Always enters this
				else
				{
					if(!this.nGramMap.get(history).containsKey(word))
						prob = getUnknownProb(history);
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
