package fr.mateoox600.mcb;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import fr.mateoox600.mcb.commands.*;
import fr.mateoox600.mcb.commands.mod.KickCommand;
import fr.mateoox600.mcb.commands.owner.*;
import fr.mateoox600.mcb.enderbot.commands.EBGiftCommand;
import fr.mateoox600.mcb.enderbot.commands.EBTowerCommand;
import fr.mateoox600.mcb.enderbot.commands.owner.EnderBotOwnerCommand;
import fr.mateoox600.mcb.enderbot.events.EBMessageEvents;
import fr.mateoox600.mcb.enderbot.events.EBMiningEvents;
import fr.mateoox600.mcb.enderbot.utils.EBTower;
import fr.mateoox600.mcb.events.BinaryEvent;
import fr.mateoox600.mcb.events.GetRolesEvents;
import fr.mateoox600.mcb.events.UserEvents;
import fr.mateoox600.mcb.utils.Config;
import fr.mateoox600.mcb.utils.reminders.RemindersManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MCB {

    public static EBTower EBTower;
    public static Config config;
    public static RemindersManager remindersManager;
    public static JDA jda;
    public static HashMap<String, Message> reactionsEventMessage = new HashMap<>();
    public static Emote MCBEmote;
    public static String[] reactionNumber = new String[]{"\u0030\u20E3","\u0031\u20E3","\u0032\u20E3","\u0033\u20E3","\u0034\u20E3","\u0035\u20E3", "\u0036\u20E3","\u0037\u20E3","\u0038\u20E3","\u0039\u20E3"};

    public static void main(String[] args) throws LoginException, InterruptedException, URISyntaxException, IOException {

        config = new Config("config.json");

        if (!config.getDataFolder().exists()) config.getDataFolder().mkdirs();

        EBTower = new EBTower();

        EventWaiter waiter = new EventWaiter();

        CommandClientBuilder client = new CommandClientBuilder();

        client.useDefaultGame().setOwnerId(config.getOwnerId()).setPrefix(config.getPrefix()).setActivity(Activity.playing("loading..."));

        // TODO: 19/07/2020 do .lockdown command 

        client.addCommands(new BinaryCommand(),
                new PingCommand(),
                new StatsCommand(),
                new CalcCommand(),
                new ReminderCommand(),
                new RemindersCommand(),
                new DeleteReminderCommand(),
                new EBTowerCommand(),
                new EBGiftCommand(),
                new KickCommand(),
                new ReportCommand(),
                new StopCommand(),
                new DebugCommand(),
                new StatusCommand(),
                new SaveCommand(),
                new SayCommand(),
                new EnderBotOwnerCommand()/*,
                new GuildDestructCommand(),
                new SpamCommand()*/);

        JDABuilder jdaBuilder = JDABuilder.createDefault(config.getToken())
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .addEventListeners(waiter, client.build());

        jdaBuilder.addEventListeners(new UserEvents(),
                new GetRolesEvents(),
                new EBMiningEvents(),
                new EBMessageEvents(),
                new BinaryEvent());

        jda = jdaBuilder.build();

        jda.awaitReady();

        MCBEmote = jda.getGuildById("713826539698913320").getEmotesByName("MCB", false).get(0);

        remindersManager = new RemindersManager();

        /*Timer timer = new Timer();

        timer.schedule(new ReactionsEventsMessagesClean(), 1000*120, 1000*120);*/

        jda.getPresence().setActivity(Activity.playing(config.getStatus()));

    }

}
class ReactionsEventsMessagesClean extends TimerTask{

    @Override
    public void run() {
        MCB.reactionsEventMessage.clear();
    }

}
