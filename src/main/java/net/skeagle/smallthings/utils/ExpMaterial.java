package net.skeagle.smallthings.utils;

import org.bukkit.Material;

public enum ExpMaterial {
    ENDER_DRAGON_EGG(32, Material.DRAGON_EGG),
    NETHER_STAR(16, Material.NETHER_STAR),
    ENCHANTED_GOLDEN_APPLE(8, Material.GOLDEN_APPLE, (byte) 1),
    EMERALD(2, Material.EMERALD),
    DIAMOND(1, Material.DIAMOND),
    GOLD_INGOT(0.167, Material.GOLD_INGOT), // 1/6
    IRON_INGOT(0.125, Material.IRON_INGOT), //  1/8
    GLOWSTONE_DUST(0.0208, Material.GLOWSTONE_DUST), //  1/48
    NETHER_QUARTZ(0.015, Material.QUARTZ), //  1/64
    LAPIS_LAZULI(0.0104167, Material.LAPIS_LAZULI, (byte) 4), // 1/96
    COAL(0.015625, Material.COAL), //  1/128
    REDSTONE_DUST(0.015625, Material.REDSTONE); //  1/128


    private double worth;
    private Material icon;
    private byte data;

    ExpMaterial(double worth, Material icon) {
        this.worth = worth;
        this.icon = icon;
        this.data = 0;
    }

    ExpMaterial(double worth, Material icon, byte data) {
        this.worth = worth;
        this.icon = icon;
        this.data = data;
    }

    public double getValue() {
        return worth;
    }

    public Material getIcon() {
        return icon;
    }

    public byte getData() {
        return data;
    }
}
