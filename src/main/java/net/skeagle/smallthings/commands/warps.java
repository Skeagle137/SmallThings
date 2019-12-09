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
public class warps extends BaseCommand {
    private Resources r;
    private WarpsHomesUtil util;

    public warps(final Resources r) {
        this.r = r;
        util = new WarpsHomesUtil(r);
    }

    @Subcommand("warps")
    @CommandAlias("warps")
    @CommandPermission("small.warps")
    @Description("List all available warp points.")
    public void onWarps(Player p) {
        FileConfiguration config = this.r.getWarps();
        if (config.get("warps.") != null) {
            List<String> warps = new ArrayList<>();
            for (String key : config.getConfigurationSection("warps.").getKeys(false)) {
                config.getString(key);
                warps.add(key);
            }
            say(p, "&7Currently showing a list of &a" + warps.size() + "&7 warp point(s): &a" + String.join("&7,&a ", warps) + "&7.");
        } else {
            say(p,"&cThere are currently no warps available.");
        }
    }

    @Subcommand("delwarp")
    @CommandCompletion("@warps")
    @CommandAlias("delwarp")
    @CommandPermission("small.delwarp")
    @Description("Delete a warp point.")
    public void onDelwarp(Player p, String[] args) {
        if (args.length < 1) {
            say(p, "&cYou must provide a warp name. /delwarp <name>.");
            return;
        }
        util.delValues(p, "warps.", args[0], false);
    }

    @Subcommand("setwarp")
    @CommandAlias("setwarp")
    @CommandPermission("small.setwarp")
    @Description("Create a warp point.")
    public void onSetwarp(Player p, String[] args) {
        if (args.length < 1) {
            say(p, "&cYou must provide a warp name. /setwarp <name>.");
            return;
        }
        util.setValues(p, "warps.", args[0], false);
    }

    @Subcommand("warp")
    @CommandCompletion("@warps")
    @CommandAlias("warp")
    @CommandPermission("small.warp")
    @Description("Warp to the specified warp point.")
    public void onWarp(Player p, String[] args) {
        if (args.length < 1) {
            say(p, "&cYou must provide a warp name. /warp <name>.");
            return;
        }
        util.teleportToLoc(p, "warps.", args[0], false);
    }
}

