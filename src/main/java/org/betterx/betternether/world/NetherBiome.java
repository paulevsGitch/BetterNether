package org.betterx.betternether.world;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

import org.betterx.bclib.api.biomes.BCLBiome;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.config.Configs;
import org.betterx.betternether.noise.OpenSimplexNoise;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.NetherStructureWorld;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;
import org.betterx.betternether.world.structures.plants.StructureWartCap;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class NetherBiome extends BCLBiome {


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

        addStructure("cap_gen", new StructureWartCap(), StructurePlacementType.WALL, 0.8F, true);


        onInit();

        final String group = configGroup();
        setPlantDensity(Configs.BIOMES.getFloat(group, "plants_and_structures_density", getPlantDensity()));
        setNoiseDensity(Configs.BIOMES.getFloat(group, "noise_density", getNoiseDensity()));
    }

    protected abstract void onInit();


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

    public void genFloorObjects(ServerLevelAccessor world,
                                BlockPos pos,
                                RandomSource random,
                                final int MAX_HEIGHT,
                                StructureGeneratorThreadContext context) {
        for (StructureInfo info : generatorsFloor)
            if (info.canGenerate(random, pos))
                info.structure.generate(world, pos, random, MAX_HEIGHT, context);
    }

    public void genWallObjects(ServerLevelAccessor world,
                               BlockPos pos,
                               RandomSource random,
                               final int MAX_HEIGHT,
                               StructureGeneratorThreadContext context) {
        for (StructureInfo info : generatorsWall)
            if (info.canGenerate(random, pos))
                info.structure.generate(world, pos, random, MAX_HEIGHT, context);
    }

    public void genCeilObjects(ServerLevelAccessor world,
                               BlockPos pos,
                               RandomSource random,
                               final int MAX_HEIGHT,
                               StructureGeneratorThreadContext context) {
        for (StructureInfo info : generatorsCeil)
            if (info.canGenerate(random, pos))
                info.structure.generate(world, pos, random, MAX_HEIGHT, context);
    }

    public void genLavaObjects(ServerLevelAccessor world,
                               BlockPos pos,
                               RandomSource random,
                               final int MAX_HEIGHT,
                               StructureGeneratorThreadContext context) {
        for (StructureInfo info : generatorsLava)
            if (info.canGenerate(random, pos))
                info.structure.generate(world, pos, random, MAX_HEIGHT, context);
    }

    protected static double getFeatureNoise(BlockPos pos, int id) {
        return SCATTER.eval(pos.getX() * 0.1, pos.getY() * 0.1 + id * 10, pos.getZ() * 0.1);
    }


    protected void addStructure(String name,
                                IStructure structure,
                                StructurePlacementType type,
                                float density,
                                boolean useNoise) {
        String group = configGroup() + ".structures." + type.getName() + "." + name;
        float dens = Configs.BIOMES.getFloat(group, "density", density);
        boolean limit = Configs.BIOMES.getBoolean(group, "limit", useNoise);
        this.addStructure(structure, type, dens, limit);
    }

    private void addStructure(IStructure structure, StructurePlacementType type, float density, boolean useNoise) {
        switch (type) {
            case CEIL -> generatorsCeil.add(new StructureInfo(structure, density, useNoise));
            case FLOOR -> generatorsFloor.add(new StructureInfo(structure, density, useNoise));
            case WALL -> generatorsWall.add(new StructureInfo(structure, density, useNoise));
            case LAVA -> generatorsLava.add(new StructureInfo(structure, density, useNoise));
            default -> {
            }
        }
    }

    protected void addStructures(String... structures) {
        //TODO: 1.19 replace this
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

    protected static String structureFormat(String name, int offset, StructurePlacementType type, float chance) {
        return String.format(Locale.ROOT,
                "name: %s; offset: %d; type: %s; chance: %f",
                name,
                offset,
                type.getName(),
                chance);
    }

    public void genFloorBuildings(ServerLevelAccessor world,
                                  BlockPos pos,
                                  RandomSource random,
                                  final int MAX_HEIGHT,
                                  StructureGeneratorThreadContext context) {
        chancedStructure(world, pos, random, MAX_HEIGHT, context, buildGeneratorsFloor);
    }

    public void genCeilBuildings(ServerLevelAccessor world,
                                 BlockPos pos,
                                 RandomSource random,
                                 final int MAX_HEIGHT,
                                 StructureGeneratorThreadContext context) {
        chancedStructure(world, pos, random, MAX_HEIGHT, context, buildGeneratorsCeil);
    }

    public void genLavaBuildings(ServerLevelAccessor world,
                                 BlockPos pos,
                                 RandomSource random,
                                 final int MAX_HEIGHT,
                                 StructureGeneratorThreadContext context) {
        chancedStructure(world, pos, random, MAX_HEIGHT, context, buildGeneratorsLava);
    }

    public void genUnderBuildings(ServerLevelAccessor world,
                                  BlockPos pos,
                                  RandomSource random,
                                  final int MAX_HEIGHT,
                                  StructureGeneratorThreadContext context) {
        chancedStructure(world, pos, random, MAX_HEIGHT, context, buildGeneratorsUnder);
    }

    private void chancedStructure(ServerLevelAccessor world,
                                  BlockPos pos,
                                  RandomSource random,
                                  final int MAX_HEIGHT,
                                  StructureGeneratorThreadContext context,
                                  List<StructureInfo> infoList) {
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

    private static StructurePlacementType typeFromString(String a) {
        if (a.contains("floor"))
            return StructurePlacementType.FLOOR;
        else if (a.contains("wall"))
            return StructurePlacementType.WALL;
        else if (a.contains("ceil"))
            return StructurePlacementType.CEIL;
        else if (a.contains("lava"))
            return StructurePlacementType.LAVA;
        else if (a.contains("under"))
            return StructurePlacementType.UNDER;
        return StructurePlacementType.FLOOR;
    }

    private void structureFromString(String structureString) {
        String[] args = structureString.split(";");

        String name = "";
        int offset = 0;
        StructurePlacementType type = StructurePlacementType.FLOOR;
        float chance = 0;

        for (String a : args) {
            if (a.contains("name:")) {
                name = a.replace("name:", "").trim();
            } else if (a.contains("offset:")) {
                offset = Integer.parseInt(a.replace("offset:", "").trim());
            } else if (a.contains("type:")) {
                type = typeFromString(a);
            } else if (a.contains("chance:")) {
                chance = Float.parseFloat(a.replace("chance:", "").trim());
            }
        }

        if (!name.isEmpty()) {
            NetherStructureWorld structure = new NetherStructureWorld(name, offset, type);
            if (structure.loaded()) {
                List<StructureInfo> infoList = null;
                switch (structure.type) {
                    case CEIL -> infoList = buildGeneratorsCeil;
                    case FLOOR -> infoList = buildGeneratorsFloor;
                    case LAVA -> infoList = buildGeneratorsLava;
                    case UNDER -> infoList = buildGeneratorsUnder;
                    default -> {
                    }
                }

                chance += getLastChance(infoList);
                StructureInfo info = new StructureInfo(structure, chance, false);
                infoList.add(info);
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
