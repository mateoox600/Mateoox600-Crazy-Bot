package fr.mateoox600.mcb.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;

public class UserEvents extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent e) {
        if (e.getGuild().getId().equals("713826539698913320")) {
            e.getGuild().addRoleToMember(e.getMember(), e.getJDA().getRoleById("714090191182233602")).complete();
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setTitle(e.getUser().getName());
            embedBuilder.setImage(e.getUser().getEffectiveAvatarUrl());
            embedBuilder.setColor(Color.GREEN);

            e.getGuild().getTextChannelById("713827717404950538").sendMessage(embedBuilder.build()).queue();
        }
    }

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent e) {
        if (e.getGuild().getId().equals("713826539698913320")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setTitle(e.getUser().getName());
            embedBuilder.setImage(e.getUser().getEffectiveAvatarUrl());
            embedBuilder.setColor(Color.RED);

            e.getGuild().getTextChannelById("713827717404950538").sendMessage(embedBuilder.build()).queue();
        }
    }
}
