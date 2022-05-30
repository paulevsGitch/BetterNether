package org.betterx.betternether.world.biomes;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.betternether.world.structures.plants.*;

public class NetherPoorGrasslands extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(113, 73, 133)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .structure(BiomeTags.HAS_BASTION_REMNANT)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS)
                   .genChance(0.3F);
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherPoorGrasslands::new;
        }
    }

    public NetherPoorGrasslands(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
        addStructure("nether_reed", new StructureReeds(), StructurePlacementType.FLOOR, 0.05F, false);
        addStructure("nether_wart", new StructureNetherWart(), StructurePlacementType.FLOOR, 0.005F, true);
        addStructure("magma_flower", new StructureMagmaFlower(), StructurePlacementType.FLOOR, 0.05F, true);
        addStructure("smoker", new StructureSmoker(), StructurePlacementType.FLOOR, 0.005F, true);
        addStructure("ink_bush", new StructureInkBush(), StructurePlacementType.FLOOR, 0.005F, true);
        addStructure("black_apple", new StructureBlackApple(), StructurePlacementType.FLOOR, 0.001F, true);
        addStructure("black_bush", new StructureBlackBush(), StructurePlacementType.FLOOR, 0.002F, true);
        addStructure("wart_seed", new StructureWartSeed(), StructurePlacementType.FLOOR, 0.002F, true);
        addStructure("nether_grass", new StructureNetherGrass(), StructurePlacementType.FLOOR, 0.04F, true);
    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
        switch (random.nextInt(3)) {
            case 0:
                BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SOIL.defaultBlockState());
                break;
            case 1:
                BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
                break;
            default:
                super.genSurfColumn(world, pos, random);
                break;
        }
        for (int i = 1; i < random.nextInt(3); i++) {
            BlockPos down = pos.below(i);
            if (random.nextInt(3) == 0 && BlocksHelper.isNetherGround(world.getBlockState(down))) {
                BlocksHelper.setWithoutUpdate(world, down, Blocks.SOUL_SAND.defaultBlockState());
            }
        }
    }
}