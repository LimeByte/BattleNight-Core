package me.limebyte.battlenight.core.util;

import java.util.ArrayList;
import java.util.List;

import me.limebyte.battlenight.core.listeners.SignListener;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class ClassSign {
    private final Sign sign;
    private List<String> names = new ArrayList<String>();

    public ClassSign(Sign sign) {
        this.sign = sign;
    }

    public void add(Player player) {
        SignListener.cleanSigns(player);

        this.addName(player.getName());
        this.refresh();
    }

    public void remove(Player player) {
        this.removeName(player.getName());
        this.refresh();
    }

    public void clear() {
        this.sign.setLine(2, "");
        this.sign.setLine(3, "");
        this.names.clear();
        this.refresh();
    }

    public Sign getSign() {
        return this.sign;
    }

    private void addName(String name) {
        this.names.remove(name);
        this.names.add(0, name);
    }

    private void removeName(String name) {
        this.names.remove(name);
    }

    private String getLine2() {
        String line2 = this.names.get(0);
        return line2 != null ? line2 : "";
    }

    private String getLine3() {
        String line3 = this.names.get(1);
        return line3 != null ? line3 : "";
    }

    private void refresh() {
        this.sign.setLine(2, getLine2());
        this.sign.setLine(3, getLine3());
        this.sign.update();
    }
}
