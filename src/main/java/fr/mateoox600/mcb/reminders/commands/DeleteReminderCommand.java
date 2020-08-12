package fr.mateoox600.mcb.reminders.commands;

import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;

import java.util.Arrays;

public class DeleteReminderCommand extends Command {

    public DeleteReminderCommand() {
        this.name = "deletereminder";
        this.help = "NOT FUNCTIONNAL";
        this.aliases = new String[]{"delreminder", "delrm"};
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);

        /*if (args.length > 0) {
            try {
                double id = Integer.parseInt(args[0]);
                if (id < 0){
                    e.getChannel().sendMessage("```ERROR: wrong id```").queue();
                    return;
                }
                List<Reminder> memberReminders = MCB.remindersManager.getAllMemberReminders(e.getMember());
                Reminder reminder = null;
                for (Reminder reminders : memberReminders){
                    if (reminders.id == id) reminder = reminders;
                }
                if (reminder == null) {
                    e.getChannel().sendMessage("```ERROR: can't find reminder with id '" + id + "'```").queue();
                    return;
                }
                reminder.timer.cancel();
                MCB.remindersManager.used_id.remove(id);
                MCB.remindersManager.reminders.remove(reminder);
            }catch (Exception ignored){
            }
        }*/

    }

}
