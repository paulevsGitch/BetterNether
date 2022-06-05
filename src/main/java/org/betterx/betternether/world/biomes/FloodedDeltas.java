package org.betterx.betternether.world.biomes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import org.betterx.betternether.MHelper;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

public class FloodedDeltas extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(104, 95, 112)
                   .loop(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
                   .additions(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_BASALT_DELTAS)
                   .particles(ParticleTypes.WHITE_ASH, 0.12F)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS)
                   .feature(NetherFeatures.STALAGNATE_BLACKSTONE_CLUSTER)
                   .feature(NetherFeatures.STALAGNATE_BASALT_CLUSTER)
                   .genChance(0.3f)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return FloodedDeltas::new;
        }

        @Override
        public boolean hasStalactites() {
            return false;
        }
    }


    public FloodedDeltas(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {

    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
        final MutableBlockPos POS = new MutableBlockPos();
        POS.set(pos);
        int d = MHelper.randRange(2, 4, random);
        BlockState state = isLavaValid(world, pos)
                ? Blocks.LAVA.defaultBlockState()
                : (random.nextInt(16) > 0 ? Blocks.BASALT.defaultBlockState() : Blocks.AIR.defaultBlockState());
        BlocksHelper.setWithoutUpdate(world, POS, state);
        if (state.getBlock() == Blocks.LAVA)
            world.getChunk(pos.getX() >> 4, pos.getZ() >> 4)
                 .markPosForPostprocessing(POS.set(pos.getX() & 15, pos.getY(), pos.getZ() & 15));
        POS.set(pos);
        for (int h = 1; h < d; h++) {
            POS.setY(pos.getY() - h);
            if (BlocksHelper.isNetherGround(world.getBlockState(POS)))
                BlocksHelper.setWithoutUpdate(world, POS, Blocks.BASALT.defaultBlockState());
            else
                break;
        }
    }

    protected boolean isLavaValid(LevelAccessor world, BlockPos pos) {
        return validWall(world, pos.below()) &&
                validWall(world, pos.north()) &&
                validWall(world, pos.south()) &&
                validWall(world, pos.east()) &&
                validWall(world, pos.west());
    }

    protected boolean validWall(LevelAccessor world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return org.betterx.bclib.util.BlocksHelper.isLava(state) || state.isCollisionShapeFullBlock(world, pos);
    }
}
