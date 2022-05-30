package org.betterx.betternether.world.biomes;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.noise.OpenSimplexNoise;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.SoundsRegistry;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.betternether.world.structures.plants.*;

public class OldSwampland extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(137, 19, 78)
                   .loop(SoundsRegistry.AMBIENT_SWAMPLAND)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .structure(BiomeTags.HAS_BASTION_REMNANT)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS);
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return OldSwampland::new;
        }

        @Override
        public <M extends Mob> int spawnWeight(NetherEntities.KnownSpawnTypes type) {
            int res = super.spawnWeight(type);
            switch (type) {
                case ENDERMAN, GHAST, ZOMBIFIED_PIGLIN, PIGLIN, HOGLIN, PIGLIN_BRUTE -> res = 0;
                case MAGMA_CUBE, STRIDER -> res = 40;
            }
            return res;
        }
    }

    protected static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(523);

    public OldSwampland(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
        addStructure("old_willow", new StructureOldWillow(), StructurePlacementType.FLOOR, 0.02F, false);
        addStructure("willow", new StructureWillow(), StructurePlacementType.FLOOR, 0.02F, false);
        addStructure("willow_bush", new StructureWillowBush(), StructurePlacementType.FLOOR, 0.1F, true);
        addStructure("feather_fern", new StructureFeatherFern(), StructurePlacementType.FLOOR, 0.05F, true);
        addStructure("nether_reed", new StructureReeds(), StructurePlacementType.FLOOR, 0.8F, false);
        addStructure("soul_vein", new StructureSoulVein(), StructurePlacementType.FLOOR, 0.5F, false);
        addStructure("smoker", new StructureSmoker(), StructurePlacementType.FLOOR, 0.05F, false);
        addStructure("jellyfish_mushroom", new StructureJellyfishMushroom(), StructurePlacementType.FLOOR, 0.03F, true);
        addStructure("black_bush", new StructureBlackBush(), StructurePlacementType.FLOOR, 0.01F, false);
        addStructure("swamp_grass", new StructureSwampGrass(), StructurePlacementType.FLOOR, 0.4F, false);
        addStructure("black_vine", new StructureBlackVine(), StructurePlacementType.CEIL, 0.4F, true);
        addStructure("wall_moss", new StructureWallMoss(), StructurePlacementType.WALL, 0.8F, true);
        addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructurePlacementType.WALL, 0.8F, true);
        addStructure("wall_brown_mushroom", new StructureWallBrownMushroom(), StructurePlacementType.WALL, 0.8F, true);
    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
        double value = TERRAIN.eval(pos.getX() * 0.2, pos.getY() * 0.2, pos.getZ() * 0.2);
        if (value > 0.3 && validWalls(world, pos))
            BlocksHelper.setWithoutUpdate(world, pos, Blocks.LAVA.defaultBlockState());
        else if (value > -0.3)
            BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.SWAMPLAND_GRASS.defaultBlockState());
        else {
            value = TERRAIN.eval(pos.getX() * 0.5, pos.getZ() * 0.5);
            BlocksHelper.setWithoutUpdate(world,
                    pos,
                    value > 0
                            ? Blocks.SOUL_SAND.defaultBlockState()
                            : Blocks.SOUL_SOIL.defaultBlockState());
        }
    }

    protected boolean validWalls(LevelAccessor world, BlockPos pos) {
        return validWall(world, pos.below())
                && validWall(world, pos.north())
                && validWall(world, pos.south())
                && validWall(world, pos.east())
                && validWall(world, pos.west());
    }

    protected boolean validWall(LevelAccessor world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return org.betterx.bclib.util.BlocksHelper.isLava(state) || BlocksHelper.isNetherGround(state);
    }
}