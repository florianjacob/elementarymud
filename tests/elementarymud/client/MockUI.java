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
