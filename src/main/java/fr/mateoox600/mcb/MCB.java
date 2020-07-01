package fr.mateoox600.mcb;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import fr.mateoox600.mcb.commands.*;
import fr.mateoox600.mcb.enderbot.commands.EBTowerCommand;
import fr.mateoox600.mcb.enderbot.events.EBMessageEvents;
import fr.mateoox600.mcb.enderbot.utils.EBTower;
import fr.mateoox600.mcb.enderbot.events.EBMiningEvents;
import fr.mateoox600.mcb.utils.reminders.RemindersManager;
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
import java.net.URISyntaxException;

public class MCB {

    public static Logger logger;
    public static EBTower EBTower;
    public static Config config;
    public static RemindersManager remindersManager;
    public static JDA jda;

    public static void main(String[] args) throws LoginException, InterruptedException, URISyntaxException, IOException {

        config = new Config("config.json");

        if (!config.getDataFolder().exists()) config.getDataFolder().mkdirs();

        EBTower = new EBTower();

        EventWaiter waiter = new EventWaiter();

        CommandClientBuilder client = new CommandClientBuilder();

        client.useDefaultGame().setOwnerId(config.getOwnerId()).setPrefix(config.getPrefix());

        client.addCommands(new BinaryCommand(),
                new PingCommand(),
                new KickCommand(),
                new ReportCommand(),
                new CalcCommand(),
                new EBTowerCommand(),
                new StatusCommand(),
                new StopCommand(),
                new DebugCommand(),
                new ReminderCommand(),
                new SaveCommand());

        JDABuilder jdaBuilder = JDABuilder.createDefault(config.getToken())
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.playing("loading..."))
                .addEventListeners(waiter, client.build());

        //jdaBuilder.addEventListeners(new BinaryEvent());
        jdaBuilder.addEventListeners(new EBMiningEvents(),
                new GetRolesEvents(),
                new UserEvents(),
                new EBMessageEvents());

        jda = jdaBuilder.build();
        logger = new Logger(jda.getTextChannelById("718563766232547489"));

        jda.awaitReady();

        remindersManager = new RemindersManager();

        jda.getPresence().setActivity(Activity.playing(config.getStatus()));

    }

}
