package fr.mateoox600.mcb.commands.manager;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {

    List<Command> commands;
    CommandExecutor commandExecutor;
    String prefix;

    public CommandBuilder(String prefix){
        this.commands = new ArrayList<>();
        this.commandExecutor = new CommandExecutor(this);
        this.prefix = prefix;
    }

    public void addCommand(Command command){
        commands.add(command);
    }

    public void addCommands(Command... commands){
        for (Command command : commands) addCommand(command);
    }

    public CommandExecutor build(){
        return commandExecutor;
    }

}
