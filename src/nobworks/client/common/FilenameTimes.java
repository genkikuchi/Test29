package nobworks.client.common;

public class FilenameTimes {
	String filename;
	int count;
	public FilenameTimes() {}
	public FilenameTimes( String filename ) {
		this.filename = filename;
		this.count = 0;
	}
	public String getFilename() {
		return filename;
	}
	public int getCount() {
		return count;
	}
	public void addPlayCount() {
		count++;
	}
	public void resetCount() {
		count = 0;
	}
	public void setCount( int count ) {
		this.count = count;
	}
}
