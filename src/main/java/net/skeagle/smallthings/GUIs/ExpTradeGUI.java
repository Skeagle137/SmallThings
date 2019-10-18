package net.skeagle.smallthings.GUIs;

import net.skeagle.smallthings.utils.CustomInventory;
import net.skeagle.smallthings.utils.ExpMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static co.aikar.commands.ACFUtil.round;
import static net.skeagle.smallthings.STmain.say;

public class ExpTradeGUI extends CustomInventory {


    public ExpTradeGUI(Player p) {
        super(18, "&9&lExp Trade - Menu");
        int slot = 0;

        for (ExpMaterial expmat : ExpMaterial.values()) {
            String name = expmat.toString().toLowerCase().replaceAll("_", " ");
            Material icon = expmat.getIcon();
            double worth = expmat.getValue();
            byte data = expmat.getData();
            ItemStack i = new ItemStack(icon, 1, data);

            setItem(slot, i, player ->
            {
                if (checkInv(p, i)) {
                    new ExpTradeAmount(p, name, icon, worth, data).open(player);
                } else {
                    player.closeInventory();
                    say(p, "&cThat item is not in your inventory.");
                }
            }, "&l" + name, new String[]{
                    "&9-----------------------------------------",
                    "&7One &b" + name + " &7is worth &b" + worth + " &7exp level(s).",
                    "&9-----------------------------------------"});
            slot++;
        }
        int exp = p.getLevel();
        float points = p.getExp();
        setItem(17, new ItemStack(Material.EMPTY_MAP), player ->
        {
        }, "&l&oStats", new String[]{
                "&9-----------------------------------------",
                "&eCurrent Exp level: &6" + exp,
                "&eCurrent next level progress: &6" + round(points, 2) + "%",
                "&9-----------------------------------------"});
    }
    private boolean checkInv(Player player, ItemStack i) {
        for(ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == i.getType() && item.getDurability() == i.getDurability()) {
                return true;
            }
        }
        return false;
    }
}
