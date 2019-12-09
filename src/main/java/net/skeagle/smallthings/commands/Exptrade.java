package net.skeagle.smallthings.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.skeagle.smallthings.GUIs.ExpTradeGUI;
import org.bukkit.entity.Player;

@CommandAlias("smallthings|smallt|sthings|st")
public class Exptrade extends BaseCommand {

    @Subcommand("exptrade")
    @CommandAlias("exptrade|etrade|expt")
    @CommandPermission("small.exptrade")
    @Description("Allows Trading items for experience points.")
    public void onTrade(Player p) {
        ExpTradeGUI exp = new ExpTradeGUI(p);
        exp.open(p);
    }
}
