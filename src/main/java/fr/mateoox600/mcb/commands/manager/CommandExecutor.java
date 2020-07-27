package fr.mateoox600.mcb.commands.manager;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class CommandExecutor extends ListenerAdapter {

    CommandBuilder commandBuilder;

    public CommandExecutor(CommandBuilder commandBuilder){
        this.commandBuilder = commandBuilder;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        if (!args[0].startsWith(commandBuilder.prefix)) return;
        if (e.getChannelType() == ChannelType.PRIVATE) return;
        if (args[0].substring(commandBuilder.prefix.length()).equalsIgnoreCase("help")){
            StringBuilder stringBuilder = new StringBuilder("**Mateoox600 Crazy Bot** commands: \n");
            for (Command command : commandBuilder.commands){
                stringBuilder.append("\n`").append(commandBuilder.prefix).append(command.name).append(" ").append(command.arguments).append("` - ").append(command.help);
            }
            stringBuilder.append("\n\nFor additional help, contact **Mateoox600**#9473");
            e.getAuthor().openPrivateChannel().queue(channel -> channel.sendMessage(stringBuilder.toString()).queue());
            return;
        }
        for (Command command : commandBuilder.commands){
            if (command.name.equalsIgnoreCase(args[0].substring(commandBuilder.prefix.length()))){
                command.execute(new CommandEvent(e.getJDA(), e.getMessage(), e.getChannel(), e.getTextChannel(), e.getAuthor(), e.getMember(), e.getGuild()));
                return;
            }else{
                for (String aliases : command.aliases){
                    if (aliases.equalsIgnoreCase(args[0].substring(commandBuilder.prefix.length()))){
                        command.execute(new CommandEvent(e.getJDA(), e.getMessage(), e.getChannel(), e.getTextChannel(), e.getAuthor(), e.getMember(), e.getGuild()));
                        return;
                    }
                }
            }
        }
    }
}
