package net.skeagle.smallthings.utils;

import net.skeagle.smallthings.STmain;

public class Resources
{
    private STmain plugin;
    private Resource warps;
    private Resource playerdata;

    public Resources(final STmain plugin) {
        this.plugin = plugin;
        this.warps = new Resource(plugin, "warps.yml");
        this.playerdata = new Resource(plugin, "playerdata.yml");
    }

    public void load() {
        warps.load();
        playerdata.load();
    }

    public void reload() {
        this.load();
    }

    public void save() {
        warps.save();
        playerdata.save();
    }

    public Resource getWarps() {
        return warps;
    }

    public Resource getplayerData() {
        return playerdata;
    }
}

