package net.skeagle.smallthings.listeners;

import net.skeagle.smallthings.STmain;
import net.skeagle.smallthings.utils.NickNameUtil;
import org.bukkit.ChatColor;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class NickListener implements Listener
{
    private final NickNameUtil util;

    public NickListener(final NickNameUtil util) {
        this.util = util;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(final PlayerJoinEvent e) {
        final String nick = util.getNickName(e.getPlayer().getUniqueId());
        if (nick != null) {
            e.getPlayer().setDisplayName(STmain.color(nick + "&r"));
            e.getPlayer().setPlayerListName(STmain.color(nick + "&r"));
            final String joinMsg = e.getJoinMessage();
            if (joinMsg != null && !joinMsg.equals("")) {
                e.setJoinMessage(joinMsg.replaceAll(e.getPlayer().getName(), nick));
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(final PlayerQuitEvent e) {
        final String nick = util.getNickName(e.getPlayer().getUniqueId());
        if (nick != null) {
            final String quitMsg = e.getQuitMessage();
            if (quitMsg != null && !quitMsg.equals("")) {
                e.setQuitMessage(quitMsg.replaceAll(e.getPlayer().getName(), nick + ChatColor.RESET + ChatColor.YELLOW));
            }
        }
    }
}
