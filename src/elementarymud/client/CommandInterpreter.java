package elementarymud.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class CommandInterpreter {

	public void onInput(String input) {
		UI ui = Client.get().getUI();
		MyCharacter myCharacter = Client.get().getMyCharacter();
		Map<RPObject.ID, RPObject> zoneObjects = Client.get().getZoneObjects();

		String[] command = input.split(" ");
		if (command.length > 0) {
			String verb = command[0];
			if (verb.equals("look")) {
				if (command.length == 1) {
					// just look around in the zone
					RPObject zone = myCharacter.getZone();
					StringBuilder output = new StringBuilder();
					output.append("You are in ").append(zone.get("description")).append(".");

					List<RPObject> otherPlayers = getPlayersExcluding(myCharacter.getCharacter());

					if (!otherPlayers.isEmpty()) {
						output.append("\n");
						if (otherPlayers.size() == 1) {
							output.append("Also in here is ");
						} else {
							output.append("Also in here are ");
						}
						appendPrettyList(output, otherPlayers);
						output.append(".");
					}	

					ui.writeln(output.toString());
				} else {
					// look at something specific
					String objectName = command[1];
					List<RPObject> results = new LinkedList<RPObject>();
					for (RPObject object : zoneObjects.values()) {
						if (object.get("name").startsWith(objectName)) {
							results.add(object);
						}
					}	

					if (results.size() != 1) {
						ui.writeln("Do you want to look at: ");
						for (RPObject object : results) {
							ui.writeln(object.get("name"));
						}
						ui.writeln("?");
					} else {
						RPObject object = results.get(0);
						ui.writeln("You see " + object.get("name") + ", " + object.get("description") + ".");
					}
				}
			} else {
				// unknown local command, sending to server..
				RPAction action = new RPAction();
				action.put("verb", verb);	
				if (command.length > 1) {
					action.put("remainder", input.substring(input.indexOf(" ") + 1));
				}
				Client.get().send(action);
			}
		}
	}
	    /**
     * Appends the names of the {@code SwordWorldObject}s in the list
     * to the builder, separated by commas, with an "and" before the final
     * item.
     *
     * @param builder the {@code StringBuilder} to append to
     * @param list the list of items to format
     */
    private void appendPrettyList(StringBuilder builder, List<RPObject> list)
    {
        if (list.isEmpty()) {
            return;
        }

        int lastIndex = list.size() - 1;
        RPObject last = list.get(lastIndex);

        Iterator<RPObject> it = list.subList(0, lastIndex).iterator();
        if (it.hasNext()) {
            RPObject other = it.next();
            builder.append(other.get("name"));
            while (it.hasNext()) {
                other = it.next();
                builder.append(" ,");
                builder.append(other.get("name"));
            }
            builder.append(" and ");
        }
        builder.append(last.get("name"));
    }	

    /**
     * Returns a list of players in this room excluding the given
     * player.
     *
     * @param player the player to exclude
     * @return the list of players
     */
    private List<RPObject> getPlayersExcluding(RPObject player) {
		MyCharacter myCharacter = Client.get().getMyCharacter();
		Map<RPObject.ID, RPObject> zoneObjects = Client.get().getZoneObjects();

        ArrayList<RPObject> otherPlayers = new ArrayList<RPObject>(zoneObjects.size());
		for (RPObject object : zoneObjects.values()) {
			if (object.instanceOf(RPClass.getRPClass("character"))
					&& !object.getID().equals(myCharacter.getCharacter().getID())) {
				otherPlayers.add(object);
			}
		}

        if (otherPlayers.isEmpty()) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(otherPlayers);
    }
}
