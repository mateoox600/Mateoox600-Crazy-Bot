package fr.mateoox600.mcb.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.enderbot.utils.reminders.RemindersManager;

import java.util.Arrays;

public class DebugCommand extends Command {

    public DebugCommand() {
        this.name = "debug";
        this.help = "Bot Owner Command: Debug in dev commands";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length > 1) {
            if (e.getAuthor().getId().equals(MCB.config.getOwnerId())) {
                long time = RemindersManager.parseReminderTime(args[0]);
                if (time <= 0) return;
                StringBuilder stringBuilder = new StringBuilder();
                for (String arg : Arrays.copyOfRange(args, 1, args.length)) stringBuilder.append(arg);
                MCB.remindersManager.addReminder(stringBuilder.toString(), e.getMember(), e.getTextChannel(), time);
            }
        }
    }

}
