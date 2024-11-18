package dev.var.api.registry;

import dev.var.api.model.NPC;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;

import java.util.*;

/**
 * @author mynameisvar <github@mynameisvar>
 */

@RequiredArgsConstructor
public class NPCRegistry {

    private final Map<UUID, NPC> npcMap = new HashMap<>();

    public NPC createNPC(String name, Location loc) {
        NPC npc = new NPC(name, loc);
        npcMap.put(npc.getUuid(), npc);
        return npc;
    }

    public Optional<NPC> getNPC(UUID uuid) {
        return Optional.ofNullable(npcMap.get(uuid));
    }

    public void removeNPC(UUID uuid) {
        NPC npc = npcMap.remove(uuid);
        if (npc != null) {
            npc.despawn();
        }
    }

    public void clearAllNPCs() {
        npcMap.values().forEach(
                NPC::despawn
        );
        npcMap.clear();
    }

    public Collection<NPC> getAllNPCs() {
        return npcMap.values();
    }
}
