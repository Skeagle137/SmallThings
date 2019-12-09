package net.skeagle.smallthings.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.skeagle.smallthings.STmain.say;

@CommandAlias("smallthings|smallt|sthings|st")
public class Invsee extends BaseCommand {

    @Subcommand("invsee")
    @CommandCompletion("@players")
    @CommandAlias("invsee")
    @CommandPermission("small.invsee")
    @Description("Allows you to see another player's inventory.")
    public void onInvsee(Player p, String[] args) {
        if (args.length < 1) {
            say(p, "&cYou must specify a player.");
        } else {
            Player a = Bukkit.getPlayerExact(args[0]);
            if (a != null) {
                p.openInventory(a.getInventory());
                say(p, "&aNow showing " + a.getName() + "'s inventory.");
            } else {
                say(p, "&cThat player is not online.");
            }
        }
    }
}
