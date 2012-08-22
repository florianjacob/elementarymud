package elementarymud.client;

/**
 * A UI provides receives output and gets initialized after
 * the connection to the server is established.
 *
 * @author raignarok
 */
public interface UI {
	/**
	 * @param text the plain text String to show the user
	 */
	public void write(final String text);	

	/**
	 * @param text the plain text String to show the user which will be appended with a newline
	 */
	public void writeln(final String text);

	/**
	 * Gets called after the client connection is established.
	 * Implement this to start UI event loops, make the UI visible etc.
	 */
	public void start();
}
