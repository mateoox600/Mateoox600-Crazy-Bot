package fr.mateoox600.mcb.commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.Arrays;

public class ReportCommand extends Command {

    public ReportCommand() {
        this.name = "report";
        this.help = "Use for reporting bug on the bot or feature you wan't to be add to the bot";
        this.arguments = "<features/suggestions/bug>";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length > 0) {
            StringBuilder reason = new StringBuilder();
            for (String arg : args) reason.append(arg).append(" ");
            e.getJDA().getTextChannelById("718525074168217600").sendMessage(e.getAuthor().getAsMention() + " a fait un report pour: ```" + reason + "```").queue(msg -> e.getChannel().sendMessage("Vous avez report une features/ une suggestions/ un bug").queue());
        } else {
            e.getChannel().sendMessage("```Correct usage: .kick <mention> [reason]```").queue();
        }
    }

}