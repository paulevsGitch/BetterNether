package org.betterx.betternether.world.biomes;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.betternether.registry.features.TerrainFeatures;
import org.betterx.betternether.world.NetherBiome;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

public class NetherSwamplandTerraces extends NetherSwampland {
    public static class Config extends NetherSwampland.Config {
        public Config(String name) {
            super(name);
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherSwamplandTerraces::new;
        }

        @Override
        protected void addCustomSwamplandBuildData(BCLBiomeBuilder builder) {
            builder.feature(TerrainFeatures.LAVA_TERRACE);
        }
    }

    public NetherSwamplandTerraces(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
//        if (validWall(world, pos.below()) && validWall(world, pos.north()) && validWall(world,
//                                                                                        pos.south()) && validWall(world,
//                                                                                                                  pos.east()) && validWall(
//                world,
//                pos.west())) {
//            BlocksHelper.setWithoutUpdate(world, pos, Blocks.LAVA.defaultBlockState());
//        } else {
//            double value = TERRAIN.eval(pos.getX() * 0.2, pos.getY() * 0.2, pos.getZ() * 0.2);
//            if (value > -0.3)
//                BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.SWAMPLAND_GRASS.defaultBlockState());
//            else {
//                value = TERRAIN.eval(pos.getX() * 0.5, pos.getZ() * 0.5);
//                BlocksHelper.setWithoutUpdate(world,
//                                              pos,
//                                              value > 0
//                                                      ? Blocks.SOUL_SAND.defaultBlockState()
//                                                      : Blocks.SOUL_SOIL.defaultBlockState());
//            }
//        }
    }
}
