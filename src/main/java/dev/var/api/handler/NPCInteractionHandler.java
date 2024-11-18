package dev.var.api.handler;

import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

/**
 * @author mynameisvar <github@mynameisvar>
 */

public class NPCInteractionHandler {

    private BiConsumer<Player, NPC> clickHandler = (player, npc) -> {};

    public void setClickHandler(BiConsumer<Player, NPC> clickHandler) {
        this.clickHandler = clickHandler;
    }

    public void handleClick(Player player, NPC npc) {
        clickHandler.accept(player, npc);
    }
}
