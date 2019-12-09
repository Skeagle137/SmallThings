package net.skeagle.smallthings;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import net.skeagle.smallthings.utils.NickNameUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.skeagle.smallthings.STmain.say;

@CommandAlias("smallthings|smallt|sthings|st")
public class RemoveNick extends BaseCommand {
    private NickNameUtil util;

    public RemoveNick(NickNameUtil util) {
        this.util = util;
    }

    @Subcommand("removenick|rnick")
    @CommandCompletion("@players")
    @CommandAlias("removenick|rnick")
    @Description("Take away a nickname that you or another player has.")
    public void onRemoveNick(CommandSender cs, Player p, String[] args) {
        if (args.length < 1) {
            if (p.hasPermission("small.nick.self")) {
                util.setNickName(p.getUniqueId(), null);
                p.setDisplayName(p.getName());
                p.setPlayerListName(p.getName());
                say(p, "&aNickname successfully removed.");
            }
            else {
                say(p, "You do not have permission to do this.");
            }
        }
        else if (cs.hasPermission("small.nick.other")) {
            Player a = Bukkit.getPlayer(args[0]);
            if (a == null) {
                say(cs, "&cThat player is not online.");
                return;
            }
            util.setNickName(a.getUniqueId(), null);
            a.setDisplayName(a.getName());
            a.setPlayerListName(a.getName());
            say(cs, "&7Removed nickname for &a" + a.getName() + ".");
            say(a, "&7Your nickname was disabled.");
        }
        else {
            say(cs, "You do not have permission to do this.");
        }
    }
}
