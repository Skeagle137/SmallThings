package net.skeagle.smallthings.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static net.skeagle.smallthings.STmain.say;

@CommandAlias("smallthings|smallt|sthings|st")
public class Skin extends BaseCommand {

    @Subcommand("skin")
    @CommandAlias("skin")
    @CommandPermission("small.skin")
    @Description("Changes your skin.")
    public void onSkin(Player p, String[] args) {
        if (args.length == 0) {
            say(p, "&c/skin <player>");
        } else {
            setSkin(p, args[0]);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.hidePlayer(p);
                pl.showPlayer(p);
            }
        }
    }

    private void setSkin(Player p, String s) {
        try {
            URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + s);
            InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
            String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();

            URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
            JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = textureProperty.get("value").getAsString();
            String signature = textureProperty.get("signature").getAsString();

            EntityPlayer ep = ((CraftPlayer) p).getHandle();
            GameProfile gp = ep.getProfile();
            PropertyMap pm = gp.getProperties();
            Property property = pm.get("textures").iterator().next();
            pm.remove("textures", property);
            pm.put("textures", new Property("textures", texture, signature));
            reloadSkin(p);
            say(p, "&aYour skin has been changed successfully.");
        } catch (IOException e) {
            System.err.println("Could not get skin data from session servers!");
            e.printStackTrace();
        }
    }

    //this is necessary to see the changes applied to yourself
    private void reloadSkin(Player p) {
        EntityPlayer ep = ((CraftPlayer) p).getHandle();
        CraftServer cs = ((CraftServer) Bukkit.getServer());
        final PacketPlayOutPlayerInfo removeInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ep);
        final PacketPlayOutPlayerInfo addInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ep);
        ep.playerConnection.sendPacket(removeInfo);
        ep.playerConnection.sendPacket(addInfo);
        cs.getHandle().moveToWorld(ep, ep.dimension, true, p.getLocation(), true);
    }
}