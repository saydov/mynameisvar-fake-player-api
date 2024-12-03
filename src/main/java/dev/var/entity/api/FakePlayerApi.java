package dev.var.entity.api;

import dev.var.entity.InitableDestroyable;
import dev.var.entity.api.skin.FakePlayerSkinData;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public interface FakePlayerApi extends InitableDestroyable {

    @NotNull FakePlayerEntity createFakePlayer(final @NotNull Location location,
                                               final @NotNull FakePlayerSkinData playerSkinData);

    @NotNull Optional<FakePlayerEntity> findFakePlayer(final @NotNull Location location);

    default @NotNull FakePlayerEntity getFakePlayer(final @NotNull Location location) throws EntityNotFoundException {
        return findFakePlayer(location)
                .orElseThrow(() -> new EntityNotFoundException("FakePlayer not found at location: " + location));
    }

    void removeFakePlayer(final @NotNull FakePlayerEntity fakePlayer);
    void removeFakePlayers();

    @NotNull Set<FakePlayerEntity> getFakePlayers();

}
