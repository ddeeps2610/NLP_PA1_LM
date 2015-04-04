package preProcessor;

/**
 * @author Deepak
 * Defines the object for individual email. 
 * Stores the id of the email, speak order along with 
 * the probability of the email for upStream and DownStream Models *
 */
public class Email 
{
	/************************* State ********************************/
	private String email;
	private int id;
	private SpeakOrder speak;
	private double upStreamProb;
	private double downStremProb;
	
	/************************* Setters and Getters *********************/
	public double getUpStreamProb() {
		return upStreamProb;
	}
	public void setUpStreamProb(double upStreamProb) {
		this.upStreamProb = upStreamProb;
	}
	public double getDownStremProb() {
		return downStremProb;
	}
	public void setDownStremProb(double downStremProb) {
		this.downStremProb = downStremProb;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SpeakOrder getSpeak() {
		return speak;
	}
	public void setSpeak(SpeakOrder speak) {
		this.speak = speak;
	}
	
	
	
}
