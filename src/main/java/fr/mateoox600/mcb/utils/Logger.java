package fr.mateoox600.mcb.utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class Logger {

    private final TextChannel channel;

    public Logger(TextChannel channel) {
        this.channel = channel;
    }

    public void logKick(Member member, Member kickMember, String reason) {
        channel.sendMessage("<@" + member.getId() + "> kick by <@" + kickMember.getId() + "> for: ```" + reason + "```").queue();
    }

    public void logBinConv(Member member, String message, String result, boolean encode) {
        if (encode)
            channel.sendMessage("<@" + member.getId() + "> convert text to binary: ```" + message + "```to ```" + result + "```").queue();
        else
            channel.sendMessage("<@" + member.getId() + "> convert binary to text: ```" + message + "```to ```" + result + "```").queue();
    }

    public void logStatusChangeNoPerm(Member member, String message){
        channel.sendMessage("<@" + member.getId() + "> try to change the status of the bot to: ```" + message + "```").queue();
    }

}
