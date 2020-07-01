package fr.mateoox600.mcb.events;

import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class SystemEvents extends ListenerAdapter {

    @Override
    public void onReady(@Nonnull ReadyEvent e) {
    }

    @Override
    public void onDisconnect(@Nonnull DisconnectEvent e) {
    }

}
