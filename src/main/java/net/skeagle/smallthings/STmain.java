package net.skeagle.smallthings;

import co.aikar.commands.PaperCommandManager;
import net.skeagle.smallthings.commands.*;
import net.skeagle.smallthings.commands.GamemodeMain;
import net.skeagle.smallthings.commands.nicknames.Nick;
import net.skeagle.smallthings.commands.nicknames.Realname;
import net.skeagle.smallthings.commands.nicknames.RemoveNick;
import net.skeagle.smallthings.listeners.*;
import net.skeagle.smallthings.utils.NickNameUtil;
import net.skeagle.smallthings.utils.Resources;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


public class STmain extends JavaPlugin {

    private PluginDescriptionFile pdf = this.getDescription();
    private static PaperCommandManager m;
    private static STmain plugin;
    private Resources resources;
    private NickNameUtil nickNameUtil;

    public STmain() {
        this.resources = new Resources(this);
        this.nickNameUtil = new NickNameUtil(resources);
    }

    //TODO: /skin saves to config
    // add tp to other homes
    // ranks acquired over time
    // teleport pads (<--- ADD AUTO AFK BEFORE)
    // redo /invsee with custom armor and offhand slots w/ health and hunger
    // some form of custom enchants



    @Override
    public void onEnable() {
        STmain.plugin = this;
        //command register
        register();
        //config stuff
        resources.load();
        nickNameUtil.loadNicks();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        //server messages
        log("&a----------------------------------------", "&aSmallThings version " + pdf.getVersion() + " is now enabled.", "&a----------------------------------------");
        //listeners
        Bukkit.getPluginManager().registerEvents(new InvClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new InvCloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new CommandSpyListener(), this);
        Bukkit.getPluginManager().registerEvents(new NickListener(nickNameUtil), this);
        Bukkit.getPluginManager().registerEvents(new BackListener(), this);
    }

    @Override
    public void onDisable() {
        nickNameUtil.saveNicks();
        resources.save();
        log("&c-----------------------------------------", "&cSmallThings version " + pdf.getVersion() + " is now disabled.", "&c-----------------------------------------");

    }

    public static void say(CommandSender cs, String message) {
        if (cs == null) return;
        cs.sendMessage(color(message));
    }

    public static void say(CommandSender cs, String... message) {
        if (cs == null) return;
        for (String msg : message) {
            cs.sendMessage(color(msg));
        }
    }

    public static void saymult(String message, CommandSender... cs) {
        for (CommandSender sender : cs) {
            if (sender == null) return;
            sender.sendMessage(color(message));
        }
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(color(message));
    }

    public static void log(String... message) {
        for (String msg : message) {
            Bukkit.getConsoleSender().sendMessage(color(msg));
        }
    }

    public static void logAdmin(Player p, String message) {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl.hasPermission("small.admin")) {
                say(pl, "&7[&e" + p.getName() + "&7]: &o" + message);
            }
        }
        log("&7[&e" + p.getName() + "&7]: &o" + message);
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
        m.registerCommand(new Realname(nickNameUtil));
        m.registerCommand(new Nick(nickNameUtil));
        m.registerCommand(new RemoveNick(nickNameUtil));
        m.registerCommand(new Back());
        m.registerCommand(new GamemodeMain());
        m.enableUnstableAPI("help");

        m.setDefaultExceptionHandler((command, registeredCommand, sender, args, t) -> {
            getLogger().warning("Error occurred while executing command " + command.getName());
            return false;
        });
        reloadCompletions();
    }


    private void reloadCompletions() {
        FileConfiguration config = resources.getWarps();

        m.getCommandCompletions().registerCompletion("warps", c ->
                config.getConfigurationSection("warps.").getKeys(false));

        m.getCommandCompletions().registerCompletion("homes", c ->
                config.getConfigurationSection("homes." + c.getPlayer().getUniqueId()).getKeys(false));
        
    }
}
