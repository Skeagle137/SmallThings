package net.skeagle.smallthings.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.skeagle.smallthings.utils.Resources;
import net.skeagle.smallthings.utils.WarpsHomesUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static net.skeagle.smallthings.STmain.say;

@CommandAlias("smallthings|smallt|sthings|st")
public class homes extends BaseCommand {
    private Resources r;
    private WarpsHomesUtil util;

    public homes(final Resources r) {
        this.r = r;
        util = new WarpsHomesUtil(r);
    }

    @Subcommand("homes")
    @CommandAlias("homes")
    @CommandPermission("small.homes")
    @Description("List all available homes.")
    public void onHomes(Player p) {
        FileConfiguration config = this.r.getWarps();
        if (config.get("homes." + p.getUniqueId()) != null) {
            List<String> homes = new ArrayList<>();
            for (String key : config.getConfigurationSection("homes." + p.getUniqueId()).getKeys(false)) {
                config.getString(key);
                homes.add(key);
            }
            String homeslist = String.join("&7,&a ", homes);
            say(p, "&7Currently showing a list of &a" + homes.size() + "&7 home(s): &a" + homeslist + "&7.");
        } else {
            say(p,"&cYou do not have any homes available.");
        }
    }

    @Subcommand("delhome")
    @CommandCompletion("@homes")
    @CommandAlias("delhome")
    @CommandPermission("small.delhome")
    @Description("Delete a home.")
    public void onDelhome(Player p, String[] args) {
        if (args.length < 1) {
            say(p, "&cYou must provide a warp name. /delhome <name>.");
        } else {
            util.delValues(p, "homes." + p.getUniqueId() + ".", args[0], true);
        }
    }

    @Subcommand("sethome")
    @CommandAlias("sethome")
    @CommandPermission("small.sethome")
    @Description("Create a home.")
    public void onSethome(Player p, String[] args) {
        if (args.length < 1) {
            say(p, "&cYou must provide a home name. /sethome <name>.");
            return;
        }
        util.setValues(p, "homes." + p.getUniqueId() + ".", args[0], true);
    }

    @Subcommand("home")
    @CommandCompletion("@homes")
    @CommandAlias("home")
    @CommandPermission("small.home")
    @Description("Teleport to the specified home.")
    public void onHome(Player p, String[] args) {
        if (args.length < 1) {
            say(p, "&cYou must provide a home name. /home <name>.");
        } else {
            util.teleportToLoc(p, "homes." + p.getUniqueId() + ".", args[0], true);
        }
    }
}
