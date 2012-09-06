package elementarymud.client;

import elementarymud.client.inputparsing.CommandInterpreter;

/**
 * A UI provides receives output and gets initialized after
 * the connection to the server is established.
 *
 * @author raignarok
 */
public interface UI {
	/**
	 * @param interpreter the CommandInterpreter the UI should send input to
	 */
	public void setCommandInterpreter(final CommandInterpreter interpreter);
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
	 * 
	 * @param prompt the prompt to display to the user
	 */
	public void start(String prompt);
}
