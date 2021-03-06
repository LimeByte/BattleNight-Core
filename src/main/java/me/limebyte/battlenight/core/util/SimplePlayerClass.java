package me.limebyte.battlenight.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.limebyte.battlenight.api.util.PlayerClass;
import me.limebyte.battlenight.core.BattleNight;
import me.limebyte.battlenight.core.tosort.ConfigManager;
import me.limebyte.battlenight.core.tosort.ConfigManager.Config;
import me.limebyte.battlenight.core.util.player.Metadata;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.potion.PotionEffect;

public class SimplePlayerClass implements PlayerClass {
    private String name;
    private Permission permission;
    private List<ItemStack> items, armour;
    private List<PotionEffect> effects;
    private HashMap<String, Boolean> permissions;
    private double maxHealth;

    public SimplePlayerClass(String name, List<ItemStack> items, List<ItemStack> armour, List<PotionEffect> effects, double maxHealth) {
        this.name = name;
        this.items = items;
        this.armour = armour;
        this.effects = effects;
        this.permissions = new HashMap<String, Boolean>();
        this.maxHealth = maxHealth;

        String perm = "battlenight.class." + name.toLowerCase();
        String permDefault = ConfigManager.get(Config.CLASSES).getString("permission-default", "true").toUpperCase();
        permission = new Permission(perm, "Permission for the class: " + name + ".", PermissionDefault.getByName(permDefault));
        try {
            Bukkit.getServer().getPluginManager().addPermission(permission);
        } catch (Exception e) {
        }
    }

    @Override
    public void equip(Player player) {
        PlayerInventory inv = player.getInventory();

        // Set it
        Metadata.set(player, "class", name);

        // Main Inventory
        inv.clear();
        player.updateInventory();
        inv.setContents(items.toArray(new ItemStack[items.size()]));

        // Armour
        inv.setHelmet(armour.get(0));
        inv.setChestplate(armour.get(1));
        inv.setLeggings(armour.get(2));
        inv.setBoots(armour.get(3));

        player.updateInventory();

        // Effects
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.addPotionEffect(new PotionEffect(effect.getType(), 0, 0), true);
        }

        for (PotionEffect effect : effects) {
            player.addPotionEffect(effect, true);
        }

        // Permissions
        try {
            PermissionAttachment perms = player.addAttachment(BattleNight.instance);
            for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
                perms.setPermission(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
        }

        player.setMaxHealth(maxHealth);
        player.setHealth(maxHealth);
    }

    @Override
    public List<ItemStack> getArmour() {
        return armour;
    }

    @Override
    public List<PotionEffect> getEffects() {
        return effects;
    }

    @Override
    public List<ItemStack> getItems() {
        return items;
    }

    @Override
    public double getMaxHealth() {
        return maxHealth;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Permission getPermission() {
        return permission;
    }

    @Override
    public HashMap<String, Boolean> getPermissions() {
        return permissions;
    }
}