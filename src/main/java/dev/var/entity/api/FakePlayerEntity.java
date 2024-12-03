package dev.var.entity.api;

import com.comphenix.protocol.wrappers.EnumWrappers;
import dev.var.entity.api.skin.FakePlayerSkinData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface FakePlayerEntity extends FakeEntity {

    @NotNull Map<EnumWrappers.ItemSlot, ItemStack> getEquipment();

    void setEquipment(@NotNull EnumWrappers.ItemSlot slot, @NotNull ItemStack item);

    void playAnimation(final @NotNull FakePlayerAnimation playerAnimation);

    void setSneaking(boolean sneaking);

    boolean isSneaking();

    void setSprinting(boolean sprinting);

    boolean isSprinting();

    void setSkin(final @NotNull FakePlayerSkinData playerSkinData);

    @NotNull FakePlayerSkinData getSkin();

}
