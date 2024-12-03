package dev.var.entity.api;

import dev.var.entity.api.skin.FakePlayerSkinData;
import lombok.*;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SimpleFakePlayerApi implements FakePlayerApi {

    private final JavaPlugin javaPlugin;
    private final Set<FakePlayerEntity> fakePlayerEntities;

    @Override
    public @NotNull FakePlayerEntity createFakePlayer(@NotNull Location location,
                                                      @NotNull FakePlayerSkinData playerSkinData) {
        final FakePlayerEntity fakePlayerEntity = SimpleFakePlayerEntity.create(location, playerSkinData);
        fakePlayerEntities.add(fakePlayerEntity);

        return fakePlayerEntity;
    }

    @Override
    public @NotNull Optional<FakePlayerEntity> findFakePlayer(@NotNull Location location) {
        return fakePlayerEntities.stream()
                .filter(fakePlayer -> fakePlayer.getLocation().equals(location))
                .findFirst();
    }

    @Override
    public void removeFakePlayer(@NotNull FakePlayerEntity fakePlayer) {
        fakePlayerEntities.remove(fakePlayer);
        fakePlayer.setPublicStatus(false);
    }

    @Override
    public void removeFakePlayers() {
        fakePlayerEntities.forEach(this::removeFakePlayer);
        fakePlayerEntities.clear();
    }

    @Override
    public @NotNull Set<FakePlayerEntity> getFakePlayers() {
        return fakePlayerEntities;
    }

    @Override
    public void init() {
        javaPlugin.getServer().getPluginManager()
                        .registerEvents(FakePlayerListener.create(javaPlugin, this), javaPlugin);

        fakePlayerEntities.forEach(fakePlayerEntity ->
                fakePlayerEntity.getViewers().forEach(fakePlayerEntity::spawn));
    }

    @Override
    public void destroy() {
        fakePlayerEntities.forEach(fakePlayerEntity ->
                fakePlayerEntity.getViewers().forEach(fakePlayerEntity::remove));
    }

    @Builder(builderMethodName = "fabric", toBuilder = true)
    public static class Fabric {

        private final JavaPlugin javaPlugin;
        private final Set<FakePlayerEntity> fakePlayerEntities;

        public @NotNull SimpleFakePlayerApi createApi() {
            return new SimpleFakePlayerApi(javaPlugin, fakePlayerEntities);
        }
    }

}
