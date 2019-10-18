package net.skeagle.smallthings.utils;

import net.skeagle.smallthings.STmain;

public class Resources
{
    private STmain plugin;
    private Resource warps;
    private Resource totalplayed;

    public Resources(final STmain plugin) {
        this.plugin = plugin;
        this.warps = new Resource(plugin, "warps.yml");
        this.totalplayed = new Resource(plugin, "totalplayed.yml");
    }

    public void load() {
        this.warps.load();
        this.totalplayed.load();
    }

    public void reload() {
        this.load();
    }

    public void save() {
        this.warps.save();
        this.totalplayed.save();
    }

    public Resource getWarps() {
        return this.warps;
    }

    public Resource getTotalPlayed() {
        return this.totalplayed;
    }
}

