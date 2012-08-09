package elementarymud.client.inputparsing.actions;

import elementarymud.client.inputparsing.Command;
import elementarymud.client.inputparsing.CommandParser;
import java.util.Set;

/**
 *
 * @author raignarok
 */
public interface Action {

	public Set<String> actionVerbs();

	public void parse(CommandParser parser, Command command);

	public void execute(Command sentence);
	
}
