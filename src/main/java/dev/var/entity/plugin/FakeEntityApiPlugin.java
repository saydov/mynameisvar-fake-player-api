package dev.var.entity.plugin;

import dev.var.entity.api.FakePlayerApi;
import dev.var.entity.api.FakePlayerApiCreator;
import dev.var.entity.api.skin.FakePlayerSkinData;
import dev.var.entity.api.skin.SimpleFakePlayerSkinData;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public final class FakeEntityApiPlugin extends JavaPlugin {

    private Location testFakePlayerLocation;
    private FakePlayerApi fakePlayerApi;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        testFakePlayerLocation = LocationUtils.fromString(getConfig()
                .getString("test-fake-player-location"));
    }

    @Override
    public void onEnable() {
        fakePlayerApi = FakePlayerApiCreator.fastSimpleApi(this);
        fakePlayerApi.init();

        // todo loading fake players from config
        final FakePlayerSkinData fakePlayerSkinData = SimpleFakePlayerSkinData.create(
                getConfig().getString("test-fake-player-skin-texture"),
                getConfig().getString("test-fake-player-skin-signature")
        );

        fakePlayerApi.createFakePlayer(testFakePlayerLocation, fakePlayerSkinData)
                .setPublicStatus(true);
    }

    @Override
    public void onDisable() {
        fakePlayerApi.destroy();
    }
}
