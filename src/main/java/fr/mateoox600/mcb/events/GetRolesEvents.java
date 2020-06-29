package fr.mateoox600.mcb.events;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class GetRolesEvents extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent e) {
        if (e.getChannel().getId().equals("722474195526746162")) {
            if (e.getReactionEmote().getName().equals("EnderBot"))
                e.getGuild().addRoleToMember(e.getMember(), e.getJDA().getRoleById("722516418267971625")).complete();
            if (e.getReactionEmote().getName().equals("Pegi18"))
                e.getGuild().addRoleToMember(e.getMember(), e.getJDA().getRoleById("715559440081748009")).complete();
        }
    }

    @Override
    public void onMessageReactionRemove(@Nonnull MessageReactionRemoveEvent e) {
        if (e.getChannel().getId().equals("722474195526746162")) {
            if (e.getReactionEmote().getName().equals("EnderBot"))
                e.getGuild().removeRoleFromMember(e.getMember(), e.getJDA().getRoleById("722516418267971625")).complete();
            if (e.getReactionEmote().getName().equals("Pegi18"))
                e.getGuild().removeRoleFromMember(e.getMember(), e.getJDA().getRoleById("715559440081748009")).complete();
        }
    }

}
