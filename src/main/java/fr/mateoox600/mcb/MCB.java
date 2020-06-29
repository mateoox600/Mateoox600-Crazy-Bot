package fr.mateoox600.mcb;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import fr.mateoox600.mcb.commands.*;
import fr.mateoox600.mcb.enderbot.commands.EnderBotTowerCommand;
import fr.mateoox600.mcb.enderbot.utils.EnderBotTower;
import fr.mateoox600.mcb.events.EnderBotMiningEvents;
import fr.mateoox600.mcb.events.GetRolesEvents;
import fr.mateoox600.mcb.events.UserEvents;
import fr.mateoox600.mcb.utils.Config;
import fr.mateoox600.mcb.utils.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class MCB {

    public static Logger logger;
    public static EnderBotTower enderBotTower;
    public static Config config;

    public static void main(String[] args) throws LoginException, InterruptedException, IOException {

        //config = new Config("config.json");
        config = new Config();

        enderBotTower = new EnderBotTower();

        EventWaiter waiter = new EventWaiter();

        CommandClientBuilder client = new CommandClientBuilder();

        client.useDefaultGame().setOwnerId(config.getOwnerId()).setPrefix(config.getPrefix());

        client.addCommands(new BinaryCommand(),
                new PingCommand(),
                new KickCommand(),
                new ReportCommand(),
                new CalcCommand(),
                new EnderBotTowerCommand(),
                new StatusCommand());

        JDABuilder jdaBuilder = JDABuilder.createDefault(config.getToken())
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.playing("loading..."))
                .addEventListeners(waiter, client.build());

        //jdaBuilder.addEventListeners(new BinaryEvent());
        jdaBuilder.addEventListeners(new EnderBotMiningEvents(), new GetRolesEvents(), new UserEvents());

        JDA jda = jdaBuilder.build();
        logger = new Logger(jda.getTextChannelById("718563766232547489"), jda.getTextChannelById("724315135228772413"));

        jda.awaitReady();

        jda.getPresence().setActivity(Activity.playing(config.getStatus()));

    }

}
