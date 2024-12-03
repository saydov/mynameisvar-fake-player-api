package dev.var.entity.api.skin;

import dev.var.entity.api.FakePlayerSkinPair;
import org.jetbrains.annotations.NotNull;

public interface FakePlayerSkinData {

    @NotNull FakePlayerSkinPair<String, String> skinData();

}
