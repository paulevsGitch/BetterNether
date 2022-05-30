package org.betterx.betternether.world.biomes;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.betternether.world.structures.plants.StructureBlackBush;
import org.betterx.betternether.world.structures.plants.StructureNetherWart;
import org.betterx.betternether.world.structures.plants.StructureWartSeed;

public class NetherWartForestEdge extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(191, 28, 28)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .feature(NetherFeatures.NETHER_RUBY_ORE);
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherWartForestEdge::new;
        }

        @Override
        public <M extends Mob> int spawnWeight(NetherEntities.KnownSpawnTypes type) {
            int res = super.spawnWeight(type);
            switch (type) {
                case FLYING_PIG -> res = type.weight;
                case NAGA -> res = 0;
            }
            return res;
        }
    }

    public NetherWartForestEdge(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
        addStructure("nether_wart", new StructureNetherWart(), StructurePlacementType.FLOOR, 0.02F, false);
        addStructure("wart_seed", new StructureWartSeed(), StructurePlacementType.FLOOR, 0.01F, false);
        addStructure("black_bush", new StructureBlackBush(), StructurePlacementType.FLOOR, 0.01F, false);
    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
        switch (random.nextInt(3)) {
            case 0:
                super.genSurfColumn(world, pos, random);
                break;
            case 1:
                BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SAND.defaultBlockState());
                break;
            case 2:
                BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SOIL.defaultBlockState());
                break;
            case 3:
                BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
                break;
        }
        for (int i = 1; i < random.nextInt(3); i++) {
            BlockPos down = pos.below(i);
            if (random.nextInt(3) == 0 && BlocksHelper.isNetherGround(world.getBlockState(down)))
                BlocksHelper.setWithoutUpdate(world, down, Blocks.SOUL_SAND.defaultBlockState());
        }
    }
}