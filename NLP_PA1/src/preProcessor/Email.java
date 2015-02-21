package preProcessor;

public class Email 
{
	private String email;
	private int id;
	private boolean stream;
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
	public boolean isStream() {
		return stream;
	}
	public void setStream(boolean stream) {
		this.stream = stream;
	}
	
	
}
