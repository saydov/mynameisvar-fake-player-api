package dev.var.api.model;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import dev.var.api.animation.Animation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author mynameisvar <github@mynameisvar>
 */

@RequiredArgsConstructor
public class NPC {

    @Getter
    private final UUID uuid = UUID.randomUUID();
    private final String name;
    private final Location loc;
    private NPCSkin skin;

    public void spawn() {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.NAMED_ENTITY_SPAWN);
        packet.getUUIDs().write(0, uuid);
        packet.getDoubles()
                .write(0, loc.getX())
                .write(1, loc.getY())
                .write(2, loc.getZ());
        packet.getStrings().write(0, name);

        Bukkit.getOnlinePlayers().forEach(player -> {
            sendPacket(player, packet);
        });
    }

    public void despawn() {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        packet.getIntegerArrays().write(0, new int[]{uuid.hashCode()});

        Bukkit.getOnlinePlayers().forEach(player -> {
            sendPacket(player, packet);
        });
    }

    public void setSkin(NPCSkin skin) {
        this.skin = skin;

        PacketContainer removePlayerInfo = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        removePlayerInfo.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        removePlayerInfo.getPlayerInfoDataLists().write(0, Collections.singletonList(createPlayerInfoData()));
        Bukkit.getOnlinePlayers().forEach(player ->
                sendPacket(player, removePlayerInfo)
        );

        PacketContainer addPlayerInfo = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        addPlayerInfo.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        addPlayerInfo.getPlayerInfoDataLists().write(0, Collections.singletonList(createPlayerInfoData()));
        Bukkit.getOnlinePlayers().forEach(player ->
                sendPacket(player, addPlayerInfo)
        );

        despawn();

        spawn();
    }

    public void playAnimation(Animation animation) {
        if (animation == Animation.SNEAK || animation == Animation.STOP_SNEAK) {
            updateSneakState(animation == Animation.SNEAK);
            return;
        }

        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ANIMATION);
        packet.getIntegers().write(0, uuid.hashCode());
        packet.getIntegers().write(1, animation.getAnimationId());

        Bukkit.getOnlinePlayers().forEach(player -> {
            sendPacket(player, packet);
        });
    }

    private void updateSneakState(boolean isSneaking) {
        PacketContainer metadataPacket = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        metadataPacket.getIntegers().write(0, uuid.hashCode());

        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class);
        watcher.setObject(0, byteSerializer, (byte) (isSneaking ? 0x02 : 0x00));

        metadataPacket.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
        Bukkit.getOnlinePlayers().forEach(player -> {
            sendPacket(player, metadataPacket);
        });
    }

    private PlayerInfoData createPlayerInfoData() {
        WrappedGameProfile profile = new WrappedGameProfile(uuid, name);
        profile.getProperties().clear();
        profile.getProperties().put("textures", new WrappedSignedProperty(
                "textures", skin.getValue(), skin.getSignature())
        );

        return new PlayerInfoData(
                profile,
                0,
                EnumWrappers.NativeGameMode.SURVIVAL,
                WrappedChatComponent.fromText(name)
        );
    }

    private void sendPacket(Player player, PacketContainer packet) {
        try {
             ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
