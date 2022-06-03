package org.betterx.betternether.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockJellyfishMushroom;
import org.betterx.betternether.blocks.BlockProperties;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class JellyfishMushroomFeature extends ContextFeature<NoneFeatureConfiguration> {
    public JellyfishMushroomFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    boolean place(ServerLevelAccessor world,
                  BlockPos pos,
                  RandomSource random,
                  int MAX_HEIGHT,
                  StructureGeneratorThreadContext context) {

        final float scale_factor = MAX_HEIGHT / 128.0f;
        final int RANDOM_BOUND = (int) (6 * scale_factor);

        BlockState under;
        if (world.getBlockState(pos.below()).is(BlockTags.NYLIUM)) {
            for (int i = 0; i < 10; i++) {
                int x = pos.getX() + (int) (random.nextGaussian() * 2);
                int z = pos.getZ() + (int) (random.nextGaussian() * 2);
                int y = pos.getY() + random.nextInt(RANDOM_BOUND);
                for (int j = 0; j < RANDOM_BOUND; j++) {
                    context.POS.set(x, y - j, z);
                    if (context.POS.getY() > 32) {
                        under = world.getBlockState(context.POS.below());
                        if (under.is(BlockTags.NYLIUM) && world.isEmptyBlock(context.POS)) {
                            grow(world, context.POS, random);
                        }
                    } else
                        break;
                }
            }
            return true;
        }
        return false;
    }

    public void grow(ServerLevelAccessor world, BlockPos pos, RandomSource random) {
        if (random.nextBoolean() && world.isEmptyBlock(pos.above()))
            growMedium(world, pos);
        else
            growSmall(world, pos);
    }

    public void growSmall(ServerLevelAccessor world, BlockPos pos) {
        Block down = world.getBlockState(pos.below()).getBlock();
        BlockProperties.JellyShape visual = down == NetherBlocks.MUSHROOM_GRASS
                ? BlockProperties.JellyShape.NORMAL
                : down == NetherBlocks.SEPIA_MUSHROOM_GRASS
                        ? BlockProperties.JellyShape.SEPIA
                        : BlockProperties.JellyShape.POOR;
        BlocksHelper.setWithUpdate(world, pos, NetherBlocks.JELLYFISH_MUSHROOM.defaultBlockState()
                                                                              .setValue(
                                                                                      BlockJellyfishMushroom.SHAPE,
                                                                                      BlockProperties.TripleShape.BOTTOM)
                                                                              .setValue(BlockJellyfishMushroom.VISUAL,
                                                                                      visual));
    }

    public void growMedium(ServerLevelAccessor world, BlockPos pos) {
        Block down = world.getBlockState(pos.below()).getBlock();
        BlockProperties.JellyShape visual = down == NetherBlocks.MUSHROOM_GRASS
                ? BlockProperties.JellyShape.NORMAL
                : down == NetherBlocks.SEPIA_MUSHROOM_GRASS
                        ? BlockProperties.JellyShape.SEPIA
                        : BlockProperties.JellyShape.POOR;
        BlocksHelper.setWithUpdate(world,
                pos,
                NetherBlocks.JELLYFISH_MUSHROOM.defaultBlockState()
                                               .setValue(BlockJellyfishMushroom.SHAPE,
                                                       BlockProperties.TripleShape.MIDDLE)
                                               .setValue(BlockJellyfishMushroom.VISUAL, visual));
        BlocksHelper.setWithUpdate(world,
                pos.above(),
                NetherBlocks.JELLYFISH_MUSHROOM.defaultBlockState()
                                               .setValue(BlockJellyfishMushroom.SHAPE,
                                                       BlockProperties.TripleShape.TOP)
                                               .setValue(BlockJellyfishMushroom.VISUAL, visual));
    }
}
