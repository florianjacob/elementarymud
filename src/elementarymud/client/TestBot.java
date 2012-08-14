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

	private Timer timer;
	private int counter = 500;

	@Override
	public void start() {
		timer = new Timer(1000, this);
		timer.setInitialDelay(500);
		timer.start();
	}

	@Override
	public void actionPerformed(final ActionEvent ae) {
		CommandInterpreter.onInput("say This is TestBot, running and talking for " + counter + "ms.");
		counter += 1000;
	}

	@Override
	public void write(final String text) {
		System.out.print(text);
	}

	@Override
	public void writeln(final String text) {
		write(text);
		System.out.println();
	}
}
