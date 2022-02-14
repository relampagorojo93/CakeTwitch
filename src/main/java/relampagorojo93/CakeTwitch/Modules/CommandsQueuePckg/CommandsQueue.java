package relampagorojo93.CakeTwitch.Modules.CommandsQueuePckg;

import java.util.ArrayList;
import java.util.List;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Enums.CommandStatus;
import relampagorojo93.CakeTwitch.Modules.ConfigPckg.Configurations.Commands.ExecutableCommand;
import relampagorojo93.LibsCollection.SpigotThreads.Objects.Thread;

public class CommandsQueue {
	
	private List<ExecutableCommand> commands = new ArrayList<>();
	
	private boolean process = false;

	private Thread thread;

	public void processCommands() {
		if (!this.process) {
			this.process = true;
			this.thread = CakeTwitchAPI.getThreadManager()
					.registerThread(new Thread(new Thread.Runnable() {
						public void run() {
							try {
								while (CommandsQueue.this.process && !CommandsQueue.this.commands.isEmpty()) {
									ExecutableCommand cmd = CommandsQueue.this.commands.get(0);
									if (cmd.checkConditions() != CommandStatus.QUEUE)
										break;
									cmd.execute();
									CommandsQueue.this.commands.remove(0);
								}
								CommandsQueue.this.process = false;
							} catch (Exception exception) {
							}
						}

						public void output(Object output) {
						}
					}));
			this.thread.startSecure();
		}
	}

	public void addCommands(List<ExecutableCommand> commands) {
		if (!commands.isEmpty()) {
			this.commands.addAll(commands);
			processCommands();
		}
	}

	public void addCommand(ExecutableCommand command) {
		this.commands.add(command);
		processCommands();
	}

	public void stopCommands() {
		this.process = false;
		if (this.thread != null)
			this.thread.stopSecure();
	}

	public void clearCommands() {
		stopCommands();
		this.commands.clear();
	}
	
}
