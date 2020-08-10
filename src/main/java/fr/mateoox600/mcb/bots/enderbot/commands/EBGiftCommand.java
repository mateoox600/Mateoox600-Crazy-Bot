package fr.mateoox600.mcb.bots.enderbot.commands;

import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;

import java.util.Arrays;

public class EBGiftCommand extends Command {

    public EBGiftCommand() {
        this.name = "ebgift";
        this.aliases = new String[]{"ebg"};
        this.arguments = "<ressources> <number>...";
        this.help = "Gift EnderBot ressources to the bot owner";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
            if (args.length > 0) {
                e.getChannel().sendMessage("<@!280726849842053120> API trade " + e.getMember().getAsMention() + " " + String.join(" ", args)).queue();
            }else{
                e.getChannel().sendMessage("```Error: Wrong arguments (.ebgift <ressources> <number>...)```").queue();
            }
    }

}
