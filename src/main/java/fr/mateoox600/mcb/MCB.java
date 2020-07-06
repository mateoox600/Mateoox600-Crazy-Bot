package fr.mateoox600.mcb;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import fr.mateoox600.mcb.commands.BinaryCommand;
import fr.mateoox600.mcb.commands.CalcCommand;
import fr.mateoox600.mcb.commands.PingCommand;
import fr.mateoox600.mcb.commands.ReminderCommand;
import fr.mateoox600.mcb.commands.mod.KickCommand;
import fr.mateoox600.mcb.commands.mod.ReportCommand;
import fr.mateoox600.mcb.commands.owner.DebugCommand;
import fr.mateoox600.mcb.commands.owner.SaveCommand;
import fr.mateoox600.mcb.commands.owner.StatusCommand;
import fr.mateoox600.mcb.commands.owner.StopCommand;
import fr.mateoox600.mcb.enderbot.commands.EBTowerCommand;
import fr.mateoox600.mcb.enderbot.events.EBMessageEvents;
import fr.mateoox600.mcb.enderbot.events.EBMiningEvents;
import fr.mateoox600.mcb.enderbot.utils.EBTower;
import fr.mateoox600.mcb.events.BinaryEvent;
import fr.mateoox600.mcb.events.GetRolesEvents;
import fr.mateoox600.mcb.events.UserEvents;
import fr.mateoox600.mcb.utils.Config;
import fr.mateoox600.mcb.utils.Logger;
import fr.mateoox600.mcb.utils.reminders.RemindersManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class MCB {

    public static Logger logger;
    public static EBTower EBTower;
    public static Config config;
    public static RemindersManager remindersManager;
    public static JDA jda;
    public static HashMap<String, Message> reactionsEventMessage = new HashMap<>();

    // TODO: 05/07/2020 game guess number with interface 

    public static void main(String[] args) throws LoginException, InterruptedException, URISyntaxException, IOException {

        config = new Config("config.json");

        if (!config.getDataFolder().exists()) config.getDataFolder().mkdirs();

        EBTower = new EBTower();

        EventWaiter waiter = new EventWaiter();

        CommandClientBuilder client = new CommandClientBuilder();

        client.useDefaultGame().setOwnerId(config.getOwnerId()).setPrefix(config.getPrefix());

        client.addCommands(new BinaryCommand(),
                new PingCommand(),
                new CalcCommand(),
                new ReminderCommand(),
                new EBTowerCommand(),
                new KickCommand(),
                new ReportCommand(),
                new StopCommand(),
                new DebugCommand(),
                new StatusCommand(),
                new SaveCommand());

        JDABuilder jdaBuilder = JDABuilder.createDefault(config.getToken())
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.playing("loading..."))
                .addEventListeners(waiter, client.build());

        jdaBuilder.addEventListeners(new UserEvents(),
                new GetRolesEvents(),
                new EBMiningEvents(),
                new EBMessageEvents(),
                new BinaryEvent());

        jda = jdaBuilder.build();

        jda.awaitReady();

        logger = new Logger(jda.getTextChannelById("718563766232547489"));

        remindersManager = new RemindersManager();

        jda.getPresence().setActivity(Activity.playing(config.getStatus()));

    }

}
