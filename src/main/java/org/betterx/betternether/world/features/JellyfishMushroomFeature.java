package org.betterx.betternether.world.features;

import org.betterx.bclib.api.v2.levelgen.features.UserGrowableFeature;
import org.betterx.bclib.blocks.BlockProperties.TripleShape;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BNBlockProperties;
import org.betterx.betternether.blocks.BlockJellyfishMushroom;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class JellyfishMushroomFeature extends ContextFeature<NoneFeatureConfiguration> implements UserGrowableFeature<NoneFeatureConfiguration> {
    public JellyfishMushroomFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    protected boolean place(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            NoneFeatureConfiguration config,
            int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        if (world.isEmptyBlock(pos) && world.getBlockState(pos.below()).is(BlockTags.NYLIUM)) {
            grow(world, pos, random);
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
        BNBlockProperties.JellyShape visual = down == NetherBlocks.MUSHROOM_GRASS
                ? BNBlockProperties.JellyShape.NORMAL
                : down == NetherBlocks.SEPIA_MUSHROOM_GRASS
                        ? BNBlockProperties.JellyShape.SEPIA
                        : BNBlockProperties.JellyShape.POOR;
        BlocksHelper.setWithUpdate(world, pos, NetherBlocks.JELLYFISH_MUSHROOM.defaultBlockState()
                                                                              .setValue(
                                                                                      BlockJellyfishMushroom.SHAPE,
                                                                                      TripleShape.BOTTOM
                                                                              )
                                                                              .setValue(
                                                                                      BlockJellyfishMushroom.VISUAL,
                                                                                      visual
                                                                              ));
    }

    public void growMedium(ServerLevelAccessor world, BlockPos pos) {
        Block down = world.getBlockState(pos.below()).getBlock();
        BNBlockProperties.JellyShape visual = down == NetherBlocks.MUSHROOM_GRASS
                ? BNBlockProperties.JellyShape.NORMAL
                : down == NetherBlocks.SEPIA_MUSHROOM_GRASS
                        ? BNBlockProperties.JellyShape.SEPIA
                        : BNBlockProperties.JellyShape.POOR;
        BlocksHelper.setWithUpdate(
                world,
                pos,
                NetherBlocks.JELLYFISH_MUSHROOM.defaultBlockState()
                                               .setValue(
                                                       BlockJellyfishMushroom.SHAPE,
                                                       TripleShape.MIDDLE
                                               )
                                               .setValue(BlockJellyfishMushroom.VISUAL, visual)
        );
        BlocksHelper.setWithUpdate(
                world,
                pos.above(),
                NetherBlocks.JELLYFISH_MUSHROOM.defaultBlockState()
                                               .setValue(
                                                       BlockJellyfishMushroom.SHAPE,
                                                       TripleShape.TOP
                                               )
                                               .setValue(BlockJellyfishMushroom.VISUAL, visual)
        );
    }

    @Override
    public boolean grow(
            ServerLevelAccessor level,
            BlockPos pos,
            RandomSource random,
            NoneFeatureConfiguration configuration
    ) {
        grow(level, pos, random);
        return true;
    }
}
