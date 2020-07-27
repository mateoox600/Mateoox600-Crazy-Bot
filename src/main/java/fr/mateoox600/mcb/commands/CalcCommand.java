package fr.mateoox600.mcb.commands;

import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Arrays;

public class CalcCommand extends Command {

    public CalcCommand() {
        this.name = "calc";
        this.help = "Calcul things";
        this.arguments = "<calcul>";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length > 0) {
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            try {
                StringBuilder stringBuilder = new StringBuilder();
                for (String arg : args) stringBuilder.append(arg);
                e.getChannel().sendMessage("<@" + e.getAuthor().getId() + ">```" + stringBuilder.toString() + "=" + engine.eval(stringBuilder.toString()) + "```").queue();
            } catch (Exception error) {
                e.getChannel().sendMessage("```Error: parsing number```").queue();
            }
        } else {
            e.getChannel().sendMessage("```Correct usage: .calc [calcul]```").queue();
        }
    }

}
