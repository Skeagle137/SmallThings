package net.skeagle.smallthings;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

@CommandAlias("smallthings|smallt|sthings|st")
public class Suicide extends BaseCommand {

    @Subcommand("suicide")
    @CommandAlias("suicide")
    @CommandPermission("small.suicide")
    @Description("Kills you. Useful if you are stuck.")
    public void onSuicide(Player p) {
        p.setHealth(0);
    }
}
