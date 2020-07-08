package fr.mateoox600.mcb.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.utils.reminders.Reminder;

import java.util.Arrays;
import java.util.List;

public class RemindersCommand extends Command {

    public RemindersCommand() {
        this.name = "reminders";
        this.help = "List all your reminders";
        this.aliases = new String[]{"rms", "rmlist"};
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        List<Reminder> memberReminders = MCB.remindersManager.getAllMemberReminders(e.getMember());
        if (memberReminders.isEmpty()) e.getChannel().sendMessage("You have no reminders").queue();
        else {
            StringBuilder stringBuilder = new StringBuilder("You have " + memberReminders.size() + " reminders:");
            for (Reminder reminder : memberReminders) {
                stringBuilder.append("\n- `").append(reminder.getText()).append("` end in ").append(Reminder.longTimeToStringTime(reminder.getTime()));
            }
            e.getChannel().sendMessage(stringBuilder.toString()).queue();
        }
    }

}
