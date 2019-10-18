package net.skeagle.smallthings;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.skeagle.smallthings.utils.Resources;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static net.skeagle.smallthings.STmain.say;


@CommandAlias("smallthings|smallt|sthings|st")
public class warps extends BaseCommand {
    private Resources r;

    public warps(final Resources r) {
        this.r = r;
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
            String warpslist = String.join("&7,&a ", warps);
            say(p, "&7Currently showing a list of &a" + warps.size() + "&7 warp point(s): &a" + warpslist + "&7.");
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
        } else {
            if (this.r.getWarps().get("warps." + args[0]) != null) {
                this.r.getWarps().set("warps." + args[0] + ".world", null);
                this.r.getWarps().set("warps." + args[0] + ".x", null);
                this.r.getWarps().set("warps." + args[0] + ".y", null);
                this.r.getWarps().set("warps." + args[0] + ".z", null);
                this.r.getWarps().set("warps." + args[0] + ".yaw", null);
                this.r.getWarps().set("warps." + args[0] + ".pitch", null);
                this.r.getWarps().set("warps." + args[0], null);
                this.r.getWarps().save();
                say(p,"&7The warp point &a" + args[0] + "&7 has been deleted.");
                STmain.getInstance().reloadWarps();
            } else {
                say(p,"&cThat warp point does not exist.");
            }
        }
    }

    @Subcommand("setwarp")
    @CommandAlias("setwarp")
    @CommandPermission("small.setwarp")
    @Description("Create a warp point.")
    public void onSetwarp(Player p, String[] args) {
        if (args.length < 1) {
            say(p, "&cYou must provide a warp name. /setwarp <name>.");
        } else {
            if (this.r.getWarps().get("warps." + args[0]) == null) {
                String w = p.getWorld().getName();
                this.r.getWarps().getString("warps");
                this.r.getWarps().set("warps." + args[0] + ".world", w);
                this.r.getWarps().set("warps." + args[0] + ".x", p.getLocation().getX());
                this.r.getWarps().set("warps." + args[0] + ".y", p.getLocation().getY());
                this.r.getWarps().set("warps." + args[0] + ".z", p.getLocation().getZ());
                this.r.getWarps().set("warps." + args[0] + ".yaw", p.getLocation().getYaw());
                this.r.getWarps().set("warps." + args[0] + ".pitch", p.getLocation().getPitch());
                this.r.getWarps().save();
                say(p, "&aWarp Successfully set. Teleport to it with /warp.");
                STmain.getInstance().reloadWarps();
            } else {
                say(p, "&cThat warp point already exists.");
            }
        }
    }

    @Subcommand("warp")
    @CommandCompletion("@warps")
    @CommandAlias("warp")
    @CommandPermission("small.warp")
    @Description("Warp to the specified warp point.")
    public void onWarp(Player p, String[] args) {
        if (args.length < 1) {
            say(p, "&cYou must provide a warp name. /warp <name>.");
        } else {
            if (this.r.getWarps().get("warps." + args[0]) != null) {
                String s = (String) this.r.getWarps().get("warps." + args[0] + ".world");
                int x = this.r.getWarps().getInt("warps." + args[0] + ".x");
                int y = this.r.getWarps().getInt("warps." + args[0] + ".y");
                int z = this.r.getWarps().getInt("warps." + args[0] + ".z");
                double yaw = this.r.getWarps().getDouble("warps." + args[0] + ".yaw");
                double pitch = this.r.getWarps().getDouble("warps." + args[0] + ".pitch");
                p.teleport(new Location(Bukkit.getWorld(s), x, y, z, (float) yaw, (float) pitch));
            } else {
                say(p, "&cThat warp point does not exist.");
            }
        }
    }


}

