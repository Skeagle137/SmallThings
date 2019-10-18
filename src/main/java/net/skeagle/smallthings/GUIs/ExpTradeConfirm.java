package net.skeagle.smallthings.GUIs;

import net.skeagle.smallthings.utils.CustomInventory;
import net.skeagle.smallthings.utils.ExpUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static jdk.nashorn.internal.objects.NativeMath.round;
import static net.skeagle.smallthings.STmain.say;

class ExpTradeConfirm extends CustomInventory {

    ExpTradeConfirm(Player p, String name, Material icon, Double worth, byte data, int amount) {
        super(9, "&9&lExp Trade - Confirm");

        float calcCost = (float) (ExpUtil.getExp(p) + worth * amount);
        int finalLevel = (int) ExpUtil.getLevelFromExp((long) calcCost);
        setItem(0, new ItemStack(Material.EMPTY_MAP), player -> {
        }, "&9&lInformation", new String[]{
                "&9Material: &a" + name,
                "&9Amount of material to trade: &a" + amount,
                "&9Exp gain from trade: &a" + round((worth * amount), 2) + " level(s)",
                "&9Final Exp level after trade: &a" + finalLevel});
        ItemStack i = new ItemStack(Material.AIR);
        i.setType(icon);
        i.setAmount(amount);
        i.setDurability(data);

        setItem(3, new ItemStack(Material.EMERALD_BLOCK), player -> {
            player.closeInventory();
            for (ItemStack item : p.getInventory().getContents()) {
                if (item != null && item.getType() == i.getType() && item.getDurability() == i.getDurability()) {
                    if (item.getAmount() >= amount) {
                        p.getInventory().removeItem(new ItemStack(item.getType(), amount, item.getDurability()));
                        expCalc(p, worth, amount);
                        say(player, "&dYou traded &5" + amount + "x &3" + name + " &dfor &2" + (worth * amount) + " &dexp level(s).");
                        break;
                    }
                }
            }
            //say(player, "&cThe item does not exist in your inventory. Could it have been removed or changed?");
        }, "&2&lCONFIRM", new String[]{});


        setItem(5, new ItemStack(Material.REDSTONE_BLOCK), player -> {
            say(player, "&cTrade canceled.");
            player.closeInventory();
        }, "&4&lCANCEL", new String[]{});


        setItem(8, new ItemStack(Material.ARROW), player ->
                new ExpTradeAmount(p, name, icon, worth, data).open(player), "&7&lBack", new String[]{});
    }

    private void expCalc(Player p, double worth, int amount) {
        //exp = 14 (approximately lvl 1 4/5), amount = 64, worth = 0.2 (you want 26.8 exp from this total)
        float exp = 0;
        exp += ExpUtil.getExp(p); //14

        if (exp < 0) {
            exp = 0;
        }

        double totalexp = ExpUtil.getLevelFromExp(exp) + worth * amount;
        int finallvl = (int) totalexp;
        p.setLevel(finallvl);
        p.setExp((float) totalexp - finallvl);
        /*
        if (exp + (worth * amount) <= ExpUtil.getExpFromLevel(p.getLevel() + 1)) { // (14 + (0.2 * 5) <= 16) = 15 <= 16
            p.setExp((float) worth * amount);  //1
        }
        else {
            float calcCost = (float) (exp + worth * amount); //26.8
            int finalLevel = (int) calcCost; //26
            say(p, String.valueOf(calcCost));
            say(p, String.valueOf(finalLevel));
            float finalExp = calcCost - finalLevel; //26.8-26
            p.setLevel(finalLevel); //3
            p.setExp(finalExp); //0.8
        }
        */
    }
}
