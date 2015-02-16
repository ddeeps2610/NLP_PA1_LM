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
			preProcessor.process(".\\InputFiles\\sample.txt", InputType.train);
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
			uniGramUpTrain.printNGramProbabilities();
			
			INGram biGramUpTrain = new BiGram();
			biGramUpTrain.computeNGramProbabilities(upTrain);
			biGramUpTrain.printNGramProbabilities();
			
			INGram triGramUpTrain = new TriGram();
			triGramUpTrain.computeNGramProbabilities(upTrain);
			triGramUpTrain.printNGramProbabilities();
			
			// DownTrain Models
			INGram uniGramDownTrain = new UniGram();
			uniGramDownTrain.computeNGramProbabilities(downTrain);
			uniGramDownTrain.printNGramProbabilities();
						
			INGram biGramDownTrain = new BiGram();
			biGramDownTrain.computeNGramProbabilities(downTrain);
			biGramDownTrain.printNGramProbabilities();
						
			INGram triGramDownTrain = new TriGram();
			triGramDownTrain.computeNGramProbabilities(downTrain);
			triGramDownTrain.printNGramProbabilities();
						
		} catch (FileNotFoundException e) {
			System.out.println("File not initialized properly");
			e.printStackTrace();
		}
		
	}
}
