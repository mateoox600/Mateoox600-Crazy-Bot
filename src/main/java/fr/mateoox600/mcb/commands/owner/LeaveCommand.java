package fr.mateoox600.mcb.commands.owner;

import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;
import net.dv8tion.jda.api.entities.Guild;

import java.util.Arrays;

public class LeaveCommand extends Command {

    public LeaveCommand() {
        this.name = "leave";
        this.help = "Make the bot leave a server";
        this.arguments = "<server id>";
        this.owner = true;
        this.inHelp = false;
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length > 0) {
            try{
                e.getJDA().getGuildById(args[0]).leave().queue();
            }catch (Exception exception){
                e.getChannel().sendMessage("```Error: Enter a valid long discord guild id```").queue();
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder("```");
            for (Guild guilds : e.getJDA().getGuilds()) {
                stringBuilder.append("\n- ").append(guilds.getName().replace("`", "")).append(": ").append(guilds.getId());
            }
            stringBuilder.append("```");
            e.getChannel().sendMessage(stringBuilder.toString()).queue();
        }
    }

}
