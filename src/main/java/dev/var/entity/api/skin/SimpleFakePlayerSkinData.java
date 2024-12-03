package dev.var.entity.api.skin;

import dev.var.entity.api.FakePlayerSkinPair;
import lombok.*;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ToString
@EqualsAndHashCode
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleFakePlayerSkinData implements FakePlayerSkinData {

    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull FakePlayerSkinData create(final @NotNull String texture,
                                                     final @NotNull String signature) {
        return new SimpleFakePlayerSkinData(FakePlayerSkinPair.create(texture, signature));
    }

    private final @NotNull FakePlayerSkinPair<String, String> skinData;

}
