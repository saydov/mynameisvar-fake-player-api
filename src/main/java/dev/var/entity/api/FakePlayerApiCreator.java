package dev.var.entity.api;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

@UtilityClass
public class FakePlayerApiCreator {

    public @NotNull FakePlayerApi fastSimpleApi(final @NotNull JavaPlugin javaPlugin) {
        return SimpleFakePlayerApi.Fabric.fabric()
                .javaPlugin(javaPlugin)
                .fakePlayerEntities(new ObjectOpenHashSet<>())
                .build()
                .createApi();
    }

    public @NotNull FakePlayerApi javaSimpleApi(final @NotNull JavaPlugin javaPlugin) {
        return SimpleFakePlayerApi.Fabric.fabric()
                .javaPlugin(javaPlugin)
                .fakePlayerEntities(new HashSet<>())
                .build()
                .createApi();
    }

}
