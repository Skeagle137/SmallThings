package net.skeagle.smallthings.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import net.skeagle.smallthings.utils.NickNameUtil;
import org.bukkit.command.CommandSender;

import java.util.UUID;

import static net.skeagle.smallthings.STmain.say;

@CommandAlias("smallthings|smallt|sthings|st")
public class Realname extends BaseCommand {

    private NickNameUtil util;

    public Realname(NickNameUtil util) {
        this.util = util;
    }

    @Subcommand("realname")
    @CommandCompletion("@players")
    @CommandAlias("realname")
    @Description("Look up a player's real name from their nick name.")
    public void onRealname(CommandSender cs, String[] args) {
        if (args.length < 1) {
            say(cs, "&cYou need to provide a nicked player name.");
            return;
        }
        if (cs.hasPermission("small.realname")) {
            UUID nick = util.getPlayerFromNickName(args[0]);
            if (nick == null) {
                say(cs,"&7There is no player that has that nickname.");
                return;
            }
            say(cs,"&7The player with the nickname " + args[0] + "&r&7 is called " + nick);
        } else {
            say(cs,"You do not have permission to do this.");
        }
    }
}
