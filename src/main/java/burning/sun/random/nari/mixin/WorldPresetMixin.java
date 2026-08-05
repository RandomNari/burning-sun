package burning.sun.random.nari.mixin;

import burning.sun.random.nari.BurningSun;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.StructureSet;
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

import java.util.Map;

@Mixin(WorldPresets.class)
public abstract class WorldPresetMixin {

    public static final ResourceKey<WorldPreset> BURNEDWORLD = ResourceKey.create(Registries.WORLD_PRESET, Identifier.fromNamespaceAndPath("burning.sun.random.nari", "burned_world"));

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void createBurnedWorld(final BootstrapContext<WorldPreset> context, CallbackInfo ci) {

        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);

        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);

        HolderGetter<MultiNoiseBiomeSourceParameterList> biomePresets = context.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);

        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        HolderGetter<StructureSet> structureSets = context.lookup(Registries.STRUCTURE_SET);

        Holder<DimensionType> overworldType = dimensionTypes.getOrThrow(BuiltinDimensionTypes.OVERWORLD);

        Holder<NoiseGeneratorSettings> overworldNoise = noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD);

        Holder.Reference<MultiNoiseBiomeSourceParameterList> overworldBiomePreset = biomePresets.getOrThrow(MultiNoiseBiomeSourceParameterLists.OVERWORLD);

    }
}


