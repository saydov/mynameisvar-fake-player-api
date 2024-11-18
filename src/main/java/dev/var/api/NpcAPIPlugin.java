package dev.var.api;

import dev.var.api.registry.NPCRegistry;
import org.bukkit.plugin.java.JavaPlugin;


public final class NpcAPIPlugin extends JavaPlugin {

    NPCRegistry npcRegistry;

    @Override
    public void onEnable() {
        this.npcRegistry = new NPCRegistry();
    }

    @Override
    public void onDisable() {
        this.npcRegistry.clearAllNPCs();
    }
}
