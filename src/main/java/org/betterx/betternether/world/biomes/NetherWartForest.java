package org.betterx.betternether.world.biomes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.v2.levelgen.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.v2.levelgen.surface.rules.Conditions;
import org.betterx.bclib.api.v2.levelgen.surface.rules.SwitchRuleSource;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.blocks.BlockSoulSandstone;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.registry.features.BiomeFeatures;
import org.betterx.betternether.registry.features.TerrainFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

import java.util.List;

public class NetherWartForest extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(151, 6, 6)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .particles(ParticleTypes.CRIMSON_SPORE, 0.05F)
                   .feature(NetherFeatures.NETHER_RUBY_ORE)
                   .feature(TerrainFeatures.NO_SURFACE_SANDSTONE)
                   .feature(BiomeFeatures.NETHER_WART_FORREST_FLOOR)
                   .feature(BiomeFeatures.NETHER_WART_FORREST_CEIL)
                   .edgeSize(9)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherWartForest::new;
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

        @Override
        public SurfaceRuleBuilder surface() {
            return super
                    .surface()
                    .rule(SurfaceRules.sequence(
                            SurfaceRules.ifTrue(
                                    SurfaceRules.ON_FLOOR,
                                    new SwitchRuleSource(Conditions.NETHER_NOISE,
                                            List.of(NetherGrasslands.SOUL_SOIL,
                                                    NetherGrasslands.SOUL_SAND,
                                                    NetherGrasslands.MOSS,
                                                    NetherGrasslands.SOUL_SAND,
                                                    NETHERRACK))
                            ),
                            SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(4, true, 1, CaveSurface.FLOOR),
                                    new SwitchRuleSource(Conditions.NETHER_NOISE,
                                            List.of(SurfaceRules.state(
                                                            NetherBlocks.SOUL_SANDSTONE
                                                                    .defaultBlockState()
                                                                    .setValue(BlockSoulSandstone.UP, true)
                                                    ),
                                                    SurfaceRules.state(
                                                            NetherBlocks.SOUL_SANDSTONE
                                                                    .defaultBlockState()
                                                                    .setValue(BlockSoulSandstone.UP, false)
                                                    )
                                            )
                                    )
                            ),
                            new SwitchRuleSource(Conditions.NETHER_NOISE,
                                    List.of(NetherGrasslands.SOUL_SOIL,
                                            NetherGrasslands.SOUL_SAND,
                                            NETHERRACK))
                    ))
                    ;
        }
    }

    public NetherWartForest(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
        final MutableBlockPos POS = new MutableBlockPos();
        switch (random.nextInt(4)) {
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

        int d1 = MHelper.randRange(2, 4, random);
        POS.setX(pos.getX());
        POS.setZ(pos.getZ());

        for (int i = 1; i < d1; i++) {
            POS.setY(pos.getY() - i);
            if (BlocksHelper.isNetherGround(world.getBlockState(POS))) {
                switch (random.nextInt(3)) {
                    case 0:
                        BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SAND.defaultBlockState());
                        break;
                    case 1:
                        BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SOIL.defaultBlockState());
                        break;
                    case 2:
                        BlocksHelper.setWithoutUpdate(world, pos, Blocks.NETHERRACK.defaultBlockState());
                        break;
                }
            } else
                return;
        }

        int d2 = MHelper.randRange(5, 7, random);
        for (int i = d1; i < d2; i++) {
            POS.setY(pos.getY() - i);
            if (BlocksHelper.isNetherGround(world.getBlockState(POS)))
                BlocksHelper.setWithoutUpdate(world, POS, NetherBlocks.SOUL_SANDSTONE.defaultBlockState().setValue(
                        BlockSoulSandstone.UP, i == d1));
            else
                return;
        }
    }
}
