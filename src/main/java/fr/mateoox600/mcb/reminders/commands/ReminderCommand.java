package fr.mateoox600.mcb.reminders.commands;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;
import fr.mateoox600.mcb.reminders.utils.RemindersManager;

import java.util.Arrays;

public class ReminderCommand extends Command {

    public ReminderCommand() {
        this.name = "remindme";
        this.help = "Reminder command (time = `1d2h4m25s`)";
        this.aliases = new String[]{"remind", "rmme", "reminder"};
        this.arguments = "<time> <message>";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length > 1) {
            long time = RemindersManager.parseReminderTime(args[0]);
            if (time <= 0) return;
            StringBuilder stringBuilder = new StringBuilder();
            for (String arg : Arrays.copyOfRange(args, 1, args.length)) stringBuilder.append(arg).append(" ");
            MCB.remindersManager.addReminder(stringBuilder.toString(), e.getMember(), e.getTextChannel(), time, true);
        }
    }

}
