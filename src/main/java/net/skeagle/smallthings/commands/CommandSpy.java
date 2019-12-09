package net.skeagle.smallthings.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static net.skeagle.smallthings.STmain.say;

@CommandAlias("smallthings|smallt|sthings|st")
public class CommandSpy extends BaseCommand {

    public static ArrayList<Player> spying = new ArrayList<>();

    @Subcommand("commandspy")
    @CommandAlias("commandspy|cspy|cmdspy")
    @CommandPermission("small.commandspy")
    @Description("Allows you to spy on other players' commands")
    public void onSpy(Player p) {
        if (spying.contains(p)) {
            spying.remove(p);
            say(p, "&cDisabled commandspy.");
        } else {
            spying.add(p);
            say(p, "&aEnabled commandspy.");
        }
    }
}
