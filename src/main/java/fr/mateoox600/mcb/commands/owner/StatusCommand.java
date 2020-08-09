package fr.mateoox600.mcb.commands.owner;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;
import net.dv8tion.jda.api.entities.Activity;

import java.util.Arrays;

public class StatusCommand extends Command {

    public StatusCommand() {
        this.name = "status";
        this.help = "Change bot status";
        this.arguments = "<new status>";
        this.owner = true;
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length > 0) {
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                builder.append(arg).append(" ");
            }
            String newStatus = MCB.config.setStatus(builder.toString());
            e.getJDA().getPresence().setActivity(Activity.playing(newStatus));
            e.getChannel().sendMessage("You change the bot status to ```" + newStatus + "```").queue();
        } else {
            e.getChannel().sendMessage("```Correct usage: .status <new status>```").queue();
        }
    }

}
