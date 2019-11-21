package net.skeagle.smallthings;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.skeagle.smallthings.STmain.say;

@CommandAlias("smallthings|smallt|sthings|st")
public class TPA extends BaseCommand {
    private STmain plugin;
    private static Map<UUID, UUID> tpa;

    private Player a;


    TPA(STmain st) {
        plugin = st;
        this.a = null;
    }

    @Subcommand("tpa")
    @CommandCompletion("@players")
    @CommandAlias("tpa|teleporta")
    @CommandPermission("small.tpa")
    @Description("Request to teleport to another player.")
    public void onTpa(Player p, String[] args) {
        if (args.length < 1) {
            say(p, "&cYou must specify a player.");
        } else {
            a = Bukkit.getPlayerExact(args[0]);
            if (a != null) {
                if (p == a) {
                    say(p, "&cYou cannot teleport to yourself.");
                }
                say(p, "&aTeleport request sent.");
                say(a, "&a" + p.getName() + " &7is requesting to teleport to you. Do /tpaccept to accept the request or /tpdeny to deny it. This request will expire in 2 minutes.");
                tpa.put(a.getUniqueId(), p.getUniqueId());
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                        this.killRequest(p.getUniqueId()), 120 * 20L);
            } else {
                say(p, "&cThat player is not online.");
            }
        }
    }

    @Subcommand("tpaccept")
    @CommandAlias("tpaccept|teleportaccept")
    @CommandPermission("small.tpaccept")
    @Description("Accept a pending teleport request.")
    public void onTpaccept(Player p, Player a) {
        UUID aUUID = tpa.get(a.getUniqueId());
        if (!tpa.containsKey(p.getUniqueId())) {
            say(p, "&cYou do not have any pending teleport requests.");
            return;
        }
        tpa.remove(p.getUniqueId());
        say(a, "&aTeleport request accepted.");
        a.teleport(p.getLocation());
    }

    @Subcommand("tpdeny")
    @CommandAlias("tpdeny|teleportdeny")
    @CommandPermission("small.tpdeny")
    @Description("Deny a pending teleport request.")
    public void onTpdeny(Player p) {
        if (!tpa.containsKey(p.getName())) {
            say(p, "&cYou do not have any pending teleport requests.");
            return;
        }
        Player a = Bukkit.getPlayer(tpa.get(p.getName()));
        tpa.remove(p.getName());
        if (a != null) {
            say(a, "&aTeleport request rejected.");
            return;
        }
        say(p, "&cThat player is not online.");
    }

    @Subcommand("tpahere")
    @CommandCompletion("@players")
    @CommandAlias("tpahere|teleportahere")
    @CommandPermission("small.tpahere")
    @Description("Request another player to teleport to you.")
    public void onTpahere(Player p, String[] args) {
        if (args.length < 1) {
            say(p, "&cYou must specify a player.");
        } else {
            Player a = Bukkit.getPlayerExact(args[0]);
            if (a != null) {
                if (p.getName().equals(a.getName())) {
                    say(p, "&cYou cannot teleport to yourself.");
                }
                say(p, "&aTeleport request sent.");
                say(a, "&a" + p.getName() + " &7is requesting to teleport to you. Do /tpaccept to accept the request or /tpdeny to deny it. This request will expire in 2 minutes.");
                tpa.put(a.getUniqueId(), p.getUniqueId());
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                        this.killRequest(p.getUniqueId()), 30 * 20L);
            } else {
                say(p, "&cThat player is not online.");
            }
        }
    }

    private void killRequest(final UUID key) {
        if (tpa.containsKey(key)) {
            final Player p = Bukkit.getPlayer(tpa.get(key));
            if (p != null) {
                say(p, "&cYour teleport request timed out.");
            }
            tpa.remove(key);
        }
    }
}