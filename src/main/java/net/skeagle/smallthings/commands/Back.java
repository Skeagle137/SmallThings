package net.skeagle.smallthings.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.skeagle.smallthings.utils.BackUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.skeagle.smallthings.STmain.say;

@CommandAlias("smallthings|smallt|sthings|st")
public class Back extends BaseCommand {

    BackUtil back = BackUtil.getBack();

    @Subcommand("back")
    @CommandCompletion("@players")
    @CommandAlias("back")
    @CommandPermission("small.back")
    @Description("Teleports you back to your previous location.")
    public void onBack(Player p, String[] args) {
        if (args.length < 1) {
            if (back.hasBackLoc(p.getUniqueId())) {
                back.teleToBackLoc(p.getUniqueId(), p);
                say(p, "&7Teleported to your last location.");
                return;
            }
            say(p, "&cYou do not have anywhere to teleport back to.");
            return;
        }
        Player a = Bukkit.getPlayer(args[0]);
        if (a == null) {
            say(p, "&cThat player is not online.");
            return;
        }
        if (back.hasBackLoc(a.getUniqueId())) {
            back.teleToBackLoc(p.getUniqueId(), a);
            say(p, "&7Teleported to &a" + a.getName() + "&7's last location.");
        }
        else {
            say(p, "&a" + a.getName() + " &7does not have a saved last location.");
        }
    }
}
