package net.skeagle.smallthings;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import static net.skeagle.smallthings.STmain.say;

@CommandAlias("smallthings|smallt|sthings|st")
public class Push extends BaseCommand {

    @Subcommand("push")
    @CommandCompletion("@players")
    @CommandAlias("push")
    @CommandPermission("small.push")
    @Description("Pushes a player.")
    public void onPush(CommandSender cs, String[] args) {
        if (args.length == 0) {
            say(cs, "&cYou must specify a player.");
        }
        else {
            Player player = Bukkit.getPlayerExact(args[0]);
            if (player != null) {
                player.setVelocity(player.getLocation().getDirection().multiply(-3));
                player.setVelocity(new Vector(player.getVelocity().getX(), 1.5D, player.getVelocity().getZ()));
                say(cs, "&eYou pushed " + player.getName() + "!");
            }
            else {
                say(cs, "&cThat player is not online.");
            }
        }
    }
}