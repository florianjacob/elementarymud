package elementarymud.server.rpobjects;

import elementarymud.server.PrivateTextEvent;
import elementarymud.server.PublicTextEvent;
import elementarymud.server.rpobjects.Character;
import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.Definition;
import marauroa.common.game.IRPZone;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public class Character extends MudRPObject {
	private static final Logger log = Log4J.getLogger(Character.class);

	private static final String RPCLASSNAME = "character";

	private IRPZone zone;
	private IRPZone lastZone;

	public Character(String name, String description) {
		super(name, description);
		setRPClass(RPCLASSNAME);
	}

	public Character(RPObject template) {
		super(template);
	}

	public void sendPrivateText(final String text) {
		addEvent(new PrivateTextEvent(text));
		notifyWorldAboutChanges();
	}

	public void say(final String text) {
		addEvent(new PublicTextEvent(text));
		notifyWorldAboutChanges();
	}

	public IRPZone getZone() {
		return lastZone;
	}

	public static void generateRPClass() {
		RPClass character = new RPClass(RPCLASSNAME);
		character.isA(MudRPObject.getRPClassName());
		character.addRPEvent("private_text", Definition.PRIVATE);
		character.addRPEvent("public_text", Definition.STANDARD);
	}

	public static String getRPClassName() {
		return RPCLASSNAME;
	}

	public void onAdded(final IRPZone zone) {
		if (this.zone != null) {
			log.error("Character added to zone " + zone + " while he was still in zone " + this.zone, new Throwable());
		}

		this.zone = zone;
		this.lastZone = zone;
	}

	/**
	 * Called when this object is being removed from a zone.
	 *
	 * @param zone
	 *            The zone this will be removed from.
	 */
	public void onRemoved(final IRPZone zone) {
		if (this.zone != zone) {
			log.error("Entity removed from wrong zone " + zone + " but it thinks it is in " + this.zone + ": ", new Throwable());
		}

		this.zone = null;
	}	

	/**
	 * Notifies the SimpleMudRPWorld that this character's attributes have changed.
	 *
	 */
	public void notifyWorldAboutChanges() {
		// Only possible if in a zone
		if (getZone() != null) {
			getZone().modify(this);
		}
	}	
	
}
