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
			/***************************** Pre Processor ****************************/
			PreProcessor preProcessor = new PreProcessor();
			preProcessor.process(".\\InputFiles\\training.txt", InputType.train);
			LinkedList<String> upTrain = preProcessor.getUpTrain();
			LinkedList<String> downTrain = preProcessor.getDownTrain();
			
			// UpTrain Models
			// Unigram
			INGram uniGramUpTrain = new UniGram();
			uniGramUpTrain.computeNGramProbabilities(upTrain);
			//uniGramUpTrain.printNGramProbabilities();
			uniGramUpTrain.generateRandomSentence();
			
			// Bigram
			INGram biGramUpTrain = new BiGram();
			biGramUpTrain.computeNGramProbabilities(upTrain);
			//biGramUpTrain.printNGramProbabilities();
			biGramUpTrain.generateRandomSentence();
			
			// Trigram
			INGram triGramUpTrain = new TriGram();
			triGramUpTrain.computeNGramProbabilities(upTrain);
			//triGramUpTrain.printNGramProbabilities();
			triGramUpTrain.generateRandomSentence();
			
			// DownTrain Models
			// Unigram
			INGram uniGramDownTrain = new UniGram();
			uniGramDownTrain.computeNGramProbabilities(downTrain);
			//uniGramDownTrain.printNGramProbabilities();
			uniGramDownTrain.generateRandomSentence();
			
			// Bigram
			INGram biGramDownTrain = new BiGram();
			biGramDownTrain.computeNGramProbabilities(downTrain);
			//biGramDownTrain.printNGramProbabilities();
			biGramDownTrain.generateRandomSentence();
			
			//Trigram
			INGram triGramDownTrain = new TriGram();
			triGramDownTrain.computeNGramProbabilities(downTrain);
			//triGramDownTrain.printNGramProbabilities();
			triGramDownTrain.generateRandomSentence();	
			
			
			/************************* Validation ****************************/
			PreProcessor validationPreProcessor = new PreProcessor();
			validationPreProcessor.process(".\\InputFiles\\validation.txt", InputType.validation);
			LinkedList<String> validationUpTrain = validationPreProcessor.getUpTrain();
			LinkedList<String> validationDownTrain = validationPreProcessor.getDownTrain();
			
			// UpTrain Models
			// Unigram
			INGram validationUniGramUpTrain = new UniGram();
			validationUniGramUpTrain.computeNGramProbabilities(validationUpTrain);
			validationUniGramUpTrain.laplaceSmoothing(upTrain);
			validationUniGramUpTrain.printNGramProbabilities();
			double unigramPerplexity = validationUniGramUpTrain.calculatePerplexity();
			System.out.println("Perplexity for the unigram model : "+ unigramPerplexity);
			
			// Bigram
			INGram validationBiGramUpTrain = new UniGram();
			validationBiGramUpTrain.computeNGramProbabilities(validationUpTrain);
			validationBiGramUpTrain.laplaceSmoothing(upTrain);
			validationBiGramUpTrain.printNGramProbabilities();
			double bigramPerplexity = validationBiGramUpTrain.calculatePerplexity();
			System.out.println("Perplexity for the bigram model : "+ bigramPerplexity);

			// Trigram
			INGram validationTriGramUpTrain = new UniGram();
			validationTriGramUpTrain.computeNGramProbabilities(validationUpTrain);
			validationTriGramUpTrain.laplaceSmoothing(upTrain);
			validationTriGramUpTrain.printNGramProbabilities();
			double trigramPerplexity = validationTriGramUpTrain.calculatePerplexity();
			System.out.println("Perplexity for the unigram model : "+ trigramPerplexity);

			
			// UpTrain Models
			// Unigram
			INGram validationUniGramDownTrain = new UniGram();
			validationUniGramDownTrain.computeNGramProbabilities(validationDownTrain);
			validationUniGramDownTrain.laplaceSmoothing(upTrain);
			validationUniGramDownTrain.printNGramProbabilities();
			double unigramDownPerplexity = validationUniGramDownTrain.calculatePerplexity();
			System.out.println("Perplexity for the unigram model : "+ unigramDownPerplexity);
			
			// Bigram
			INGram validationBiGramDownTrain = new UniGram();
			validationBiGramDownTrain.computeNGramProbabilities(validationDownTrain);
			validationBiGramDownTrain.laplaceSmoothing(upTrain);
			validationBiGramDownTrain.printNGramProbabilities();
			double bigramDownPerplexity = validationBiGramDownTrain.calculatePerplexity();
			System.out.println("Perplexity for the bigram model : "+ bigramDownPerplexity);

			// Trigram
			INGram validationTriGramDownTrain = new UniGram();
			validationTriGramDownTrain.computeNGramProbabilities(validationDownTrain);
			validationTriGramDownTrain.laplaceSmoothing(upTrain);
			validationTriGramDownTrain.printNGramProbabilities();
			double trigramDownPerplexity = validationTriGramDownTrain.calculatePerplexity();
			System.out.println("Perplexity for the unigram model : "+ trigramDownPerplexity);

			
			
			System.out.println("\n\n\n");
			System.out.println("Perplexity for the unigram uptrain : "+ unigramPerplexity);
			System.out.println("Perplexity for the bigram uptrain : "+bigramPerplexity);
			System.out.println("Perplexity for the trigram uptrain : "+trigramPerplexity);
			System.out.println("Perplexity for the unigram downtrain : "+ unigramDownPerplexity);
			System.out.println("Perplexity for the bigram downtrain : "+bigramDownPerplexity);
			System.out.println("Perplexity for the trigram downtrain : "+trigramDownPerplexity);
			
			
			
			/****************************** Testing ****************************/
			// Contest - Test your Up and Down Trained modules for the test mails
			PreProcessor contestPreProcessor = new PreProcessor();
			contestPreProcessor.process(".\\InputFiles\\test.txt", InputType.test);
			LinkedList<Email> testEmails = contestPreProcessor.getTestEmails();
			
			//triGramUpTrain.deepakSmoothing(upTrain);
			//triGramDownTrain.deepakSmoothing(downTrain);
			// Result storage
			HashMap<Integer,SpeakOrder> guesses = new HashMap<Integer, SpeakOrder>();
			for(Email email : testEmails)
			{
				double upstreamProb = triGramUpTrain.calculateEmailProbability(email);
				double downstreamProb = triGramDownTrain.calculateEmailProbability(email);
				
				email.setDownStremProb(downstreamProb);
				email.setUpStreamProb(upstreamProb);
				email.setSpeak(upstreamProb > downstreamProb ?SpeakOrder.UpSpeak :SpeakOrder.DownSpeak);
				guesses.put(email.getId(), email.getSpeak());
				System.out.println(/*"Email ID:Speak:UpProb:DownProb :: " + */email.getId() + ","+email.getSpeak().ordinal());
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File not initialized properly");
			e.printStackTrace();
		}
		
	}
}
