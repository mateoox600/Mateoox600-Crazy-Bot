package fr.mateoox600.mcb.commands.manager;

public abstract class Command {

    public String name = "no_name";
    public String[] aliases = new String[] {};
    public String help = "No help for this command";
    public String arguments = "";
    public boolean owner = false;
    public boolean inHelp = true;
    public String guildOnly = "no";

    protected abstract void execute(CommandEvent event);

}
