package net.skeagle.smallthings.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.skeagle.smallthings.STmain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

import static net.skeagle.smallthings.STmain.say;
import static net.skeagle.smallthings.STmain.saymult;

@CommandAlias("smallthings|smallt|sthings|st")
public class TPA extends BaseCommand {
    private STmain plugin;
    private HashMap<UUID, UUID> StoredPlayer;
    private static boolean tpahere;
    private static int task;

    public TPA(STmain st) {
        plugin = st;
        StoredPlayer = new HashMap<>();
    }

    @Subcommand("tpa")
    @CommandCompletion("@players")
    @CommandAlias("tpa|teleporta")
    @CommandPermission("small.tpa")
    @Description("Request to teleport to another player.")
    public void onTpa(Player p, String[] args) {
        if (args.length < 1) {
            say(p, "&cYou must specify a player.");
            return;
        }
        Player a = Bukkit.getPlayer(args[0]);
        if (a != null) {
            if (p.getName().equalsIgnoreCase(args[0])) {
                say(p, "&cYou cannot teleport to yourself.");
                return;
            }
            if (hasRequest(a)) {
                say(p, "&cYou already have a pending teleport request.");
                return;
            }
            if (hasSentRequest(a)) {
                say(p, "&cThat player already has a pending request.");
                return;
            }
            tpahere = false;
            say(p, "&aTeleport request sent.");
            say(a, "&a" + p.getName() + " &7is requesting to teleport to you. Do /tpaccept to accept the request or /tpdeny to deny it. This request will expire in 2 minutes.");
            StoredPlayer.put(a.getUniqueId(), p.getUniqueId());
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                    this.DelRequest(a.getUniqueId(), p.getUniqueId(), true), 120 * 20L);
            return;
        }
        say(p, "&cThat player is not online.");
    }

    @Subcommand("tpaccept")
    @CommandAlias("tpaccept|teleportaccept")
    @CommandPermission("small.tpaccept")
    @Description("Accept a pending teleport request.")
    public void onTpaccept(Player p) {
        if (!hasRequest(p)) {
            say(p, "&cYou do not have any pending teleport requests.");
            return;
        }
        Player a = getStoredPlayer(p);
        if (a != null) {
            saymult("&aTeleport request accepted.", p, a);
            teleportPlayer(p, a);
            return;
        }
        say(p, "&cThat player is not online.");
    }

    @Subcommand("tpdeny")
    @CommandAlias("tpdeny|teleportdeny")
    @CommandPermission("small.tpdeny")
    @Description("Deny a pending teleport request.")
    public void onTpdeny(Player p) {
        if (!hasRequest(p)) {
            say(p, "&cYou do not have any pending teleport requests.");
            return;
        }
        Player a = getStoredPlayer(p);
        if (a != null) {
            say(p,"&cDenied the current teleport request from " + a.getName() + ".");
            say(a,"&cYour teleport request was denied.");
            DelRequest(p.getUniqueId(), a.getUniqueId(), false);
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
            return;
        }
        Player a = Bukkit.getPlayer(args[0]);
        if (a != null) {
            if (p.getName().equalsIgnoreCase(args[0])) {
                say(p, "&cYou cannot teleport to yourself.");
                return;
            }
            if (hasRequest(a)) {
                say(p, "&cYou already have a pending teleport request.");
                return;
            }
            if (hasSentRequest(a)) {
                say(p, "&cThat player already has a pending request.");
                return;
            }
            tpahere = true;
            say(p, "&aTeleport request sent.");
            say(a, "&a" + p.getName() + " &7is requesting for you to teleport to them. Do /tpaccept to accept the request or /tpdeny to deny it. This request will expire in 2 minutes.");
            StoredPlayer.put(a.getUniqueId(), p.getUniqueId());
            task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                    this.DelRequest(a.getUniqueId(), p.getUniqueId(), true), 120 * 20L);
            return;
        }
        say(p, "&cThat player is not online.");
    }



    private boolean hasRequest(final Player p) {
        UUID uuid = StoredPlayer.get(p.getUniqueId());
        return uuid != null;
    }

    private boolean hasSentRequest(final Player p) {
        return StoredPlayer.containsKey(p.getUniqueId());
    }

    private Player getStoredPlayer(final Player p) {
        if (hasRequest(p)) {
            UUID uuid = StoredPlayer.get(p.getUniqueId());
            return Bukkit.getPlayer(uuid);
        }
        say(p, "&cThat player is not online.");
        return null;
    }

    private void teleportPlayer(final Player p, final Player a) {
        if (tpahere) {
            p.teleport(a);
            DelRequest(p.getUniqueId(), a.getUniqueId(), false);
            return;
        }
        a.teleport(p);
        DelRequest(p.getUniqueId(), a.getUniqueId(), false);
        Bukkit.getScheduler().cancelTask(task);
    }

    private void DelRequest(final UUID u1, final UUID u2, boolean showmsg) {
        if (showmsg) {
            say(Bukkit.getPlayer(u2),"&cThe teleport request has expired.");
            say(Bukkit.getPlayer(u1),"&cThe teleport request from " + Bukkit.getPlayer(u2).getName() + " has expired.");
        }
        StoredPlayer.remove(u1, u2);
        Bukkit.getScheduler().cancelTask(task);
    }
}