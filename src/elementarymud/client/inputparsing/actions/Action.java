package elementarymud.client.inputparsing.actions;

import elementarymud.client.UI;
import elementarymud.client.ZoneObjects;
import elementarymud.client.inputparsing.CommandScanner;
import java.util.Set;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;

/**
 *
 * @author raignarok
 */
public abstract class Action implements Cloneable {

	private RPObject target = null;
	private UI ui;
	private ZoneObjects zoneObjects;

	protected void init(ZoneObjects zoneObjects, UI ui) {
		this.ui = ui;
		this.zoneObjects = zoneObjects;
	}

	protected UI getUI() {
		return ui;
	}

	protected ZoneObjects getZoneObjects() {
		return zoneObjects;
	}


	public abstract Set<String> actionVerbs();

	public abstract boolean configure(CommandScanner scanner);

	public abstract RPAction execute();

	@Override
	public Action clone() {
		try {
			return (Action) super.clone();
		} catch (CloneNotSupportedException ex) {
			// This should never happen, as Action implements Cloneable..
			ex.printStackTrace();
			return null;
		}
	}

	public RPObject.ID getTargetID() {
		if (hasTarget()) {
			return target.getID();
		} else {
			return null;
		}
	}

	public boolean hasTarget() {
		return target != null;
	}

	public RPObject getTarget() {
		return target;
	}

	public void setTarget(RPObject target) {
		this.target = target;
	}
	
}
