package dev.var.entity.api;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import dev.var.entity.api.skin.FakePlayerSkinData;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

@Getter
public class SimpleFakePlayerEntity extends FakeEntityBase implements FakePlayerEntity {

    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull FakePlayerEntity create(final @NotNull Location location,
                                                   final @NotNull FakePlayerSkinData skinData) {
        return new SimpleFakePlayerEntity(location, skinData);
    }

    private final Map<EnumWrappers.ItemSlot, ItemStack> equipment;
    private FakePlayerSkinData skin;

    private boolean sneaking, sprinting;

    protected SimpleFakePlayerEntity(final @NotNull Location location,
                                     final @NotNull FakePlayerSkinData skinData) {
        super(location);

        this.skin = skinData;
        this.equipment = new Object2ObjectOpenHashMap<>();
    }

    @Override
    public void spawn(@NotNull Player player) {
        final PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.NAMED_ENTITY_SPAWN);

        packetContainer.getIntegers().write(0, getEntityId());
        packetContainer.getUUIDs().write(0, getUniqueId());

        packetContainer.getDoubles().write(0, getLocation().getX());
        packetContainer.getDoubles().write(1, getLocation().getY());
        packetContainer.getDoubles().write(2, getLocation().getZ());

        if (getDisplayName() != null) {
            packetContainer.getStrings().write(0, getDisplayName());
        }

        broadcastPacket(packetContainer);

        //todo sending packet with metadata
    }

    @Override
    public void remove(@NotNull Player player) {
        final PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        packetContainer.getIntegerArrays().write(0, new int[]{getEntityId()});

        broadcastPacket(packetContainer);
    }

    @Override
    public void setEquipment(EnumWrappers.@NotNull ItemSlot slot, @NotNull ItemStack item) {
        equipment.put(slot, item);

        //todo sending packet with equipment
    }

    @Override
    public void playAnimation(@NotNull FakePlayerAnimation playerAnimation) {
        final PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.ANIMATION);

        packetContainer.getIntegers().write(0, getEntityId());
        packetContainer.getIntegers().write(1, playerAnimation.animationId());

        broadcastPacket(packetContainer);
    }

    @Override
    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;

        //todo sending packet with metadata
    }

    @Override
    public void setSprinting(boolean sprinting) {
        this.sprinting = sprinting;

        //todo sending packet with metadata
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setSkin(@NotNull FakePlayerSkinData playerSkinData) {
        this.skin = playerSkinData;

        final PacketContainer removePlayerInfo = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);

        removePlayerInfo.getPlayerInfoAction().write(0,
                EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        removePlayerInfo.getPlayerInfoDataLists().write(0,
                Collections.singletonList(createPlayerData(playerSkinData)));

        broadcastPacket(removePlayerInfo);

        final PacketContainer addPlayerInfo = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);

        addPlayerInfo.getPlayerInfoAction().write(0,
                EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        addPlayerInfo.getPlayerInfoDataLists().write(0,
                Collections.singletonList(createPlayerData(playerSkinData)));

        broadcastPacket(addPlayerInfo);

        //todo remove and spawn
    }

    private @NotNull PlayerInfoData createPlayerData(final @NotNull FakePlayerSkinData playerSkinData) {
        final WrappedGameProfile gameProfile = new WrappedGameProfile(getUniqueId(), getDisplayName());
        final FakePlayerSkinPair<String, String> skinData = playerSkinData.skinData();

        final WrappedSignedProperty property = new WrappedSignedProperty("textures",
                skinData.texture(), skinData.signature());

        gameProfile.getProperties().put("textures", property);
        return new PlayerInfoData(gameProfile, 0,
                EnumWrappers.NativeGameMode.SURVIVAL, null);
    }

    // todo maybe move this to a utility class or something
    private void broadcastPacket(final @NotNull PacketContainer packetContainer) {
        ProtocolLibrary.getProtocolManager().broadcastServerPacket(packetContainer);
    }
}
