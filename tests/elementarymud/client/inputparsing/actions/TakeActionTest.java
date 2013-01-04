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

package elementarymud.client.inputparsing.actions;

import elementarymud.client.MockUI;
import elementarymud.client.PopulatedZoneObjects;
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
