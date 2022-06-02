package org.betterx.betternether.world.biomes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

public class NetherMagmaLand extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(248, 158, 68)
                   .loop(SoundEvents.AMBIENT_NETHER_WASTES_LOOP)
                   .additions(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_NETHER_WASTES_MOOD)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS)
                   .feature(NetherFeatures.LAVA_BLOBS)
                   .feature(NetherFeatures.MAGMA_BLOBS)
                   .feature(NetherFeatures.CRYSTAL_FATURE)
                   .feature(NetherFeatures.GOLDEN_VINE)
                   .feature(NetherFeatures.GEYSER)
                   .feature(NetherFeatures.MAGMA_FLOWER)
            ;
        }


        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherMagmaLand::new;
        }
    }

    private static final boolean[] MASK;

    public NetherMagmaLand(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
        if (isMask(pos.getX(), pos.getZ())) {
            final MutableBlockPos POS = new MutableBlockPos();
            POS.set(pos);
            boolean magma = true;
            if (random.nextInt(4) == 0) {
                if (validWall(world, POS.below()) && validWall(world, POS.north()) && validWall(world,
                        POS.south()) && validWall(
                        world,
                        POS.east()) && validWall(world, POS.west())) {
                    BlocksHelper.setWithoutUpdate(world, POS, Blocks.LAVA.defaultBlockState());
                    magma = false;
                }
            }
            if (magma)
                for (int y = 0; y < random.nextInt(3) + 1; y++) {
                    POS.setY(pos.getY() - y);
                    if (BlocksHelper.isNetherGround(world.getBlockState(POS)))
                        BlocksHelper.setWithoutUpdate(world, POS, Blocks.MAGMA_BLOCK.defaultBlockState());
                }
        } else
            super.genSurfColumn(world, pos, random);
    }

    protected boolean validWall(LevelAccessor world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return org.betterx.bclib.util.BlocksHelper.isLava(state) || BlocksHelper.isNetherGroundMagma(state);
    }

    protected boolean isMask(int x, int z) {
        x &= 15;
        z &= 15;
        return MASK[(x << 4) | z];
    }

    static {
        MASK = new boolean[]{
                false,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                true,
                true,
                false,
                false,
                true,
                true,
                true,
                false,
                false,
                false,
                true,
                true,
                false,
                false,
                false,
                true,
                false,
                false,
                true,
                true,
                true,
                false,
                false,
                true,
                true,
                true,
                true,
                false,
                true,
                true,
                true,
                true,
                false,
                false,
                false,
                true,
                true,
                false,
                false,
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                true,
                true,
                false,
                false,
                false,
                true,
                true,
                true,
                true,
                false,
                false,
                false,
                true,
                true,
                false,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                false,
                false,
                true,
                true,
                false,
                true,
                true,
                false,
                false,
                false,
                true,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                true,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                true,
                false,
                true,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                true,
                false,
                true,
                true,
                false,
                false,
                true,
                false,
                false,
                false,
                true,
                true,
                true,
                true,
                true,
                true,
                false,
                true,
                false,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                true,
                true,
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                false,
                false
        };
    }
}
