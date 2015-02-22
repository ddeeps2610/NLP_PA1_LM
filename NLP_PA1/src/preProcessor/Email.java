package preProcessor;

public class Email 
{
	private String email;
	private int id;
	private SpeakOrder speak;
	private double upStreamProb;
	private double downStremProb;
	
	
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
