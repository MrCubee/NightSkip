package fr.mrcubee.nightskip;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BedTask extends BukkitRunnable {

    private static long SKIP_TIME = 5;

    private final NightSkip nightSkip;
    private long startSkipTime;

    public BedTask(NightSkip nightSkip) {
        this.nightSkip = nightSkip;
        this.startSkipTime = -1;
    }

    @Override
    public void run() {
        final int numberOfPlayer = Bukkit.getOnlinePlayers().size();
        int minPlayer = (20 * numberOfPlayer) / 100;
        long time;

        if (minPlayer < 1)
            minPlayer = 1;
        this.nightSkip.playersInBed.removeIf(player -> !player.isSleeping());
        if (numberOfPlayer < 2 || this.nightSkip.playersInBed.size() < minPlayer) {
            this.startSkipTime = -1;
            return;
        }
        if (this.startSkipTime == -1)
            this.startSkipTime = System.currentTimeMillis();
        time = (System.currentTimeMillis() - this.startSkipTime) / 1000;
        if (time < 0)
            time = 0;
        for (Player player : this.nightSkip.playersInBed)
            player.sendMessage(ChatColor.YELLOW.toString() + (SKIP_TIME - time) + "...");
        if (time >= SKIP_TIME) {
            for (World world : Bukkit.getWorlds())
                world.setTime(0);
            this.startSkipTime = -1;
        }
    }
}
