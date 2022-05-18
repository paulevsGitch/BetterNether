package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.materials.Materials;

public class BlockEyeball extends BlockEyeBase {
    public BlockEyeball() {
        super(FabricBlockSettings.of(Materials.NETHER_PLANT)
                                 .mapColor(MaterialColor.COLOR_BROWN)
                                 .sounds(SoundType.SLIME_BLOCK)
                                 .hardness(0.5F)
                                 .resistance(0.5F)
                                 .randomTicks());
    }

    @Environment(EnvType.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble() * 0.3;
            double z = pos.getZ() + random.nextDouble();
            world.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0, 0, 0);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextInt(64) == 0) {
            int y = BlocksHelper.downRay(world, pos, 64) + 1;
            BlockPos down = pos.below(y);
            BlockState cauldron = world.getBlockState(down);
            if (cauldron.getBlock() == Blocks.CAULDRON) {
                int level = cauldron.getValue(LayeredCauldronBlock.LEVEL);
                if (level < 3) {
                    world.setBlockAndUpdate(down, cauldron.setValue(LayeredCauldronBlock.LEVEL, level + 1));
                }
            }
        }
    }
}
