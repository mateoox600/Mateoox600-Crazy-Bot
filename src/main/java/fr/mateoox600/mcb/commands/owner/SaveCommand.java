package fr.mateoox600.mcb.commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.mateoox600.mcb.MCB;

import java.io.IOException;
import java.util.Arrays;

public class SaveCommand extends Command {

    public SaveCommand() {
        this.name = "save";
        this.help = "Bot Owner Command: Save the bot data";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (e.getAuthor().getId().equals(MCB.config.getOwnerId())) {
            try {
                MCB.remindersManager.save();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.getChannel().sendMessage("You made a save of all the bot data").queue();
        }
    }

}
