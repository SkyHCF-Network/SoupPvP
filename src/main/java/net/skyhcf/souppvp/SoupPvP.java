package net.skyhcf.souppvp;

import lombok.Getter;
import net.frozenorb.qlib.command.FrozenCommandHandler;
import net.frozenorb.qlib.scoreboard.FrozenScoreboardHandler;
import net.skyhcf.souppvp.commands.parameter.KitParameterType;
import net.skyhcf.souppvp.database.MongoManager;
import net.skyhcf.souppvp.event.listeners.GeneralListener;
import net.skyhcf.souppvp.killstreak.KillstreakManager;
import net.skyhcf.souppvp.kit.Kit;
import net.skyhcf.souppvp.kit.KitManager;
import net.skyhcf.souppvp.profile.KitProfileManager;
import net.skyhcf.souppvp.scoreboard.SoupScoreConfig;
import net.skyhcf.souppvp.utils.Cooldowns;
import org.bukkit.Bukkit;
import net.skyhcf.souppvp.tab.TabHandler;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import net.frozenorb.qlib.tab.FrozenTabHandler;

public class SoupPvP extends JavaPlugin {

    @Getter private static SoupPvP instance;

    @Getter private MongoManager mongoManager;
    @Getter private KitProfileManager kitProfileManager;
    @Getter private KitManager kitManager;
    @Getter private KillstreakManager killstreakManager;

    @Override
    public void onEnable() {
        instance = this;

        this.mongoManager = new MongoManager();

        mongoManager.connect();

        this.kitProfileManager = new KitProfileManager();
        this.kitManager = new KitManager();
        this.killstreakManager = new KillstreakManager();

        FrozenScoreboardHandler.setConfiguration(SoupScoreConfig.create());
        FrozenCommandHandler.registerAll(this);
        FrozenCommandHandler.registerParameterType(Kit.class, new KitParameterType());

        getServer().getScheduler().runTaskTimer(this, kitProfileManager::refresh, 1L, 1L);
        getServer().getPluginManager().registerEvents(new GeneralListener(), this);

        createCooldowns();

    }

    @Override
    public void onDisable() {
        for(Block block : GeneralListener.blocksToRemove){
            block.setType(Material.AIR);
            block.getWorld().save();
        }
    }

    private void createCooldowns(){
        Cooldowns.createCooldown("combat");
        Cooldowns.createCooldown("golden_apple");
        Cooldowns.createCooldown("enderpearl");
        Cooldowns.createCooldown("launchpad");

        FrozenTabHandler.setLayoutProvider(new TabHandler());
    }
}
