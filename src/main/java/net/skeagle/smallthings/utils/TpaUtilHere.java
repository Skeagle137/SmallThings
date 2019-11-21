package net.skeagle.smallthings.utils;

import org.bukkit.entity.Player;

public class TpaUtilHere {
    private Player self;
    private Player target;

    public TpaUtilHere(final Player self, final Player target) {
        this.setSelf(self);
        this.setTarget(target);
    }

    public Player getTarget() {
        return this.target;
    }

    public void setTarget(final Player target) {
        this.target = target;
    }

    public Player getSelf() {
        return this.self;
    }

    public void setSelf(final Player self) {
        this.self = self;
    }
}
