package fr.mateoox600.mcb;

import fr.mateoox600.mcb.reminders.events.BadmintonBotMessageEvents;
import fr.mateoox600.mcb.commands.*;
import fr.mateoox600.mcb.commands.manager.CommandBuilder;
import fr.mateoox600.mcb.commands.mod.KickCommand;
import fr.mateoox600.mcb.commands.owner.*;
import fr.mateoox600.mcb.bots.enderbot.commands.EBGiftCommand;
import fr.mateoox600.mcb.bots.enderbot.commands.EBTowerCommand;
import fr.mateoox600.mcb.bots.enderbot.commands.owner.EBOwnerCommand;
import fr.mateoox600.mcb.reminders.events.EBMessageEvents;
import fr.mateoox600.mcb.bots.enderbot.events.EBMiningEvents;
import fr.mateoox600.mcb.bots.enderbot.utils.EBTower;
import fr.mateoox600.mcb.events.BinaryEvent;
import fr.mateoox600.mcb.events.GetRolesEvents;
import fr.mateoox600.mcb.events.UserEvents;
import fr.mateoox600.mcb.reminders.commands.DeleteReminderCommand;
import fr.mateoox600.mcb.reminders.commands.ReminderCommand;
import fr.mateoox600.mcb.reminders.commands.RemindersCommand;
import fr.mateoox600.mcb.ticket.TicketCommand;
import fr.mateoox600.mcb.ticket.TicketManager;
import fr.mateoox600.mcb.utils.Config;
import fr.mateoox600.mcb.utils.controlpanel.ControlPanel;
import fr.mateoox600.mcb.utils.Logger;
import fr.mateoox600.mcb.reminders.utils.RemindersManager;
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
import java.util.*;

public class MCB {

    public static Logger logger;
    public static EBTower EBTower;
    public static Config config;
    public static RemindersManager remindersManager;
    public static JDA jda;
    public static Emote MCBEmote;
    public static ControlPanel controlPanel;
    public static TicketManager ticketManager;
    public static HashMap<String, Message> reactionsEventMessage = new HashMap<>();
    public static String[] reactionNumber = new String[]{"\u0030\u20E3", "\u0031\u20E3", "\u0032\u20E3", "\u0033\u20E3", "\u0034\u20E3", "\u0035\u20E3", "\u0036\u20E3", "\u0037\u20E3", "\u0038\u20E3", "\u0039\u20E3"};

    public static void main(String[] args) throws LoginException, InterruptedException, IOException {

        config = new Config("config.json");

        controlPanel = new ControlPanel("MCB Control Panel");

        logger = new Logger(System.out, "latest.log");
        logger.attach();
        System.out.println("[INFO] Control Panel created");
        System.out.println("[INFO] Logging enable");

        config.launch();
        System.out.println("[INFO] Config loaded");

        if (!config.getDataFolder().exists()) config.getDataFolder().mkdirs();

        EBTower = new EBTower();
        System.out.println("[INFO] EnderBot Tower loaded");

        CommandBuilder client = new CommandBuilder(config.getPrefix());
        System.out.println("[INFO] CommandBuilder created");

        client.addCommands(new BinaryCommand(),
                new PingCommand(),
                new StatsCommand(),
                new UserInfoCommand(),
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
                new LeaveCommand(),
                new StatusCommand(),
                new SaveCommand(),
                new SayCommand(),
                new QueryCommand(),
                new EBOwnerCommand(),
                new TicketCommand());

        JDABuilder jdaBuilder = JDABuilder.createDefault(config.getToken())
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .addEventListeners(client.build(),
                        new UserEvents(),
                        new GetRolesEvents(),
                        new EBMiningEvents(),
                        new EBMessageEvents(),
                        new BadmintonBotMessageEvents(),
                        new BinaryEvent());
        System.out.println("[INFO] JDABuilder created");

        jda = jdaBuilder.build();
        System.out.println("[INFO] JDA build");

        jda.getPresence().setActivity(Activity.playing("Loading..."));
        System.out.println("[INFO] await ready");
        jda.awaitReady();

        MCBEmote = Objects.requireNonNull(jda.getGuildById("713826539698913320")).getEmotesByName("MCB", false).get(0);
        System.out.println("[INFO] Bot default emote loaded");

        remindersManager = new RemindersManager();
        System.out.println("[INFO] ReminderManager loaded");

        ticketManager = new TicketManager();
        System.out.println("[INFO] TicketManager loaded");

        controlPanel.startThread();

        Timer timer = new Timer();

        timer.schedule(new ReactionsEventsMessagesClean(), 1000 * 120, 1000 * 120);

        jda.getPresence().setActivity(Activity.playing(config.getStatus()));
        System.out.println("[INFO] Setting default status");

        System.out.println("[INFO] Bot Launched");

    }

    public static void stop() throws IOException {
        remindersManager.save();
        jda.shutdown();
        System.out.println("[INFO] Bot stopped");
        System.exit(0);
    }

}

class ReactionsEventsMessagesClean extends TimerTask {

    @Override
    public void run() {
        MCB.reactionsEventMessage.clear();
    }

}
