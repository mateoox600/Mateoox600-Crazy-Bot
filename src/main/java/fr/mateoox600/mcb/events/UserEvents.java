package fr.mateoox600.mcb.events;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class UserEvents extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent e) {
        e.getGuild().addRoleToMember(e.getMember(), e.getJDA().getRoleById("714090191182233602")).complete();
    }

}
