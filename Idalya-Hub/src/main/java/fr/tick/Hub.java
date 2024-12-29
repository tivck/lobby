package fr.tick;

import fr.tick.listeners.PlayerListeners;
import fr.tick.scoreboard.manager.ScoreboardManager;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Hub extends JavaPlugin {

    public static Hub instance;
    private InventoryManager inventoryManager;
    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Iterator<World> iterator = Bukkit.getWorlds().iterator();
        while (iterator.hasNext()) {
            World next = iterator.next();
            next.setDifficulty(Difficulty.PEACEFUL);
        }

        this.getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
        inventoryManager = new InventoryManager(this);
        inventoryManager.init();
        this.scheduledExecutorService = Executors.newScheduledThreadPool(16);
        this.executorMonoThread = Executors.newScheduledThreadPool(1);
        this.scoreboardManager = new ScoreboardManager();
    }

    @Override
    public void onDisable() {

    }

    public static Hub getInstance() {
        return instance;
    }


    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }
}
