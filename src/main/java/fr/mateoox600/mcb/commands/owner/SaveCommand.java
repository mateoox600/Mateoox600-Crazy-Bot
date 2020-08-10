package fr.mateoox600.mcb.commands.owner;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;

import java.io.IOException;
import java.util.Arrays;

public class SaveCommand extends Command {

    public SaveCommand() {
        this.name = "save";
        this.help = "Save the bot data";
        this.owner = true;
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        try {
            MCB.remindersManager.save();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        e.getChannel().sendMessage("You made a save of all the bot data").queue();
    }

}
