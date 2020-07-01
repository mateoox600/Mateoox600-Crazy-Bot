package fr.mateoox600.mcb.events;

import fr.mateoox600.mcb.MCB;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.IOException;

public class SystemEvents extends ListenerAdapter {

    @Override
    public void onReady(@Nonnull ReadyEvent e) {
        try {
            e.getJDA().awaitReady();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        MCB.logger.logStart();
    }

    @Override
    public void onDisconnect(@Nonnull DisconnectEvent e) {
        try {
            MCB.remindersManager.save();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        MCB.logger.logStop();
    }

}
