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
import java.util.LinkedList;
import java.util.List;
import junit.framework.Assert;

/**
 *
 * @author raignarok
 */
public class MockUI implements UI {

	private LinkedList<String> expectedLines = new LinkedList<>();

	@Override
	public void setCommandInterpreter(CommandInterpreter interpreter) {
	}

	@Override
	public void write(String text) {
		Assert.assertTrue(expectedLines.size() > 0);
		Assert.assertEquals(expectedLines.pollFirst(), text);
	}

	@Override
	public void writeln(String text) {
		write(text + "\n");
	}

	@Override
	public void start(String prompt) {
	}

	public void addExpectedLine(String line) {
		expectedLines.push(line);
	}
	
}
