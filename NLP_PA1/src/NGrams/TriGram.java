/**
 * 
 */
package NGrams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import preProcessor.Email;
import preProcessor.PreProcessor;

/**
 * @author Deepak
 *
 */
public class TriGram extends AbstractNGrams 
{

	/* (non-Javadoc)
	 * @see NGrams.AbstractNGrams#countNGram(java.util.LinkedList)
	 * * @author Deepak
	 * Counts the occurrence of each NGram
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
			
			
			for(String word : wordTokens)
			{
				history = nMinus2 +" " + nMinus1;
				
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
						this.nGramMap.get(history).get(word).setCount(this.nGramMap.get(history).get(word).getCount()+1);
					}
				}
				nMinus2 = nMinus1;
				nMinus1 = word;
				
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see NGrams.INGram#calculatePerplexity(java.util.LinkedList)
	 * * @author Deepak
	 * Calculates the perplexity(intrinsic evaluation) for the given NGram Model.
	 */
	public double calculatePerplexity(LinkedList<String> corpus) 
	{
		//System.out.println("\nCalculating perplexity..!!");
		double  prob = 0.0;
		double perplexity = 0;
		int count=0;
		
		String nMinus1;
		String nMinus2;
		String history = null;
		
		for(String sentenceToken : corpus)
		{
			List<String> wordTokens = this.tokenizeSentence(sentenceToken);
			nMinus1 = "";
			nMinus2 = "";
			
			
			for(String word : wordTokens)
			{
				history = nMinus2 +" " + nMinus1;
				
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
				nMinus2 = nMinus1;
				nMinus1 = word;
				
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

		String previousTwoWord = "<s>";
		String previousOneWord = "<s>";
		String nextWord = "";
		int nextIndex = 0;
		Random rand = new Random();
		List<String> tempList = new ArrayList<String>();
		retVal.append("<s> ");
		
		// to get the first word
		for(int index = 0; index < tempCorpus.size() - 1; index++) {
			if(tempCorpus.get(index).equalsIgnoreCase(previousTwoWord)) {
				tempList.add(tempCorpus.get(index + 1));
			}
		}
		nextIndex = rand.nextInt(tempList.size());
		nextWord = tempList.get(nextIndex);
		retVal.append(nextWord + " ");
		previousTwoWord = previousOneWord;
		previousOneWord = nextWord;		
		tempList.clear();
		
		while(true) {	
			tempList.clear();
			for(int index = 0; index < tempCorpus.size() - 1; index++) {
				if(tempCorpus.get(index).equalsIgnoreCase(previousTwoWord) && tempCorpus.get(index + 1).equalsIgnoreCase(previousOneWord)) {
					tempList.add(tempCorpus.get(index + 2));
				}
			}
			nextIndex = rand.nextInt(tempList.size());
			nextWord = tempList.get(nextIndex);
			if(nextWord.equalsIgnoreCase("</s>")) break;
			retVal.append(nextWord + " ");
			previousTwoWord = previousOneWord;
			previousOneWord = nextWord;
		}
		
		retVal.append("</s>");
		
		System.out.println("\nRandom Sentence for Trigram Model: \n" + retVal);
		
		return retVal.toString();
	}

	private String getNextWord(String nGramMinusOneWord) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double calculateEmailProbability(Email email) 
	{
		double emailProbability = 0.0;
		
		List<String> sentences = PreProcessor.tokenizeEmails(email);
		
		for(String sentence: sentences)
		{
			List<String> wordTokens = this.tokenizeSentence(sentence);
			String nMinus1 = "";
			String nMinus2 = "";
			String history = nMinus2 +" " + nMinus1;
			
			
			double prob = 0.0;
			for(String word : wordTokens)
			{
				if(!this.nGramMap.containsKey(history))
				{
					prob = getUnknownProb(history);
				}
				else
				{
					if(!this.nGramMap.get(history).containsKey(word))
						prob = getUnknownProb(history);
					else
					{
						prob = this.nGramMap.get(history).get(word).getProbability();
					}
				}
				nMinus2 = nMinus1;
				nMinus1 = word;
				history = nMinus2 +" " + nMinus1;
				
				
				if(prob != 0)
					emailProbability += Math.log(prob);
			}
		}
		//System.out.println("Log(Prob) = "+emailProbability);
		return Math.pow(Math.E, emailProbability);
	}
	
	


}
