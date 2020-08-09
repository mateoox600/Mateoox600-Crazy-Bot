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
        System.out.println("[INFO] Command add: " + command.name);
    }

    public void addCommands(Command... commands){
        System.out.println("[INFO] Command loading start");
        for (Command command : commands) {
            addCommand(command);
        }
        System.out.println("[INFO] Commands loaded");
    }

    public CommandExecutor build(){
        return commandExecutor;
    }

}
