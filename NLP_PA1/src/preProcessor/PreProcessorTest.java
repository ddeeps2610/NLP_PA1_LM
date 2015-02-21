package preProcessor;
import java.io.FileNotFoundException;
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
		PreProcessor preProcessor = new PreProcessor();
		try 
		{
			preProcessor.process(".\\InputFiles\\training.txt", InputType.train);
			LinkedList<String> upTrain = preProcessor.getUpTrain();
			LinkedList<String> downTrain = preProcessor.getDownTrain();
			
			System.out.println("UP_TRAIN:");
			for(String token: upTrain)
				System.out.println(token);
			
			System.out.println("\n\n DOWN_TRAIN:");
			for(String token : downTrain)
				System.out.println(token);
			
			// UpTrain Models
			INGram uniGramUpTrain = new UniGram();
			uniGramUpTrain.computeNGramProbabilities(upTrain);
			//uniGramUpTrain.printNGramProbabilities();
			uniGramUpTrain.generateRandomSentence();
			uniGramUpTrain.laplaceSmoothing(upTrain);
			double perplexity = uniGramUpTrain.calculatePerplexity();
			System.out.println("Perplexity for the unigram model : "+ perplexity);
			
			
			
			INGram biGramUpTrain = new BiGram();
			biGramUpTrain.computeNGramProbabilities(upTrain);
			//biGramUpTrain.printNGramProbabilities();
			biGramUpTrain.laplaceSmoothing(upTrain);
			double bigramPerplexity = biGramUpTrain.calculatePerplexity();
			System.out.println("Perplexity for the bigram Model : "+bigramPerplexity);
			
			INGram triGramUpTrain = new TriGram();
			triGramUpTrain.computeNGramProbabilities(upTrain);
			//triGramUpTrain.printNGramProbabilities();
			triGramUpTrain.laplaceSmoothing(upTrain);
			double trigramPerplexity = biGramUpTrain.calculatePerplexity();
			System.out.println("Perplexity for the trigram Model : "+trigramPerplexity);
			
			System.out.println("\n\n\n");
			System.out.println("Perplexity for the unigram model : "+ perplexity);
			System.out.println("Perplexity for the bigram Model : "+bigramPerplexity);
			System.out.println("Perplexity for the trigram Model : "+trigramPerplexity);
			
			// DownTrain Models
			/*INGram uniGramDownTrain = new UniGram();
			uniGramDownTrain.computeNGramProbabilities(downTrain);
			uniGramDownTrain.printNGramProbabilities();
			uniGramDownTrain.generateRandomSentence();
			*/
			/*
			INGram biGramDownTrain = new BiGram();
			biGramDownTrain.computeNGramProbabilities(downTrain);
			biGramDownTrain.printNGramProbabilities();
						
			INGram triGramDownTrain = new TriGram();
			triGramDownTrain.computeNGramProbabilities(downTrain);
			triGramDownTrain.printNGramProbabilities();
				
			*/
		} catch (FileNotFoundException e) {
			System.out.println("File not initialized properly");
			e.printStackTrace();
		}
		
	}
}
