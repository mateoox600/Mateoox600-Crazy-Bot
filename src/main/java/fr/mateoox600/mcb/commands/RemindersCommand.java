package fr.mateoox600.mcb.commands;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;
import fr.mateoox600.mcb.utils.reminders.Reminder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

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
        Member member = e.getMember();
        boolean parseNumber = true;
        if (!(args.length < 1)) {
            int args_skip = 0;
            for (int i = 0; i < args.length; i++) {
                if (args_skip > 0) {
                    args_skip--;
                } else {
                    String arg = args[i];
                    if (arg.equalsIgnoreCase("-id")) {
                        if (args.length > i + 1) {
                            args_skip++;
                            if (e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                                try {
                                    member = e.getGuild().getMemberById(args[i + 1]);
                                } catch (Exception exception) {
                                    e.getChannel().sendMessage("```Error: -id suffix require valid long user id```").queue();
                                    return;
                                }
                                if (member == null) {
                                    e.getChannel().sendMessage("```Error: -id suffix require valid long user id```").queue();
                                    return;
                                }
                            } else {
                                e.getChannel().sendMessage("```Error: -id suffix require ADMINISTRATOR permission```").queue();
                            }
                        } else {
                            e.getChannel().sendMessage("```Error: -id suffix incorrect usage```").queue();
                        }
                    } else if (arg.equalsIgnoreCase("-p")) {
                        parseNumber = false;
                    }
                }
            }
        }

        List<Reminder> memberReminders = MCB.remindersManager.getAllMemberReminders(member);
        if (memberReminders.isEmpty())
            e.getChannel().sendMessage(member.getEffectiveName() + " has no reminders").queue();
        else {
            StringBuilder stringBuilder = new StringBuilder(member.getEffectiveName() + " has " + memberReminders.size() + " reminders:");
            for (Reminder reminder : memberReminders) {
                if (parseNumber)
                    stringBuilder.append("\n- `").append(reminder.getText()).append("` end in ").append(Reminder.longTimeToStringTime(reminder.getTime()));
                else
                    stringBuilder.append("\n- `").append(reminder.getText()).append("` end in ").append(reminder.getTime());
            }
            e.getChannel().sendMessage(stringBuilder.toString()).queue();
        }

    }

}
