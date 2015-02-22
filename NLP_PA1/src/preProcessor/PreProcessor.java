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
	private LinkedList<String> upSpeakEmails;
	private LinkedList<String> downSpeakEmails;
	private LinkedList<String> testEmails;
	
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
	
	public LinkedList<String> getTestEmails() {
		return testEmails;
	}
	/******************** Business Logic 
	 * @throws FileNotFoundException **********/
	public void process(String fileName, InputType inputType) throws FileNotFoundException
	{
		File inputFile = new File(fileName);
		
		if(!(inputFile.exists() && inputFile.isFile() && inputFile.canRead()))
		{
			System.out.println("Either the file doesn;t exist or it is not a file or can;t be read...!!!");
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
				if(line.contains("**START**"))
				{
					mailReadFlag = true;
					email = new StringBuilder();
				}
				else if(line.contains("**EOM**"))
				{
					mailReadFlag = false;
					if(upSpeak)
					{
						if(this.upSpeakEmails == null)
							this.upSpeakEmails = new LinkedList<String>();
						this.upSpeakEmails.add(email.toString());
						email = null;
						upSpeak = false;
					}
					else if(downSpeak)
					{
						if(this.downSpeakEmails == null)
							this.downSpeakEmails = new LinkedList<String>();
						this.downSpeakEmails.add(email.toString());
						email = null;
						downSpeak = false;
					}
					else //if(!upSpeak && !downSpeak)
					{
						if(this.testEmails == null)
							this.testEmails = new LinkedList<String>();
						this.testEmails.add(email.toString());
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
					if (mailReadFlag && email != null)
						email.append(line);
				}
				
			}
			
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Unable to read the file. Some problem with the IO.");
			e.printStackTrace();
		}
	}
	
	public static LinkedList<String> tokenizeEmails(LinkedList<String> emailList)
	{
		LinkedList<String> corpus = null;
		for(String email : emailList)
		{
			String[] emailCorpus = email.split("\\.");
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
