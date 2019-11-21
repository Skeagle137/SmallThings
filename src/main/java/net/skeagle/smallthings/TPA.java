package net.skeagle.smallthings;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.skeagle.smallthings.utils.TpaUtil;
import net.skeagle.smallthings.utils.TpaUtilHere;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

import static net.skeagle.smallthings.STmain.say;
//TODO: TEST!!!
@CommandAlias("smallthings|smallt|sthings|st")
public class TPA extends BaseCommand {
    private STmain plugin;
    private List<TpaUtil> tpaList;
    private List<TpaUtilHere> tpaHereList;

    TPA(STmain st) {
        plugin = st;
        tpaList = new ArrayList<>();
        tpaHereList = new ArrayList<>();
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
            Player a = Bukkit.getPlayerExact(args[0]);
            if (a != null) {
                if (p.getName().equalsIgnoreCase(args[0])) {
                    say(p, "&cYou cannot teleport to yourself.");
                }
                say(p, "&aTeleport request sent.");
                say(a, "&a" + p.getName() + " &7is requesting to teleport to you. Do /tpaccept to accept the request or /tpdeny to deny it. This request will expire in 2 minutes.");
                final TpaUtil tpa = new TpaUtil(p, a);
                tpaList.add(tpa);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                        this.killRequest(p, a), 120 * 20L);
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
        if (!this.IsPlayerInvitedByPlayer_tphere(a, p) && !this.IsPlayerInvitedByPlayer_tpa(a, p)) {
            say(p, "&cYou do not have any pending teleport requests.");
            return;
        }
        if (this.IsPlayerInvitedByPlayer_tpa(a, p)) {
            a.teleport(p);
        }
        if (this.IsPlayerInvitedByPlayer_tphere(a, p)) {
            p.teleport(a);
        }
        this.DeletetpaRequest(a, p);
        say(a, "&aTeleport request accepted.");
        a.teleport(p.getLocation());
    }

    @Subcommand("tpdeny")
    @CommandAlias("tpdeny|teleportdeny")
    @CommandPermission("small.tpdeny")
    @Description("Deny a pending teleport request.")
    public void onTpdeny(Player p, Player a) {
        if (!this.IsPlayerInvitedByPlayer_tphere(a, p) && !this.IsPlayerInvitedByPlayer_tpa(a, p)) {
            say(p, "&cYou do not have any pending teleport requests.");
            return;
        }
        if (a != null) {
            if (this.IsPlayerInvitedByPlayer_tpa(a, p)) {
                this.DeletetpaRequest(a, p);
            }
            if (this.IsPlayerInvitedByPlayer_tphere(a, p)) {
                this.DeletetpahereRequest(a, p);
            }
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
                if (p.getName().equalsIgnoreCase(args[0])) {
                    say(p, "&cYou cannot teleport to yourself.");
                }
                say(p, "&aTeleport request sent.");
                say(a, "&a" + p.getName() + " &7is requesting to teleport to you. Do /tpaccept to accept the request or /tpdeny to deny it. This request will expire in 2 minutes.");
                final TpaUtilHere tpaHere = new TpaUtilHere(p, a);
                tpaHereList.add(tpaHere);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                        this.killRequest(p, a), 30 * 20L);
            } else {
                say(p, "&cThat player is not online.");
            }
        }
    }

    private void killRequest(Player p, Player a) {
        if (this.IsPlayerInvitedByPlayer_tphere(a, p) || this.IsPlayerInvitedByPlayer_tpa(a, p)) ;
        if (p != null) {
            say(p, "&cYour teleport request timed out.");
        }
        if (this.IsPlayerInvitedByPlayer_tpa(a, p)) {
            this.DeletetpaRequest(a, p);
        }
        if (this.IsPlayerInvitedByPlayer_tphere(a, p)) {
            this.DeletetpahereRequest(a, p);
        }
    }

    private boolean IsPlayerInvitedByPlayer_tphere(final Player p1, final Player p2) {
        for (TpaUtilHere tpahere : tpaHereList) {
            if (p1.getDisplayName().equals(tpahere.getSelf().getDisplayName()) && p2.getDisplayName().equals(tpahere.getTarget().getDisplayName())) {
                return true;
            }
        }
        return false;
    }

    private boolean IsPlayerInvitedByPlayer_tpa(final Player p1, final Player p2) {
        for (final TpaUtil tpa : tpaList) {
            if (p1.getDisplayName().equals(tpa.getSelf().getDisplayName()) && p2.getDisplayName().equals(tpa.getTarget().getDisplayName())) {
                return true;
            }
        }
        return false;
    }

    private void DeletetpaRequest(final Player p1, final Player p2) {
        tpaList.removeIf(tpaData -> tpaData.getSelf().getDisplayName().equals(p1.getDisplayName()) && tpaData.getTarget().getDisplayName().equals(p2.getDisplayName()));
    }

    private void DeletetpahereRequest(final Player p1, final Player p2) {
        tpaHereList.removeIf(tpaHereData -> tpaHereData.getSelf().getDisplayName().equals(p1.getDisplayName()) && tpaHereData.getTarget().getDisplayName().equals(p2.getDisplayName()));
    }
}