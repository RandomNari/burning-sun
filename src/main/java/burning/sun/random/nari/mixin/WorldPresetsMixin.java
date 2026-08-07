package burning.sun.random.nari.mixin;

import burning.sun.random.nari.BurningSun;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.DebugLevelSource;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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

import static burning.sun.random.nari.BurningSun.MOD_ID;

@Mixin(WorldPresets.class)
public abstract class WorldPresetsMixin {
    private static final ResourceKey<@NotNull WorldPreset> BURNEDWORLD = ResourceKey.create(Registries.WORLD_PRESET,
            Identifier.fromNamespaceAndPath(MOD_ID, "burnedworld"));
    static{
        System.out.println("Upper");
    }
    @Inject(method = "bootstrap", at = @At("TAIL"))

    public static void createBurnedWorld(final BootstrapContext<@NotNull WorldPreset> context, CallbackInfo ci) {
        System.out.println("inject");

        HolderGetter<@NotNull DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<@NotNull NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<@NotNull MultiNoiseBiomeSourceParameterList> biomePresets =
                context.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);
        HolderGetter<@NotNull Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<@NotNull PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<@NotNull StructureSet> structureSets = context.lookup(Registries.STRUCTURE_SET);
        Holder<@NotNull DimensionType> overworldType = dimensionTypes.getOrThrow(BuiltinDimensionTypes.OVERWORLD);
        Holder<@NotNull NoiseGeneratorSettings> overworldNoise = noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD);
        Holder.Reference<@NotNull MultiNoiseBiomeSourceParameterList> overworldBiomePreset =
                biomePresets.getOrThrow(MultiNoiseBiomeSourceParameterLists.OVERWORLD);
        Holder<@NotNull DimensionType> netherDimensionType = dimensionTypes.getOrThrow(BuiltinDimensionTypes.NETHER);
        Holder<@NotNull DimensionType> endDimensionType = dimensionTypes.getOrThrow(BuiltinDimensionTypes.END);

        Holder<NoiseGeneratorSettings> netherNoise =
                noiseSettings.getOrThrow(NoiseGeneratorSettings.NETHER);
        Holder<NoiseGeneratorSettings> endNoise =
                noiseSettings.getOrThrow(NoiseGeneratorSettings.END);

        Holder<DimensionType> netherType =
                dimensionTypes.getOrThrow(BuiltinDimensionTypes.NETHER);
        Holder<DimensionType> endType =
                dimensionTypes.getOrThrow(BuiltinDimensionTypes.END);

        Holder.Reference<MultiNoiseBiomeSourceParameterList> netherBiomePreset =
                biomePresets.getOrThrow(MultiNoiseBiomeSourceParameterLists.NETHER);

        LevelStem nether = new LevelStem(netherType, new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.createFromPreset(netherBiomePreset), netherNoise));

        LevelStem end = new LevelStem(endType, new NoiseBasedChunkGenerator(TheEndBiomeSource.create(biomes), endNoise));
        LevelStem overworld = new LevelStem(overworldType,
                new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.createFromPreset(overworldBiomePreset), overworldNoise));
        Holder.Reference<@NotNull DimensionType> netherStem = dimensionTypes.getOrThrow(BuiltinDimensionTypes.NETHER);
        Holder.Reference<@NotNull DimensionType> endStem = dimensionTypes.getOrThrow(BuiltinDimensionTypes.END);
        context.register(BURNEDWORLD, new WorldPreset(Map.of(LevelStem.OVERWORLD, overworld, LevelStem.NETHER, nether, LevelStem.END, end)));

    }

}


