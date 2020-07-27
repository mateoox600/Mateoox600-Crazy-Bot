package fr.mateoox600.mcb.commands;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;

import java.util.Arrays;

public class BinaryCommand extends Command {

    public BinaryCommand() {
        this.name = "binary";
        this.aliases = new String[]{"bin"};
        this.help = "Binary encoder and decoder";
        this.arguments = "<text/binary>";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length > 0) {
            e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Encode or Decode (E/D): \n```" + String.join(" ", args) + "```").queue(msg -> msg.addReaction("\uD83C\uDDEA").queue(emote -> msg.addReaction("\uD83C\uDDE9").queue(emote1 -> MCB.reactionsEventMessage.put(msg.getId(), msg))));
        } else {
            e.getChannel().sendMessage("```Correct usage: .binary <text/binary>```").queue();
        }
    }

}
