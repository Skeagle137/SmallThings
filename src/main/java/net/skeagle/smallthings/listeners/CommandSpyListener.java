package net.skeagle.smallthings.listeners;

import net.skeagle.smallthings.commands.CommandSpy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import static net.skeagle.smallthings.STmain.say;

public class CommandSpyListener implements Listener {


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommandSpy(PlayerCommandPreprocessEvent e) {
        for (Player spy : CommandSpy.spying) {
            say(spy, "&d&o(" + e.getPlayer().getName() + ") &5&l> &d" + e.getMessage());
        }
    }
}
