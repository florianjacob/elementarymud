package elementarymud.client.inputparsing.actions;

import elementarymud.client.inputparsing.CommandScanner;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import marauroa.common.game.DetailLevel;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;
import marauroa.common.net.OutputSerializer;

/**
 *
 * @author raignarok
 */
public class SerializeAction extends Action {

	@Override
	public Set<String> actionVerbs() {
		Set<String> actionVerbs = new HashSet<>();
		actionVerbs.add("serialize");
		return actionVerbs;
	}

	@Override
	public boolean configure(CommandScanner scanner) {
		// ignore possible arguments atm
		return true;
	}

	@Override
	public RPAction execute() {
		try (OutputStream objectsOutStream = Files.newOutputStream(Paths.get("ZoneObjects.dat"))) {
			OutputSerializer objectsOut = new OutputSerializer(objectsOutStream);

			RPObject zoneObject = getZoneObjects().getMyCharacter().getZone();
			objectsOut.write(zoneObject.getID().getZoneID());
			objectsOut.write(zoneObject.getID().getObjectID());
			objectsOut.write(getZoneObjects().getMyCharacter().getCharacter().getID().getObjectID());
			objectsOut.write(getZoneObjects().getMyCharacter().getName());

			objectsOut.write(getZoneObjects().getAllObjects().size());
			Iterator<RPObject> oit = getZoneObjects().getAllObjects().iterator();
			while (oit.hasNext()) {
				RPObject object = oit.next();
				objectsOut.write(object.getID().getObjectID());
				object.writeObject(objectsOut, DetailLevel.FULL);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		try (OutputStream classesOutStream = Files.newOutputStream(Paths.get("RPClasses.dat"))) {
			OutputSerializer classesOut = new OutputSerializer(classesOutStream);

			int classes = RPClass.size();
			// sort out the default rp class if it is there
			for (Iterator<RPClass> it = RPClass.iterator(); it.hasNext();) {
				RPClass rp_class = it.next();
				if ("".equals(rp_class.getName())) {
					classes--;
					break;
				}
			}


			classesOut.write(classes);
			Iterator<RPClass> cit = RPClass.iterator();
			while (cit.hasNext()) {
				RPClass rp_class = cit.next();
				if (!"".equals(rp_class.getName())) // sort out default class if it
				// is there
				{
					classesOut.write(rp_class);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
