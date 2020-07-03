package fr.mateoox600.mcb.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Member;

import java.util.Arrays;

public class ReportCommand extends Command {

    public ReportCommand() {
        this.name = "report";
        this.help = "Use for reporting some bad people (report are send to a channel that the staff can read)";
        this.arguments = "<member> <reason>";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length > 1) {
            Member target = e.getMessage().getMentionedMembers().get(0);
            if (target != null) {
                if (target.getIdLong() != e.getAuthor().getIdLong() && !target.getUser().isBot()) {
                    StringBuilder reason = new StringBuilder();
                    for (String arg : Arrays.copyOfRange(args, 1, args.length)) reason.append(arg).append(" ");
                    e.getJDA().getTextChannelById("718525074168217600").sendMessage("<@" + e.getAuthor().getId() + "> a report <@" + target.getId() + "> pour: ```" + reason + "```").queue(msg -> msg.addReaction("?:x:").queue(reac -> e.getChannel().sendMessage("Vous avez report <@" + target.getNickname() + ">").queue()));
                } else {
                    e.getChannel().sendMessage("```Error: You can't report that user```").queue();
                }
            } else {
                e.getChannel().sendMessage("```Invalid user, usage: .kick <mention> [reason]```").queue();
            }
        } else {
            e.getChannel().sendMessage("```Correct usage: .kick <mention> [reason]```").queue();
        }
    }

}