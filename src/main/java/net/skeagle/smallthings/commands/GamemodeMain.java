package net.skeagle.smallthings.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import static net.skeagle.smallthings.STmain.say;

@CommandAlias("gm|gamemode")
@Description("Toggle gamemode.")
public class GamemodeMain extends BaseCommand {
    @CatchUnknown
    public void unknownCmd(Player player) {
        player.sendMessage("&cYou have to specify the gamemode and/or player. /gamemode <creative|spectator|adventure|survival> [player]");
    }

    @Subcommand("creative|1|c")
    @CommandAlias("gmc")
    @CommandCompletion("@players")
    public void onGameModeC(Player p) {
        p.setGameMode(GameMode.CREATIVE);
        say(p, "&7Your gamemode has been updated to &acreative&7.");
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.hasPermission("small.admin")) {
                say(pl, "&7[&e" + p.getName() + "&7]: Updated gamemode to creative");
            }
        }
        Bukkit.getConsoleSender().sendMessage("&7[&e" + p.getName() + "&7]: Updated gamemode to creative");
    }

    @Subcommand("creative|1|c")
    @CommandAlias("gmc")
    @CommandCompletion("@players")
    public void onGameModeOtherC(Player p, OnlinePlayer a) {
        a.getPlayer().setGameMode(GameMode.CREATIVE);
        say(a.getPlayer(), "&7Your gamemode has been updated to &acreative&7.");
        say(p, "&a" + a.getPlayer().getName() + "&7's gamemode has been updated to &acreative&7.");
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.hasPermission("small.admin")) {
                say(pl, "&7[&e" + p.getName() + "&7]: Updated " + a.getPlayer().getName() + "'s gamemode to creative");
            }
        }
        Bukkit.getConsoleSender().sendMessage("&7[&e" + p.getName() + "&7]: Updated " + a.getPlayer().getName() + "'s gamemode to creative");
    }

    @Subcommand("adventure|2|a")
    @CommandAlias("gma")
    @CommandCompletion("@players")
    public void onGameModeA(Player p) {
        p.setGameMode(GameMode.ADVENTURE);
        say(p, "&7Your gamemode has been updated to &aadventure&7.");
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.hasPermission("small.admin")) {
                say(pl, "&7[&e" + p.getName() + "&7]: Updated gamemode to adventure");
            }
        }
        Bukkit.getConsoleSender().sendMessage("&7[&e" + p.getName() + "&7]: Updated gamemode to adventure");
    }

    @Subcommand("adventure|2|a")
    @CommandAlias("gma")
    @CommandCompletion("@players")
    public void onGameModeOtherA(Player p, OnlinePlayer a) {
        a.getPlayer().setGameMode(GameMode.ADVENTURE);
        say(a.getPlayer(), "&7Your gamemode has been updated to &aadventure&7.");
        say(p, "&a" + a.getPlayer().getName() + "&7's gamemode has been updated to &aadventure&7.");
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.hasPermission("small.admin")) {
                say(pl, "&7[&e" + p.getName() + "&7]: Updated " + a.getPlayer().getName() + "'s gamemode to adventure");
            }
        }
        Bukkit.getConsoleSender().sendMessage("&7[&e" + p.getName() + "&7]: Updated " + a.getPlayer().getName() + "'s gamemode to adventure");
    }

    @Subcommand("survival|0|s")
    @CommandAlias("gms")
    @CommandCompletion("@players")
    public void onGameModeS(Player p) {
        p.setGameMode(GameMode.SURVIVAL);
        say(p, "&7Your gamemode has been updated to &asurvival&7.");
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.hasPermission("small.admin")) {
                say(pl, "&7[&e" + p.getName() + "&7]: Updated gamemode to survival");
            }
        }
        Bukkit.getConsoleSender().sendMessage("&7[&e" + p.getName() + "&7]: Updated gamemode to survival");
    }

    @Subcommand("survival|0|s")
    @CommandAlias("gms")
    @CommandCompletion("@players")
    public void onGameModeOtherS(Player p, OnlinePlayer a) {
        a.getPlayer().setGameMode(GameMode.SURVIVAL);
        say(a.getPlayer(), "&7Your gamemode has been updated to &asurvival&7.");
        say(p, "&a" + a.getPlayer().getName() + "&7's gamemode has been updated to &asurvival&7.");
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.hasPermission("small.admin")) {
                say(pl, "&7[&e" + p.getName() + "&7]: Updated " + a.getPlayer().getName() + "'s gamemode to survival");
            }
        }
        Bukkit.getConsoleSender().sendMessage("&7[&e" + p.getName() + "&7]: Updated " + a.getPlayer().getName() + "'s gamemode to survival");
    }

    @Subcommand("spectator|3|sp")
    @CommandAlias("gmsp")
    @CommandCompletion("@players")
    public void onGameModeSP(Player p) {
        p.setGameMode(GameMode.SPECTATOR);
        say(p, "&7Your gamemode has been updated to &aspectator&7.");
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.hasPermission("small.admin")) {
                say(pl, "&7[&e" + p.getName() + "&7]: Updated gamemode to spectator");
            }
        }
        Bukkit.getConsoleSender().sendMessage("&7[&e" + p.getName() + "&7]: Updated gamemode to spectator");
    }

    @Subcommand("spectator|3|sp")
    @CommandAlias("gmsp")
    @CommandCompletion("@players")
    public void onGameModeOtherSP(Player p, OnlinePlayer a) {
        a.getPlayer().setGameMode(GameMode.SPECTATOR);
        say(a.getPlayer(), "&7Your gamemode has been updated to &aspectator&7.");
        say(p, "&a" + a.getPlayer().getName() + "&7's gamemode has been updated to &aspectator&7.");
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.hasPermission("small.admin")) {
                say(pl, "&7[&e" + p.getName() + "&7]: Updated " + a.getPlayer().getName() + "'s gamemode to spectator");
            }
        }
        Bukkit.getConsoleSender().sendMessage("&7[&e" + p.getName() + "&7]: Updated " + a.getPlayer().getName() + "'s gamemode to spectator");
    }
}
