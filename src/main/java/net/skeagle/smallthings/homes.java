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
public class homes extends BaseCommand {
    private Resources r;

    public homes(final Resources r) {
        this.r = r;
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
            if (this.r.getWarps().get("homes." + p.getUniqueId() + "." + args[0]) != null) {
                this.r.getWarps().set("homes." + p.getUniqueId() + "." + args[0] + ".world", null);
                this.r.getWarps().set("homes." + p.getUniqueId() + "." + args[0] + ".x", null);
                this.r.getWarps().set("homes." + p.getUniqueId() + "." + args[0] + ".y", null);
                this.r.getWarps().set("homes." + p.getUniqueId() + "." + args[0] + ".z", null);
                this.r.getWarps().set("homes." + p.getUniqueId() + "." + args[0] + ".yaw", null);
                this.r.getWarps().set("homes." + p.getUniqueId() + "." + args[0] + ".pitch", null);
                this.r.getWarps().set("homes." + p.getUniqueId() + "." + args[0], null);
                this.r.getWarps().save();
                say(p,"&7The home &a" + args[0] + "&7 has been deleted.");
                STmain.getInstance().reloadHomes();
            } else {
                say(p,"&cThat home does not exist.");
            }
        }
    }

    @Subcommand("sethome")
    @CommandAlias("sethome")
    @CommandPermission("small.sethome")
    @Description("Create a home.")
    public void onSethome(Player p, String[] args) {
        if (args.length < 1) {
            say(p, "&cYou must provide a home name. /sethome <name>.");
        } else {
            if (this.r.getWarps().get("homes." + p.getUniqueId() + "." + args[0]) == null) {
                String w = p.getWorld().getName();
                this.r.getWarps().getString("warps");
                this.r.getWarps().set("homes." + p.getUniqueId() + "." + args[0] + ".world", w);
                this.r.getWarps().set("homes." + p.getUniqueId() + "." + args[0] + ".x", p.getLocation().getX());
                this.r.getWarps().set("homes." + p.getUniqueId() + "." + args[0] + ".y", p.getLocation().getY());
                this.r.getWarps().set("homes." + p.getUniqueId() + "." + args[0] + ".z", p.getLocation().getZ());
                this.r.getWarps().set("homes." + p.getUniqueId() + "." + args[0] + ".yaw", p.getLocation().getYaw());
                this.r.getWarps().set("homes." + p.getUniqueId() + "." + args[0] + ".pitch", p.getLocation().getPitch());
                this.r.getWarps().save();
                say(p, "&aHome Successfully set. Teleport to it with /home.");
                STmain.getInstance().reloadHomes();
            } else {
                say(p, "&cThat home already exists.");
            }
        }
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
            if (this.r.getWarps().get("homes." + p.getUniqueId() + "." + args[0]) != null) {
                String s = (String) this.r.getWarps().get("homes." + p.getUniqueId() + "." + args[0] + ".world");
                int x = this.r.getWarps().getInt("homes." + p.getUniqueId() + "." + args[0] + ".x");
                int y = this.r.getWarps().getInt("homes." + p.getUniqueId() + "." + args[0] + ".y");
                int z = this.r.getWarps().getInt("homes." + p.getUniqueId() + "." + args[0] + ".z");
                double yaw = this.r.getWarps().getDouble("homes." + p.getUniqueId() + "." + args[0] + ".yaw");
                double pitch = this.r.getWarps().getDouble("homes." + p.getUniqueId() + "." + args[0] + ".pitch");
                p.teleport(new Location(Bukkit.getWorld(s), x, y, z, (float) yaw, (float) pitch));
            } else {
                say(p, "&cThat home does not exist.");
            }
        }
    }
}
