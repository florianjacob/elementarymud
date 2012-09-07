package elementarymud.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;
import marauroa.common.net.InputSerializer;

/**
 *
 * @author raignarok
 */
public class PopulatedZoneObjects extends ZoneObjects {

	private Logger log = Log4J.getLogger(PopulatedZoneObjects.class);

	public PopulatedZoneObjects() {
		populateZone();
	}

	// These values are copied from the default current game start room on 2012-09-7
	private void populateZone() {
		Map<RPObject.ID, RPObject> map = getInternalObjectsMap();

		try (InputStream classesInputStream = Files.newInputStream(Paths.get("RPClasses.dat"))) {
			InputSerializer inputSerializer = new InputSerializer(classesInputStream);

			int numberOfClasses = inputSerializer.readInt();
			log.info("Loading " + numberOfClasses + " classes:");
			for (int i = 0; i < numberOfClasses; i++) {
				RPClass clazz = (RPClass) inputSerializer.readObject(new RPClass());
				log.info(clazz.getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (InputStream objectsInputStream = Files.newInputStream(Paths.get("ZoneObjects.dat"))) {
			InputSerializer inputSerializer = new InputSerializer(objectsInputStream);

			String zoneId = inputSerializer.readString();
			int zoneObjectId = inputSerializer.readInt();
			int myCharacterId = inputSerializer.readInt();
			String characterName = inputSerializer.readString();

			int numberOfObjects = inputSerializer.readInt();
			log.info("Loading " + numberOfObjects + " objects for zone "
					+ zoneId + "." + zoneObjectId + ":");
			for (int i = 0; i < numberOfObjects; i++) {
				int objectId = inputSerializer.readInt();
				RPObject object = (RPObject) inputSerializer.readObject(new RPObject());
				RPObject.ID id = new RPObject.ID(objectId, zoneId);
				object.setID(id);
				map.put(id, object);
				log.info(object.getID().getObjectID() + ": " + object.get("name"));
			}

			RPObject character = getObject(new RPObject.ID(myCharacterId, zoneId));
			log.info("MyCharacter is: " + character.get("name"));
			getMyCharacter().setName(characterName);
			getMyCharacter().setCharacter(character);
			getMyCharacter().setZone(map.get(new RPObject.ID(zoneObjectId, zoneId)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
