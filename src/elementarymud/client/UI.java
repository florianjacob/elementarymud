/**
 * This file is part of ElementaryMUD.
 *
 * ElementaryMUD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ElementaryMUD is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ElementaryMUD. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2012 Florian Jacob
 */

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
