package fr.mateoox600.mcb.events;

import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class BinaryEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent e) {
        // TODO: 06/06/2020 upgrade binary system 
    }

}
