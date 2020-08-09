package fr.mateoox600.mcb.commands;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.Calendar;

public class UserInfoCommand extends Command {

    public UserInfoCommand() {
        this.name = "uinfo";
        this.help = "Give user information";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);

        Member user = e.getMember();


        if (args.length > 0) {
            int args_skip = 0;
            for (int i = 0; i < args.length; i++) {
                if (args_skip > 0) {
                    args_skip--;
                } else {
                    String arg = args[i];
                    if (arg.equalsIgnoreCase("-id")) {
                        if (args.length > i + 1) {
                            args_skip++;
                            if (e.getMember().hasPermission(Permission.ADMINISTRATOR) || e.getMember().getId().equals(MCB.config.getOwnerId())) {
                                try {
                                    user = e.getGuild().getMemberById(args[i + 1]);
                                } catch (Exception exception) {
                                    e.getChannel().sendMessage("```Error: -id suffix require valid long user id```").queue();
                                    return;
                                }
                                if (user == null) {
                                    e.getChannel().sendMessage("```Error: -id suffix require valid long user id```").queue();
                                    return;
                                }
                            } else {
                                e.getChannel().sendMessage("```Error: -id suffix require ADMINISTRATOR permission```").queue();
                            }
                        } else {
                            e.getChannel().sendMessage("```Error: -id suffix incorrect usage```").queue();
                        }
                    }else if (arg.equalsIgnoreCase("-user")) {
                        if (args.length > i + 1) {
                            args_skip++;
                            if (e.getMember().hasPermission(Permission.ADMINISTRATOR) || e.getMember().getId().equals(MCB.config.getOwnerId())) {
                                try {
                                    user = e.getGuild().getMemberById(args[i + 1].substring(3, args[i+1].length()-1));
                                } catch (Exception exception) {
                                    e.getChannel().sendMessage("```Error: -user suffix require valid user mention (<@!id>)```").queue();
                                    return;
                                }
                                if (user == null) {
                                    e.getChannel().sendMessage("```Error: -user suffix require valid user mention (<@!id>)```").queue();
                                    return;
                                }
                            } else {
                                e.getChannel().sendMessage("```Error: -user suffix require ADMINISTRATOR permission```").queue();
                            }
                        } else {
                            e.getChannel().sendMessage("```Error: -user suffix incorrect usage```").queue();
                        }
                    }
                }
            }
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(Long.toBinaryString(Long.parseLong(user.getId())).substring(22), 2) + 1420070400000L);
        StringBuilder badges = new StringBuilder();
        for (User.UserFlag flag : user.getUser().getFlags()){
            badges.append("- ").append(flag.getName()).append("\n");
        }
        EmbedBuilder embed = new EmbedBuilder().setTitle(user.getUser().getName() + " account information")
                .setImage(user.getUser().getEffectiveAvatarUrl())
                .setColor(user.getRoles().get(0).getColor())
                .addField("Created", calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND), true)
                .addField("Id", user.getId(), true)
                .addField("Badges", badges.toString(), true);
        e.getChannel().sendMessage(embed.build()).queue();
    }

}