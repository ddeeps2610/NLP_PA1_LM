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
			for(int index = 0; index < 5; index++)
				uniGramUpTrain.generateRandomSentence(upTrain);
			
			for(String string : uniGramUpTrain.getFrequentNGrams(10)) {
				System.out.println(string);
			}
			
			// Bigram
			INGram biGramUpTrain = new BiGram();
			biGramUpTrain.computeNGramProbabilities(upTrain);
			//biGramUpTrain.printNGramProbabilities();
			for(int index = 0; index < 5; index++)
				biGramUpTrain.generateRandomSentence(upTrain);
			
			for(String string : biGramUpTrain.getFrequentNGrams(10)) {
				System.out.println(string);
			}
			
			// Trigram
			INGram triGramUpTrain = new TriGram();
			triGramUpTrain.computeNGramProbabilities(upTrain);
			//triGramUpTrain.printNGramProbabilities();
			for(int index = 0; index < 5; index++)
				triGramUpTrain.generateRandomSentence(upTrain);
			
			for(String string : triGramUpTrain.getFrequentNGrams(10)) {
				System.out.println(string);
			}
			
			// DownTrain Models
			// Unigram
			INGram uniGramDownTrain = new UniGram();
			uniGramDownTrain.computeNGramProbabilities(downTrain);
			//uniGramDownTrain.printNGramProbabilities();
			for(int index = 0; index < 5; index++)
				uniGramDownTrain.generateRandomSentence(downTrain);
			
			for(String string : uniGramDownTrain.getFrequentNGrams(10)) {
				System.out.println(string);
			}
			
			// Bigram
			INGram biGramDownTrain = new BiGram();
			biGramDownTrain.computeNGramProbabilities(downTrain);
			//biGramDownTrain.printNGramProbabilities();
			for(int index = 0; index < 5; index++)
				biGramDownTrain.generateRandomSentence(downTrain);
			
			for(String string : biGramDownTrain.getFrequentNGrams(10)) {
				System.out.println(string);
			}
			
			//Trigram
			INGram triGramDownTrain = new TriGram();
			triGramDownTrain.computeNGramProbabilities(downTrain);
			//triGramDownTrain.printNGramProbabilities();
			for(int index = 0; index < 5; index++)
				triGramDownTrain.generateRandomSentence(downTrain);	
			
			for(String string : triGramDownTrain.getFrequentNGrams(10)) {
				System.out.println(string);
			}
			
			/************************* Validation ****************************/
			PreProcessor validationPreProcessor = new PreProcessor();
			validationPreProcessor.process(".\\InputFiles\\validation.txt", InputType.validation);
			LinkedList<String> upValidation = validationPreProcessor.getUpValidation();
			LinkedList<String> downValidation = validationPreProcessor.getDownValidation();
			
			// UpTrain Models
			// Unigram
			INGram validationUniGramUpTrain = new UniGram();
			validationUniGramUpTrain.computeNGramProbabilities(upValidation);
			validationUniGramUpTrain.laplaceSmoothing(upValidation);
			//validationUniGramUpTrain.printNGramProbabilities();
			double unigramPerplexity = validationUniGramUpTrain.calculatePerplexity(upValidation);
			//System.out.println("Perplexity for the unigram model : "+ unigramPerplexity);
			
			// Bigram
			INGram validationBiGramUpTrain = new BiGram();
			validationBiGramUpTrain.computeNGramProbabilities(upValidation);
			validationBiGramUpTrain.laplaceSmoothing(upValidation);
			//validationBiGramUpTrain.printNGramProbabilities();
			double bigramPerplexity = validationBiGramUpTrain.calculatePerplexity(upValidation);
			//System.out.println("Perplexity for the bigram model : "+ bigramPerplexity);

			// Trigram
			INGram validationTriGramUpTrain = new TriGram();
			validationTriGramUpTrain.computeNGramProbabilities(upValidation);
			validationTriGramUpTrain.laplaceSmoothing(upValidation);
			//validationTriGramUpTrain.printNGramProbabilities();
			double trigramPerplexity = validationTriGramUpTrain.calculatePerplexity(upValidation);
			//System.out.println("Perplexity for the trigram model : "+ trigramPerplexity);

			
			// UpTrain Models
			// Unigram
			INGram validationUniGramDownTrain = new UniGram();
			validationUniGramDownTrain.computeNGramProbabilities(downValidation);
			validationUniGramDownTrain.laplaceSmoothing(downValidation);
			//validationUniGramDownTrain.printNGramProbabilities();
			double unigramDownPerplexity = validationUniGramDownTrain.calculatePerplexity(downValidation);
			//System.out.println("Perplexity for the unigram model : "+ unigramDownPerplexity);
			
			// Bigram
			INGram validationBiGramDownTrain = new BiGram();
			validationBiGramDownTrain.computeNGramProbabilities(downValidation);
			validationBiGramDownTrain.laplaceSmoothing(downValidation);
			//validationBiGramDownTrain.printNGramProbabilities();
			double bigramDownPerplexity = validationBiGramDownTrain.calculatePerplexity(downValidation);
			//System.out.println("Perplexity for the bigram model : "+ bigramDownPerplexity);

			// Trigram
			INGram validationTriGramDownTrain = new TriGram();
			validationTriGramDownTrain.computeNGramProbabilities(downValidation);
			validationTriGramDownTrain.laplaceSmoothing(downValidation);
			//validationTriGramDownTrain.printNGramProbabilities();
			double trigramDownPerplexity = validationTriGramDownTrain.calculatePerplexity(downValidation);
			//System.out.println("Perplexity for the trigram model : "+ trigramDownPerplexity);

			
			
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
			
			//triGramUpTrain.printNGramProbabilities();
			triGramUpTrain.deepakSmoothing(upTrain);
			//triGramUpTrain.printNGramProbabilities();
			triGramDownTrain.deepakSmoothing(downTrain);
			// Result storage
			HashMap<Integer,SpeakOrder> guesses = new HashMap<Integer, SpeakOrder>();
			
			System.out.println("\nGuessing the Speak Order of the Test Emails...!!!");
			System.out.println("Id,Prediction");
			
			
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
