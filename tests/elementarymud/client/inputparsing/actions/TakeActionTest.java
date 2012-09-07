package elementarymud.client.inputparsing.actions;

import elementarymud.client.MockUI;
import elementarymud.client.PopulatedZoneObjects;
import elementarymud.client.UI;
import elementarymud.client.ZoneObjects;
import elementarymud.client.inputparsing.CommandScanner;
import marauroa.common.game.RPAction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author raignarok
 */
public class TakeActionTest {

	private static ZoneObjects zoneObjects;
	private static ActionRepository actions;
	private static MockUI ui;

	@BeforeClass
	public static void setUp() {
		zoneObjects = new PopulatedZoneObjects();	
		ui = new MockUI();
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
		ui.addExpectedLine("Usage: take <object>\n");
		Assert.assertFalse("TakeAction with nonexisting target returns true", action.configure(scanner));
	}


	@Test
	public void testWrongTarget() {
		CommandScanner scanner = new CommandScanner(zoneObjects, actions, "take the blablub");
		Action action = scanner.firstWordAction();
		ui.addExpectedLine("Usage: take <object>\n");
		Assert.assertFalse("TakeAction with nonexisting target returns true", action.configure(scanner));

	}

	@Test
	public void testTakeItem() {
		CommandScanner scanner = new CommandScanner(zoneObjects, actions, "take lightpost");
		Action action = scanner.firstWordAction();
		boolean successful = action.configure(scanner);
		Assert.assertTrue("TakeAction with existing target returns false", successful);

		RPAction rpAction = action.execute();
		Assert.assertEquals(rpAction.get("verb"), "take");
		Assert.assertEquals(rpAction.get("target"), "34");
	}
	
}
