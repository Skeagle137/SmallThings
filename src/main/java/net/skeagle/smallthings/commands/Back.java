package net.skeagle.smallthings.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.UUID;

@CommandAlias("smallthings|smallt|sthings|st")
public class Back extends BaseCommand {

    private HashMap<UUID, Location> spying = new HashMap<>();
}
