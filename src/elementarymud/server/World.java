package elementarymud.server;

import elementarymud.server.rpobjects.Bench;
import elementarymud.server.rpobjects.Character;
import elementarymud.server.rpobjects.Exit;
import elementarymud.server.rpobjects.LightPost;
import elementarymud.server.rpobjects.LongSword;
import elementarymud.server.rpobjects.MudRPObject;
import elementarymud.server.rpobjects.ShortSword;
import elementarymud.server.rpobjects.Torch;
import elementarymud.server.rpobjects.ZoneRPObject;
import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.IRPZone;
import marauroa.common.game.RPClass;
import marauroa.server.game.rp.RPWorld;

/**
 *
 * @author raignarok
 */
public class World extends RPWorld {
	private static final World instance = new World();
	private static final Logger log = Log4J.getLogger(World.class);

	public static final IRPZone.ID defaultZoneId = new IRPZone.ID("room");

	//TODO: andere get() falsch implementiert serverseitig?
	//TODO: im Tutorial verdammt nochmal ändern! initialize() der Elternklasse muss aufgerufen werden.
	public static World get() {
		return instance;
	}	

	//TODO: It's somewhat strange to have onInit() which gets called by the RPManager on creation and
	//initialize() which I have to call by myself. Maybe onInit() isn't allowed to block for long?
	// Maybe there is a difference between creating a completely new world and loading an existing one?
	@Override
	public void onInit() {
		super.onInit();
		super.initialize();
		
		PublicTextEvent.generateRPClass();
		PrivateTextEvent.generateRPClass();

		MudRPObject.generateRPClass();
		Character.generateRPClass();
		ZoneRPObject.generateRPClass();
		Exit.generateRPClass();
		Bench.generateRPClass();
		LightPost.generateRPClass();
		ShortSword.generateRPClass();
		LongSword.generateRPClass();
		Torch.generateRPClass();
		RPClass.bakeAll();

		RPZone plainRoom = new RPZone(defaultZoneId.getID(), "Plain Room", "a nondescript room");
		addRPZone(plainRoom);

		RPZone cave = new RPZone("cave", "Dark Cave", "a dark and creepy cave");
		addRPZone(cave);
		
		plainRoom.add(new Exit("north", "n", "a small path to the north", cave.getID()));
		cave.add(new Exit("south", "s", "a small path to the south", plainRoom.getID()));

		plainRoom.add(new Bench());
		plainRoom.add(new LightPost());
		cave.add(new ShortSword());
		cave.add(new LongSword());
		cave.add(new Torch());
	}
}
