package preProcessor;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;

import NGrams.BiGram;
import NGrams.INGram;
import NGrams.TriGram;
import NGrams.UniGram;

/**
 * 
 */

/**
 * @author Deepak
 *
 */
public class PreProcessorTest 
{
	public static void main (String[] args)
	{
		try 
		{
			PreProcessor preProcessor = new PreProcessor();
			
			preProcessor.process(".\\InputFiles\\training.txt", InputType.train);
			LinkedList<String> upTrain = preProcessor.getUpTrain();
			LinkedList<String> downTrain = preProcessor.getDownTrain();
			
			// UpTrain Models
			// Unigram
			INGram uniGramUpTrain = new UniGram();
			uniGramUpTrain.computeNGramProbabilities(upTrain);
			uniGramUpTrain.printNGramProbabilities();
			//uniGramUpTrain.generateRandomSentence();
			//uniGramUpTrain.laplaceSmoothing(upTrain);
			//double unigramPerplexity = uniGramUpTrain.calculatePerplexity();
			//System.out.println("Perplexity for the unigram model : "+ unigramPerplexity);
			/*
			// Bigram
			INGram biGramUpTrain = new BiGram();
			biGramUpTrain.computeNGramProbabilities(upTrain);
			//biGramUpTrain.printNGramProbabilities();
			//biGramUpTrain.generateRandomSentence();
			biGramUpTrain.laplaceSmoothing(upTrain);
			double bigramPerplexity = biGramUpTrain.calculatePerplexity();
			System.out.println("Perplexity for the bigram Model : "+bigramPerplexity);
			
			// Trigram
			INGram triGramUpTrain = new TriGram();
			triGramUpTrain.computeNGramProbabilities(upTrain);
			//triGramUpTrain.printNGramProbabilities();
			//triGramUpTrain.generateRandomSentence();
			triGramUpTrain.laplaceSmoothing(upTrain);
			double trigramPerplexity = biGramUpTrain.calculatePerplexity();
			System.out.println("Perplexity for the trigram Model : "+trigramPerplexity);
			
			// DownTrain Models
			// Unigram
			INGram uniGramDownTrain = new UniGram();
			uniGramDownTrain.computeNGramProbabilities(downTrain);
			//uniGramDownTrain.printNGramProbabilities();
			//uniGramDownTrain.generateRandomSentence();
			uniGramDownTrain.laplaceSmoothing(downTrain);
			double uniDownPerplexity = uniGramDownTrain.calculatePerplexity();
			System.out.println("Perplexity for the unigram for downtrain : " + uniDownPerplexity);
			
			// Bigram
			INGram biGramDownTrain = new BiGram();
			biGramDownTrain.computeNGramProbabilities(downTrain);
			//biGramDownTrain.printNGramProbabilities();
			//biGramDownTrain.generateRandomSentence();
			biGramDownTrain.laplaceSmoothing(downTrain);
			double biDownPerplexity = biGramDownTrain.calculatePerplexity();
			System.out.println("Perplexity for the bigram for downtrain : " + biDownPerplexity);
			
			//Trigram
			INGram triGramDownTrain = new TriGram();
			triGramDownTrain.computeNGramProbabilities(downTrain);
			//triGramDownTrain.printNGramProbabilities();
			//triGramDownTrain.generateRandomSentence();	
			triGramDownTrain.laplaceSmoothing(downTrain);
			double triDownPerplexity = triGramDownTrain.calculatePerplexity();
			System.out.println("Perplexity for trigram for downtrain : " + triDownPerplexity);
			
			System.out.println("\n\n\n");
			System.out.println("Perplexity for the unigram uptrain : "+ unigramPerplexity);
			System.out.println("Perplexity for the bigram uptrain : "+bigramPerplexity);
			System.out.println("Perplexity for the trigram uptrain : "+trigramPerplexity);
			System.out.println("Perplexity for the unigram downtrain : "+ uniDownPerplexity);
			System.out.println("Perplexity for the bigram downtrain : "+biDownPerplexity);
			System.out.println("Perplexity for the trigram downtrain : "+triDownPerplexity);
			
			// Contest - Test your Up and Down Trained modules for the test mails
			PreProcessor contestPreProcessor = new PreProcessor();
			contestPreProcessor.process(".\\InputFiles\\test.txt", InputType.train);
			LinkedList<String> upDownTest= contestPreProcessor.getUpDownTest();
			LinkedList<Email> testEmails = contestPreProcessor.getTestEmails();
			
			// Result storage
			int i = 0;
			HashMap<Integer,Integer> guesses = new HashMap<Integer, Integer>();
			for(Email email : testEmails)
			{
				double upstreamProb = triGramUpTrain.calculateEmailProbability(email);
				double downstreamProb = triGramDownTrain.calculateEmailProbability(email);
				
				
				email.setDownStremProb(downstreamProb);
				email.setUpStreamProb(upstreamProb);
				email.setSpeak(upstreamProb > downstreamProb ?SpeakOrder.UpSpeak :SpeakOrder.DownSpeak);
				System.out.println("Email ID:Speak :: " + email.getId() + ":"+email.getSpeak());
			}
			*/
			
		} catch (FileNotFoundException e) {
			System.out.println("File not initialized properly");
			e.printStackTrace();
		}
		
	}
}
