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

package elementarymud.server;

import elementarymud.server.rpobjects.Bench;
import elementarymud.server.rpobjects.Character;
import elementarymud.server.rpobjects.Exit;
import elementarymud.server.rpobjects.LightPost;
import elementarymud.server.rpobjects.LongSword;
import elementarymud.server.rpobjects.ShortSword;
import elementarymud.server.rpobjects.Torch;
import elementarymud.server.rpobjects.ZoneRPObject;
import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;
import marauroa.server.game.rp.RPObjectFactory;

/**
 *
 * @author raignarok
 */
public class RPFactory extends RPObjectFactory {
	private static final Logger log = Log4J.getLogger(RPFactory.class);

	private static final RPFactory instance = new RPFactory();

	/**
	 * returns the factory instance (this method is called
	 * by Marauroa using reflection).
	 * 
	 * @return RPObjectFactory
	 */
	public static RPObjectFactory getFactory() {
		return instance;
	}

	@Override
	public RPObject transform(RPObject object) {
		
		final RPClass clazz = object.getRPClass();
		if (clazz == null) {
			log.error("Cannot create concrete object for " + object
					+ " because it does not have an RPClass.");
			return super.transform(object);
		}

		final String name = clazz.getName();
		if (name.equals(Character.getRPClassName())) {
			return new Character(object);
		} else if (name.equals(ZoneRPObject.getRPClassName())) {
			return new ZoneRPObject(object);
		} else if (name.equals(Bench.getRPClassName())) {
			return new Bench(object);
		} else if (name.equals(Exit.getRPClassName())) {
			return new Exit(object);
		} else if (name.equals(LightPost.getRPClassName())) {
			return new LightPost(object);
		} else if (name.equals(Torch.getRPClassName())) {
			return new Torch(object);
		} else if (name.equals(ShortSword.getRPClassName())) {
			return new ShortSword(object);	
		} else if (name.equals(LongSword.getRPClassName())) {
			return new LongSword(object);
		} else {
			log.warn("No suitable transformation found for rpclass: " + name);
			return super.transform(object);
		}
	}
	
}
