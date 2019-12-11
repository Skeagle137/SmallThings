package net.skeagle.smallthings.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.skeagle.smallthings.STmain.say;

@CommandAlias("smallthings|smallt|sthings|st")
public class Back extends BaseCommand {

    private final Map<UUID, Location> backLoc;

    public Back() {
        backLoc = new HashMap<>();
    }

    @Subcommand("back")
    @CommandCompletion("@players")
    @CommandAlias("back")
    @CommandPermission("small.back")
    @Description("Teleports you back to your previous location.")
    public void onBack(Player p, String[] args) {
        say(p, "&4no.");
        boolean no = false;
        if (no) {
            return;
        }
        say(p, String.valueOf(backLoc.get(p.getUniqueId())));
        if (args.length < 1) {
            if (hasBackLoc(p.getUniqueId())) {
                teleToBackLoc(p.getUniqueId(), backLoc.get(p.getUniqueId()));
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
        if (hasBackLoc(a.getUniqueId())) {
            teleToBackLoc(p.getUniqueId(), backLoc.get(a.getUniqueId()));
            say(p, "&7Teleported to &a" + a.getName() + "&7's last location.");
        }
        else {
            say(p, "&a" + a.getName() + " &7does not have a saved last location.");
        }
    }

    public void setBackLoc(UUID id, Location loc) {
        this.backLoc.remove(id);
        this.backLoc.put(id, loc);
    }

    private boolean hasBackLoc(UUID id) {
        return this.backLoc.containsKey(id);
    }

    private void teleToBackLoc(UUID id, Location loc) {
        Bukkit.getPlayer(id).teleport(loc);
    }
}
