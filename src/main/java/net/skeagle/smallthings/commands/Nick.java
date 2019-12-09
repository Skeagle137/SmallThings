package net.skeagle.smallthings.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.skeagle.smallthings.STmain;
import net.skeagle.smallthings.utils.NickNameUtil;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.command.*;

import static net.skeagle.smallthings.STmain.say;

@CommandAlias("smallthings|smallt|sthings|st")
public class Nick extends BaseCommand {
    private NickNameUtil util;

    public Nick(NickNameUtil util) {
        this.util = util;
    }

    @Subcommand("nick|nickname")
    @CommandCompletion("@players")
    @CommandAlias("nick|nickname")
    @Description("Give yourself or another player a nickname.")
    public void onNick(CommandSender cs, Player p, String[] args) {
        if (args.length < 1) {
            say(p, "&cYou must provide a nickname or player. /nick <nickname> or /nick <playername> <nickname>");
        } else if (args.length == 1) {
            if (p.hasPermission("small.nick.self")) {
                String nick = STmain.color(args[0] + "&r");
                util.setNickName(p.getUniqueId(), nick);
                p.setDisplayName(p.getName());
                p.setDisplayName(nick);
                p.setPlayerListName(nick);
                say(p, "&aNickname successfully changed.");
            } else {
                say(p, "&cYou do not have permission to do this.");
            }
        } else {
            if (p.hasPermission("nicknames.nick.other")) {
                Player a = Bukkit.getPlayer(args[0]);
                if (a == null) {
                    say(cs, "&cThat player is not online.");
                    return;
                }
                String nick = STmain.color(args[1] + "&r");
                util.setNickName(a.getUniqueId(), nick);
                a.setDisplayName(nick);
                a.setPlayerListName(nick);
                say(p, "&7Successfully set &a" + a.getName() + "&7's nick to " + nick + "&r&7.");
            } else {
                say(cs, "&cYou do not have permission to do this.");
            }
        }
    }
}
