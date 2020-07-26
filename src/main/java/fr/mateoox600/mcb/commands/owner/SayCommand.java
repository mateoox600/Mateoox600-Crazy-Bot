package fr.mateoox600.mcb.commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.mateoox600.mcb.MCB;

import java.util.Arrays;

public class SayCommand extends Command {

    public SayCommand() {
        this.name = "say";
        this.help = "Bot Owner Command: Make the bot say things";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (e.getAuthor().getId().equals(MCB.config.getOwnerId())) {
            e.getChannel().sendMessage("```" + String.join(" ", args) + "```").queue(msg -> e.getMessage().delete().queue());
        } else {
            e.getChannel().sendMessage("```Error: You don't have the permission (Only bot owner command)```").queue();
        }
    }
}
