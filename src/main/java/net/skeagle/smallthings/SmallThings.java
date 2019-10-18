package net.skeagle.smallthings;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import org.bukkit.command.CommandSender;

@CommandAlias("smallthings|smallt|sthings|st")
@Description("Main SmallThings command.")
public class SmallThings extends BaseCommand {

    @Default
    @HelpCommand
    public void onST(CommandSender cs, CommandHelp help) {
        help.showHelp();
    }
}
