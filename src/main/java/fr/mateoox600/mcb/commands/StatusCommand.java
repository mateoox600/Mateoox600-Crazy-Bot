package fr.mateoox600.mcb.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.mateoox600.mcb.MCB;
import net.dv8tion.jda.api.entities.Activity;

import java.util.Arrays;

public class StatusCommand extends Command {

    public StatusCommand() {
        this.name = "status";
        this.help = "Bot Owner Command: Change bot status";
        this.aliases = new String[]{"stats", "stat"};
        this.arguments = "<new status>";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length > 0) {
            if (e.getAuthor().getId().equals(MCB.config.getOwnerId())) {
                StringBuilder builder = new StringBuilder();
                for (String arg : args) {
                    builder.append(arg).append(" ");
                }
                String newStatus = MCB.config.setStatus(builder.toString());
                e.getJDA().getPresence().setActivity(Activity.playing(newStatus));
                e.getChannel().sendMessage("You change the bot status to ```" + newStatus + "```").queue();
            } else {
                e.getChannel().sendMessage("```Error: You don't have the permission (only bot owner command)```").queue();
                StringBuilder builder = new StringBuilder("(In Dev)");
                for (String arg : args) {
                    builder.append(" ").append(arg);
                }
                MCB.logger.logStatusChangeNoPerm(e.getMember(), builder.toString());
            }
        } else {
            e.getChannel().sendMessage("```Correct usage: .status <new status>```").queue();
        }
    }

}
