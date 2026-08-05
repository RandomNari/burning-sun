package burning.sun.random.nari.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;

@Mixin(WorldPresets.class)
public abstract class WorldPresetMixin {

    public static final ResourceKey<WorldPreset> BURNEDWORLD = ResourceKey.create(Registries.WORLD_PRESET, Identifier.fromNamespaceAndPath("burning.sun.random.nari", "burned_world"));

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void createBurnedWorld(final BootstrapContext<WorldPreset> context, CallbackInfo ci) {

    }
}
