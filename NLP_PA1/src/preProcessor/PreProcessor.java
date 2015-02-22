package preProcessor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;

/**
 * 
 */

/**
 * @author Deepak
 *
 */
public class PreProcessor 
{
	/********** Processed Tokenized Sentences buffer ********/
	private LinkedList<String> upTrain;
	private LinkedList<String> downTrain;
	private LinkedList<String> upValidation;
	private LinkedList<String> downValidation;
	private LinkedList<String> upDownTest;

	/**************** Segmented Email buffers **************/
	private LinkedList<Email> upSpeakEmails;
	private LinkedList<Email> downSpeakEmails;
	private LinkedList<Email> testEmails;
	
	/******************* Input Handler *********************/
	private BufferedReader input;
	
	/********************* Getters******************/
	public LinkedList<String> getUpTrain() {
		return this.upTrain;
	}
	public LinkedList<String> getDownTrain() {
		return downTrain;
	}
	public LinkedList<String> getUpDownTest() {
		return upDownTest;
	} 
	public LinkedList<String> getUpValidation() {
		return upValidation;
	}
	public LinkedList<String> getDownValidation() {
		return downValidation;
	}
	
	public LinkedList<Email> getTestEmails() {
		return testEmails;
	}
	/******************** Business Logic **********/
	 /* @throws FileNotFoundException */
	public void process(String fileName, InputType inputType) throws FileNotFoundException
	{
		File inputFile = new File(fileName);
		
		if(!(inputFile.exists() && inputFile.isFile() && inputFile.canRead()))
		{
			System.out.println("Either the file doesn;t exist or it is not a file or can;t be read...!!!");
			System.exit(0);
			throw new FileNotFoundException();	
		}
		
		this.input = new BufferedReader(new FileReader(fileName));
		
		// Segregate the emails based on labels
		this.segmentEmails();
		
		// Tokenize emails based on the input type and labels
		switch(inputType)
		{
		case train:
			// Segment mails based on the label
			this.downTrain = (LinkedList<String>) PreProcessor.tokenizeEmails(this.downSpeakEmails);
			this.upTrain = (LinkedList<String>) PreProcessor.tokenizeEmails(this.upSpeakEmails);
			break;
		case validation:
			this.downValidation= (LinkedList<String>) PreProcessor.tokenizeEmails(this.downSpeakEmails);
			this.upValidation = (LinkedList<String>) PreProcessor.tokenizeEmails(this.upSpeakEmails);
			break;
		case test:
			this.upDownTest = (LinkedList<String>) PreProcessor.tokenizeEmails(this.testEmails);
			break;
			default:
				System.out.println("Wrong Input file type. Please Enter 0->Train 1->Validation 2->Test");
				break;
		}
		
	}
	
	private void segmentEmails()
	{
		String line;
		boolean mailReadFlag = false;
		boolean upSpeak = false;
		boolean downSpeak = false;
		StringBuilder email= null;
		try 
		{
			Email newEmail  = null;
			int id = 0;
			while((line = this.input.readLine()) != null)
			{
				
				
				if(line.contains("**START**"))
				{
					mailReadFlag = true;
					email = new StringBuilder();
					newEmail= new Email();
				}
				else if(line.contains("**EOM**"))
				{
					mailReadFlag = false;
					if(upSpeak)
					{
						if(this.upSpeakEmails == null)
							this.upSpeakEmails = new LinkedList<Email>();
						
						newEmail.setEmail(email.toString());
						newEmail.setSpeak(SpeakOrder.UpSpeak);
						this.upSpeakEmails.add(newEmail);
						email = null;
						upSpeak = false;
					}
					else if(downSpeak)
					{
						if(this.downSpeakEmails == null)
							this.downSpeakEmails = new LinkedList<Email>();
						newEmail.setEmail(email.toString());
						newEmail.setSpeak(SpeakOrder.DownSpeak);
						this.downSpeakEmails.add(newEmail);
						email = null;
						downSpeak = false;
					}
					else //if(!upSpeak && !downSpeak)
					{
						if(this.testEmails == null)
							this.testEmails = new LinkedList<Email>();
						
						newEmail.setEmail(email.toString());
						newEmail.setId(id);
						this.testEmails.add(newEmail);
						email = null;
					}
				}
				else if(line.contains("UPSPEAK"))
				{
					upSpeak = true;					
				}
				else if(line.contains("DOWNSPEAK"))
				{
					downSpeak = true;
				}
				else
				{
					if(PreProcessor.isInteger(line))
						id = Integer.parseInt(line);
					if (mailReadFlag && email != null)
						email.append(line);
				}
				
			}
			
		}
		catch (IOException e) 
		{
			System.out.println("Unable to read the file. Some problem with the IO.");
			e.printStackTrace();
		}
	}
	
	private static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	
	public static  List<String> tokenizeEmails(Email email) {
		List<String> corpus = new ArrayList<String>();
		Reader reader = new StringReader(email.getEmail());
		DocumentPreprocessor dp = new DocumentPreprocessor(reader);

		Iterator<List<HasWord>> it = dp.iterator();
		while (it.hasNext()) {
		   StringBuilder sentenceSb = new StringBuilder();
		   List<HasWord> sentence = it.next();
		   for (HasWord token : sentence) {
		      if(sentenceSb.length()>1) {
		         sentenceSb.append(" ");
		      }
		      sentenceSb.append(token);
		   }
		   corpus.add("<s> " + sentenceSb.toString() + " </s>");
		}
		return corpus;
	}
	
	public static List<String> tokenizeEmails(LinkedList<Email> emailList)
	{
		LinkedList<String> corpus = new LinkedList<String>();
		
		for(Email email : emailList) {
			corpus.addAll(PreProcessor.tokenizeEmails(email));
		}
		return corpus;
	}
	
}
