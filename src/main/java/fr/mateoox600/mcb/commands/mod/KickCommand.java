package fr.mateoox600.mcb.commands.mod;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.Arrays;

public class KickCommand extends Command {

    public KickCommand() {
        this.name = "kick";
        this.help = "Kick guild members";
        this.arguments = "<member> [reason]";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (!e.getMember().hasPermission(Permission.KICK_MEMBERS) && !e.getMember().hasPermission(Permission.ADMINISTRATOR) && !e.getAuthor().getId().equals(MCB.config.getOwnerId()) && !e.getMember().isOwner()) {
            e.getChannel().sendMessage("```Error: You don't have this permission```").queue();
            return;
        }
        if (args.length > 0) {
            Member target = e.getMessage().getMentionedMembers().get(0);
            if (target != null) {

                if (target.getId().equals(MCB.config.getOwnerId())){
                    e.getChannel().sendMessage("```Error: you cannot kick bot owner```").queue();
                    return;
                }

                if (args.length == 1) {
                    target.getUser().openPrivateChannel().complete().sendMessage("You have been kick from " + e.getGuild().getName()).queue();
                    e.getGuild().kick(target).queue();
                    e.getChannel().sendMessage(target.getAsMention() + " was kick").queue();
                } else {
                    StringBuilder reason = new StringBuilder();
                    for (String arg : Arrays.copyOfRange(args, 1, args.length)) reason.append(arg).append(" ");
                    target.getUser().openPrivateChannel().complete().sendMessage("You have been kick from " + e.getGuild().getName() + " for '" + reason.toString() + "'").queue();
                    e.getGuild().kick(target).queue();
                    e.getChannel().sendMessage(target.getAsMention() + " was kick for '" + reason.toString() + "'").queue();
                }
            } else {
                e.getChannel().sendMessage("```Invalid user, usage: .kick <mention> [reason]```").queue();
            }
        } else {
            e.getChannel().sendMessage("```Correct usage: .kick <mention> [reason]```").queue();
        }
    }

}
