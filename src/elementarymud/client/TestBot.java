package elementarymud.client;

import elementarymud.client.inputparsing.CommandInterpreter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author raignarok
 */
public class TestBot implements UI, ActionListener {

	private CommandInterpreter commandInterpreter;
	private Timer timer;
	private int counter = 500;

	@Override
	public void start() {
		timer = new Timer(1000, this);
		timer.setInitialDelay(500);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		commandInterpreter.onInput("say This is TestBot, running and talking for " + counter + "ms.");
		counter += 1000;
	}

	@Override
	public void write(String text) {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) {
		write(text);
		System.out.println();
	}

	@Override
	public void setCommandInterpreter(CommandInterpreter commandInterpreter) {
		this.commandInterpreter = commandInterpreter;
	}
}
