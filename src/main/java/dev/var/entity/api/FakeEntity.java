package dev.var.entity.api;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public interface FakeEntity {

    @NotNull WrappedDataWatcher getDataWatcher();

    int getEntityId();

    @NotNull UUID getUniqueId();

    @NotNull Location getLocation();

    @Nullable String getDisplayName();

    @NotNull FakeEntity setDisplayName(@Nullable String displayName);

    @NotNull Set<Player> getViewers();

    void addViewer(@NotNull Player player);

    void removeViewer(@NotNull Player player);

    boolean isPublicStatus();

    @NotNull FakeEntity setPublicStatus(boolean status);

    void spawn(final @NotNull Player player);

    void remove(final @NotNull Player player);

}
