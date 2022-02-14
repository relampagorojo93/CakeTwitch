package relampagorojo93.CakeTwitch.Modules.ConfigPckg.Configurations.Options.Subscriptions;

import java.util.List;

import relampagorojo93.CakeTwitch.Modules.ConfigPckg.Configurations.Condition;
import relampagorojo93.CakeTwitch.Modules.ConfigPckg.Configurations.Commands.CommandList;
import relampagorojo93.CakeTwitch.Modules.ConfigPckg.Configurations.Options.Option;

public class SubscriptionOption extends Option {
	private CommandList ifgifted;
	public SubscriptionOption(List<Condition> conditions, CommandList commands, CommandList ifgifted) {
		super(conditions,commands);
		this.ifgifted = ifgifted; 
	}
	public CommandList getCommands(boolean gifted) {
		return gifted ? ifgifted : getCommandList();
	}
}
