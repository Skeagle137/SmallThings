package net.skeagle.smallthings.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.skeagle.smallthings.STmain;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static net.skeagle.smallthings.STmain.say;

@CommandAlias("smallthings|smallt|sthings|st")
public class Rename extends BaseCommand {

    @Subcommand("rename")
    @CommandAlias("rename")
    @CommandPermission("small.rename")
    @Description("Renames the item in your main hand.")
    public void onRename(Player p, String[] args) {
        String s = String.join(" ", args);
        ItemStack is = p.getInventory().getItemInMainHand();
        if (is != null) {
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(STmain.color(s));
            is.setItemMeta(im);
            say(p, "&aItem successfully renamed.");
        } else {
            say(p, "&cYou must be holding an item in your main hand.");
        }
    }
}
