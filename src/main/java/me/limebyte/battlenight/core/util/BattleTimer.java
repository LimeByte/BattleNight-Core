package me.limebyte.battlenight.core.util;

import me.limebyte.battlenight.core.Battle;
import me.limebyte.battlenight.core.managers.CoreMusicManager;
import me.limebyte.battlenight.core.util.sound.Note;

import org.bukkit.ChatColor;
import org.bukkit.Sound;

public class BattleTimer extends Timer {

    private Battle battle;
    private static final int MINUTE = 60;
    private static final int COUNTDOWN_START = 10;

    public BattleTimer(Battle battle, long time) {
        super(time);
        this.battle = battle;
    }

    @Override
    public void onTimeChange(long time) {
        if (time == CoreMusicManager.battleEnd.length()) {
            Messenger.playSong(CoreMusicManager.battleEnd, true);
        }

        if (time % 10 == 0) {
            long timeSec = time / 10;
            if (timeSec <= COUNTDOWN_START) {
                Messenger.playSound(Sound.NOTE_PIANO, Note.convertPitch(18), true);
                Messenger.tellEveryone("" + ChatColor.RED + timeSec + "!", true);
            } else if (timeSec == MINUTE) {
                Messenger.tellEveryone(ChatColor.RED + "1 minute remaining!", true);
            } else if (timeSec % MINUTE == 0) {
                Messenger.tellEveryone(timeSec / MINUTE + " minutes remaining.", true);
            }
        }
    }

    @Override
    public void onTimerEnd() {
        battle.stop();
    }

}