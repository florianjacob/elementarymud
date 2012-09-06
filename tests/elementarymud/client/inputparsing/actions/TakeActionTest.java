package elementarymud.client.inputparsing.actions;

import elementarymud.client.UI;
import elementarymud.client.ZoneObjects;
import elementarymud.client.inputparsing.CommandInterpreter;
import elementarymud.client.inputparsing.CommandScanner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;

/**
 *
 * @author raignarok
 */
public class TakeActionTest {

	private static ZoneObjects zoneObjects;
	private static ActionRepository actions;
	private static UI ui;

	@BeforeClass
	public static void setUp() {
		zoneObjects = new ZoneObjects();	
		ui = new UI() {

			@Override
			public void setCommandInterpreter(CommandInterpreter interpreter) {
			}

			@Override
			public void write(String text) {
				System.out.print(text);
			}

			@Override
			public void writeln(String text) {
				write(text + "\n");
			}

			@Override
			public void start(String prompt) {
			}

		};
		actions = new ActionRepository(zoneObjects, ui);
	}

	@Test
	public void testActionExists() {
		CommandScanner scanner = new CommandScanner(zoneObjects, actions, "take");
		Action action = scanner.firstWordAction();
		Assert.assertNotNull(action);
		Assert.assertTrue(action instanceof TakeAction);
	}

	@Test
	public void testNoTarget() {
		CommandScanner scanner = new CommandScanner(zoneObjects, actions, "take");
		Action action = scanner.firstWordAction();
		Assert.assertFalse("Action wrongly configured but still returns true",
				action.configure(scanner));
	}

	@Test
	public void testWrongTarget() {
		CommandScanner scanner = new CommandScanner(zoneObjects, actions, "take the blablub");
		Action action = scanner.firstWordAction();
		Assert.assertFalse("TakeAction with nonexisting target returns true", action.configure(scanner));

	}
	
}
