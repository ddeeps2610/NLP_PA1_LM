package preProcessor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

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
			this.downTrain = this.tokenizeEmails(this.downSpeakEmails);
			this.upTrain = this.tokenizeEmails(this.upSpeakEmails);
			break;
		case validation:
			this.downValidation= this.tokenizeEmails(this.downSpeakEmails);
			this.upValidation = this.tokenizeEmails(this.upSpeakEmails);
			break;
		case test:
			this.upDownTest = this.tokenizeEmails(this.testEmails);
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
			while((line = this.input.readLine()) != null)
			{
				Email newEmail  = null;
				int id = 0;
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
	
	public static LinkedList<String> tokenizeEmails(LinkedList<Email> emailList)
	{
		LinkedList<String> corpus = null;
		for(Email email : emailList)
		{
			String[] emailCorpus = email.getEmail().split("\\.");
			for(String sentence: emailCorpus)
			{
				if(sentence != null && sentence.length() > 0)
				{
					StringBuilder sentenceToken = new StringBuilder("<s> ");
					sentenceToken.append(sentence);
					sentenceToken.append(" </s>");
					
					if(corpus == null)
						corpus = new LinkedList<String>();
					corpus.add(sentenceToken.toString());
					sentenceToken = null;
				}
			}
		}
		return corpus;
	}
	
	public static LinkedList<String> tokenizeEmails(String email)
	{
		return null;
		
	}
}
