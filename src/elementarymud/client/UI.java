package elementarymud.client;

/**
 * A UI provides input and receives output.
 *
 * @author raignarok
 */
public interface UI {
	public void write(final String text);	
	public void writeln(final String text);
	/**
	 * Starts UI event loops, make UI visible etc.
	 */
	public void start();
}
