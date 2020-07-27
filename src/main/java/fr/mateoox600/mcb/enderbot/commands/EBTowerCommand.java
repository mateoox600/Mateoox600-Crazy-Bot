package fr.mateoox600.mcb.enderbot.commands;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;

import java.util.Arrays;

public class EBTowerCommand extends Command {

    public EBTowerCommand() {
        this.name = "ebtower";
        this.help = "Know which level of the tower of enderbot you can go";
        this.arguments = "<defense>";
        this.aliases = new String[]{"ebto", "ebtow"};
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length > 0) {
            e.getChannel().sendMessage("You can go to the level " + MCB.EBTower.getBeatableBoss(Integer.parseInt(args[0])) + " of the tower!").queue();
        }
    }
}
