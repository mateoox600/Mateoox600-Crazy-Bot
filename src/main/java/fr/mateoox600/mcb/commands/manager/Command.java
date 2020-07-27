package fr.mateoox600.mcb.commands.manager;

public abstract class Command {

    public String name = "no_name";
    public String[] aliases = new String[] {};
    public String help = "No help for this command";
    public String arguments = "";

    protected abstract void execute(CommandEvent event);

}
