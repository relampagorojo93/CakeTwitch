package relampagorojo93.CakeTwitch.Modules.ConfigPckg.Configurations.Options;

import java.util.List;

import relampagorojo93.CakeTwitch.Modules.ConfigPckg.Configurations.Condition;
import relampagorojo93.CakeTwitch.Modules.ConfigPckg.Configurations.Commands.CommandList;

public class Option {
	private List<Condition> conditions;
	private CommandList commands;
	public Option(List<Condition> conditions, CommandList commands) {
		this.conditions = conditions;
		this.commands = commands;
	}
	public boolean matchConditions(int value) {
		for (Condition condition:conditions) if (!condition.matchCondition(value)) return false;
		return true;
	}
	public List<Condition> getConditions() {
		return conditions;
	}
	public CommandList getCommandList() {
		return commands;
	}
}
