package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.betterx.betternether.MHelper;

public class BlockNetherSakuraLeaves extends BNLeaves {
    private static final RandomSource RANDOM = new LegacyRandomSource(130520220101l);
    private static final int COLOR = MHelper.color(251, 113, 143);

    public BlockNetherSakuraLeaves(Block sapling) {
        super(sapling, MaterialColor.COLOR_PINK, (settings) -> settings.lightLevel((state) -> {
            return 13;
        }));
    }

    @Environment(EnvType.CLIENT)
    public float getShadeBrightness(BlockState state, BlockGetter view, BlockPos pos) {
        return super.getShadeBrightness(state, view, pos) * 0.5F + 0.5F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter view, BlockPos pos) {
        return true;
    }

    @Environment(EnvType.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) == 0) {
            BlockPos blockPos = pos.below();
            if (world.isEmptyBlock(blockPos)) {
                double x = (double) pos.getX() + random.nextDouble();
                double y = (double) pos.getY() - 0.05D;
                double z = (double) pos.getZ() + random.nextDouble();
                world.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state),
                                  x,
                                  y,
                                  z,
                                  0.0D,
                                  0.0D,
                                  0.0D);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public int getColor(BlockState state, BlockGetter world, BlockPos pos) {
        return COLOR;
    }
}