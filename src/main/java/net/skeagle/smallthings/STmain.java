package net.skeagle.smallthings;

import co.aikar.commands.PaperCommandManager;
import net.skeagle.smallthings.listeners.CommandSpyListener;
import net.skeagle.smallthings.listeners.InvClickListener;
import net.skeagle.smallthings.listeners.InvCloseListener;
import net.skeagle.smallthings.utils.Resources;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


public class STmain extends JavaPlugin {

    private PluginDescriptionFile pdf = this.getDescription();
    private static PaperCommandManager m;
    private static STmain plugin;
    private Resources resources;

    public STmain() {
        this.resources = new Resources(this);
    }



    @Override
    public void onEnable() {
        STmain.plugin = this;
        //command register
        register();
        //config stuff
        resources.load();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        //server messages
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "----------------------------------------");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "SmallThings version " + pdf.getVersion() + " is now enabled.");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "----------------------------------------");
        //listeners
        Bukkit.getPluginManager().registerEvents(new InvClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new InvCloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new CommandSpyListener(), this);
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "-----------------------------------------");
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "SmallThings version " + pdf.getVersion() + " is now disabled.");
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "-----------------------------------------");

    }

    public static void say(CommandSender cs, String message) {
        cs.sendMessage(color(message));
    }

    public static void saymult(String message, CommandSender... cs) {
        for (CommandSender sender : cs) {
            sender.sendMessage(color(message));
        }
    }

    public static String color(String i) {
        return ChatColor.translateAlternateColorCodes('&', i);
    }

    @SuppressWarnings("deprecation")
    private void register() {
        m = new PaperCommandManager(this);
        //actual cmds
        m.registerCommand(new SmallThings());
        m.registerCommand(new Push());
        m.registerCommand(new Rename());
        m.registerCommand(new Skin());
        m.registerCommand(new Exptrade());
        m.registerCommand(new Invsee());
        m.registerCommand(new TPA(this));
        m.registerCommand(new Suicide());
        m.registerCommand(new CommandSpy());
        m.registerCommand(new warps(this.resources));
        m.registerCommand(new homes(this.resources));
        m.enableUnstableAPI("help");

        m.setDefaultExceptionHandler((command, registeredCommand, sender, args, t) -> {
            getLogger().warning("Error occurred while executing command " + command.getName());
            return false;
        });
        reloadWarps();
        reloadHomes();
    }

    void reloadWarps() {
        FileConfiguration config = this.resources.getWarps();

        m.getCommandCompletions().registerCompletion("warps", c ->
                config.getConfigurationSection("warps.").getKeys(false));
    }

    void reloadHomes() {
        FileConfiguration config = this.resources.getWarps();

        m.getCommandCompletions().registerCompletion("homes", c ->
                config.getConfigurationSection("homes." + c.getPlayer().getUniqueId()).getKeys(false));
    }

    public static STmain getInstance() {
        return STmain.plugin;
    }
}
