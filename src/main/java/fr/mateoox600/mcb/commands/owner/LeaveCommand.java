package fr.mateoox600.mcb.commands.owner;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;
import net.dv8tion.jda.api.entities.Guild;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeaveCommand extends Command {

    public LeaveCommand() {
        this.name = "leave";
        this.help = "Bot Owner Command: Make the bot leave a server";
        this.arguments = "<server id>";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (e.getAuthor().getId().equals(MCB.config.getOwnerId())) {
            if (args.length > 0){
                e.getJDA().getGuildById(args[0]).leave().queue();
            }else{
                List<String> guildsId = new ArrayList<>();
                for (Guild g : e.getJDA().getGuilds()) guildsId.add(g.getId());
                e.getChannel().sendMessage(String.join(", ", guildsId.toArray(new String[]{}))).queue();
            }
        } else {
            e.getChannel().sendMessage("```Error: You don't have the permission (Only bot owner command)```").queue();
        }
    }

}
