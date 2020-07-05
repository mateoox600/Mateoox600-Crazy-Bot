package fr.mateoox600.mcb.commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.mateoox600.mcb.MCB;

import java.io.IOException;
import java.util.Arrays;

public class StopCommand extends Command {

    public StopCommand() {
        this.name = "stop";
        this.help = "Bot Owner Command: Stop the bot";
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
            e.getChannel().sendMessage("reminders saved").queue(msg -> e.getChannel().sendMessage("jda shutdown").queue(msg1 -> e.getJDA().shutdown()));
            int exitCode = 0;
            try {
                if (args.length > 0) exitCode = Integer.parseInt(args[0]);
            } catch (Exception ignored) {
            }
            System.exit(exitCode);
        }
    }

}
