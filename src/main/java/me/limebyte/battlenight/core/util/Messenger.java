package me.limebyte.battlenight.core.util;

import java.util.List;
import java.util.logging.Level;

import me.limebyte.battlenight.api.BattleNightAPI;
import me.limebyte.battlenight.api.battle.Battle;
import me.limebyte.battlenight.api.tosort.Arena;
import me.limebyte.battlenight.api.tosort.Page;
import me.limebyte.battlenight.api.util.Song;
import me.limebyte.battlenight.core.BattleNight;
import me.limebyte.battlenight.core.Team;
import me.limebyte.battlenight.core.TeamedBattle;
import me.limebyte.battlenight.core.util.config.ConfigManager;
import me.limebyte.battlenight.core.util.config.ConfigManager.Config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Messenger {

    public enum Message {
        // Battle Messages
        BATTLE_IN_PROGRESS(ChatColor.RED + "A Battle is already in progress!"),
        BATTLE_NOT_IN_PROGRESS(ChatColor.RED + "A Battle is not in progress!"),
        ALREADY_IN_BATTLE(ChatColor.RED + "You have already joined a Battle!"),
        NOT_IN_BATTLE(ChatColor.RED + "You are not in a Battle!"),
        NO_CHEATING(ChatColor.RED + "Not so fast!  No cheating."),
        NO_TELEPORTING(ChatColor.RED + "You are not permitted to teleport while in a Battle."),
        NO_COMMAND(ChatColor.RED + "You are not permitted to perform this command while in a Battle."),
        ALREADY_SPECTATING(ChatColor.RED + "You are already watching a Battle!"),
        WELCOME_SPECTATOR("Welcome!  You are spectating $1."),
        TARGET_CYCLED("You are now spectating $1."),
        GOODBYE_SPECTATOR("You have stopped spectating."),
        CANT_SPECTATE("You must leave the Battle before spectating."),
        WAYPOINTS_UNSET(ChatColor.RED + "All waypoints must be set up first."),
        NO_ARENAS(ChatColor.RED + "No setup or enabled Arenas!"),
        JOINED_BATTLE("Welcome!  You are playing on $1."),
        PLAYER_JOINED_BATTLE("$1" + ChatColor.GRAY + " has joined the Battle."),
        BATTLE_STARTED(ChatColor.GREEN + "Let the Battle begin!"),
        BATTLE_ENDED("The Battle has ended."),
        BATTLE_FULL(ChatColor.RED + "The battle is full!"),
        NOT_ENOUGH_PLAYERS(ChatColor.RED + "You need $1 more players to start the battle!"),

        // Teamed Battle Messages
        TEAM_IS_READY("$1" + ChatColor.WHITE + " Team is ready!"),
        JOINED_TEAM("You are on team $1" + ChatColor.WHITE + "."),
        PLAYER_JOINED_TEAM("$1" + ChatColor.GRAY + " has joined team $2" + ChatColor.GRAY + "."),

        // Lounge Messages
        NO_PERMISSION_CLASS(ChatColor.RED + "You do not have permission to use this class."),
        NO_CLASS(ChatColor.RED + "You have not selected a class!"),
        PLAYER_IS_READY("$1" + ChatColor.GRAY + " is ready!"),

        // Kill Messages
        YOU_WERE_KILLED(ChatColor.GRAY + "You were killed by $1" + ChatColor.GRAY + "!"),
        PLAYER_WAS_KILLED("$1 " + ChatColor.GRAY + "was killed by" + ChatColor.WHITE + " $2" + ChatColor.GRAY + "."),

        // Win Messages
        PLAYER_WON(ChatColor.GOLD + "$1 won the Battle!"),
        TEAM_WON("$1 Team won the Battle!"),
        DRAW(ChatColor.DARK_PURPLE + "Draw!"),

        // Class Sign Creation Messages
        SUCCESSFUL_SIGN(ChatColor.GREEN + "Successfully created sign for $1!"),
        UNSUCCESSFUL_SIGN(ChatColor.RED + "Error creating sign for $1!  Leave last 3 lines blank."),

        // Command Messages
        USAGE("Usage: $1"),
        NO_PERMISSION_COMMAND(ChatColor.RED + "You do not have permission to use this $1."),
        PLAYER_ONLY(ChatColor.RED + "This command can only be performed by a player!"),
        SPECIFY_MESSAGE(ChatColor.RED + "Please specify a message."),
        SPECIFY_PLAYER(ChatColor.RED + "Please specify a player."),
        SPECIFY_WAYPOINT(ChatColor.RED + "Please specify a waypoint."),
        SPECIFY_COORDINATE(ChatColor.RED + "Please specify a coordinate."),
        SPECIFY_ARENA(ChatColor.RED + "Please specify an arena name."),
        SPECIFY_TEST(ChatColor.RED + "Please specify a test."),
        INVALID_COMMAND(ChatColor.RED + "Invalid Command.  Type '/bn help' for help."),
        INVALID_WAYPOINT(ChatColor.RED + "Invalid Waypoint.  Type \"/bn waypoints\" for a list."),
        WAYPOINT_SET(ChatColor.GREEN + "$1 Waypoint set to $2 in world $3."),
        WAYPOINT_SET_CURRENT_LOC(ChatColor.GREEN + "$1 Waypoint set to your current location."),
        WAYPOINT_SET_THIS_WORLD(ChatColor.GREEN + "$1 Waypoint set to $2 in this world."),
        WAYPOINT_UNSET(ChatColor.RED + "$1 Waypoint is not set."),
        KICKED("You have been kicked from the Battle."),
        PLAYER_KICKED("$1 has been kicked from the Battle."),
        REASONED_KICK("You have been kicked from the Battle for $1."),
        PLAYER_REASONED_KICKED("$1 has been kicked from the Battle for $2."),

        INVALID_ARENA(ChatColor.RED + "An Arena by that name does not exists!"),
        ARENA_EXISTS(ChatColor.RED + "An Arena by that name already exists!"),
        ARENA_CREATED(ChatColor.GREEN + "Arena $1 created."),
        ARENA_DELETED(ChatColor.GREEN + "Arena $1 deleted."),
        ARENA_ENABLED(ChatColor.GREEN + "$1 Arena enabled."),
        ARENA_DISABLED(ChatColor.GREEN + "$1 Arena disabled."),
        SPAWN_CREATED(ChatColor.GREEN + "Spawn point $1 created for Arena $2."),
        SPAWN_REMOVED(ChatColor.GREEN + "Spawn point $1 removed for Arena $2."),
        ARENA_NAMED(ChatColor.GREEN + "Set display name to $1 for Arena $2."),
        TEXTUREPACK_SET(ChatColor.GREEN + "Texture pack set for Arena $1."),

        INCORRECT_USAGE(ChatColor.RED + "Incorrect usage."),
        DEPRICATED_COMMAND(ChatColor.RED + "This command is deprecated, please use \"/bn $1\" instead."),
        CANT_FIND_PLAYER(ChatColor.RED + "Can't find player \"$1\"."),
        CANT_FIND_WORLD(ChatColor.RED + "Can't find world \"$1\"."),
        RELOADING("Reloading BattleNight..."),
        RELOAD_SUCCESSFUL(ChatColor.GREEN + "Reloaded successfully."),
        RELOAD_FAILED(ChatColor.RED + "Reload failed.  See console for error log."),
        PLAYER_NOT_IN_BATTLE(ChatColor.RED + "Player \"$1\" is not in the Battle.");

        private String message;

        Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return message;
        }
    }

    public static final String PREFIX = ChatColor.GRAY + "[BattleNight] " + ChatColor.WHITE;

    private static BattleNightAPI api;

    public static void debug(Level level, String message) {
        if (ConfigManager.get(Config.MAIN).getBoolean("Debug", false)) {
            log(level, message);
        }
    }

    private static String describeEntity(Entity entity) {
        if (entity instanceof Player) return ((Player) entity).getName();

        return entity.getType().toString().toLowerCase().replace("_", " ");
    }

    private static String describeLocation(Location loc) {
        return loc.getX() + ", " + loc.getY() + ", " + loc.getZ();
    }

    private static String describeMaterial(Material material) {
        if (material == Material.INK_SACK) return "dye";

        return material.toString().toLowerCase().replace("_", " ");
    }

    private static String describeObject(Object obj) {
        if (obj instanceof ComplexEntityPart)
            return describeObject(((ComplexEntityPart) obj).getParent());
        else if (obj instanceof Item)
            return describeMaterial(((Item) obj).getItemStack().getType());
        else if (obj instanceof ItemStack)
            return describeMaterial(((ItemStack) obj).getType());
        else if (obj instanceof Player)
            return getColouredName((Player) obj);
        else if (obj instanceof Entity)
            return describeEntity((Entity) obj);
        else if (obj instanceof Block)
            return describeMaterial(((Block) obj).getType());
        else if (obj instanceof Material)
            return describeMaterial((Material) obj);
        else if (obj instanceof Location)
            return describeLocation((Location) obj);
        else if (obj instanceof World)
            return ((World) obj).getName();
        else if (obj instanceof Team)
            return ((Team) obj).getColour() + ((Team) obj).getDisplayName();
        else if (obj instanceof List<?>)
            return ((List<?>) obj).toString().replaceAll("\\[|\\]", "").replaceAll("[,]([^,]*)$", " and$1");
        else if (obj instanceof Arena) return ((Arena) obj).getDisplayName();
        return obj.toString();
    }

    public static String format(Message message, Object... args) {
        String msg = message.getMessage();

        for (int i = 0; i < args.length; i++) {
            msg = msg.replace("$" + (i + 1), describeObject(args[i]));
        }

        return msg;
    }

    private static String getColouredName(Player player) {
        String name = player.getName();
        Battle battle = api.getBattle();

        if (battle.containsPlayer(player)) {
            ChatColor teamColour = ChatColor.WHITE;
            if (battle instanceof TeamedBattle) {
                teamColour = ((TeamedBattle) battle).getTeam(player).getColour();
            }
            Messenger.debug(Level.INFO, "Coloured name is " + teamColour + name);
            return teamColour + name;
        } else {
            Messenger.debug(Level.INFO, "Coloured name is " + ChatColor.DARK_GRAY + name);
            return ChatColor.DARK_GRAY + name;
        }
    }

    public static void init(BattleNightAPI api) {
        Messenger.api = api;
    }

    /** Kill feed **/

    public static void killFeed(Player player, Player killer, String deathMessage) {
        deathMessage = ChatColor.GRAY + deathMessage;
        deathMessage = deathMessage.replaceAll(player.getName(), getColouredName(player) + ChatColor.GRAY);
        deathMessage = deathMessage.replaceAll(player.getDisplayName(), getColouredName(player) + ChatColor.GRAY);

        if (killer != null) {
            deathMessage = deathMessage.replaceAll(killer.getName(), getColouredName(killer) + ChatColor.GRAY);
            deathMessage = deathMessage.replaceAll(killer.getDisplayName(), getColouredName(killer) + ChatColor.GRAY);
        }

        tellEveryone(deathMessage, true);
    }

    public static void log(Level level, String message) {
        BattleNight.instance.getLogger().log(level, message);
    }

    public static void playSong(Song battleEnd, boolean spectators) {
        for (String name : api.getBattle().getPlayers()) {
            Player p = Bukkit.getPlayerExact(name);
            if (p != null) {
                battleEnd.play(p);
            }
        }

        if (spectators) {
            for (String name : api.getSpectatorManager().getSpectators()) {
                Player p = Bukkit.getPlayerExact(name);
                if (p != null) {
                    battleEnd.play(p);
                }
            }
        }
    }

    /** Sounds **/

    public static void playSound(Sound sound, float pitch, boolean spectators) {
        for (String name : api.getBattle().getPlayers()) {
            Player p = Bukkit.getPlayerExact(name);
            if (p != null) {
                p.playSound(p.getLocation(), sound, 20f, pitch);
            }
        }

        if (spectators) {
            for (String name : api.getSpectatorManager().getSpectators()) {
                Player p = Bukkit.getPlayerExact(name);
                if (p != null) {
                    p.playSound(p.getLocation(), sound, 20f, pitch);
                }
            }
        }
    }

    /** Messages **/

    public static void tell(CommandSender sender, Message message) {
        tell(sender, message.getMessage());
    }

    public static void tell(CommandSender sender, Message message, Object... args) {
        tell(sender, format(message, args));
    }

    /** Pages **/

    public static void tell(CommandSender sender, Page page) {
        sender.sendMessage(page.getPage());
    }

    /** Strings **/

    public static void tell(CommandSender sender, String message) {
        sender.sendMessage(PREFIX + message);
    }

    public static void tellEveryone(boolean spectators, Message message) {
        tellEveryone(message.getMessage(), spectators);
    }

    public static void tellEveryone(boolean spectators, Message message, Object... args) {
        tellEveryone(format(message, args), spectators);
    }

    public static void tellEveryone(Page page, boolean spectators) {
        if (spectators) {
            for (String name : api.getSpectatorManager().getSpectators()) {
                Player p = Bukkit.getPlayerExact(name);
                if (p != null) {
                    tell(p, page);
                }
            }
        }

        for (String name : api.getBattle().getPlayers()) {
            Player p = Bukkit.getPlayerExact(name);
            if (p != null) {
                tell(p, page);
            }
        }
    }

    public static void tellEveryone(String message, boolean spectators) {
        if (spectators) {
            for (String name : api.getSpectatorManager().getSpectators()) {
                Player p = Bukkit.getPlayerExact(name);
                if (p != null) {
                    tell(p, message);
                }
            }
        }

        for (String name : api.getBattle().getPlayers()) {
            Player p = Bukkit.getPlayerExact(name);
            if (p != null) {
                tell(p, message);
            }
        }
    }

    public static void tellEveryoneExcept(Player player, boolean spectators, Message message) {
        tellEveryoneExcept(player, message.getMessage(), spectators);
    }

    public static void tellEveryoneExcept(Player player, boolean spectators, Message message, Object... args) {
        tellEveryoneExcept(player, format(message, args), spectators);
    }

    public static void tellEveryoneExcept(Player player, Page page, boolean spectators) {
        if (spectators) {
            for (String name : api.getSpectatorManager().getSpectators()) {
                Player p = Bukkit.getPlayerExact(name);
                if (p != null && player != p) {
                    tell(p, page);
                }
            }
        }

        for (String name : api.getBattle().getPlayers()) {
            Player p = Bukkit.getPlayerExact(name);
            if (p != null && player != p) {
                tell(p, page);
            }
        }
    }

    public static void tellEveryoneExcept(Player player, String message, boolean spectators) {
        if (spectators) {
            for (String name : api.getSpectatorManager().getSpectators()) {
                Player p = Bukkit.getPlayerExact(name);
                if (p != null && player != p) {
                    tell(p, message);
                }
            }
        }

        for (String name : api.getBattle().getPlayers()) {
            Player p = Bukkit.getPlayerExact(name);
            if (p != null && player != p) {
                tell(p, message);
            }
        }
    }

}
