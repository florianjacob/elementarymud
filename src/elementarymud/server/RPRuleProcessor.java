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

import elementarymud.server.rpobjects.Character;
import elementarymud.server.rpobjects.MudRPObject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.crypto.Hash;
import marauroa.common.game.AccountResult;
import marauroa.common.game.CharacterResult;
import marauroa.common.game.IRPZone;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPObjectInvalidException;
import marauroa.common.game.RPObjectNotFoundException;
import marauroa.common.game.Result;
import marauroa.server.db.DBTransaction;
import marauroa.server.db.TransactionPool;
import marauroa.server.game.db.AccountDAO;
import marauroa.server.game.db.CharacterDAO;
import marauroa.server.game.db.DAORegister;
import marauroa.server.game.rp.IRPRuleProcessor;
import marauroa.server.game.rp.RPServerManager;

/**
 *
 * @author raignarok
 */
public class RPRuleProcessor implements IRPRuleProcessor {

	private static final Logger log = Log4J.getLogger(RPRuleProcessor.class);
	private static final RPRuleProcessor instance = new RPRuleProcessor();
	private World world = World.get();
	private RPServerManager manager;

	public static IRPRuleProcessor get() {
		return instance;
	}

	@Override
	public void setContext(RPServerManager rpman) {
		manager = rpman;
	}

	@Override
	public boolean checkGameVersion(String game, String version) {
		return game.equals("Chat");
	}

	//TODO: in Marauroa heißt der Parameter object
	@Override
	public synchronized boolean onInit(RPObject character) throws RPObjectInvalidException {
		IRPZone zone = world.getRPZone(World.defaultZoneId);
		zone.add(character);
		return true;
	}

	//TODO: in Marauroa heißt der Parameter object
	@Override
	public synchronized void onTimeout(RPObject character) throws RPObjectNotFoundException {
		onExit(character);
	}

	//TODO: in Marauroa heißt der Parameter object
	@Override
	public synchronized boolean onExit(RPObject character) throws RPObjectNotFoundException {
		world.remove(character.getID());
		return true;
	}

	@Override
	public void beginTurn() {
	}

	//TODO: in Marauroa heißt caster object
	@Override
	public boolean onActionAdd(RPObject caster, RPAction action, List<RPAction> actionList) {
		return true;
	}

	@Override
	public void endTurn() {
	}

	// TODO: caster heißt in Marauroa object
	@Override
	public void execute(RPObject caster, RPAction action) {
		if (caster instanceof Character) {
			Character character = (Character) caster;
			IRPZone.ID zoneId = character.getZone().getID();
			int objectId;
			RPObject.ID targetId;
			RPObject target;

			switch (action.get("verb")) {
				case "say":
					character.say(action.get("words"));
					break;
				case "tell":
				case "sayto":
					objectId = Integer.parseInt(action.get("target"));
					targetId = new RPObject.ID(objectId, zoneId);
					target = character.getZone().get(targetId);
					if (target == null) {
						character.sendPrivateText("Your target isn't here.");
					} else {
						//TODO: proper targeted speaking here!
						character.say("to " + target.get("name") + ": " + action.get("words"));
					}
					break;
				case "go":
					String exit = action.get("target");
					RPZone oldZone = (RPZone) character.getZone();
					if (!oldZone.hasExit(exit)) {
						character.sendPrivateText("Exit " + exit + " doesn't exist.");
					}
					world.modify(caster);
					world.changeZone(oldZone.getExit(exit).getTargetZoneId(), caster);
					break;
				case "take":
					objectId = Integer.parseInt(action.get("target"));
					targetId = new RPObject.ID(objectId, zoneId);
					target = character.getZone().get(targetId);
					if (target == null) {
						character.sendPrivateText("Your target doesn't exist.");
					} else if(!((MudRPObject) target).isTakeableBy(character)) {
						character.sendPrivateText("You can't put that into your inventory.");
					} else {
						world.modify(character);
						world.modify(target);
						character.getZone().remove(targetId);
						character.addToInventory(target);
					}
					break;
				case "drop":
					objectId = Integer.parseInt(action.get("target"));
					// the targetId is a SlodID, so the zoneID part (set to an empty string here) will be ignored anyway
					targetId = new RPObject.ID(objectId, "");
					target = character.getFromInventory(targetId);
					if (target == null) {
						character.sendPrivateText("Your target doesn't exist.");
					} else {
						world.modify(character);
						character.removeFromInventory(targetId);
						character.getZone().add(target);
					}
					break;
				case "desc":
					// not reachable at the moment
					world.modify(caster);
					character.setDescription(action.get("remainder"));
					break;
				default:
					character.sendPrivateText("Unknown action verb " + action.get("verb"));
					break;
			}

		} else {
			log.error("Something strange wantet to send actions: " + caster.getClass());
		}

	}

	@Override
	public AccountResult createAccount(String username, String password, String email) {
		TransactionPool transactionPool = TransactionPool.get();
		DBTransaction trans = transactionPool.beginWork();
		AccountDAO accountDAO = DAORegister.get().get(AccountDAO.class);
		try {
			if (accountDAO.hasPlayer(trans, username)) {
				return new AccountResult(Result.FAILED_PLAYER_EXISTS, username);
			}
			accountDAO.addPlayer(trans, username, Hash.hash(password), email);
			transactionPool.commit(trans);
			return new AccountResult(Result.OK_CREATED, username);
		} catch (SQLException e) {
			transactionPool.rollback(trans);

			return new AccountResult(Result.FAILED_EXCEPTION, username);
		}
	}

	@Override
	public CharacterResult createCharacter(String username, String characterName, RPObject template) {
		TransactionPool transactionPool = TransactionPool.get();
		DBTransaction trans = transactionPool.beginWork();
		CharacterDAO characterDAO = DAORegister.get().get(CharacterDAO.class);
		try {
			if (characterDAO.hasCharacter(trans, username, characterName)) {
				return new CharacterResult(Result.FAILED_CHARACTER_EXISTS, characterName, template);
			}
			IRPZone zone = world.getRPZone(World.defaultZoneId);
			// ignoring the template completely..
			RPObject character = new Character(characterName, "seeker of the sword");
			//TODO: Why does the zone need to be set here and in onInit()?
			zone.assignRPObjectID(character);
			characterDAO.addCharacter(username, characterName, character);
			transactionPool.commit(trans);
			// error in example: should return the newly-created object and not the template here
			return new CharacterResult(Result.OK_CREATED, characterName, character);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			transactionPool.rollback(trans);
			return new CharacterResult(Result.FAILED_EXCEPTION, characterName, template);
		}
	}
}
