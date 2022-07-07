package org.betterx.betternether.world;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiome;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.bclib.util.WeightedList;
import org.betterx.betternether.noise.OpenSimplexNoise;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NetherBiome extends BCLBiome {
    public static final Codec<NetherBiome> CODEC = RecordCodecBuilder.create(instance ->
            codecWithSettings(instance).apply(instance, NetherBiome::new)
    );
    public static final KeyDispatchDataCodec<NetherBiome> KEY_CODEC = KeyDispatchDataCodec.of(CODEC);

    @Override
    public KeyDispatchDataCodec<? extends BCLBiome> codec() {
        return KEY_CODEC;
    }

    protected NetherBiome(
            float terrainHeight,
            float fogDensity,
            float genChance,
            int edgeSize,
            boolean vertical,
            Optional<ResourceLocation> edge,
            ResourceLocation biomeID,
            Optional<List<Climate.ParameterPoint>> parameterPoints,
            Optional<ResourceLocation> biomeParent,
            Optional<WeightedList<ResourceLocation>> subbiomes,
            Optional<String> intendedType
    ) {
        super(
                terrainHeight,
                fogDensity,
                genChance,
                edgeSize,
                vertical,
                edge,
                biomeID,
                parameterPoints,
                biomeParent,
                subbiomes,
                intendedType
        );
    }

    private static final OpenSimplexNoise SCATTER = new OpenSimplexNoise(1337);
    private static int structureID = 0;

    private final ArrayList<StructureInfo> generatorsFloor = new ArrayList<>();
    private final ArrayList<StructureInfo> generatorsWall = new ArrayList<>();
    private final ArrayList<StructureInfo> generatorsCeil = new ArrayList<>();
    private final ArrayList<StructureInfo> generatorsLava = new ArrayList<>();

    private final ArrayList<StructureInfo> buildGeneratorsFloor = new ArrayList<>();
    private final ArrayList<StructureInfo> buildGeneratorsCeil = new ArrayList<>();
    private final ArrayList<StructureInfo> buildGeneratorsLava = new ArrayList<>();
    private final ArrayList<StructureInfo> buildGeneratorsUnder = new ArrayList<>();


    protected float plantDensity = 1.0001F;
    protected float noiseDensity = 0.3F;

    protected NetherBiome(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }


    public void setPlantDensity(float density) {
        this.plantDensity = density * 1.0001F;
    }

    public float getPlantDensity() {
        return plantDensity;
    }

    public void setNoiseDensity(float density) {
        this.noiseDensity = 1 - density * 2;
    }

    public float getNoiseDensity() {
        return (1F - this.noiseDensity) / 2F;
    }

    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
    }

    public void genFloorObjects(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        for (StructureInfo info : generatorsFloor)
            if (info.canGenerate(random, pos))
                info.structure.generate(world, pos, random, MAX_HEIGHT, context);
    }

    public void genWallObjects(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        for (StructureInfo info : generatorsWall)
            if (info.canGenerate(random, pos))
                info.structure.generate(world, pos, random, MAX_HEIGHT, context);
    }

    public void genCeilObjects(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        for (StructureInfo info : generatorsCeil)
            if (info.canGenerate(random, pos))
                info.structure.generate(world, pos, random, MAX_HEIGHT, context);
    }

    public void genLavaObjects(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        for (StructureInfo info : generatorsLava)
            if (info.canGenerate(random, pos))
                info.structure.generate(world, pos, random, MAX_HEIGHT, context);
    }

    protected static double getFeatureNoise(BlockPos pos, int id) {
        return SCATTER.eval(pos.getX() * 0.1, pos.getY() * 0.1 + id * 10, pos.getZ() * 0.1);
    }


    protected class StructureInfo {
        final IStructure structure;
        final float density;
        final boolean useNoise;
        final int id;

        StructureInfo(IStructure structure, float density, boolean useNoise) {
            this.structure = structure;
            this.density = density;
            this.useNoise = useNoise;
            id = structureID++;
        }

        boolean canGenerate(RandomSource random, BlockPos pos) {
            return (!useNoise || getFeatureNoise(pos, id) > noiseDensity) && random.nextFloat() < density;
        }
    }

    public void genFloorBuildings(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        chancedStructure(world, pos, random, MAX_HEIGHT, context, buildGeneratorsFloor);
    }

    public void genCeilBuildings(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        chancedStructure(world, pos, random, MAX_HEIGHT, context, buildGeneratorsCeil);
    }

    public void genLavaBuildings(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        chancedStructure(world, pos, random, MAX_HEIGHT, context, buildGeneratorsLava);
    }

    public void genUnderBuildings(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        chancedStructure(world, pos, random, MAX_HEIGHT, context, buildGeneratorsUnder);
    }

    private void chancedStructure(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context,
            List<StructureInfo> infoList
    ) {
        float chance = getLastChance(infoList);
        if (chance > 0) {
            float rnd = random.nextFloat() * chance;
            for (StructureInfo info : infoList)
                if (rnd <= info.density) {
                    info.structure.generate(world, pos, random, MAX_HEIGHT, context);
                    return;
                }
        }
    }

    private float getLastChance(List<StructureInfo> info) {
        int size = info.size();
        return size > 0 ? info.get(size - 1).density : 0;
    }

    public boolean hasCeilStructures() {
        return !buildGeneratorsCeil.isEmpty();
    }

}
