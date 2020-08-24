package fr.mateoox600.mcb.commands.owner;

import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;

public class QueryCommand extends Command {

    public QueryCommand() {
        this.name = "query";
        this.aliases = new String[]{"q"};
        this.help = "Query command";
        this.owner = true;
        this.arguments = "<userid/usertag> <text>";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("userid")) {
                if (args.length > 1) {
                    User user = e.getJDA().getUserById(args[1]);
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle("Query")
                            .addField(user == null ? "None" : "Find " + user.getName(), user == null ? "" : ("Id: " + user.getId() + " \nMutual guild: " + user.getMutualGuilds() + " \nBadges: " + user.getFlags()).replace("```", "`"), true);
                    e.getChannel().sendMessage(embedBuilder.build()).queue();
                } else {
                    e.getChannel().sendMessage("```Error: .q userid <id>```").queue();
                }
            }else if(args[0].equalsIgnoreCase("usertag")){
                if (args.length > 1) {
                    User user = e.getJDA().getUserByTag(args[1]);
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle("Query")
                            .addField(user == null ? "None" : "Find " + user.getName(), user == null ? "" : ("Id: " + user.getId() + " \nMutual guild: " + user.getMutualGuilds() + " \nBadges: " + user.getFlags()).replace("```", "`"), true);
                    e.getChannel().sendMessage(embedBuilder.build()).queue();
                } else {
                    e.getChannel().sendMessage("```Error: .q user <tag>```").queue();
                }
            } else {
                e.getChannel().sendMessage("```Error: .q <userid/usertag> <text>```").queue();
            }
        } else {
            e.getChannel().sendMessage("```Error: .q <userid/usertag> <text>```").queue();
        }
    }

}
