package elementarymud.client;

/**
 * A UI provides input and receives output.
 *
 * @author raignarok
 */
public interface UI {
	public void write(String text);	
	public void writeln(String text);
	/**
	 * Starts UI event loops, make UI visible etc.
	 */
	public void start();
}
