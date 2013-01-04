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

	private CommandInterpreter commandInterpreter;

	@Override
	public void start(String prompt) {
		timer = new Timer(1000, this);
		timer.setInitialDelay(500);
		timer.start();
	}

	@Override
	public void actionPerformed(final ActionEvent ae) {
		commandInterpreter.onInput("say This is TestBot, running and talking for " + counter + "ms.");
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

	@Override
	public void setCommandInterpreter(final CommandInterpreter interpreter) {
		this.commandInterpreter = interpreter;
	}
}
