package fr.mateoox600.mcb.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.mateoox600.mcb.MCB;

import java.util.Arrays;

public class BinaryCommand extends Command {

    public BinaryCommand() {
        this.name = "binary";
        this.aliases = new String[]{"bin"};
        this.help = "Binary encoder and decoder";
        this.arguments = "<encode/decode> <text/binary>";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length > 0) {
            /*if (args[0].equalsIgnoreCase("encode") || args[0].equalsIgnoreCase("e")) {
                try {
                    StringBuilder output = new StringBuilder();
                    for (char c : String.join(" ", Arrays.copyOfRange(args, 1, args.length)).toLowerCase().toCharArray())
                        output.append(Integer.toBinaryString(c)).append(" ");
                    e.getMessage().delete().queue(msg -> e.getChannel().sendMessage("<@" + e.getAuthor().getId() + "> ```" + output.toString() + "```").queue());
                    MCB.logger.logBinConv(e.getMember(), String.join(" ", Arrays.copyOfRange(args, 1, args.length)), output.toString(), true);
                } catch (Exception error) {
                    error.printStackTrace();
                    e.getChannel().sendMessage("```ERROR: Can't parse this words \nLINE 22 TO 27```").queue();
                }
            } else if (args[0].equalsIgnoreCase("decode") || args[0].equalsIgnoreCase("d")) {
                try {
                    StringBuilder output = new StringBuilder();
                    for (String str_n : Arrays.copyOfRange(args, 1, args.length))
                        output.append((char) Integer.parseInt(str_n, 2));
                    e.getMessage().delete().queue(msg -> e.getChannel().sendMessage("<@" + e.getAuthor().getId() + "> ```" + output.toString() + "```").queue());
                    MCB.logger.logBinConv(e.getMember(), String.join(" ", Arrays.copyOfRange(args, 1, args.length)), output.toString(), false);
                } catch (Exception error) {
                    e.getChannel().sendMessage("```ERROR: Can't parse this binary numbers \nLINE 31 TO 36```").queue();
                }
            } else {
                e.getChannel().sendMessage("```Correct usage: .binary <encode/decode> <text/binary>```").queue();
            }*/
            e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Encode or Decode (E/D): \n```" + String.join(" ", args) + "```").queue(msg -> msg.addReaction("\uD83C\uDDEA").queue(emote -> msg.addReaction("\uD83C\uDDE9").queue(emote1 -> MCB.reactionsEventMessage.put(msg.getId(), msg))));
        } else {
            e.getChannel().sendMessage("```Correct usage: .binary <text/binary>```").queue();
        }
    }

}
