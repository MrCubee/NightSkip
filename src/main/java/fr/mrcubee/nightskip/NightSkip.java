package fr.mrcubee.nightskip;

import org.bukkit.Bukkit;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashSet;
import java.util.Set;

public class NightSkip extends JavaPlugin implements Listener {

    protected Set<Player> playersInBed;
    private BedTask bedTask;

    @Override
    public void onEnable() {
        this.playersInBed = new LinkedHashSet<Player>();
        this.bedTask = new BedTask(this);
        this.bedTask.runTaskTimer(this, 0, 20L);
        getServer().getPluginManager().registerEvents(this, this);
        for (Player player : Bukkit.getOnlinePlayers())
            if (player.isSleeping())
                this.playersInBed.add(player);
    }

    @EventHandler
    public void playerBedEnterEvent(PlayerBedEnterEvent event) {
        this.playersInBed.add(event.getPlayer());
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
        this.playersInBed.remove(event.getPlayer());
    }

}
