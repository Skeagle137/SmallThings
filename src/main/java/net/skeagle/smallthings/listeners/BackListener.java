package net.skeagle.smallthings.listeners;

import net.skeagle.smallthings.commands.Back;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BackListener implements Listener {

    private final Back back = new Back();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleport(PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND) {
            back.setBackLoc(e.getPlayer().getUniqueId(), e.getFrom());
        }

    }
}
