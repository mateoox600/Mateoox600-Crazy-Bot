package fr.mateoox600.mcb.commands;

import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class PingCommand extends Command {

    public PingCommand() {
        this.name = "ping";
        this.help = "Give bot ping";
    }

    @Override
    protected void execute(CommandEvent e) {
        e.getChannel().sendMessage("pong..").queue(
                msg -> e.getChannel().sendMessage(new EmbedBuilder()
                        .addField("Ping:", "**Discord:** " + e.getJDA().getGatewayPing() + "ms\n" +
                                "**You:** " + ((msg.getTimeCreated().getNano() / 1000000) - (e.getMessage().getTimeCreated().getNano() / 1000000)) + "ms", false)
                        .build()).queue());
    }
}
